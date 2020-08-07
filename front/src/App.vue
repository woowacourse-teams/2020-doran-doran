<template>
  <div class="v-application v-application--is-ltr theme--light app-container">
    <DoranAppBar />
    <RouterView />
  </div>
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
</style>
