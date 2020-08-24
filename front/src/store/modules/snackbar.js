export default {
  namespaced: true,
  state: {
    message: "",
    visible: false,
  },
  mutations: {
    SHOW(state, message) {
      state.message = message;
      state.visible = true;
    },
    SET_MESSAGE(state, val) {
      state.message = val;
    },
    SET_VISIBLE(state, val) {
      state.visible = val;
    },
  },
  getters: {
    message(state) {
      return state.message;
    },
    visible(state) {
      return state.visible;
    },
  },
};
