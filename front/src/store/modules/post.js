import * as api from "../../api/posts";

export default {
  namespaced: true,
  state: {
    posts: [],
  },
  mutations: {
    ADD_POST(state, post) {
      state.posts.push(post);
    },
    SET_POSTS(state, posts) {
      state.posts = posts;
    },
    REMOVE_POST(state, postId) {
      const deleteIndex = state.posts.findIndex((post) => post.id === postId);
      state.posts.splice(deleteIndex, 1);
    },
  },
  actions: {
    async createPost({ commit }, newPost) {
      await api.createPost(newPost);
      commit("ADD_POST", newPost);
    },
    async loadPost(context, postId) {
      return await api.loadPost(postId);
    },
    async loadPosts({ commit }) {
      const { data } = await api.loadPosts();
      commit("SET_POSTS", data);
    },
    async deletePost({ commit }, postId) {
      await api.deletePost(postId);
      commit("REMOVE_POST", postId);
    },
  },
  getters: {
    getPosts: (state) => {
      return state.posts;
    },
  },
};
