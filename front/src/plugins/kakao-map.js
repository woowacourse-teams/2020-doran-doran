import { KAKAO_MAP_APP_KEY } from "../secure/appkey";

const KakaoMap = {
  install(Vue) {
    const script = document.createElement("script");
    script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${KAKAO_MAP_APP_KEY}`;
    document.head.appendChild(script);

    /* global kakao */
    const loadApi = new Promise((resolve) => {
      script.onload = () => kakao.maps.load(resolve);
    });

    Vue.prototype.$drawMap = async (mapContainer) => {
      await loadApi;
      const options = {
        center: new kakao.maps.LatLng(36.5152, 127.103),
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
        .catch(() => alert("위치 정보를 확인할 수 없습니다."));
    };

    Vue.prototype.$setLocation = (location) => {
      if (!this.map || !location) {
        return;
      }
      const target = new kakao.maps.LatLng(
        location.latitude,
        location.longitude,
      );
      this.map.setCenter(target);
    };
  },
};

export default KakaoMap;
