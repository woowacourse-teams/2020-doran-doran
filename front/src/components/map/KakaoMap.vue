<template>
  <v-container fill-height fluid ref="map">
    <v-icon
      class="center-marker"
      color="red"
      size="40"
      v-if="this.isMarkerMode"
    >
      mdi-map-marker
    </v-icon>
  </v-container>
</template>

<script>
export default {
  name: "KakaoMap",
  computed: {
    isDefaultMode() {
      return this.$store.getters["modal/isDefaultMode"];
    },
    isMarkerMode() {
      return this.$store.getters["modal/isMarkerMode"];
    },
  },
  async mounted() {
    await this.$drawMap(this.$refs.map);
    if (this.isDefaultMode) {
      await this.initMainMap();
    }
  },
  watch: {
    isDefaultMode(val) {
      val ? this.$showPostOverlays() : this.$closePostOverlays();
    },
  },
  methods: {
    async initMainMap() {
      const currentLocation = await this.$getCurrentLocation();
      this.$setLocation(currentLocation);
      this.$setCurrentLocationMarker(currentLocation);
      await this.setInitialAppBarAddress();
      await this.$store.dispatch("post/loadPosts");
      await this.drawPosts();
      await this.$watchMapCenterChange(
        this.changeAppBarAddressByCenterLocation,
      );
    },
    async setInitialAppBarAddress() {
      const centerLocation = this.$getCenterLocation();
      await this.$getAddress(centerLocation).then((centerAddress) => {
        this.$store.commit("appBar/CHANGE_ADDRESS", centerAddress);
      });
    },
    async changeAppBarAddressByCenterLocation() {
      const centerLocation = await this.$getCenterLocation();
      const centerAddress = await this.$getAddress(centerLocation);
      this.$store.commit("appBar/CHANGE_ADDRESS", centerAddress);
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
  margin-top: -35px;
  text-shadow: 2px 2px 3px gray;
}
</style>
