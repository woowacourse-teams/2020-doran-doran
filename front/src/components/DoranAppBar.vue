<template>
  <v-app-bar flat max-height="56" color="white" color="white">
    <v-btn icon fab text @click="goToPreviousPage" fixed left>
      <v-icon>mdi-chevron-left</v-icon>
    </v-btn>
    <VSpacer />
    <v-toolbar-title class="app-bar-title">
      {{ centerAddress }}
    </v-toolbar-title>
    <VSpacer />
  </v-app-bar>
</template>

<script>
import router from "@/router";

export default {
  name: "DoranAppBar",
  methods: {
    goToPreviousPage() {
      router.go(-1);
    },
  },
  computed: {
    centerAddress() {
      return this.$store.getters["appBar/centerAddress"];
    },
  },
};
</script>

<style scoped>
.app-bar-title {
  font-size: 1rem;
}
</style>
