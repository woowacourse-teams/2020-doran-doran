import Vue from "vue";
import VueRouter from "vue-router";
import MainPage from "../components/MainPage";
import PostCreatePage from "../components/PostCreatePage";
import PostDetailPage from "../components/PostDetailPage";
import TimelinePage from "../components/TimelinePage";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "MainPage",
    component: MainPage,
  },
  {
    path: "/timeline",
    name: "TimeLine",
    component: TimelinePage,
  },
  {
    path: "/create-post",
    name: "PostCreatePage",
    component: PostCreatePage,
  },
  {
    path: "/posts/:id",
    name: "PostDetailPage",
    component: PostDetailPage,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
