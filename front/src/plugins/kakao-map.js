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
        center: new kakao.maps.LatLng(37.5152, 127.103),
        level: 2,
      };
      this.maps = new kakao.maps.Map(mapContainer, options);
    };
  },
};

export default KakaoMap;
