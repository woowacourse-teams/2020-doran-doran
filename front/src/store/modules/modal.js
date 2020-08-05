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
    isDefaultMode(state) {
      return state.mapMode === MAP_MODE.DEFAULT;
    },
    isMarkerMode(state) {
      return state.mapMode === MAP_MODE.MARKER;
    },
    isPostMode(state) {
      return state.mapMode === MAP_MODE.POST;
    },
  },
};
