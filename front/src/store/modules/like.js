import { DORAN_DORAN_COLORS } from "@/utils/constants";

export default {
  namespaced: true,
  state: {
    liked: {
      color: DORAN_DORAN_COLORS.LIKE_COLOR,
      icon: "mdi-heart",
    },
    notLiked: {
      color: "",
      icon: "mdi-heart-outline",
    },
  },
  getters: {
    liked: (state) => {
      return state.liked;
    },
    notLiked: (state) => {
      return state.notLiked;
    },
  },
};
