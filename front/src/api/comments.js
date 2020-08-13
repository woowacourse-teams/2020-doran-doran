import { API_BASE_URL } from "@/utils/constants";
import axios from "axios";

const client = axios.create({
  baseURL: API_BASE_URL.EC2 + "/comments",
});

const api = (() => {
  const createComment = (newComment) => client.post("", newComment);
  const createCommentLike = (newCommentLike) =>
    client.post(`/likes`, newCommentLike).then(res => res.headers.location);
  const deleteCommentLike = (commentLikeId) =>
    client.delete(`/likes/${commentLikeId}`);
  return {
    createComment,
    createCommentLike,
    deleteCommentLike,
  };
})();

export default api;
