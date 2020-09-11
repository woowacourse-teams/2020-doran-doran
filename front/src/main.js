import Vue from "vue";
import App from "@/App.vue";
import router from "@/router";
import store from "@/store";
import vuetify from "@/plugins/vuetify";
import KakaoMap from "@/plugins/kakao-map";
import VueAnalytics from "vue-analytics";
import LogRocket from "logrocket";
import { LOGROCKET_APP_ID, GOOGLE_ANALYTICS_TRACKING_ID } from "./secure/trackingid";

Vue.use(VueAnalytics, {
  id: GOOGLE_ANALYTICS_TRACKING_ID,
  router,
});

LogRocket.init(LOGROCKET_APP_ID);

Vue.config.productionTip = false;
Vue.use(KakaoMap);

const moment = require("moment");
require("moment/locale/ko");

Vue.use(require("vue-moment"), {
  moment,
});

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
