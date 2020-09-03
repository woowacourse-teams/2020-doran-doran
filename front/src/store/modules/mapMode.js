import { MAP_MODE } from "@/utils/constants";

export default {
  namespaced: true,
  state: {
    map: null,
    mapMode: MAP_MODE.DEFAULT,
  },
  mutations: {
    SET_MAP(state, map) {
      state.map = map;
    },
    CHANGE_STATE(state, target) {
      state.mapMode = target;
    },
  },
  getters: {
    map(state) {
      return state.map;
    },
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
