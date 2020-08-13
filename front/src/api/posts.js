import { API_BASE_URL } from "@/utils/constants";
import axios from "axios";

const client = axios.create({
  baseURL: API_BASE_URL.EC2 + "/posts",
});

const api = (() => {
  const createPost = (newPost) => client.post("", newPost);
  const loadPost = (postId) => client.get(`/${postId}`).then((res) => res.data);
  const loadPosts = () => client.get("").then((res) => res.data);
  const deletePost = (postId) => client.delete(`/${postId}`);
  const searchPosts = (data) => {
    const params = new URLSearchParams(data).toString();
    return client.get(`/search?` + params).then((res) => res.data);
  };
  return {
    createPost,
    loadPost,
    loadPosts,
    deletePost,
    searchPosts,
  };
})();

export default api;
