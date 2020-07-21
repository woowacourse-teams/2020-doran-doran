import * as POST_API from "../../api/posts";

export default {
  namespaced: true,
  state: {
    posts: [],
  },
  mutations: {
    ADD_POST(state, post) {
      state.posts.push(post);
    },
    SET_POST(state, posts) {
      state.posts = posts;
    },
    SET_POSTS(state, posts) {
      state.posts = posts;
    },
    REMOVE_POST(state, postId) {
      const deleteIndex = state.posts.findIndex((val) => val.id === postId);
      state.posts.splice(deleteIndex, 1);
    },
  },
  actions: {
    async createPost({ commit }, newPost) {
      await POST_API.createPost(newPost);
      commit("ADD_POST", newPost);
    },
    async loadPost({ commit }, postId) {
      const { data } = await POST_API.loadPost(postId);
      commit("SET_POST", data);
    },
    async loadPosts({ commit }) {
      const { data } = await POST_API.loadPosts();
      commit("SET_POSTS", data);
    },
    async deletePost({ commit }, postId) {
      await POST_API.deletePost(postId);
      commit("REMOVE_POST", postId);
    },
  },
  getters: {
    getPosts: (state) => {
      return state.posts;
    },
  },
};
