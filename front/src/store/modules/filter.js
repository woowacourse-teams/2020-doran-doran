import api from "@/api/posts";
import { DATE_FORMAT } from "@/utils/constants";
import moment from "moment";

export default {
  namespaced: true,
  state: {
    keyword: "",
    startDate: moment().subtract(24, "hours").format(DATE_FORMAT.DEFAULT),
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
        endDate: state.endDate
          ? state.endDate
          : moment().add(1, "seconds").format(DATE_FORMAT.DEFAULT),
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
