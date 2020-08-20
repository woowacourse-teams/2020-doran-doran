import Vue from "vue";
import Vuex from "vuex";
import comment from "@/store/modules/comment";
import member from "@/store/modules/member";
import modal from "@/store/modules/modal";
import post from "@/store/modules/post";
import memberSidebar from "@/store/modules/memberSidebar";
import appBar from "@/store/modules/appBar";
import snackbar from "@/store/modules/snackbar";
import postModal from "@/store/modules/postModal";
import postDetailModal from "@/store/modules/postDetailModal";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    post,
    comment,
    member,
    modal,
    memberSidebar,
    appBar,
    snackbar,
    postModal,
    postDetailModal,
  },
});
