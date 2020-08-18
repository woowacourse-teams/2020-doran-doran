<template>
  <div class="pt-3 comment-input">
    <VTextField
      v-model="content"
      placeholder="댓글을 입력해주세요."
      append-icon="mdi-pencil"
      maxlength="120"
      dense
      rounded
      filled
      color="black"
      @click:append="createComment"
      @keydown.enter="createComment"
    />
  </div>
</template>

<script>
import { ERROR_MESSAGE } from "@/utils/constants";

const CREATE_COMMENT_SUCCESS_MESSAGE = "🎉 댓글이 등록되었습니다.";

export default {
  name: "CommentInput",
  props: {
    postId: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      content: "",
    };
  },
  methods: {
    async createComment() {
      if (this.content === "") {
        this.$emit("input", ERROR_MESSAGE.NO_CONTENT_MESSAGE);
        return;
      }

      const authorLocation = await this.$kakaoMap.getCurrentLocation();
      if (!authorLocation) {
        this.$emit("input", ERROR_MESSAGE.UNIDENTIFIABLE_LOCATION);
        return;
      }

      const data = {
        memberId: 1,
        postId: this.postId,
        content: this.content,
        location: authorLocation,
      };
      await this.$store.dispatch("comment/createComment", data);
      await this.$store.dispatch("post/loadPost", this.postId);
      this.content = "";
      this.$emit("input", CREATE_COMMENT_SUCCESS_MESSAGE);
    },
  },
};
</script>

<style scoped>
.comment-input {
  position: fixed;
  bottom: -1%;
  left: 50%;
  transform: translate(-50%);
  width: 90vw;
  background-color: white;
}
</style>
