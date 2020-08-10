export default {
  namespaced: true,
  state: {
    centerAddress: "",
  },
  mutations: {
    CHANGE_ADDRESS(state, newAddress) {
      state.centerAddress = newAddress;
    },
  },
  getters: {
    centerAddress: (state) => {
      return state.centerAddress;
    },
  },
};
