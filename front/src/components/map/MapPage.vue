<template>
  <v-container fill-height fluid class="pa-0">
    <KakaoMap />
    <PeriodFilterButton v-if="isDefaultMode" />
    <v-btn color="white" class="timeline-btn" @click="goToTimeline">
      <v-icon>mdi-format-list-bulleted</v-icon>
      목록
    </v-btn>
    <MapAssistantButtons v-if="isDefaultMode" />
    <PostCreateButton class="post-create-btn" />
    <PostCreateModal
      v-if="isPostMode"
      :location="this.$kakaoMap.getCenterLocation()"
    />
    <MemberUpdateModal
      v-if="isInitialMember"
      @close="closeMemberUpdateModal"
    />
  </v-container>
</template>

<script>
import KakaoMap from "@/components/map/KakaoMap";
import PostCreateButton from "@/components/map/PostCreateButton";
import PostCreateModal from "@/components/map/PostCreateModal";
import MapAssistantButtons from "@/components/map/MapAssistantButtons";
import PeriodFilterButton from "@/components/map/filter/PeriodFilterButton";
import MemberUpdateModal from "@/components/member/MemberUpdateModal";

export default {
  name: "MapPage",
  components: {
    MapAssistantButtons,
    PeriodFilterButton,
    KakaoMap,
    PostCreateButton,
    PostCreateModal,
    MemberUpdateModal,
  },
  data() {
    return {
      isInitialMember: false,
    };
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
  async created() {
    this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
    this.checkUrl();
    await this.checkToken();
    this.isInitialMember = this.$store.getters["member/isInitialMember"];
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
    closeMemberUpdateModal() {
      this.isInitialMember = false;
    },
    goToTimeline() {
      const boundsFromKakao = this.$kakaoMap.getBounds();
      const bounds = {
        upperBound: boundsFromKakao.ja,
        lowerBound: boundsFromKakao.ka,
        leftBound: boundsFromKakao.da,
        rightBound: boundsFromKakao.ia,
      };
      const params = new URLSearchParams(bounds).toString();
      this.$router.push("/timeline?" + params);
    },
  },
};
</script>

<style scoped>
.timeline-btn {
  position: fixed;
  top: 60px;
  right: 5px;
  z-index: 9999;
}

.post-create-btn {
  position: absolute;
  bottom: 15px;
  right: 15px;
  z-index: 1;
}
</style>
