export default {
  namespaced: true,
  state: {
    snackbarMessage: "",
    snackbarWarning: false,
  },
  mutations: {
    SHOW_SNACKBAR(state, message) {
      state.snackbarMessage = message;
      state.snackbarWarning = true;
    },
    SET_SNACKBAR_MESSAGE(state, val) {
      state.snackbarMessage = val;
    },
    SET_SNACKBAR_WARNING(state, val) {
      state.snackbarWarning = val;
    },
  },
  getters: {
    snackbarMessage(state) {
      return state.snackbarMessage;
    },
    snackbarWarning(state) {
      return state.snackbarWarning;
    },
  },
};
