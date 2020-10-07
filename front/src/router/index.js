import Vue from "vue";
import VueRouter from "vue-router";
import LoginPage from "@/components/login/LoginPage";
import MapPage from "@/components/map/MapPage";
import TimelineModal from "@/components/timeline/TimelineModal";
import PostModal from "@/components/post/PostModal";
import { pages } from "@/plugins/doran-pages";

Vue.use(VueRouter);

const routes = [
  {
    ...pages.map,
    component: MapPage,
    meta: {
      postCreate: false,
      timeline: false,
      post: false,
    },
    children: [
      {
        ...pages.postCreate,
        meta: {
          postCreate: true,
        },
      },
      {
        ...pages.timeline,
        components: {
          page: TimelineModal,
        },
        meta: {
          timeline: true,
        },
      },
      {
        ...pages.post(":id"),
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
    ...pages.login,
    component: LoginPage,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
