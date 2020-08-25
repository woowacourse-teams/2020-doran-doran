const ERROR_MESSAGE = {
  UNIDENTIFIABLE_LOCATION: "😢 위치 정보를 확인할 수 없습니다.",
  NO_CONTENT_MESSAGE: "🤔 내용을 입력해주세요.",
  LOGIN_REQUIRED: "🔑 로그인이 필요해요.",
};

const API_BASE_URL = {
  EC2: "//dorandoran.io:8080",
};

const EVENT_TYPE = {
  CENTER_CHANGE: "center_changed",
};

const MAP_MODE = {
  DEFAULT: "default",
  MARKER: "marker",
  POST: "post",
};

const DORAN_DORAN_COLORS = {
  POINT_COLOR: "amber accent-3",
  LIKE_COLOR: "red accent-3",
};

const LIKE_BUTTON_TYPE = {
  DEFAULT: {
    color: "",
    icon: "mdi-heart-outline",
  },
  LIKED: {
    color: DORAN_DORAN_COLORS.LIKE_COLOR,
    icon: "mdi-heart",
  },
};

export {
  ERROR_MESSAGE,
  API_BASE_URL,
  EVENT_TYPE,
  MAP_MODE,
  DORAN_DORAN_COLORS,
  LIKE_BUTTON_TYPE,
};
