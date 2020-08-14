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
          <v-icon small @click="toggleLike" :color="likeButtonType.color">
            {{ likeButtonType.icon }}
          </v-icon>
          <span class="mx-1">{{ comment.likes.length }}</span>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CommentItem",
  props: {
    comment: {
      type: Object,
      required: true,
    },
  },
  computed: {
    commentDate() {
      return this.$moment(this.comment.createdAt).fromNow();
    },
    liked() {
      return this.comment.likes.some(
        (like) =>
          like.memberId === this.$store.getters["member/getMembers"] &&
          like.commentId === this.comment.id,
      );
    },
    likeButtonType() {
      return this.liked
        ? this.$store.getters["like/liked"]
        : this.$store.getters["like/notLiked"];
    },
  },
  methods: {
    async toggleLike() {
      this.liked
        ? await this.deleteCommentLike()
        : await this.createCommentLike();
    },
    async deleteCommentLike() {
      const data = this.comment.likes.find(
        (like) =>
          like.memberId === this.$store.getters["member/getMembers"] &&
          like.commentId === this.comment.id,
      );
      await this.$store.dispatch("comment/deleteCommentLike", data.id);
      this.$emit("load-post");
    },
    async createCommentLike() {
      const data = {
        memberId: this.comment.author.id,
        commentId: this.comment.id,
      };
      await this.$store.dispatch("comment/createCommentLike", data);
      this.$emit("load-post");
    },
  },
};
</script>

<style scoped>
.comment-distance,
.comment-created {
  font-size: 0.8rem;
}
</style>
