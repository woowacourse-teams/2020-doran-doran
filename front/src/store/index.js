import Vue from "vue";
import Vuex from "vuex";
import post from "@/store/modules/post";
import comment from "@/store/modules/comment";
import member from "@/store/modules/member";
import modal from "@/store/modules/modal";
import appBar from "@/store/modules/appBar";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    post,
    comment,
    member,
    modal,
    appBar,
  },
});
