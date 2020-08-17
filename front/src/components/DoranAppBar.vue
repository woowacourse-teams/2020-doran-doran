<template>
  <v-app-bar v-show="appBarVisible" flat max-height="56" color="white">
    <v-container
      fluid
      class="d-flex flex-row align-center justify-space-between pa-0"
    >
      <v-icon v-show="backButton" @click="goToPreviousPage">
        mdi-chevron-left
      </v-icon>
      <v-icon v-show="myPageButton" @click="goToMyPage">
        mdi-account
      </v-icon>

      <v-toolbar-title class="app-bar-title">
        {{ appBarTitle }}
      </v-toolbar-title>

      <div class="text-right app-bar-right">
        <router-link v-show="searchButton" to="/search">
          <v-icon>mdi-magnify</v-icon>
        </router-link>
        <router-link v-show="timelineButton" to="/timeline">
          <v-icon>mdi-format-list-bulleted</v-icon>
        </router-link>
        <router-link v-show="mapButton" to="/">
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
    appBarVisible() {
      return this.$store.getters["appBar/appBarVisible"];
    },
    backButton() {
      return this.$store.getters["appBar/backButton"];
    },
    myPageButton() {
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
  padding: 8px;
}
.app-bar-right > * {
  margin-left: 8px;
}
</style>
