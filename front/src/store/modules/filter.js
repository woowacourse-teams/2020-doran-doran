import api from "@/api/posts";
import { DATE_FORMAT } from "@/utils/constants";
import moment from "moment";

export default {
  namespaced: true,
  state: {
    keyword: "",
    startDate: "",
    endDate: "",
  },
  mutations: {
    SET_KEYWORD(state, keyword) {
      state.keyword = keyword;
    },
    SET_START_DATE(state, startDate) {
      state.startDate = startDate + " 00:00:00";
    },
    SET_END_DATE(state, endDate) {
      state.endDate = endDate + " 23:59:59";
    },
    SET_FILTER_FROM_X_HOURS_AGO_TO_NOW(state, x) {
      state.startDate = moment()
        .subtract(x, "hours")
        .format(DATE_FORMAT.DEFAULT);
      state.endDate = moment().add(1, "seconds").format(DATE_FORMAT.DEFAULT);
    },
    SET_FILTER_FROM_X_DAYS_AGO_TO_NOW(state, x) {
      state.startDate =
        moment().subtract(x, "days").format("YYYY-MM-DD") + " 00:00:00";
      state.endDate = moment().add(1, "seconds").format(DATE_FORMAT.DEFAULT);
    },
    INITIALIZE_PERIOD_FILTER(state) {
      state.startDate = "";
      state.endDate = "";
    },
  },
  actions: {
    async filterPosts(context) {
      const data = {
        keyword: context.state.keyword,
        startDate: context.state.startDate,
        endDate: context.state.endDate,
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
