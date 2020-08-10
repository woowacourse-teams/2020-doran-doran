<template>
  <v-app class="app-container">
    <DoranAppBar />
    <transition name="fade" mode="out-in">
      <RouterView />
    </transition>
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

.fade-enter-active,
.fade-leave-active {
  transition-duration: 0.2s;
  transition-property: opacity;
  transition-timing-function: ease;
}

.fade-enter,
.fade-leave-active {
  opacity: 0;
}
</style>
