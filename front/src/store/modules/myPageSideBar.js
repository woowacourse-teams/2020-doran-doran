export default {
  namespaced: true,
  state: {
    sideBarMode: false,
  },
  mutations: {
    SET_SIDE_BAR(state, val) {
      state.sideBarMode = val;
    },
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
