import Vue from "vue";
import VueRouter from "vue-router";
import KakaoMap from "../components/KakaoMap";
import CreatePost from "../components/PostCreate";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "kakaoMap",
    component: KakaoMap,
  },
  {
    path: "/create-post",
    name: "createPost",
    component: CreatePost,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
