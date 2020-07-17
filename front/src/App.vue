<template>
  <v-app>
    <doran-app-bar />
    <router-view></router-view>
  </v-app>
</template>

<script>
import doranAppBar from "./components/DoranAppBar";

export default {
  name: "app",
  components: {
    doranAppBar,
  },
};
</script>

<style>
@import "./assets/styles/post-overlay.css";
</style>
