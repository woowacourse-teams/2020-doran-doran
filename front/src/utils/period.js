import moment from "moment";

const DATE_FORMAT = "YYYY-MM-DD HH:mm:ss";

const PERIOD = (() => {
  const format = (date) => {
    moment(date).format(DATE_FORMAT);
  };

  const now = () => moment().add(1, "seconds").format(DATE_FORMAT);

  const ago = (x, type) => moment().subtract(x, type).format(DATE_FORMAT);

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
    startDate: () => PERIOD.ago(24, "hours"),
  },
  WEEK: {
    order: 2,
    title: "7일 이내",
    startDate: () => PERIOD.ago(7, "days"),
  },
  MONTH: {
    order: 3,
    title: "30일 이내",
    startDate: () => PERIOD.ago(30, "days"),
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

export { PERIOD, PERIOD_OPTIONS };
