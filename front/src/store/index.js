import Vue from "vue";
import Vuex from "vuex";
import comment from "@/store/modules/comment";
import member from "@/store/modules/member";
import post from "@/store/modules/post";
import memberSidebar from "@/store/modules/memberSidebar";
import appBar from "@/store/modules/appBar";
import filter from "@/store/modules/filter";
import snackbar from "@/store/modules/snackbar";
import mapMode from "@/store/modules/mapMode";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    post,
    comment,
    member,
    memberSidebar,
    appBar,
    filter,
    snackbar,
    mapMode,
  },
});
