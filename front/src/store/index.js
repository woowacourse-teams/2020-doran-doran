import Vue from "vue";
import Vuex from "vuex";
import { MOCK_POSTS } from "../utils/mock-data";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    posts: [],
  },
  mutations: {
    SET_POSTS(state, posts) {
      state.posts = posts;
    },
  },
  actions: {
    loadPosts({ commit }) {
      //TODO API 요청
      commit("SET_POSTS", MOCK_POSTS);
    },
  },
  getters: {
    getPosts: (state) => {
      return state.posts;
    },
  },
});
