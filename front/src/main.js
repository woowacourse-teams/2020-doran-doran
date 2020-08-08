import Vue from "vue";
import App from "@/App.vue";
import router from "@/router";
import store from "@/store";
import vuetify from "@/plugins/vuetify";
import momentLocale from "moment/locale/ko";
import KakaoMap from "@/plugins/kakao-map";

Vue.config.productionTip = false;
Vue.use(KakaoMap);
Vue.use(require("vue-moment"));

new Vue({
  router,
  store,
  vuetify,
  momentLocale,
  render: (h) => h(App),
}).$mount("#app");
