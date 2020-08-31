import store from "../store/index";

export const PERIOD_OPTIONS = {
  DAY: {
    order: 1,
    title: "24시간 이내",
    action: () => store.commit("filter/SET_FILTER_FROM_X_HOURS_AGO_TO_NOW", 24),
  },
  WEEK: {
    order: 2,
    title: "7일 이내",
    action: () => store.commit("filter/SET_FILTER_FROM_X_DAYS_AGO_TO_NOW", 7),
  },
  MONTH: {
    order: 3,
    title: "30일 이내",
    action: () => store.commit("filter/SET_FILTER_FROM_X_DAYS_AGO_TO_NOW", 30),
  },
  ALL: {
    order: 4,
    title: "전체",
    action: () => store.commit("filter/INITIALIZE_PERIOD_FILTER"),
  },
  CUSTOM: {
    order: 5,
    title: "직접입력",
  },
};
