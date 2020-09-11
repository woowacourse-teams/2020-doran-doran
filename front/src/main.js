import Vue from "vue";
import App from "@/App.vue";
import router from "@/router";
import store from "@/store";
import vuetify from "@/plugins/vuetify";
import KakaoMap from "@/plugins/kakao-map";
import VueAnalytics from "vue-analytics";

Vue.use(VueAnalytics, {
  id: "UA-176070170-1",
  router,
});

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
