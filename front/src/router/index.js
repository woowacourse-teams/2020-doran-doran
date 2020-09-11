import Vue from "vue";
import VueRouter from "vue-router";
import LoginPage from "@/components/login/LoginPage";
import MapPage from "@/components/map/MapPage";
import TimelineModal from "@/components/timeline/TimelineModal";
import PostModal from "@/components/post/PostModal";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "MapPage",
    component: MapPage,
    meta: {
      timeline: false,
      post: false,
    },
    children: [
      {
        path: "post-create",
        name: "PostCreate",
      },
      {
        path: "timeline",
        name: "TimelineModal",
        components: {
          page: TimelineModal,
        },
        meta: {
          timeline: true,
        },
      },
      {
        path: "posts/:id",
        name: "PostModal",
        components: {
          page: PostModal,
        },
        meta: {
          post: true,
        },
      },
    ],
  },
  {
    path: "/login",
    name: "LoginPage",
    component: LoginPage,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
