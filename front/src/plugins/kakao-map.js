import { KAKAO_MAP_APP_KEY } from "../secure/appkey";
import INITIAL_LOCATION from "../config/config";
import { API_BASE_URL, ERROR_MESSAGE, EVENT_TYPE } from "../utils/constants";

const KakaoMap = {
  install(Vue) {
    const script = document.createElement("script");
    script.src = API_BASE_URL.KAKAO_MAP + KAKAO_MAP_APP_KEY;
    document.head.appendChild(script);

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
    };

    Vue.prototype.$getCurrentLocation = async () => {
      const getLocation = () =>
        new Promise((resolve, reject) =>
          navigator.geolocation.getCurrentPosition(resolve, reject, {
            timeout: 3000,
          }),
        );
      return await getLocation()
        .then((location) => {
          return {
            latitude: location.coords.latitude,
            longitude: location.coords.longitude,
          };
        })
        .catch(() => alert(ERROR_MESSAGE.UNIDENTIFIABLE_LOCATION));
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

    Vue.prototype.$setPostOverlay = (post, router) => {
      const kakaoLocation = createKakaoLocation(post.location);
      const overlay = document.createElement("div");
      overlay.classList.add("speech-bubble");
      overlay.innerHTML = `
        <div class="speech-arrow"></div>
        ${post.content}
      `;
      const toDetailPage = () =>
        router.push({
          name: "detailPage",
          params: {
            id: post.id,
          },
        });
      overlay.addEventListener(EVENT_TYPE.CLICK, toDetailPage);

      const customOverlay = new kakao.maps.CustomOverlay({
        position: kakaoLocation,
        content: overlay,
      });
      customOverlay.setMap(this.map);
    };
  },
};

export default KakaoMap;
