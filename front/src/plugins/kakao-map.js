import { KAKAO_MAP_APP_KEY } from "../secure/appkey";
import INITIAL_LOCATION from "../config/config";
import { ERROR_MESSAGE, API_BASE_URL } from "../utils/constants";

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
        center: createKakaoLocation(INITIAL_LOCATION),
        level: 2,
      };
      this.map = new kakao.maps.Map(mapContainer, options);
    };

    Vue.prototype.$getCurrentLocation = async () => {
      const getLocation = () =>
        new Promise((resolve, reject) =>
          navigator.geolocation.getCurrentPosition(resolve, reject),
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

    Vue.prototype.$setLocation = (location) => {
      if (!this.map || !location) {
        return;
      }
      const targetLocation = createKakaoLocation(location);
      this.map.setCenter(targetLocation);
    };

    Vue.prototype.$setMarker = (location) => {
      if (!this.map || !location) {
        return;
      }
      const targetLocation = createKakaoLocation(location);
      const marker = new kakao.maps.Marker({
        position: targetLocation,
      });
      marker.setMap(this.map);
    };
  },
};

export default KakaoMap;
