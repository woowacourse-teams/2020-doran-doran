const METHOD = {
  POST(data) {
    return {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        ...data,
      }),
    };
  },
  DELETE() {
    return {
      method: "DELETE",
    };
  },
};

const api = (() => {
  const request = (uri, config) => fetch(uri, config);
  const requestWithJsonData = (uri, config) =>
    fetch(uri, config).then((data) => data.json());

  const post = {
    get(id) {
      return requestWithJsonData(`/posts/${id}`);
    },
    getAll() {
      return requestWithJsonData(`/posts`);
    },
    create(data) {
      return requestWithJsonData(`/posts`, METHOD.POST(data));
    },
    delete(id) {
      return request(`/posts/${id}`, METHOD.DELETE());
    },
  };
  return {
    post,
  };
})();

export default api;
