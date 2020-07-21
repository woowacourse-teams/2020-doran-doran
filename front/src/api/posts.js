import { API_BASE_URL } from "../utils/constants";
import axios from "axios";

const client = axios.create({
  baseURL: API_BASE_URL.EC2,
});
export const createPost = (newPost) => client.post("", newPost);
export const loadPost = (postId) => client.get(`/${postId}`);
export const loadPosts = () => client.get("");
export const deletePost = (postId) => client.delete(`/${postId}`);
