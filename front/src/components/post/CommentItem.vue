<template>
  <div>
    <VDivider class="comment-divider my-2" />
    <div class="mx-2">
      <div class="mb-1">
        <v-icon class="mr-2">mdi-account-circle</v-icon>
        <span class="font-weight-bold">{{ comment.author.nickname }}</span>
        <div class="float-right text--disabled comment-distance">
          {{ comment.distance.toFixed(2) }} km 밖에서
        </div>
      </div>
      <div>{{ comment.content }}</div>
      <div>
        <span class="text--disabled comment-created">{{ commentDate }}</span>
        <span class="float-right">
          <v-icon small>mdi-heart-outline</v-icon>
          <span class="mx-1">0</span>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CommentItem",
  data() {
    return {
      commentDate: "",
    };
  },
  props: {
    comment: {
      type: Object,
      required: true,
    },
  },
  async mounted() {
    this.commentDate = this.$moment(this.comment.createdAt).fromNow();
  },
};
</script>

<style scoped>
.comment-distance,
.comment-created {
  font-size: 0.8rem;
}
</style>
