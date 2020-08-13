<template>
  <div class="mx-4 my-3" @click="routePage">
    <div class="my-2">
      <v-icon large class="mr-3">mdi-account-circle</v-icon>
      <span class="font-weight-bold">{{ post.memberResponse.nickname }}</span>
      <div class="float-right mt-1">{{ postDate }}</div>
    </div>
    <div class="text-break">{{ post.content }}</div>
    <div class="text-right">
      <v-icon small>mdi-comment-processing-outline</v-icon>
      <span class="mx-1">{{ post.comments.length }}</span>
      <v-icon small :color="likeButtonType.color">
        {{ likeButtonType.icon }}
      </v-icon>
      <span class="mx-1">{{ post.likes.length }}</span>
    </div>
    <VDivider class="my-2" />
  </div>
</template>

<script>
import { LIKE_BUTTON_TYPE } from "@/utils/constants";

export default {
  name: "PostItem",
  props: {
    post: {
      type: Object,
      required: true,
    },
  },
  computed: {
    postDate() {
      return this.$moment(this.post.createdAt).fromNow();
    },
    liked() {
      return this.post.likes.some(this.hasLike);
    },
    likeButtonType() {
      return this.liked ? LIKE_BUTTON_TYPE.LIKED : LIKE_BUTTON_TYPE.DEFAULT;
    },
  },
  methods: {
    routePage() {
      this.$router.push("posts/" + this.post.id);
    },
    hasLike(like) {
      return (
        like.memberId === this.$store.getters["member/getMembers"] &&
        like.postId === this.post.id
      );
    },
  },
};
</script>

<style scoped></style>
