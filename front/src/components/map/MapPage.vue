<template>
  <v-container fill-height fluid class="pa-0">
    <v-snackbar v-model="locationAlert" timeout="1500" top>
      ✏️ 글을 작성할 위치를 선택해주세요.
    </v-snackbar>
    <KakaoMap />
    <PostCreateButton />
    <PostCreateModal v-if="this.isPostMode" />
  </v-container>
</template>

<script>
import KakaoMap from "@/components/map/KakaoMap";
import PostCreateButton from "@/components/map/PostCreateButton";
import PostCreateModal from "@/components/map/PostCreateModal";

export default {
  name: "MapPage",
  components: {
    KakaoMap,
    PostCreateButton,
    PostCreateModal,
  },
  mounted() {
    this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
  },
  data() {
    return {
      locationAlert: this.isMarkerMode,
    };
  },
  computed: {
    isMarkerMode() {
      return this.$store.getters["modal/isMarkerMode"];
    },
    isPostMode() {
      return this.$store.getters["modal/isPostMode"];
    },
  },
  watch: {
    isMarkerMode(val) {
      this.locationAlert = val;
    },
  },
};
</script>

<style scoped></style>
