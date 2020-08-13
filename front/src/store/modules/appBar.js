export default {
  namespaced: true,
  state: {
    backButton: false,
    myPageButton: false,
    title: "",
    timelineButton: false,
    mapButton: false,
  },
  mutations: {
    CHANGE_ADDRESS(state, newAddress) {
      state.title = newAddress;
    },
    MAP_PAGE_DEFAULT_MODE(state) {
      state.backButton = false;
      state.myPageButton = true;
      state.title = "";
      state.timelineButton = true;
      state.mapButton = false;
    },
    MAP_PAGE_NON_DEFAULT_MODE(state) {
      state.backButton = false;
      state.myPageButton = false;
      state.title = "";
      state.timelineButton = false;
      state.mapButton = false;
    },
  },
  getters: {
    backButton: (state) => {
      return state.backButton;
    },
    myPageButton: (state) => {
      return state.myPageButton;
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
