<template>
  <v-app-bar flat max-height="64">
    <v-btn icon fab text @click="goToPreviousPage">
      <v-icon>mdi-chevron-left</v-icon>
    </v-btn>
    <VSpacer />
    <v-icon class="mdi pr-1">mdi-map-marker</v-icon>
    <v-toolbar-title class="app-bar-title">
      서울특별시 송파구 가락본동
    </v-toolbar-title>
    <VSpacer />
  </v-app-bar>
</template>

<script>
import router from "../router/index";

export default {
  name: "doranAppBar",
  methods: {
    goToPreviousPage() {
      router.go(-1);
    }
  }
};
</script>

<style scoped>
.app-bar-title {
  font-size: 1rem;
}
</style>
