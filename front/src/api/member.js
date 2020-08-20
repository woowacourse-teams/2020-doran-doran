import { API_BASE_URL } from "@/utils/constants";
import axios from "axios";

const client = axios.create({
  baseURL: API_BASE_URL.EC2 + "/my",
});

const options = {
  headers: {
    Authorization: "Bearer " + sessionStorage.getItem("accessToken"),
  },
};

const api = (() => {
  const loadMember = () =>
    client
      .get("", options)
      .then((res) => res.data)
      .catch(() => {
        return {
          id: 0,
          nickname: "미인증 사용자",
        };
      });
  return {
    loadMember,
  };
})();

export default api;
