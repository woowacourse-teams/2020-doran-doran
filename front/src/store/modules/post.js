import { MOCK_POSTS } from "../../utils/mock-data";

export default {
  namespaced: true,
  state: {
    posts: [],
  },
  mutations: {
    SET_POSTS(state, posts) {
      state.posts = posts;
    },
    ADD_POST(state, post) {
      state.posts.push(post);
    },
  },
  actions: {
    loadPosts({ commit }) {
      //TODO API 요청
      commit("SET_POSTS", MOCK_POSTS);
    },
    addPost({ commit }, newPost) {
      commit("ADD_POST", newPost);
    },
  },
  getters: {
    getPosts: (state) => {
      return state.posts;
    },
  },
};
