import api from "@/api/posts";
import filter from "@/store/modules/filter";

export default {
  namespaced: true,
  modules: {
    filter,
  },
  state: {
    post: {
      id: 0,
      author: {
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
      entityStatus: "",
    },
    posts: [],
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
    REMOVE_POST(state, postId) {
      const deleteIndex = state.posts.findIndex((post) => post.id === postId);
      state.posts.splice(deleteIndex, 1);
    },
  },
  actions: {
    async createPost(context, newPost) {
      await api.createPost(newPost);
    },
    async loadPost({ commit }, postId) {
      const data = await api.loadPost(postId);
      commit("SET_POST", data);
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
    post: (state) => {
      return state.post;
    },
    posts: (state) => {
      return state.posts;
    },
    postsInBounds: (state) => (bounds) => {
      return state.posts.filter(
        ({ location }) =>
          bounds.top > location.latitude &&
          bounds.bottom < location.latitude &&
          bounds.left < location.longitude &&
          bounds.right > location.longitude,
      );
    },
  },
};
