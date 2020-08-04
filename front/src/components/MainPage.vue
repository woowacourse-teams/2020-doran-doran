<template>
  <v-container fill-height fluid class="pa-0">
    <KakaoMap :show-overlay="showOverlay" />

    <PostCreateButton @click.native="showPostCreateModal" />

    <Modal v-if="showModal" @close="showModal = false">
      <template slot="content">
        <v-form class="pa-3 fill-height">
          <v-container fluid class="map-container ma-0 pa-0 mb-6">
            <KakaoMap :usage="'create-post'" />
          </v-container>
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
            @click.prevent="showModal = false"
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
      content: "",
    };
  },
  methods: {
    showPostCreateModal() {
      this.showOverlay = false;
      // this.showModal = true;
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
        .then(() => (this.showModal = false));
      router.go(0);
    },
  },
};
</script>

<style scoped>
.map-container {
  height: 40vh;
}
</style>
