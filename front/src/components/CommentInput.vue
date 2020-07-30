<template>
  <div class="my-3">
    <v-text-field
      v-model="content"
      placeholder="댓글을 입력해주세요."
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
export default {
  name: "CommentInput",
  data() {
    return {
      content: "",
    };
  },
  props: {
    postId: {
      type: Number,
      required: true,
    },
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
        .then(() => alert("댓글이 등록되었습니다."));
    },
  },
};
</script>

<style scoped></style>
