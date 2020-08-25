import Vue from "vue";
import VueRouter from "vue-router";
import LoginPage from "@/components/login/LoginPage";
import MapPage from "@/components/map/MapPage";
import PostDetailPage from "@/components/post/PostDetailPage";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "MapPage",
    component: MapPage,
  },
  {
    path: "/login",
    name: "LoginPage",
    component: LoginPage,
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
