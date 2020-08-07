<template>
  <div class="pt-3 comment-input">
    <v-text-field
      v-model="content"
      placeholder="댓글을 입력해주세요."
      maxlength="120"
      append-icon="mdi-pencil"
      @click:append="createComment"
      color="black"
      rounded
      dense
      filled
    />
  </div>
</template>

<script>
import router from "../../router";

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
      const data = {
        memberId: 1,
        postId: this.postId,
        content: this.content,
        location: await this.$getCurrentLocation(),
      };
      this.$store
        .dispatch("comment/createComment", data)
        .then(() => alert("댓글이 등록되었습니다."))
        .then(() => router.go(0));
    },
  },
};
</script>

<style scoped>
.comment-input {
  position: fixed;
  width: 90vw;
  bottom: -1%;
  left: 50%;
  transform: translate(-50%);
  background-color: white;
}
</style>
