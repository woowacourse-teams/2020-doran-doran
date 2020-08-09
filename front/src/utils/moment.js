import moment from "moment";

export const CHANGE_DATE_FROM_NOW = (date) => {
  return moment(date).fromNow();
};
