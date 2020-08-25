export default {
  namespaced: true,
  state: {
    backButton: false,
    myPageButton: false,
    cancelButton: false,
    title: "",
    searchButton: false,
  },
  mutations: {
    CHANGE_TITLE(state, newTitle) {
      state.title = newTitle;
    },
    MAP_PAGE_DEFAULT_MODE(state) {
      state.backButton = false;
      state.myPageButton = true;
      state.cancelButton = false;
      state.searchButton = true;
    },
    MAP_PAGE_MARKER_MODE(state) {
      state.backButton = false;
      state.myPageButton = false;
      state.cancelButton = true;
      state.searchButton = false;
    },
    TIMELINE_PAGE(state) {
      state.backButton = true;
      state.myPageButton = false;
      state.cancelButton = false;
      state.title = "타임라인";
      state.searchButton = false;
    },
    POST_DETAIL_PAGE(state) {
      state.backButton = true;
      state.myPageButton = false;
      state.cancelButton = false;
      state.title = "게시글";
      state.searchButton = false;
    },
    SEARCH_PAGE(state) {
      state.backButton = true;
      state.myPageButton = false;
      state.cancelButton = false;
      state.title = "검색";
      state.searchButton = false;
    },
    SEARCH_RESULT_PAGE(state) {
      state.backButton = true;
      state.myPageButton = false;
      state.cancelButton = false;
      state.title = "검색 결과";
      state.searchButton = false;
    },
  },
  getters: {
    backButton: (state) => {
      return state.backButton;
    },
    myPageButton: (state) => {
      return state.myPageButton;
    },
    cancelButton: (state) => {
      return state.cancelButton;
    },
    searchButton: (state) => {
      return state.searchButton;
    },
    title: (state) => {
      return state.title;
    },
  },
};
