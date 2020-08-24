<template>
  <div class="assistant-buttons">
    <v-btn
      small
      fab
      :retain-focus-on-click="false"
      @click="setMapToCurrentLocation"
    >
      <v-icon>mdi-radiobox-marked</v-icon>
    </v-btn>
    <v-btn small fab class="mt-2" @click="refreshPosts">
      <v-icon>mdi-refresh</v-icon>
    </v-btn>
  </div>
</template>

<script>
export default {
  name: "MapAssistantButtons",
  methods: {
    async setMapToCurrentLocation() {
      await this.$kakaoMap.setCenterByCurrentLocation();
    },
    refreshPosts() {
      this.$store.dispatch("post/loadPosts");
      // TODO: 113번 브랜치 머지되면 24시간 내 글들만 가져오도록 로직 변경
    },
  },
};
</script>

<style scoped>
.assistant-buttons {
  display: flex;
  flex-direction: column;
  position: fixed;
  left: 15px;
  bottom: 20px;
  z-index: 9998;
}
.v-btn:not(.v-btn--text):not(.v-btn--outlined):hover:before {
  opacity: 0 !important;
}
</style>
