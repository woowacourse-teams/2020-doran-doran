import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";
import ReportPage from "@/views/ReportPage.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/reports",
    name: "ReportPage",
    component: ReportPage,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
