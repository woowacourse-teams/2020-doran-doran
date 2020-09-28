const ERROR_MESSAGE = {
  UNIDENTIFIABLE_LOCATION: "😢 위치 정보를 확인할 수 없습니다.",
  NO_CONTENT_MESSAGE: "🤔 내용을 입력해주세요.",
  NO_POST_MESSAGE: "😭 해당 기간에 글이 존재하지 않습니다.",
  NO_KEYWORD_INPUT: "🧐 키워드를 입력해주세요.",
  NO_SEARCH_RESULT_MESSAGE: "😭 해당 키워드의 검색 결과가 없습니다.",
  INVALID_USER_DATE_INPUT: "😨 시작 날짜는 종료 날짜보다 뒤일 수 없습니다.",
  LOGIN_REQUIRED: "🔑 로그인이 필요해요.",
};

const API_BASE_URL = {
  EC2: process.env.VUE_APP_EC2,
};

const EVENT_TYPE = {
  CENTER_CHANGE: "center_changed",
  CLICK: "click",
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
