import { API_BASE_URL } from "../utils/constants";
import axios from "axios";

const client = axios.create({
  baseURL: API_BASE_URL.EC2 + "/comments",
});
export const createComment = (newComment) => client.post("", newComment);
