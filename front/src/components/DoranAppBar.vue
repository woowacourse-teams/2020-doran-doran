<template>
  <v-app-bar v-show="appBarVisible" flat max-height="56" color="white">
    <v-container
      fluid
      class="d-flex flex-row align-center justify-space-between pa-0"
    >
      <v-icon v-show="backButton" @click="goToPreviousPage">
        mdi-chevron-left
      </v-icon>
      <v-icon v-show="myPageButton" @click="openSideBar">
        mdi-account
      </v-icon>

      <v-toolbar-title class="app-bar-title">
        {{ appBarTitle }}
      </v-toolbar-title>

      <div class="text-right app-bar-right">
        <router-link v-show="searchButton" to="/search">
          <v-icon>mdi-magnify</v-icon>
        </router-link>
        <v-icon v-show="timelineButton" @click="goToTimelinePage">
          mdi-format-list-bulleted
        </v-icon>
        <router-link v-show="mapButton" to="/map">
          <v-icon>mdi-map</v-icon>
        </router-link>
      </div>
    </v-container>
  </v-app-bar>
</template>

<script>
export default {
  name: "DoranAppBar",
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
  methods: {
    goToPreviousPage() {
      this.$router.go(-1);
    },
    openSideBar() {
      this.$store.commit("myPageSideBar/ACTIVATE_SIDE_BAR");
    },
    goToTimelinePage() {
      const boundsFromKakao = this.$kakaoMap.getBounds();
      const bounds = {
        upperBound: boundsFromKakao.ja,
        lowerBound: boundsFromKakao.ka,
        leftBound: boundsFromKakao.da,
        rightBound: boundsFromKakao.ia,
      };
      const params = new URLSearchParams(bounds).toString();
      this.$router.push("/timeline?" + params);
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
