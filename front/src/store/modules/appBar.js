export default {
  namespaced: true,
  state: {
    centerAddress: "",
  },
  mutations: {
    CHANGE_ADDRESS(state, value) {
      state.centerAddress = value;
    },
  },
  getters: {
    centerAddress: (state) => {
      return state.centerAddress;
    },
  },
};
