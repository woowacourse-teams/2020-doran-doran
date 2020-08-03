<template>
  <v-container fill-height fluid class="pa-0">
    <KakaoMap />

    <PostCreateButton @click.native="showModal = true" />

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
            autofocus
            no-resize
          />
          <v-btn
            @click.prevent="createPost"
            class="float-right"
            color="amber accent-4"
          >
            등록
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
import router from "../router";
import { ERROR_MESSAGE } from "../utils/constants";

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
      content: "",
    };
  },
  methods: {
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
        .then(() => (this.showModal = false))
        .then(() => router.push("/")); //not working
    },
  },
};
</script>

<style scoped>
.map-container {
  height: 40vh;
}
</style>
