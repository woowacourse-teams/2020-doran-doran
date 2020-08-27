<template>
  <div class="assistant-buttons">
    <v-btn small fab @click="setCenterByCurrentLocation">
      <v-icon>mdi-crosshairs-gps</v-icon>
    </v-btn>
    <v-btn small fab class="mt-2" @click="refreshPosts">
      <v-icon>mdi-refresh</v-icon>
    </v-btn>
  </div>
</template>

<script>
import { ERROR_MESSAGE } from "@/utils/constants";

export default {
  name: "MapAssistantButtons",
  methods: {
    async setCenterByCurrentLocation() {
      await this.$kakaoMap
        .setCenterByCurrentLocation()
        .catch(() =>
          this.$store.commit(
            "snackbar/SHOW",
            ERROR_MESSAGE.UNIDENTIFIABLE_LOCATION,
          ),
        );
    },
    async refreshPosts() {
      await this.$store.commit("post/CLEAR_POSTS");
      const filteredPosts = await this.$store.dispatch("filter/filterPosts");
      await this.$store.commit("post/SET_POSTS", filteredPosts);
    },
  },
};
</script>

<style scoped>
.assistant-buttons {
  display: flex;
  flex-direction: column;
  position: absolute;
  left: 15px;
  bottom: 20px;
  z-index: 9998;
}

.v-btn:before {
  opacity: 0 !important;
}
</style>
