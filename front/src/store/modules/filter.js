import api from "@/api/posts";
import { PERIOD } from "@/utils/period";

export default {
  namespaced: true,
  state: {
    keyword: "",
    startDate: PERIOD.ago(24, "hours"),
    endDate: "",
  },
  mutations: {
    SET_KEYWORD(state, keyword) {
      state.keyword = keyword;
    },
    SET_START_DATE(state, startDate) {
      state.startDate = startDate;
    },
    SET_END_DATE(state, endDate) {
      state.endDate = endDate;
    },
    RESET_END_DATE(state) {
      state.endDate = "";
    },
  },
  actions: {
    async filterPosts({ state }) {
      const data = {
        keyword: state.keyword,
        startDate: state.startDate,
        endDate: state.endDate ? state.endDate : PERIOD.now(),
      };
      return await api.filterPosts(data);
    },
  },
  getters: {
    keyword: (state) => {
      return state.keyword;
    },
    startDate: (state) => {
      return state.startDate;
    },
    endDate: (state) => {
      return state.endDate;
    },
  },
};
