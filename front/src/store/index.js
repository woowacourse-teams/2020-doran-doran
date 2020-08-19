import Vue from "vue";
import Vuex from "vuex";
import comment from "@/store/modules/comment";
import member from "@/store/modules/member";
import modal from "@/store/modules/modal";
import post from "@/store/modules/post";
import myPageSideBar from "@/store/modules/myPageSideBar";
import appBar from "@/store/modules/appBar";
import snackbar from "@/store/modules/snackbar";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    post,
    comment,
    member,
    modal,
    myPageSideBar,
    appBar,
    snackbar,
  },
});
