import moment from "moment";

export const DATE_FILTER_TYPE = () => {
  return {
    ALL: {
      name: "date-all",
      startDate: "",
    },
    DAY: {
      name: "date-day",
      startDate: moment().subtract(1, "days").format("YYYY-MM-DD HH:mm:ss"),
    },
    WEEK: {
      name: "date-week",
      startDate: moment().subtract(7, "days").format("YYYY-MM-DD HH:mm:ss"),
    },
    MONTH: {
      name: "date-month",
      startDate: moment().subtract(1, "months").format("YYYY-MM-DD HH:mm:ss"),
    },
  };
};
