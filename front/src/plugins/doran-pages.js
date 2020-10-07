export const pages = {
  map: {
    path: "/",
    name: "Map",
  },
  postCreate: {
    path: "/post-create",
    name: "PostCreate",
  },
  timeline: {
    path: "/timeline",
    name: "Timeline",
  },
  post: (id) => {
    return {
      path: "/posts/" + id,
      name: "Post",
      params: {
        id,
      },
    };
  },
  login: {
    path: "/login",
    name: "Login",
  },
};

export default {
  install(Vue) {
    Vue.prototype.$pages = pages;
  },
};
