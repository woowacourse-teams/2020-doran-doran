import Vue from "vue";
import VueRouter from "vue-router";
import CreatePost from "../components/PostCreatePage";
import MainPage from "../components/MainPage";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "mainPage",
    component: MainPage,
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
