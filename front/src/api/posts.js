import { API_BASE_URL } from "@/utils/constants";
import axios from "axios";

const client = axios.create({
  baseURL: API_BASE_URL.EC2 + "/posts",
});

const options = {
  headers: {
    Authorization: 'Bearer ' + sessionStorage.getItem("accessToken")
  }
}

const api = (() => {
  const createPost = (newPost) => client.post("", newPost, options);
  const loadPost = (postId) => client.get(`/${postId}`).then((res) => res.data);
  const loadPosts = () => client.get("").then((res) => res.data);
  const loadPostsInBounds = (bounds) => {
    const params = new URLSearchParams(bounds).toString();
    return client.get(`/bounds?` + params).then((res) => res.data);
  };
  const deletePost = (postId) => client.delete(`/${postId}`, options);
  const searchPosts = (data) => {
    const params = new URLSearchParams(data).toString();
    return client.get(`/filter?` + params).then((res) => res.data);
  };
  const createPostLike = (newPostLike) => client.post(`/likes`, newPostLike, options);
  const deletePostLike = (postLikeId) => client.delete(`/likes/${postLikeId}`, options);
  return {
    createPost,
    loadPost,
    loadPosts,
    loadPostsInBounds,
    deletePost,
    searchPosts,
    createPostLike,
    deletePostLike,
  };
})();

export default api;
