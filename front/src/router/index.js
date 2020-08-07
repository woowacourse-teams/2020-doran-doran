import Vue from "vue";
import VueRouter from "vue-router";
import MapPage from "@/components/map/MapPage";
import PostDetailPage from "@/components/post/PostDetailPage";
import TimelinePage from "@/components/timeline/TimelinePage";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "MapPage",
    component: MapPage,
  },
  {
    path: "/timeline",
    name: "TimeLine",
    component: TimelinePage,
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
