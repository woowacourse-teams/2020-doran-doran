const ERROR_MESSAGE = {
  UNIDENTIFIABLE_LOCATION: "위치 정보를 확인할 수 없습니다.",
};

const API_BASE_URL = {
  KAKAO_MAP: "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=",
  EC2: "//woowacourse.com:8080",
  LIBRARY: "&libraries="
};

const KAKAO_MAP_LIBRARY = {
  SERVICE: "services",
}

const EVENT_TYPE = {
  CLICK: "click",
};

export { ERROR_MESSAGE, API_BASE_URL, KAKAO_MAP_LIBRARY, EVENT_TYPE };
