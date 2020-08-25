import { MAP_MODE } from "@/utils/constants";

export default {
  namespaced: true,
  state: {
    mapMode: MAP_MODE.DEFAULT,
  },
  mutations: {
    CHANGE_STATE(state, target) {
      state.mapMode = target;
    },
  },
  getters: {
    isDefault(state) {
      return state.mapMode === MAP_MODE.DEFAULT;
    },
    isMarker(state) {
      return state.mapMode === MAP_MODE.MARKER;
    },
    isPost(state) {
      return state.mapMode === MAP_MODE.POST;
    },
  },
};
