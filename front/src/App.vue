<template>
  <v-app>
    <DoranAppBar />
    <RouterView />
  </v-app>
</template>

<script>
import DoranAppBar from "./components/DoranAppBar";

export default {
  name: "app",
  components: {
    DoranAppBar,
  },
};
</script>

<style>
@import "./assets/styles/post-overlay.css";
</style>
