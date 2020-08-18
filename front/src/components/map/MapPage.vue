<template>
  <v-container fill-height fluid class="pa-0">
    <v-snackbar top timeout="1500" v-model="snackbarWarning">
      {{ snackbarMessage }}
    </v-snackbar>
    <KakaoMap />
    <PostCreateButton />
    <PostCreateModal v-if="this.isPostMode" @create-post="createPost" />
  </v-container>
</template>

<script>
import KakaoMap from "@/components/map/KakaoMap";
import PostCreateButton from "@/components/map/PostCreateButton";
import PostCreateModal from "@/components/map/PostCreateModal";

const MARKER_MODE_MESSAGE = "✏️ 글을 작성할 위치를 선택해주세요.";
const CREATE_POST_SUCCESS_MESSAGE = "🎉 글이 등록되었습니다."

export default {
  name: "MapPage",
  components: {
    KakaoMap,
    PostCreateButton,
    PostCreateModal,
  },
  data() {
    return {
      snackbarWarning: this.isMarkerMode,
      snackbarMessage: "",
    };
  },
  created() {
    this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
  },
  computed: {
    isMarkerMode() {
      return this.$store.getters["modal/isMarkerMode"];
    },
    isPostMode() {
      return this.$store.getters["modal/isPostMode"];
    },
  },
  methods: {
    createPost() {
      this.snackbarMessage = CREATE_POST_SUCCESS_MESSAGE;
      this.snackbarWarning = true;
    },
  },
  watch: {
    isMarkerMode(val) {
      this.snackbarMessage = MARKER_MODE_MESSAGE;
      this.snackbarWarning = val;
    },
  },
};
</script>

<style scoped></style>
