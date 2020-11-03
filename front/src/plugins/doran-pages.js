export const pages = {
  map: () => {
    return {
      path: "/",
      name: "Map",
    };
  },
  postCreate: () => {
    return {
      path: "/post-create",
      name: "PostCreate",
    };
  },
  timeline: () => {
    return {
      path: "/timeline",
      name: "Timeline",
    };
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
  login: () => {
    return {
      path: "/login",
      name: "Login",
    };
  },
};

export default {
  install(Vue) {
    Vue.prototype.$pages = pages;
  },
};
