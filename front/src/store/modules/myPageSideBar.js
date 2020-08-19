export default {
  namespaced: true,
  state: {
    sideBarMode: false,
  },
  mutations: {
    ACTIVATE_SIDE_BAR(state) {
      state.sideBarMode = true;
    },
    DEACTIVATE_SIDE_BAR(state) {
      state.sideBarMode = false;
    },
  },
  getters: {
    mode: (state) => {
      return state.sideBarMode;
    },
  },
};
