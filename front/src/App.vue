<template>
  <v-app class="app-container">
    <DoranAppBar />
    <RouterView />
  </v-app>
</template>

<script>
import DoranAppBar from "@/components/DoranAppBar";

export default {
  name: "App",
  components: {
    DoranAppBar,
  },
};
</script>

<style>
@import "./assets/styles/post-overlay.css";
@import "./assets/styles/index.css";

html,
body {
  width: 100%;
  height: 100%;
  letter-spacing: -0.7px;
}

.app-container {
  width: 100%;
  height: 100%;
  flex-direction: column;
}

.app-container > div {
  height: 100%;
}
</style>
