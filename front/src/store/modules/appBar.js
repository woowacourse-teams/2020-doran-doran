export default {
  namespaced: true,
  state: {
    backButton: false,
    myPageButton: false,

    centerAddress: "",

    timelineButton: false,
    mapButton: false,
  },
  mutations: {
    CHANGE_ADDRESS(state, newAddress) {
      state.centerAddress = newAddress;
    },
    MAP_PAGE_DEFAULT_MODE(state) {
      state.backButton = false;
      state.myPageButton = true;
      state.centerAddress = "";
      state.timelineButton = true;
      state.mapButton = false;
    },
    MAP_PAGE_NON_DEFUALT_MODE(state) {
      state.backButton = false;
      state.myPageButton = false;
      state.centerAddress = "";
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
    centerAddress: (state) => {
      return state.centerAddress;
    },
    timelineButton: (state) => {
      return state.timelineButton;
    },
    mapButton: (state) => {
      return state.mapButton;
    },
  },
};
