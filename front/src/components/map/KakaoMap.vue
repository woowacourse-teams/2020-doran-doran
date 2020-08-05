<template>
  <v-container fill-height fluid ref="map">
    <v-icon
      class="center-marker"
      color="red"
      size="40"
      v-if="this.isMarkerState"
    >
      mdi-map-marker
    </v-icon>
  </v-container>
</template>

<script>
export default {
  name: "KakaoMap",
  computed: {
    isMapState() {
      return this.$store.getters["modal/isDefaultMode"];
    },
    isMarkerState() {
      return this.$store.getters["modal/isMarkerMode"];
    }
  },
  watch: {
    showOverlay() {
      if (this.isMapState()) {
        this.$closeOverlays();
      } else {
        this.$showOverlays();
      }
    },
  },
  async mounted() {
    await this.$drawMap(this.$refs.map);
    if (!this.isCreatePost) {
      await this.initMainMap();
    }
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
