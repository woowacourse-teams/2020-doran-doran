<template>
  <v-app>
    <Header />
    <router-view></router-view>
  </v-app>
</template>

<script>
import Header from "./components/Header";

export default {
  name: "app",
  components: {
    Header,
  },
};
</script>

<style>
@import "./assets/styles/post-overlay.css";
</style>
