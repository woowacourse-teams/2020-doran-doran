export default {
  namespaced: true,
  state: {
    visible: true,
    backButton: false,
    myPageButton: false,
    title: "",
    searchButton: false,
    timelineButton: false,
    mapButton: false,
  },
  mutations: {
    TOGGLE(state) {
      state.visible = !state.visible;
    },
    HIDE(state) {
      state.visible = false;
    },
    CHANGE_TITLE(state, newTitle) {
      state.title = newTitle;
    },
    MAP_PAGE_DEFAULT_MODE(state) {
      state.visible = true;
      state.backButton = false;
      state.myPageButton = true;
      state.searchButton = true;
      state.timelineButton = true;
      state.mapButton = false;
    },
    TIMELINE_PAGE(state) {
      state.visible = true;
      state.backButton = false;
      state.myPageButton = true;
      state.title = "타임라인";
      state.searchButton = true;
      state.timelineButton = false;
      state.mapButton = true;
    },
    POST_DETAIL_PAGE(state) {
      state.visible = true;
      state.backButton = true;
      state.myPageButton = false;
      state.title = "게시글";
      state.searchButton = false;
      state.timelineButton = false;
      state.mapButton = false;
    },
    SEARCH_PAGE(state) {
      state.visible = true;
      state.backButton = true;
      state.myPageButton = false;
      state.title = "검색";
      state.searchButton = false;
      state.timelineButton = false;
      state.mapButton = false;
    },
    SEARCH_RESULT_PAGE(state) {
      state.visible = true;
      state.backButton = true;
      state.myPageButton = false;
      state.title = "검색 결과";
      state.searchButton = false;
      state.timelineButton = false;
      state.mapButton = false;
    },
  },
  getters: {
    visible: (state) => {
      return state.visible;
    },
    backButton: (state) => {
      return state.backButton;
    },
    myPageButton: (state) => {
      return state.myPageButton;
    },
    searchButton: (state) => {
      return state.searchButton;
    },
    title: (state) => {
      return state.title;
    },
    timelineButton: (state) => {
      return state.timelineButton;
    },
    mapButton: (state) => {
      return state.mapButton;
    },
  },
};
