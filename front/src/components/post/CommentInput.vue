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
    />
  </div>
</template>

<script>
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
        .then(() => this.$router.go(0));
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
