<template>
  <v-container ref="map" fill-height fluid>
    <v-icon
      v-if="this.isMarkerMode"
      size="40"
      color="red"
      class="center-marker"
    >
      mdi-map-marker
    </v-icon>
    <template v-if="isMapRendered" class="d-none">
      <PostOverlay v-for="post in posts" :key="post.id" :post="post" />
    </template>
  </v-container>
</template>

<script>
import PostOverlay from "@/components/map/PostOverlay";
import { EVENT_TYPE } from "@/utils/constants";

export default {
  name: "KakaoMap",
  components: {
    PostOverlay,
  },
  data() {
    return {
      isMapRendered: false,
    };
  },
  computed: {
    isDefaultMode() {
      return this.$store.getters["modal/isDefaultMode"];
    },
    isMarkerMode() {
      return this.$store.getters["modal/isMarkerMode"];
    },
    posts() {
      return this.$store.getters["post/getPosts"];
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
      await this.changeAppBarAddressByCenterLocation();
      this.$clearPostOverlay();
      await this.$store.dispatch("post/loadPosts");
      await this.$addEventToMap(
        EVENT_TYPE.CENTER_CHANGE,
        this.changeAppBarAddressByCenterLocation,
      );
      this.isMapRendered = true;
    },
    async changeAppBarAddressByCenterLocation() {
      const centerLocation = await this.$getCenterLocation();
      const centerAddress = await this.$getAddress(centerLocation);
      const address = Object.values(centerAddress).join(" ");
      this.$store.commit("appBar/CHANGE_TITLE", address);
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