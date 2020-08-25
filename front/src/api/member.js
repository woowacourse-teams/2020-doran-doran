import { API_BASE_URL } from "@/utils/constants";
import axios from "axios";

const client = axios.create({
  baseURL: API_BASE_URL.EC2 + "/me",
});

const options = {
  headers: {
    Authorization: "Bearer " + localStorage.getItem("accessToken"),
    "Content-Type": "application/json",
  },
};

const api = (() => {
  const loadMember = () => client.get("", options).then((res) => res.data);
  const updateMember = (newNickname) => {
    const request = JSON.stringify(newNickname);
    return client.put("", request, options).then((res) => res.data);
  };
  const deleteMember = () => client.delete("");

  return {
    loadMember,
    updateMember,
    deleteMember,
  };
})();

export default api;
