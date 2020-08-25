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
      location: {
        longitude: 0,
        latitude: 0,
      },
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
    timelinePosts: [],
  },
  mutations: {
    SET_POST(state, post) {
      state.post = post;
    },
    SET_POSTS(state, posts) {
      state.posts = posts;
    },
    CLEAR_POSTS(state) {
      state.posts = null;
    },
    SET_TIMELINE_POSTS(state, timelinePosts) {
      state.timelinePosts = timelinePosts;
    },
    REMOVE_POST(state, postId) {
      const deleteIndex = state.posts.findIndex((post) => post.id === postId);
      state.posts.splice(deleteIndex, 1);
    },
  },
  actions: {
    async createPost({ commit }, newPost) {
      await api.createPost(newPost);
      commit("CLEAR_POSTS");
    },
    async loadPost({ commit }, postId) {
      const data = await api.loadPost(postId);
      commit("SET_POST", data);
    },
    async filterPosts({ commit }, data) {
      commit("CLEAR_POSTS");
      const searchResult = await api.filterPosts(data);
      commit("SET_POSTS", searchResult);
    },
    async loadPostsInBounds({ commit }, bounds) {
      const postsInBounds = await api.loadPostsInBounds(bounds);
      commit("SET_TIMELINE_POSTS", postsInBounds);
    },
    async deletePost({ commit }, postId) {
      await api.deletePost(postId);
      commit("REMOVE_POST", postId);
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
    getPostsInBounds: (state) => {
      return state.timelinePosts;
    },
  },
};
