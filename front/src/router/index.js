import Vue from "vue";
import VueRouter from "vue-router";
import MainPage from "../components/MainPage";
import PostCreatePage from "../components/PostCreatePage";
import PostDetailPage from "../components/PostDetailPage";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "mainPage",
    component: MainPage,
  },
  {
    path: "/create-post",
    name: "postCreatePage",
    component: PostCreatePage,
  },
  {
    path: "/posts/:id",
    name: "postDetailPage",
    component: PostDetailPage,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
