<template>
  <v-container fill-height fluid class="pa-0">
    <KakaoMap @render="renderMap" />
    <PeriodFilterButton v-if="isDefaultMode" />
    <router-link to="/timeline" class="px-3 font-size-x-small timeline-btn">
      <v-icon color="black" class="mb-1" small>mdi-format-list-bulleted</v-icon>
      목록
    </router-link>
    <MapAssistantButtons v-if="isDefaultMode" />
    <PostCreateButton class="post-create-btn" />
    <PostCreateModal v-if="isPostMode" />
    <template v-if="isMapRendered">
      <TimelineModal v-if="timeline" />
    </template>
    <PostModal v-if="post" />
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
      isMapRendered: false,
      isInitialMember: false,
      timeline: this.$route.meta.timeline,
      post: this.$route.meta.post,
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
  created() {
    this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
    this.isInitialMember = this.$store.getters["member/isInitialMember"];
  },
  methods: {
    renderMap() {
      this.isMapRendered = true;
    },
  },
  watch: {
    "$route.meta"(val) {
      this.timeline = val.timeline;
      this.post = val.post;
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
  padding-top: 6px !important;
  padding-bottom: 6px !important;
  height: 30px;
  border-radius: 100px;
  background-color: white;
  box-shadow: 1px 1px 8px grey;
}

.post-create-btn {
  position: absolute;
  bottom: 15px;
  right: 15px;
  z-index: 1;
}
</style>
