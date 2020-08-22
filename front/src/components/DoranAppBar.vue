<template>
  <v-app-bar flat max-height="56" color="white">
    <v-container
      class="d-flex flex-row align-center justify-space-between pa-0"
    >
      <v-icon v-show="backButton" @click="goToPreviousPage">
        mdi-chevron-left
      </v-icon>
      <v-icon v-show="myPageButton" @click="showSidebar">
        mdi-account
      </v-icon>
      <!--TODO: backButton으로 변경하기-->
      <v-icon v-show="cancelButton" @click="setMapToDefault">
        mdi-chevron-left
      </v-icon>

      <v-toolbar-title class="app-bar-title">
        {{ appBarTitle }}
      </v-toolbar-title>

      <div class="text-right app-bar-right">
        <v-icon v-show="searchButton" @click="toggleMode">
          mdi-magnify
        </v-icon>
        <v-icon v-show="timelineButton" @click="goToTimelinePage">
          mdi-format-list-bulleted
        </v-icon>
        <router-link v-show="mapButton" to="/">
          <v-icon>mdi-map</v-icon>
        </router-link>
      </div>
    </v-container>

    <v-expand-x-transition>
      <v-container
        v-if="!this.defaultMode"
        fluid
        class="d-flex flex-row align-center justify-space-between pa-0"
      >
        <VTextField
          v-model="keyword"
          autofocus
          placeholder="검색어를 입력하세요."
          color="amber accent-3"
          filled
          rounded
          dense
          hide-details
          class="mx-2 font-size-small"
          @keypress.enter="filterPosts"
        />
        <v-icon @click="showEveryPosts">
          mdi-window-close
        </v-icon>
      </v-container>
    </v-expand-x-transition>
  </v-app-bar>
</template>

<script>
import { DATE_FILTER_TYPE } from "@/utils/time-filter-type";
import { MAP_MODE } from "@/utils/constants";

export default {
  name: "DoranAppBar",
  data() {
    return {
      defaultMode: true,
      keyword: "",
    };
  },
  computed: {
    backButton() {
      return this.$store.getters["appBar/backButton"];
    },
    myPageButton() {
      return this.$store.getters["appBar/myPageButton"];
    },
    cancelButton() {
      return this.$store.getters["appBar/cancelButton"];
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
    showSidebar() {
      this.$store.commit("memberSidebar/SHOW");
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
    setMapToDefault() {
      this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
      this.$store.commit("mapMode/CHANGE_STATE", MAP_MODE.DEFAULT);
    },
    toggleMode() {
      this.defaultMode = !this.defaultMode;
    },
    async filterPosts() {
      const data = {
        keyword: this.keyword,
      };
      const dateType = Object.values(DATE_FILTER_TYPE()).find(
        (dateType) => dateType.name === this.radios,
      );
      if (!dateType) {
        data.startDate = "2020-08-19" + " 00:00:00";
        data.endDate = "2020-09-20" + " 23:59:59";
      } else {
        data.startDate = dateType.startDate;
        data.endDate = this.$moment().format("YYYY-MM-DD HH:mm:ss");
      }
      await this.$store.dispatch("post/searchPosts", data);
      this.keyword = "";
    },
    async showEveryPosts() {
      this.toggleMode();
      this.keyword = "";
      await this.$store.dispatch("post/loadPosts");
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
