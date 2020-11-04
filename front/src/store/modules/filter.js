import api from "@/api/posts";
import { period, PERIOD_TYPE } from "@/utils/period";

export default {
  namespaced: true,
  state: {
    keyword: "",
    startDate: period.ago(24, PERIOD_TYPE.HOUR),
    endDate: "",
  },
  mutations: {
    SET_KEYWORD(state, keyword) {
      state.keyword = keyword;
    },
    SET_START_DATE(state, startDate) {
      if (startDate === "") {
        return;
      }
      state.startDate = startDate;
    },
    SET_END_DATE(state, endDate) {
      if (endDate === "") {
        return;
      }
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
        endDate: state.endDate ? state.endDate : period.now(),
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
