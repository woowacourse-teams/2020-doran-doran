import { KAKAO_MAP_APP_KEY } from "@/secure/appkey";
import INITIAL_LOCATION from "@/config/config";
import { POST_OVERLAY_TEMPLATES } from "@/utils/template";

const KAKAO_MAP_URL = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=";
const LIBRARY = "&libraries=services,clusterer";

const KakaoMap = {
  install(Vue) {
    const script = document.createElement("script");
    script.src = KAKAO_MAP_URL + KAKAO_MAP_APP_KEY + LIBRARY;
    document.head.appendChild(script);

    const postOverlays = [];

    /* global kakao */
    const loadApi = new Promise((resolve) => {
      script.onload = () => kakao.maps.load(resolve);
    });

    const createKakaoLocation = (location) => {
      return new kakao.maps.LatLng(location.latitude, location.longitude);
    };

    Vue.prototype.$drawMap = async (mapContainer) => {
      await loadApi;
      const options = {
        center: createKakaoLocation(INITIAL_LOCATION.JAMSIL_LUTHER),
        level: 2,
      };
      this.map = new kakao.maps.Map(mapContainer, options);
      this.clusterer = createClusterer();
    };

    const createClusterer = () => {
      return new kakao.maps.MarkerClusterer({
        map: this.map,
        averageCenter: true,
        minLevel: 3,
        calculator: [3, 7, 11, 15],
      });
    }

    Vue.prototype.$getCurrentLocation = async () => {
      const getLocation = () =>
        new Promise((resolve, reject) =>
          navigator.geolocation.getCurrentPosition(resolve, reject, {
            timeout: 1000,
          }),
        );
      return await getLocation()
        .then((location) => {
          return {
            latitude: location.coords.latitude,
            longitude: location.coords.longitude,
          };
        })
        .catch((e) => console.log(e));
    };

    Vue.prototype.$getCenterLocation = () => {
      const center = this.map.getCenter();
      return {
        longitude: center.getLng(),
        latitude: center.getLat(),
      };
    };

    Vue.prototype.$setLocation = (location) => {
      if (!this.map || !location) {
        return;
      }
      const targetLocation = createKakaoLocation(location);
      this.map.setCenter(targetLocation);
    };

    const createMarkerImage = (src, size, options) => {
      return new kakao.maps.MarkerImage(src, size, options);
    };

    const createMarker = (position, image) => {
      return new kakao.maps.Marker({
        position: position,
        image: image,
      });
    };

    Vue.prototype.$setCurrentLocationMarker = (currentLocation) => {
      if (!this.map || !currentLocation) {
        return;
      }
      const currentLocationImage =
        "https://sheengroup.com.au/assets/Uploads/misc/current-location.png";
      const markerSize = new kakao.maps.Size(36, 36);
      const markerImage = createMarkerImage(currentLocationImage, markerSize);

      const currentKakaoLocation = createKakaoLocation(currentLocation);
      const marker = createMarker(currentKakaoLocation, markerImage);
      marker.setMap(this.map);
    };

    Vue.prototype.$setPostOverlay = (post) => {
      const kakaoLocation = createKakaoLocation(post.location);

      const customOverlay = new kakao.maps.CustomOverlay({
        position: kakaoLocation,
        content: POST_OVERLAY_TEMPLATES(post),
      });
      customOverlay.setMap(this.map);
      this.clusterer.addMarker(customOverlay);

      postOverlays.push(customOverlay);
    };

    Vue.prototype.$closePostOverlays = () => {
      postOverlays.forEach((overlay) => {
        overlay.setMap(null);
      });
    };

    Vue.prototype.$showPostOverlays = () => {
      postOverlays.forEach((overlay) => {
        overlay.setMap(this.map);
      });
    };

    Vue.prototype.$getAddress = async (location) => {
      const geocoder = new kakao.maps.services.Geocoder();

      return await new Promise((resolve) =>
        geocoder.coord2RegionCode(
          location.longitude,
          location.latitude,
          resolve,
        ),
      ).then((result) => {
        return {
          depth1: result[1].region_1depth_name,
          depth2: result[1].region_2depth_name,
          depth3: result[1].region_3depth_name,
        };
      });
    };

    Vue.prototype.$addEventToMap = (eventType, func) => {
      this.map.addListener(eventType, func);
    };
  },
};

export default KakaoMap;
