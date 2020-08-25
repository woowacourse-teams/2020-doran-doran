<template>
  <v-app-bar flat max-height="56" color="white">
    <v-container
      v-show="this.isSearching"
      fluid
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

      <v-toolbar-title class="app-bar-title font-size-small">
        {{ appBarTitle }}
      </v-toolbar-title>

      <v-icon v-show="searchButton" @click="toggleSearchInput">
        mdi-magnify
      </v-icon>
    </v-container>

    <v-expand-x-transition>
      <VTextField
        v-show="isSearching"
        v-model="keyword"
        autofocus
        placeholder="검색어를 입력하세요."
        color="black"
        filled
        rounded
        dense
        hide-details
        append-outer-icon="mdi-window-close"
        class="search-input font-size-small"
        @keypress.enter="filterPosts"
        @click:append-outer="initializeMapPage"
      />
    </v-expand-x-transition>
  </v-app-bar>
</template>

<script>
import { ERROR_MESSAGE, MAP_MODE } from "@/utils/constants";

export default {
  name: "DoranAppBar",
  data() {
    return {
      isSearching: false,
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
  },
  methods: {
    goToPreviousPage() {
      this.$router.go(-1);
    },
    showSidebar() {
      this.$store.commit("memberSidebar/SHOW");
    },
    setMapToDefault() {
      this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
      this.$store.commit("mapMode/CHANGE_STATE", MAP_MODE.DEFAULT);
    },
    toggleSearchInput() {
      this.isSearching = !this.isSearching;
    },
    async filterPosts() {
      if (this.keyword === "") {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.NO_KEYWORD_INPUT);
        return;
      }
      this.$store.commit("filter/SET_KEYWORD", this.keyword);
      this.$store.commit("post/CLEAR_POSTS");
      const filteredPosts = await this.$store.dispatch("filter/filterPosts");
      this.$store.commit("post/SET_POSTS", filteredPosts);
      if (filteredPosts.length === 0) {
        this.$store.commit(
          "snackbar/SHOW",
          ERROR_MESSAGE.NO_SEARCH_RESULT_MESSAGE,
        );
      }
    },
    async initializeMapPage() {
      this.toggleSearchInput();
      this.keyword = "";
      this.$store.commit("filter/SET_KEYWORD", "");
      const filteredPosts = await this.$store.dispatch("filter/filterPosts");
      await this.$store.commit("post/SET_POSTS", filteredPosts);
    },
  },
};
</script>

<style scoped>
.app-bar-title {
  position: absolute;
  left: 50%;
  transform: translate(-50%);
  padding: 8px;
}

.app-bar-right > * {
  margin-left: 8px;
}

.search-input {
  position: absolute;
  right: 18px;
  width: calc(100% - 65px);
  background-color: white;
}
</style>
