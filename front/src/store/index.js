import Vue from "vue";
import Vuex from "vuex";
import post from "@/store/modules/post";
import comment from "@/store/modules/comment";
import modal from "@/store/modules/modal";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    post,
    comment,
    modal,
  },
});
