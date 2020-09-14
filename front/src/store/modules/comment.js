import api from "@/api/comments";

export default {
  namespaced: true,
  actions: {
    async createComment(context, newComment) {
      await api.createComment(newComment);
    },
    async deleteComment(context, commentId) {
      await api.deleteComment(commentId);
    },
    async createCommentLike(context, newCommentLike) {
      return await api.createCommentLike(newCommentLike);
    },
    async deleteCommentLike(context, commentLikeId) {
      await api.deleteCommentLike(commentLikeId);
    },
  },
};
