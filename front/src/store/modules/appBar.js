export default {
  namespaced: true,
  state: {
    myPageButton: false,
    backButton: false,
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
      state.title = state.address;
      state.searchButton = true;
    },
    MAP_PAGE_MARKER_MODE(state) {
      state.myPageButton = false;
      state.backButton = true;
      state.searchButton = false;
    },
    POST_DETAIL_PAGE(state) {
      state.myPageButton = false;
      state.backButton = true;
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
    searchButton: (state) => {
      return state.searchButton;
    },
    title: (state) => {
      return state.title;
    },
  },
};
