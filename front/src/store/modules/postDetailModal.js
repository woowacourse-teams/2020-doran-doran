import { POST_MODE } from "@/utils/constants";

export default {
  namespaced: true,
  state: {
    postMode: POST_MODE.DEFAULT,
  },
  mutations: {
    CHANGE_MODE(state, target) {
      state.postMode = target;
    },
  },
  getters: {
    isDefaultMode(state) {
      return state.postMode === POST_MODE.DEFAULT;
    },
    isMapModalMode(state) {
      return state.postMode === POST_MODE.MAP_MODAL;
    },
  },
};
