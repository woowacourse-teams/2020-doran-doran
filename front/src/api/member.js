import { API_BASE_URL } from "@/utils/constants";
import axios from "axios";

const client = axios.create({
  baseURL: API_BASE_URL.EC2 + "/me",
});

const options = {
  headers: {
    Authorization: "Bearer " + localStorage.getItem("accessToken"),
  },
};

const api = (() => {
  const loadMember = () => client.get("", options).then((res) => res.data);
  return {
    loadMember,
  };
})();

export default api;
