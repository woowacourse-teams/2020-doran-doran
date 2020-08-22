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
    INITIALIZE_PERIOD_FILTER(state) {
      state.startDate = "";
      state.endDate = "";
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
