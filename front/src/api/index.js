import axios from "axios";

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
  const deleteRequest = (uri, config) => axios.delete(uri, config);
  const getRequestWithJsonData = (uri, config) =>
    axios.get(uri, config).then((response) => response.data.json());
  const postRequestWithJsonData = (uri, config) =>
    axios.post(uri, config).then((response) => response.data.json());

  const post = {
    get(id) {
      return getRequestWithJsonData(`/posts/${id}`);
    },
    getAll() {
      return getRequestWithJsonData(`/posts`);
    },
    create(data) {
      return postRequestWithJsonData(`/posts`, METHOD.POST(data));
    },
    delete(id) {
      return deleteRequest(`/posts/${id}`, METHOD.DELETE());
    },
  };
  return {
    post,
  };
})();

export default api;
