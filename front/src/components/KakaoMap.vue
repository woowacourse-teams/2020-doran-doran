<template>
  <v-container fill-height fluid ref="map">
    <v-icon
      class="center-marker"
      color="blue"
      size="50"
      v-if="this.isCreatePost"
    >
      mdi-map-marker
    </v-icon>
  </v-container>
</template>

<script>
export default {
  name: "KakaoMap",
  props: {
    usage: {
      type: String,
      default: "default-map",
    },
    showOverlay: {
      type: Boolean,
    },
  },
  async mounted() {
    await this.$drawMap(this.$refs.map);
    if (!this.isCreatePost) {
      await this.initMainMap();
    }
  },
  computed: {
    isCreatePost() {
      return this.usage === "create-post";
    },
  },
  watch: {
    showOverlay() {
      if (this.showOverlay) {
        this.$showOverlays();
      } else {
        this.$closeOverlays();
      }
    },
  },
  methods: {
    async initMainMap() {
      const currentLocation = await this.$getCurrentLocation();
      this.$setLocation(currentLocation);
      this.$setCurrentLocationMarker(currentLocation);
      await this.$store.dispatch("post/loadPosts");
      await this.drawPosts();
    },
    drawPosts() {
      this.$store.getters["post/getPosts"].forEach((post) => {
        this.$setPostOverlay(post);
      });
    },
  },
};
</script>

<style scoped>
.center-marker {
  position: relative;
  z-index: 2;
  left: 50%;
  margin-left: -20px;
}
</style>
