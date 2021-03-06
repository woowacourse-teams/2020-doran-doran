import { API_BASE_URL } from "@/utils/constants";
import axios from "axios";

const client = axios.create({
  baseURL: API_BASE_URL.EC2 + "/posts",
});

const options = {
  headers: {
    Authorization: "Bearer " + localStorage.getItem("accessToken"),
  },
};

const api = (() => {
  const createPost = (newPost) => client.post("", newPost, options);
  const loadPost = (postId) => client.get(`/${postId}`).then((res) => res.data);
  const filterPosts = (data) => {
    const params = new URLSearchParams(data).toString();
    return client.get(`/filter?` + params).then((res) => res.data);
  };
  const deletePost = (postId) => client.delete(`/${postId}`, options);
  const createPostLike = (newPostLike) => client.post(`/likes`, newPostLike, options);
  const deletePostLike = (postLikeId) => client.delete(`/likes/${postLikeId}`, options);
  return {
    createPost,
    loadPost,
    filterPosts,
    deletePost,
    createPostLike,
    deletePostLike,
  };
})();

export default api;
