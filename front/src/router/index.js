import Vue from "vue";
import VueRouter from "vue-router";
import LoginPage from "@/components/login/LoginPage";
import MapPage from "@/components/map/MapPage";
import TimelineModal from "@/components/timeline/TimelineModal";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "MapPage",
    component: MapPage,
    meta: {
      timeline: false,
    },
    children: [
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
