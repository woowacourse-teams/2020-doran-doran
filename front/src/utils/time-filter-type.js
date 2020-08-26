import moment from "moment";
import {DATE_FORMAT} from "@/utils/constants";

export const DATE_FILTER_TYPE = () => {
  return {
    ALL: {
      name: "date-all",
      startDate: "",
    },
    DAY: {
      name: "date-day",
      startDate: moment().subtract(1, "days").format(DATE_FORMAT.DEFAULT),
    },
    WEEK: {
      name: "date-week",
      startDate: moment().subtract(7, "days").format(DATE_FORMAT.DEFAULT),
    },
    MONTH: {
      name: "date-month",
      startDate: moment().subtract(1, "months").format(DATE_FORMAT.DEFAULT),
    },
  };
};
