<template>
  <v-container fill-height fluid class="pa-0">
    <v-snackbar v-model="snackbar" timeout="1500" top>
      ✏️ 글을 작성할 위치를 선택해주세요.
    </v-snackbar>
    <KakaoMap :show-overlay="showOverlay" :usage="mapUsage" />

    <PostCreateButton @click.native="showMarker" />

    <Modal v-if="showModal" @close="showModal = false">
      <template slot="content">
        <v-icon class="mdi pt-3 px-3">mdi-map-marker</v-icon>
        <span class="pt-3">글 위치 글 위치</span>

        <v-form class="pa-3 fill-height">
          <VTextarea
            v-model="content"
            type="text"
            name="content"
            placeholder="글 내용을 입력해주세요."
            maxlength="200"
            :counter="200"
            required
            solo
            no-resize
          />

          <v-btn
            @click.prevent="createPost"
            class="float-right my-6 mx-1 rounded-pill"
            color="amber accent-3"
          >
            등록
          </v-btn>
          <v-btn
            @click.prevent="cancelPost"
            class="float-right my-6 mx-1 rounded-pill"
            color="grey lighten-4"
          >
            취소
          </v-btn>
        </v-form>
      </template>
    </Modal>
  </v-container>
</template>

<script>
import KakaoMap from "./KakaoMap";
import PostCreateButton from "./PostCreateButton";
import Modal from "./Modal";
import { ERROR_MESSAGE } from "../utils/constants";
import router from "../router";

export default {
  name: "MainPage",
  components: {
    KakaoMap,
    PostCreateButton,
    Modal,
  },
  data() {
    return {
      showModal: false,
      showOverlay: true,
      mapUsage: "default-map",
      snackbar: false,
      content: "",
    };
  },
  methods: {
    showMarker() {
      //메인 화면 -> 글 작성할 위치에 마커 찍는 화면
      if (this.mapUsage !== "create-post") {
        this.showOverlay = false;
        this.mapUsage = "create-post";
        this.snackbar = true;
      } else {
        //마커 찍는 화면 -> 글 작성 모달
        this.showOverlay = false;
        this.mapUsage = "";
        this.showModal = true;
      }
    },
    createPost() {
      if (this.content === "") {
        alert(ERROR_MESSAGE.POST_CONTENT_NOT_FOUND);
        return;
      }
      const data = {
        memberId: 1,
        content: this.content,
        location: this.$getCenterLocation(),
      };
      this.$store
        .dispatch("post/createPost", data)
        .then(() => alert("글이 등록되었습니다."))
        .then(() => (this.content = ""))
        .then(() => (this.showModal = false))
        .then(() => (this.showOverlay = true));
      router.go(0);
    },
    cancelPost() {
      this.content = "";
      this.showModal = false;
      this.showOverlay = true;
    },
  },
};
</script>

<style scoped>
.map-container {
  height: 40vh;
}
</style>
