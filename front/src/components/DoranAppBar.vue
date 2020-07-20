<template>
  <v-app-bar flat>
    <v-btn icon fab text href="/">
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
export default {
  name: "doranAppBar",
};
</script>

<style scoped>
.app-bar-title {
  font-size: 1rem;
}
</style>
