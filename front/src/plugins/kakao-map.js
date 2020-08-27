import { KAKAO_MAP_APP_KEY } from "@/secure/appkey";

const KAKAO_MAP_URL = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=";
const LIBRARY = "&libraries=services,clusterer";

// Location of Luther Hall, Jamsil, Korea
const INITIAL_LOCATION = {
  latitude: 37.515416,
  longitude: 127.103,
};
const CURRENT_MARKER_IMAGE =
  "https://dorandoran.s3.ap-northeast-2.amazonaws.com/project/map/current-location.png";

export const KakaoMap = (() => {
  let map = null;
  let postOverlays = [];
  let clusterer = null;
  let marker = null;

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
      calculator: [4, 8, 16, 32],
      styles: [
        {
          cursor: "pointer",
          width: "52px",
          height: "52px",
          lineHeight: "54px",
          background:
            "url(https://dorandoran.s3.ap-northeast-2.amazonaws.com/project/map/cluster-size-1.png) no-repeat",
          textAlign: "center",
          fontSize: "14px",
          fontWeight: "bold",
        },
        {
          cursor: "pointer",
          width: "56px",
          height: "56px",
          lineHeight: "57px",
          background:
            "url(https://dorandoran.s3.ap-northeast-2.amazonaws.com/project/map/cluster-size-2.png) no-repeat",
          textAlign: "center",
          fontSize: "16px",
          fontWeight: "bold",
        },
        {
          cursor: "pointer",
          width: "66px",
          height: "66px",
          lineHeight: "68px",
          background:
            "url(https://dorandoran.s3.ap-northeast-2.amazonaws.com/project/map/cluster-size-3.png) no-repeat",
          textAlign: "center",
          fontSize: "18px",
          fontWeight: "bold",
        },
        {
          cursor: "pointer",
          width: "78px",
          height: "78px",
          lineHeight: "81px",
          background:
            "url(https://dorandoran.s3.ap-northeast-2.amazonaws.com/project/map/cluster-size-4.png) no-repeat",
          textAlign: "center",
          fontSize: "20px",
          fontWeight: "bold",
        },
        {
          cursor: "pointer",
          width: "90px",
          height: "90px",
          lineHeight: "94px",
          background:
            "url(https://dorandoran.s3.ap-northeast-2.amazonaws.com/project/map/cluster-size-5.png) no-repeat",
          textAlign: "center",
          fontSize: "22px",
          fontWeight: "bold",
        },
      ],
    });
  };

  const drawMap = async (mapContainer) => {
    await loadApi;
    const options = {
      center: _createKakaoLocation(INITIAL_LOCATION),
      level: 2,
    };
    map = new kakao.maps.Map(mapContainer, options);
    postOverlays = [];
    clusterer = _createClusterer();
  };

  const _getGeolocation = () => {
    return new Promise((resolve, reject) =>
      navigator.geolocation.getCurrentPosition(resolve, reject, {
        timeout: 1000,
      }),
    ).catch((e) => {
      console.log(e);
      throw e;
    });
  };

  const getCurrentLocation = async () => {
    const currentLocation = await _getGeolocation().catch((e) => {
      throw e;
    });
    return {
      latitude: currentLocation.coords.latitude,
      longitude: currentLocation.coords.longitude,
    };
  };

  const getCenterLocation = () => {
    if (!map) {
      return;
    }
    const center = map.getCenter();
    return {
      longitude: center.getLng(),
      latitude: center.getLat(),
    };
  };

  const setCenterLocation = (location) => {
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

  const setMarker = (location, img) => {
    if (!map || !location) {
      return;
    }
    const markerSize = new kakao.maps.Size(36, 36);
    const markerImage = _createMarkerImage(img, markerSize);

    const kakaoLocation = _createKakaoLocation(location);
    const newMarker = _createMarker(kakaoLocation, markerImage);
    newMarker.setMap(map);
    return newMarker;
  };

  const setCenterByCurrentLocation = async () => {
    if (!map) {
      return;
    }
    const currentLocation = await getCurrentLocation();
    setCenterLocation(currentLocation);
    if (!marker) {
      marker = setMarker(currentLocation, CURRENT_MARKER_IMAGE);
    } else {
      marker.setPosition(_createKakaoLocation(currentLocation));
    }
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
    if (!map) {
      return;
    }
    const kakaoLocation = _createKakaoLocation(location);
    const customOverlay = _createOverlay(overlayTemplate, kakaoLocation);

    customOverlay.setMap(map);
    clusterer.addMarker(customOverlay);
    postOverlays.push(customOverlay);
  };

  const hidePostOverlays = () => {
    if (postOverlays.length === 0) {
      return;
    }
    clearClusterer();
    postOverlays.forEach((overlay) => {
      overlay.setMap(null);
    });
  };

  const showPostOverlays = () => {
    if (postOverlays.length === 0) {
      return;
    }
    clearClusterer();
    postOverlays.forEach((overlay) => {
      overlay.setMap(map);
      clusterer.addMarker(overlay);
    });
  };

  const clearClusterer = () => {
    clusterer.clear();
  };

  const clearPostOverlay = () => {
    postOverlays = [];
  };

  const _getAdministrativeAddress = (location) => {
    const geocoder = new kakao.maps.services.Geocoder();
    return new Promise((resolve) =>
      geocoder.coord2RegionCode(location.longitude, location.latitude, resolve),
    );
  };

  const getAddress = async (location) => {
    if (!map || !location) {
      return;
    }

    const address = await _getAdministrativeAddress(location);
    return {
      depth1: address[1].region_1depth_name,
      depth2: address[1].region_2depth_name,
      depth3: address[1].region_3depth_name,
    };
  };

  const addEventToMap = (eventType, func) => {
    if (!map) {
      return;
    }

    map.addListener(eventType, func);
  };

  return {
    drawMap,
    getCurrentLocation,
    getCenterLocation,
    setCenterLocation,
    setCenterByCurrentLocation,
    setMarker,
    getBounds,
    setPostOverlay,
    hidePostOverlays,
    showPostOverlays,
    clearClusterer,
    clearPostOverlay,
    getAddress,
    addEventToMap,
  };
})();

export default {
  install(Vue) {
    Vue.prototype.$kakaoMap = KakaoMap;
  },
};
