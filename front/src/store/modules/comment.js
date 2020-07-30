import * as api from "../../api/comments";

export default {
  namespaced: true,
  actions: {
    async createComment(context, newComment) {
      await api.createComment(newComment);
    },
  },
};
