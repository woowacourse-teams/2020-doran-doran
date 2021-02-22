<template>
  <v-container ref="map" fill-height fluid>
    <v-icon v-if="isMarkerMode" size="40" color="red" class="center-marker">
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
      return this.$store.getters["map/isDefault"];
    },
    isMarkerMode() {
      return this.$store.getters["map/isMarker"];
    },
    posts() {
      return this.$store.getters["post/posts"];
    },
  },
  async mounted() {
    const map = await this.$kakaoMap.drawMap(this.$refs.map);
    await this.showCustomizedMessage();
    this.$store.commit("map/SET_MAP", map);
    await this.$kakaoMap.setCenterByCurrentLocation().catch(() => {});
    await this.changeAppBarByCenterAddress();
    await this.$store.dispatch("post/loadPosts");
    this.$kakaoMap.addEventToMap(
      EVENT_TYPE.CENTER_CHANGE,
      this.changeAppBarByCenterAddress,
    );
    this.isMapRendered = true;
    this.$emit("render");
  },
  methods: {
    async changeAppBarByCenterAddress() {
      const centerLocation = this.$kakaoMap.getCenterLocation();
      const centerAddress = await this.$kakaoMap.getAddress(centerLocation);
      const address = Object.values(centerAddress).join(" ");
      this.$store.commit("appBar/CHANGE_ADDRESS", address);
    },
    async checkMemberLocationInformation() {
      await this.$kakaoMap
        .getCurrentLocation()
        .then(() => {
          this.$store.commit("member/SET_LOCATION_INFORMATION", true);
        })
        .catch(() => {});
    },
    _checkIfMobile() {
      let isMobile = false;
      const filter = "win16|win32|win64|mac|macintel";
      if (navigator.platform) {
        isMobile = !filter.includes(navigator.platform.toLowerCase());
      }
      return isMobile;
    },
    async showCustomizedMessage() {
      await this.checkMemberLocationInformation();
      const hasLocationInformation = this.$store.getters[
        "member/hasLocationInformation"
      ];
      let isMobile = this._checkIfMobile();
      if (!hasLocationInformation) {
        if (isMobile) {
          this.$store.commit(
            "snackbar/SHOW",
            ERROR_MESSAGE.UNIDENTIFIABLE_LOCATION_MOBILE,
          );
        } else {
          this.$store.commit(
            "snackbar/SHOW",
            ERROR_MESSAGE.UNIDENTIFIABLE_LOCATION_PC,
          );
        }
      }
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
