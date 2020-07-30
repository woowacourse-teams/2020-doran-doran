import Vue from "vue";
import Vuex from "vuex";
import post from "./modules/post";
import comment from "./modules/comment";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    post,
    comment,
  },
});
