import { KAKAO_MAP_APP_KEY } from "@/secure/appkey";
import INITIAL_LOCATION from "@/config/config";

const KAKAO_MAP_URL = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=";
const LIBRARY = "&libraries=services,clusterer";

const KakaoMap = (() => {
  let map = null;
  let clusterer = null;
  let postOverlays = [];

  const script = document.createElement("script");
  script.src = KAKAO_MAP_URL + KAKAO_MAP_APP_KEY + LIBRARY;
  document.head.appendChild(script);

  /* global kakao */
  const loadApi = new Promise((resolve) => {
    script.onload = () => kakao.maps.load(resolve);
  });

  const _createKakaoLocation = (location) => {
    return new kakao.maps.LatLng(location.latitude, location.longitude);
  };

  const _createClusterer = () => {
    return new kakao.maps.MarkerClusterer({
      map: map,
      averageCenter: true,
      minLevel: 3,
      calculator: [3, 7, 11, 15],
    });
  };

  const drawMap = async (mapContainer) => {
    await loadApi;
    const options = {
      center: _createKakaoLocation(INITIAL_LOCATION.JAMSIL_LUTHER),
      level: 2,
    };
    map = new kakao.maps.Map(mapContainer, options);
    clusterer = _createClusterer();
  };

  const _getGeolocation = () => {
    return new Promise((resolve, reject) =>
      navigator.geolocation.getCurrentPosition(resolve, reject, {
        timeout: 1000,
      }),
    ).catch(console.log);
  };

  const getCurrentLocation = async () => {
    const currentLocation = await _getGeolocation();
    return {
      latitude: currentLocation.coords.latitude,
      longitude: currentLocation.coords.longitude,
    };
  };

  const getCenterLocation = () => {
    const center = map.getCenter();
    return {
      longitude: center.getLng(),
      latitude: center.getLat(),
    };
  };

  const setLocation = (location) => {
    if (!map || !location) {
      return;
    }
    const targetLocation = _createKakaoLocation(location);
    map.setCenter(targetLocation);
  };

  const _createMarkerImage = (src, size, options) => {
    return new kakao.maps.MarkerImage(src, size, options);
  };

  const _createMarker = (position, image) => {
    return new kakao.maps.Marker({
      position: position,
      image: image,
    });
  };

  const setCurrentLocationMarker = (currentLocation) => {
    if (!map || !currentLocation) {
      return;
    }
    const currentLocationImage =
      "https://sheengroup.com.au/assets/Uploads/misc/current-location.png";
    const markerSize = new kakao.maps.Size(36, 36);
    const markerImage = _createMarkerImage(currentLocationImage, markerSize);

    const currentKakaoLocation = _createKakaoLocation(currentLocation);
    const marker = _createMarker(currentKakaoLocation, markerImage);
    marker.setMap(map);
  };

  const _createOverlay = (content, position) => {
    return new kakao.maps.CustomOverlay({
      content: content,
      position: position,
    });
  };

  const getBounds = () => {
    if (!map) {
      return;
    }
    return map.getBounds();
  };

  const setPostOverlay = (overlayTemplate, location) => {
    const kakaoLocation = _createKakaoLocation(location);
    const customOverlay = _createOverlay(overlayTemplate, kakaoLocation);

    customOverlay.setMap(map);
    clusterer.addMarker(customOverlay);
    postOverlays.push(customOverlay);
  };

  const closePostOverlays = () => {
    postOverlays.forEach((overlay) => {
      overlay.setMap(null);
    });
  };

  const showPostOverlays = () => {
    postOverlays.forEach((overlay) => {
      overlay.setMap(map);
    });
  };

  const _getAdministrativeAddress = (location) => {
    const geocoder = new kakao.maps.services.Geocoder();
    return new Promise((resolve) =>
      geocoder.coord2RegionCode(location.longitude, location.latitude, resolve),
    );
  };

  const getAddress = async (location) => {
    const address = await _getAdministrativeAddress(location);
    return {
      depth1: address[1].region_1depth_name,
      depth2: address[1].region_2depth_name,
      depth3: address[1].region_3depth_name,
    };
  };

  const addEventToMap = (eventType, func) => {
    map.addListener(eventType, func);
  };

  return {
    drawMap,
    getCurrentLocation,
    getCenterLocation,
    setLocation,
    setCurrentLocationMarker,
    getBounds,
    setPostOverlay,
    closePostOverlays,
    showPostOverlays,
    getAddress,
    addEventToMap,
  };
})();

export default {
  install(Vue) {
    Vue.prototype.$kakaoMap = KakaoMap;
  },
};
