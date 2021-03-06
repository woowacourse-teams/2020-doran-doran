import moment from "moment";

const FORMAT = "YYYY-MM-DD HH:mm:ss";

const PERIOD_TYPE = {
  SECOND: "seconds",
  HOUR: "hours",
  DAY: "days",
};

const period = (() => {
  const format = (date) => moment(date).format(FORMAT);

  const now = () => moment().add(1, PERIOD_TYPE.SECOND).format(FORMAT);

  const ago = (x, type) => moment().subtract(x, type).format(FORMAT);

  return {
    format,
    now,
    ago,
  };
})();

const PERIOD_OPTIONS = {
  DAY: {
    order: 1,
    title: "24시간 이내",
    startDate: () => period.ago(24, PERIOD_TYPE.HOUR),
  },
  WEEK: {
    order: 2,
    title: "7일 이내",
    startDate: () => period.ago(7, PERIOD_TYPE.DAY),
  },
  MONTH: {
    order: 3,
    title: "30일 이내",
    startDate: () => period.ago(30, PERIOD_TYPE.DAY),
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

export { period, PERIOD_OPTIONS, PERIOD_TYPE };
