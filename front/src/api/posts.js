import { API_BASE_URL } from "@/utils/constants";
import axios from "axios";
import moment from "moment";

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
  const loadPostsIn24Hours = () => {
    const data = {
      startDate: moment().subtract(1, "days").format("YYYY-MM-DD HH:mm:ss"),
      endDate: moment().add(1, "seconds").format("YYYY-MM-DD HH:mm:ss"),
    };
    const params = new URLSearchParams(data).toString();
    return client.get(`/filter?` + params).then((res) => res.data);
  };
  const loadPostsIn1Week = () => {
    const data = {
      startDate: moment().subtract(7, "days").format("YYYY-MM-DD HH:mm:ss"),
      endDate: moment().add(1, "seconds").format("YYYY-MM-DD HH:mm:ss"),
    };
    const params = new URLSearchParams(data).toString();
    return client.get(`/filter?` + params).then((res) => res.data);
  };
  const loadPostsIn1Month = () => {
    const data = {
      startDate: moment().subtract(1, "months").format("YYYY-MM-DD HH:mm:ss"),
      endDate: moment().add(1, "seconds").format("YYYY-MM-DD HH:mm:ss"),
    };
    const params = new URLSearchParams(data).toString();
    return client.get(`/filter?` + params).then((res) => res.data);
  };
  const loadAllPosts = () => client.get("").then((res) => res.data);
  const loadPostsInBounds = (bounds) => {
    const params = new URLSearchParams(bounds).toString();
    return client.get(`/bounds?` + params).then((res) => res.data);
  };
  const deletePost = (postId) => client.delete(`/${postId}`, options);
  const searchPosts = (data) => {
  const deletePost = (postId) => client.delete(`/${postId}`);
  const filterPosts = (data) => {
    const params = new URLSearchParams(data).toString();
    return client.get(`/filter?` + params).then((res) => res.data);
  };
  const createPostLike = (newPostLike) =>
    client.post(`/likes`, newPostLike, options);
  const deletePostLike = (postLikeId) =>
    client.delete(`/likes/${postLikeId}`, options);
  return {
    createPost,
    loadPost,
    loadPostsIn24Hours,
    loadPostsIn1Week,
    loadPostsIn1Month,
    loadAllPosts,
    loadPostsInBounds,
    deletePost,
    filterPosts,
    createPostLike,
    deletePostLike,
  };
})();

export default api;
