<template>
  <v-container fill-height fluid class="pa-0">
    <KakaoMap />
    <PeriodFilterButton v-if="isDefaultMode" />
    <router-link to="/timeline" class="pa-2 text-subtitle-2 timeline-btn">
      <v-icon dense>mdi-format-list-bulleted</v-icon>
      목록
    </router-link>
    <MapAssistantButtons v-if="isDefaultMode"/>
    <PostCreateButton class="post-create-btn" />
    <PostCreateModal
      v-if="isPostMode"
      :location="this.$kakaoMap.getCenterLocation()"
    />
    <template v-if="timeline">
      <TimelineModal />
    </template>
    <v-slide-y-reverse-transition>
      <PostModal v-if="postModal" />
    </v-slide-y-reverse-transition>
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
import TimelineModal from "@/components/timeline/TimelineModal";
import MemberUpdateModal from "@/components/member/MemberUpdateModal";
import PostModal from "@/components/post/PostModal";

export default {
  name: "MapPage",
  components: {
    MapAssistantButtons,
    PostModal,
    PeriodFilterButton,
    TimelineModal,
    KakaoMap,
    PostCreateButton,
    PostCreateModal,
    MemberUpdateModal,
  },
  data() {
    return {
      isInitialMember: false,
      timeline: this.$route.meta.timeline,
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
    postModal() {
      return this.$store.getters["post/modal"];
    },
  },
  async created() {
    this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
    this.isInitialMember = this.$store.getters["member/isInitialMember"];
  },
  watch: {
    "$route.meta"({ timeline }) {
      this.timeline = timeline;
    },
  },
};
</script>

<style scoped>
.timeline-btn {
  position: absolute;
  top: 66px;
  right: 10px;
  z-index: 1;
  border-radius: 5px;
  background-color: white;
}

.post-create-btn {
  position: absolute;
  bottom: 15px;
  right: 15px;
  z-index: 1;
}
</style>
