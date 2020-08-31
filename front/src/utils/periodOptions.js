import { DATE_FORMAT } from "@/utils/constants";
import moment from "moment";

export const PERIOD_OPTIONS = {
  DAY: {
    order: 1,
    title: "24시간 이내",
    startDate: () => moment().subtract(24, "hours").format(DATE_FORMAT.DEFAULT),
  },
  WEEK: {
    order: 2,
    title: "7일 이내",
    startDate: () => moment().subtract(7, "days").format(DATE_FORMAT.DEFAULT),
  },
  MONTH: {
    order: 3,
    title: "30일 이내",
    startDate: () => moment().subtract(30, "days").format(DATE_FORMAT.DEFAULT),
  },
  ALL: {
    order: 4,
    title: "전체",
    startDate: () => "",
  },
  CUSTOM: {
    order: 5,
    title: "직접입력",
  },
};
