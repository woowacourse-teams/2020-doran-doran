<template>
  <div>
    <CommentItem
      v-for="comment in comments"
      :key="comment.id"
      :comment="comment"
      @delete-comment="deleteComment"
    />
  </div>
</template>

<script>
import CommentItem from "@/components/post/CommentItem";
export default {
  name: "CommentList",
  components: {
    CommentItem,
  },
  props: {
    comments: {
      type: Array,
      required: true,
    },
  },
  methods: {
    async deleteComment(postId, commentId) {
      await this.$store.dispatch("comment/deleteComment", commentId);
      this.$store.dispatch("post/loadPost", postId);
    },
  },
};
</script>

<style scoped></style>
