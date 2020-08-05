<template>
  <v-container fill-height fluid class="pa-0">
    <v-snackbar v-model="snackbarMarker" timeout="1500" top>
      ✏️ 글을 작성할 위치를 선택해주세요.
    </v-snackbar>
    <KakaoMap />

    <PostCreateButton @click.native="showMarker" />

    <PostCreateModal v-if="this.postCreateState" />
  </v-container>
</template>

<script>
import KakaoMap from "./KakaoMap";
import PostCreateButton from "./PostCreateButton";
import PostCreateModal from "./PostCreateModal";
import { MAP_MODE } from "@/utils/constants";

export default {
  name: "MapPage",
  components: {
    KakaoMap,
    PostCreateButton,
    PostCreateModal,
  },
  data() {
    return {
      showModal: false,
      showOverlay: true,
      mapUsage: "default-map",
      snackbarMarker: false,
      snackbarCreated: false,
      content: "",
    };
  },
  computed: {
    mapState() {
      return this.$store.getters["modal/isDefaultMode"];
    },
    markerState() {
      return this.$store.getters["modal/isMarkerMode"];
    },
    postCreateState() {
      return this.$store.getters["modal/isPostMode"];
    },
  },
  methods: {
    showMarker() {
      if (this.mapState) {
        this.$store.commit("modal/CHANGE_STATE", MAP_MODE.MARKER);
        this.showOverlay = false;
        this.snackbarMarker = true;
      } else if (this.markerState) {
        this.$store.commit("modal/CHANGE_STATE", MAP_MODE.POST);
        this.mapUsage = "";
        this.showModal = true;
      }
    },
  },
};
</script>

<style scoped>
.map-container {
  height: 40vh;
}
</style>
