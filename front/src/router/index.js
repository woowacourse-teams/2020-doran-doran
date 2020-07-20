import Vue from "vue";
import VueRouter from "vue-router";
import Map from "../components/Map";
import CreatePost from "../components/PostCreate";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "map",
    component: Map,
  },
  {
    path: "/create-post",
    name: "create-post",
    component: CreatePost,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
