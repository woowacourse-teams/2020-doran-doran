<template>
  <v-container fill-height fluid className="pa-0">
    <v-snackbar v-model="locationAlert" timeout="1500" top>
      ✏️ 글을 작성할 위치를 선택해주세요.
    </v-snackbar>
    <KakaoMap />
    <PostCreateButton />
    <PostCreateModal v-if="this.isPostMode" />
  </v-container>
</template>

<script>
import KakaoMap from "./KakaoMap";
import PostCreateButton from "./PostCreateButton";
import PostCreateModal from "./PostCreateModal";

export default {
  name: "MapPage",
  components: {
    KakaoMap,
    PostCreateButton,
    PostCreateModal,
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
};
</script>

<style scoped></style>
