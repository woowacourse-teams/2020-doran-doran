<template>
  <div>
    <CommentItem
      v-for="comment in comments"
      :key="comment.id"
      :comment="comment"
      @load-post="loadPost"
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
    loadPost() {
      this.$emit("load-post");
    }
  },
};
</script>

<style scoped></style>
