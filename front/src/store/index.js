import Vue from "vue";
import Vuex from "vuex";
import post from "./modules/post";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    post,
  },
});
