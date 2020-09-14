export default {
  namespaced: true,
  state: {
    myPageButton: false,
    backButton: false,
    cancelButton: false,
    title: "",
    address: "",
    searchButton: false,
  },
  mutations: {
    CHANGE_ADDRESS(state, address) {
      state.address = address;
      state.title = address;
    },
    MAP_PAGE_DEFAULT_MODE(state) {
      state.myPageButton = true;
      state.backButton = false;
      state.cancelButton = false;
      state.title = state.address;
      state.searchButton = true;
    },
    MAP_PAGE_MARKER_MODE(state) {
      state.myPageButton = false;
      state.backButton = false;
      state.cancelButton = true;
      state.searchButton = false;
    },
    POST_DETAIL_PAGE(state) {
      state.myPageButton = false;
      state.backButton = true;
      state.cancelButton = false;
      state.title = "게시글";
      state.searchButton = false;
    },
  },
  getters: {
    myPageButton: (state) => {
      return state.myPageButton;
    },
    backButton: (state) => {
      return state.backButton;
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
