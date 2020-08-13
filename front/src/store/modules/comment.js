import api from "@/api/comments";

export default {
  namespaced: true,
  actions: {
    async createComment(context, newComment) {
      await api.createComment(newComment);
    },
    async createCommentLike(context, newCommentLike) {
      await api.createCommentLike(newCommentLike);
    },
    async deleteCommentLike(context, commentLikeId) {
      await api.deleteCommentLike(commentLikeId);
    },
  },
};
