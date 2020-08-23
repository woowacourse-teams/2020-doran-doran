export default {
  namespaced: true,
  state: {
    visible: false,
  },
  mutations: {
    SET(state, val) {
      state.visible = val;
    },
    SHOW(state) {
      state.visible = true;
    },
    HIDE(state) {
      state.visible = false;
    },
  },
  getters: {
    visible: (state) => {
      return state.visible;
    },
  },
};
