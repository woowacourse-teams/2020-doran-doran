<template>
  <v-container fill-height fluid class="pa-0">
    <PeriodFilterButton v-if="this.isDefaultMode"/>
    <KakaoMap />
    <PostCreateButton />
    <PostCreateModal
      v-if="this.isPostMode"
      :location="this.$kakaoMap.getCenterLocation()"
    />
  </v-container>
</template>

<script>
import KakaoMap from "@/components/map/KakaoMap";
import PostCreateButton from "@/components/map/PostCreateButton";
import PostCreateModal from "@/components/map/PostCreateModal";
import PeriodFilterButton from "@/components/map/PeriodFilterButton";

export default {
  name: "MapPage",
  components: {
    PeriodFilterButton,
    KakaoMap,
    PostCreateButton,
    PostCreateModal,
  },
  created() {
    this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
    this.checkUrl();
    this.checkToken();
  },
  computed: {
    isDefaultMode() {
      return this.$store.getters["mapMode/isDefault"];
    },
    isMarkerMode() {
      return this.$store.getters["mapMode/isMarker"];
    },
    isPostMode() {
      return this.$store.getters["mapMode/isPost"];
    },
  },
  methods: {
    checkUrl() {
      const urlToken = location.href.split("token=")[1];
      if (urlToken) {
        localStorage.setItem("accessToken", urlToken);
        location.href = "/";
      }
    },
    async checkToken() {
      const storageToken = localStorage.getItem("accessToken");
      if (storageToken && storageToken !== "guest") {
        await this.$store.dispatch("member/loadMember");
      } else if (!storageToken) {
        this.$router.push("/login");
      }
    },
  },
};
</script>

<style scoped></style>
