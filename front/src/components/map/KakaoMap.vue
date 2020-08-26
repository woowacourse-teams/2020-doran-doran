<template>
  <v-container ref="map" fill-height fluid>
    <v-icon
      v-if="isMarkerMode"
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
import { ERROR_MESSAGE, EVENT_TYPE } from "@/utils/constants";

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
      return this.$store.getters["mapMode/isDefault"];
    },
    isMarkerMode() {
      return this.$store.getters["mapMode/isMarker"];
    },
    posts() {
      return this.$store.getters["post/posts"];
    },
  },
  async mounted() {
    await this.$kakaoMap.drawMap(this.$refs.map);
    await this.$kakaoMap
      .setCenterByCurrentLocation()
      .catch(() =>
        this.$store.commit(
          "snackbar/SHOW",
          ERROR_MESSAGE.UNIDENTIFIABLE_LOCATION,
        ),
      );
    await this.changeAppBarByCenterAddress();
    await this.$store.commit("filter/SET_FILTER_FROM_X_HOURS_AGO_TO_NOW", 24);
    const filteredPosts = await this.$store.dispatch("filter/filterPosts");
    await this.$store.commit("post/SET_POSTS", filteredPosts);
    await this.$kakaoMap.addEventToMap(
      EVENT_TYPE.CENTER_CHANGE,
      this.changeAppBarByCenterAddress,
    );
    this.isMapRendered = true;
  },
  methods: {
    async changeAppBarByCenterAddress() {
      const centerLocation = await this.$kakaoMap.getCenterLocation();
      const centerAddress = await this.$kakaoMap.getAddress(centerLocation);
      const address = Object.values(centerAddress).join(" ");
      this.$store.commit("appBar/CHANGE_ADDRESS", address);
    },
  },
  watch: {
    posts() {
      this.$kakaoMap.clearClusterer();
    },
    isDefaultMode(val) {
      val
        ? this.$kakaoMap.showPostOverlays()
        : this.$kakaoMap.hidePostOverlays();
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
  text-shadow: 2px 2px 3px grey;
}
</style>
