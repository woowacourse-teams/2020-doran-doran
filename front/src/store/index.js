import Vue from "vue";
import Vuex from "vuex";
import appBar from "@/store/modules/appBar";
import comment from "@/store/modules/comment";
import like from "@/store/modules/like";
import member from "@/store/modules/member";
import modal from "@/store/modules/modal";
import post from "@/store/modules/post";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    appBar,
    comment,
    like,
    member,
    modal,
    post,
  },
});
