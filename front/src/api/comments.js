import { API_BASE_URL } from "@/utils/constants";
import axios from "axios";

const client = axios.create({
  baseURL: API_BASE_URL.EC2 + "/comments",
});

const options = {
  headers: {
    Authorization: "Bearer " + localStorage.getItem("accessToken"),
  },
};

const api = (() => {
  const createComment = (newComment) => client.post("", newComment, options);
  const createCommentLike = (newCommentLike) =>
    client.post(`/likes`, newCommentLike, options);
  const deleteCommentLike = (commentLikeId) =>
    client.delete(`/likes/${commentLikeId}`, options);
  return {
    createComment,
    createCommentLike,
    deleteCommentLike,
  };
})();

export default api;
