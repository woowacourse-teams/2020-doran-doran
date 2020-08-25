<template>
  <div>
    <VDivider class="my-2" />
    <div class="mx-2">
      <div class="mb-1">
        <v-icon class="mr-2">mdi-account-circle</v-icon>
        <span class="font-weight-bold">{{ comment.author.nickname }}</span>
        <div class="float-right text--disabled font-size-x-small">
          {{ comment.distance.toFixed(2) }} km 밖에서
        </div>
      </div>
      <div>
        {{ comment.content }}
        <div
          v-show="this.isCommentOfCurrentMember"
          class="float-right"
          @click="deleteComment"
        >
          <v-icon size="large">mdi-delete</v-icon>
          삭제
        </div>
      </div>
      <div>
        <span class="text--disabled font-size-x-small">{{ commentDate }}</span>
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
import { ERROR_MESSAGE, LIKE_BUTTON_TYPE } from "@/utils/constants";

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
      return this.comment.likes.some(this.hasLike);
    },
    likeButtonType() {
      return this.liked ? LIKE_BUTTON_TYPE.LIKED : LIKE_BUTTON_TYPE.DEFAULT;
    },
    isCommentOfCurrentMember() {
      console.log(this.comment);
      return (
        this.comment.author.id === this.$store.getters["member/getMember"].id
      );
    },
  },
  methods: {
    hasLike(like) {
      return (
        like.memberId === this.$store.getters["member/getMember"].id &&
        like.commentId === this.comment.id
      );
    },
    async toggleLike() {
      this.liked
        ? await this.deleteCommentLike()
        : await this.createCommentLike();
    },
    async deleteCommentLike() {
      const data = this.comment.likes.find(this.hasLike);
      await this.$store
        .dispatch("comment/deleteCommentLike", data.id)
        .catch((e) => {
          this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.LOGIN_REQUIRED);
          throw e;
        });
      this.$store.dispatch("post/loadPost", this.comment.postId);
    },
    async createCommentLike() {
      const data = {
        commentId: this.comment.id,
      };
      await this.$store
        .dispatch("comment/createCommentLike", data)
        .catch((e) => {
          this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.LOGIN_REQUIRED);
          throw e;
        });
      this.$store.dispatch("post/loadPost", this.comment.postId);
    },
    deleteComment() {
      this.$emit("delete-comment", this.comment.postId, this.comment.id);
    },
  },
};
</script>

<style scoped></style>
