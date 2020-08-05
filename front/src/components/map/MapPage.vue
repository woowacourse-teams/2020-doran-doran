<template>
  <v-container fill-height fluid class="pa-0">
    <v-snackbar v-model="snackbarMarker" timeout="1500" top>
      ✏️ 글을 작성할 위치를 선택해주세요.
    </v-snackbar>
    <KakaoMap />

    <PostCreateButton @click.native="showMarker" />

    <PostCreateModal v-if="this.postMode" />
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
      snackbarMarker: false,
      content: "",
    };
  },
  computed: {
    mapMode() {
      return this.$store.getters["modal/isDefaultMode"];
    },
    markerMode() {
      return this.$store.getters["modal/isMarkerMode"];
    },
    postMode() {
      return this.$store.getters["modal/isPostMode"];
    },
  },
  watch: {
    showOverlay(val) {
      if (val) {
        this.$showOverlays();
      } else {
        this.$closeOverlays();
      }
    }
  },
  methods: {
    showMarker() {
      if (this.mapMode) {
        this.$store.commit("modal/CHANGE_STATE", MAP_MODE.MARKER);
        this.showOverlay = false;
        this.snackbarMarker = true;
      } else if (this.markerMode) {
        this.$store.commit("modal/CHANGE_STATE", MAP_MODE.POST);
        this.showModal = true;
      }
    },
  },
}
</script>

<style scoped></style>
