<template>
  <v-app-bar flat max-height="56" color="white">
    <v-container
      fluid
      class="d-flex flex-row align-center justify-space-between pa-0"
    >
      <v-icon v-if="backButton" @click="goToPreviousPage">mdi-chevron-left</v-icon>
      <v-icon v-if="myPageButton" @click="goToMyPage">mdi-account</v-icon>

      <v-toolbar-title class="app-bar-title">
        {{ appBarTitle }}
      </v-toolbar-title>

      <div class="text-right">
        <router-link v-if="searchButton" to="/search" class="ml-2">
          <v-icon>mdi-magnify</v-icon>
        </router-link>
        <router-link v-if="timelineButton" to="/timeline" class="ml-2">
          <v-icon>mdi-format-list-bulleted</v-icon>
        </router-link>
        <router-link v-if="mapButton" to="/" class="ml-2">
          <v-icon>mdi-map</v-icon>
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
    searchButton() {
      return this.$store.getters["appBar/searchButton"];
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
