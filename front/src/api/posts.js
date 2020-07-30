import { API_BASE_URL } from "../utils/constants";
import axios from "axios";

const client = axios.create({
  baseURL: API_BASE_URL.EC2 + "/posts",
});
export const createPost = (newPost) => client.post("", newPost);
export const loadPost = (postId) =>
  client.get(`/${postId}`).then((res) => res.data);
export const loadPosts = () => client.get("").then((res) => res.data);
export const deletePost = (postId) => client.delete(`/${postId}`);
