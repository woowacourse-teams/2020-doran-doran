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
          <v-btn
            icon
            @click="addClickEventToLikeButton"
            :color="likeButtonType.color"
          >
            <v-icon small>{{ likeButtonType.icon }}</v-icon>
          </v-btn>
          <span class="mx-1">{{ comment.likes.length }}</span>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import { LIKE_BUTTON_TYPE } from "@/utils/constants";

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
          ? LIKE_BUTTON_TYPE.LIKED
          : LIKE_BUTTON_TYPE.DEFAULT;
    }
  },
  methods: {
    async addClickEventToLikeButton() {
      if (this.liked) {
        await this.deleteCommentLike();
      } else {
        await this.createCommentLike();
      }
    },
    async deleteCommentLike() {
      const thisCommentLike = this.comment.likes.find(
        (like) =>
          like.memberId === this.$store.getters["member/getMembers"] &&
          like.commentId === this.comment.id,
      );
      await this.$store.dispatch(
        "comment/deleteCommentLike",
        thisCommentLike.id,
      );
      this.$emit("load-post");
    },
    async createCommentLike() {
      const newCommentLike = {
        memberId: this.comment.author.id,
        commentId: this.comment.id,
      };
      await this.$store.dispatch("comment/createCommentLike", newCommentLike);
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
