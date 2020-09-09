import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import ManageReport from "../views/ManageReport.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/manage-report",
    name: "ManageReport",
    component: ManageReport,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
