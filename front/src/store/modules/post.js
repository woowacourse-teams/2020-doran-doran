import api from "@/api/posts";

export default {
  namespaced: true,
  state: {
    post: {
      id: 0,
      memberResponse: {
        id: 0,
        nickname: "",
      },
      content: "",
      address: {
        depth1: "",
        depth2: "",
        depth3: "",
      },
      authorAddress: {
        depth1: "",
        depth2: "",
        depth3: "",
      },
      likes: [],
      createdAt: "",
      comments: [],
    },
    posts: [],
    searchedPosts: [],
  },
  mutations: {
    SET_POST(state, post) {
      state.post = post;
    },
    SET_POSTS(state, posts) {
      state.posts = posts;
    },
    SET_SEARCHED_POSTS(state, searchedPosts) {
      state.searchedPosts = searchedPosts;
    },
    REMOVE_POST(state, postId) {
      const deleteIndex = state.posts.findIndex((post) => post.id === postId);
      state.posts.splice(deleteIndex, 1);
    },
  },
  actions: {
    async createPost({ dispatch }, newPost) {
      await api.createPost(newPost);
      dispatch("loadPosts");
    },
    async loadPost({ commit }, postId) {
      const data = await api.loadPost(postId);
      commit("SET_POST", data);
    },
    async loadPosts({ commit }) {
      const data = await api.loadPosts();
      commit("SET_POSTS", data);
    },
    async deletePost({ commit }, postId) {
      await api.deletePost(postId);
      commit("REMOVE_POST", postId);
    },
    async searchPosts({ commit }, data) {
      const searchResult = await api.searchPosts(data);
      commit("SET_SEARCHED_POSTS", searchResult);
    },
    async createPostLike(context, newPostLike) {
      await api.createPostLike(newPostLike);
    },
    async deletePostLike(context, postLikeId) {
      await api.deletePostLike(postLikeId);
    },
  },
  getters: {
    getPost: (state) => {
      return state.post;
    },
    getPosts: (state) => {
      return state.posts;
    },
    getSearchedPosts: (state) => {
      return state.searchedPosts;
    },
  },
};
