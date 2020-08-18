import Vue from "vue";
import VueRouter from "vue-router";
import LoginPage from "@/components/login/LoginPage";
import MapPage from "@/components/map/MapPage";
import PostDetailPage from "@/components/post/PostDetailPage";
import TimelinePage from "@/components/timeline/TimelinePage";
import SearchPage from "@/components/search/SearchPage";
import SearchResultPage from "@/components/search/SearchResultPage";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "LoginPage",
    component: LoginPage,
  },
  {
    path: "/map",
    name: "MapPage",
    component: MapPage,
  },
  {
    path: "/timeline",
    name: "TimeLinePage",
    component: TimelinePage,
  },
  {
    path: "/posts/:id",
    name: "PostDetailPage",
    component: PostDetailPage,
  },
  {
    path: "/search",
    name: "SearchPage",
    component: SearchPage,
  },
  {
    path: "/searchResult",
    name: "SearchResultPage",
    component: SearchResultPage,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
