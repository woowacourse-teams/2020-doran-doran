<template>
  <v-app-bar flat max-height="56" color="white">
    <v-container
      fluid
      class="d-flex flex-row align-center justify-space-between pa-0"
    >
      <v-icon @click="goToPreviousPage">mdi-chevron-left</v-icon>
      <v-toolbar-title class="app-bar-title">
        {{ centerAddress }}
      </v-toolbar-title>
      <div class="text-right">
        <router-link to="/search" class="mr-2">
          <v-icon>mdi-magnify</v-icon>
        </router-link>
        <router-link to="/timeline">
          <v-icon>mdi-format-list-bulleted</v-icon>
        </router-link>
      </div>
    </v-container>
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
    goToMyPage() {
      router.push("/mypage");
    },
  },
  computed: {
    backButton(){
      return this.$store.getters["appBar/backButton"];
    },
    myPageButton(){
      return this.$store.getters["appBar/myPageButton"];
    },
    appBarTitle() {
      return this.$store.getters["appBar/title"];
    },
    timelineButton() {
      return this.$store.getters["appBar/timelineButton"];
    },
    mapButton() {
      return this.$store.getters["appBar/mapButton"];
    },
  },
};
</script>

<style scoped>
.app-bar-title {
  position: fixed;
  left: 50%;
  transform: translate(-50%);
  font-size: 0.9rem;
}
</style>
