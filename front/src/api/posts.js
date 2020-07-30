import { API_BASE_URL } from "../utils/constants";
import axios from "axios";

const client = axios.create({
  baseURL: API_BASE_URL.EC2 + "/posts",
});

const api = () => {
  const createPost = (newPost) => client.post("", newPost);
  const loadPost = (postId) => client.get(`/${postId}`).then((res) => res.data);
  const loadPosts = () => client.get("").then((res) => res.data);
  const deletePost = (postId) => client.delete(`/${postId}`);
  return {
    createPost,
    loadPost,
    loadPosts,
    deletePost,
  };
};

export default api;
