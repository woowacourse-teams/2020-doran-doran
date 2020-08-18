export default {
  namespaced: true,
  state: {
    bounds: [],
  },
  mutations: {
    SET_BOUNDS(state, bounds) {
      state.bounds = bounds;
    },
    CHANGE_BOUNDS(state, newBounds) {
      state.bounds.upperBound = newBounds.ja;
      state.bounds.lowerBound = newBounds.ka;
      state.bounds.leftBound = newBounds.da;
      state.bounds.rightBound = newBounds.ia;
    }
  },
  getters: {
    getBounds: (state) => {
      return state.bounds;
    }
  },
};
