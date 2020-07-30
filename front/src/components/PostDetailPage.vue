<template>
  <div class="pa-4">
    <div class="mb-3">
      <v-icon x-large class="mr-3">mdi-account-circle</v-icon>
      <span class="post-author">{{ post.memberResponse.nickname }}</span>
      <div class="float-right mt-2">1분 전</div>
    </div>
    <div class="text--disabled">서울시 중구 장충동에 외침</div>
    <div class="my-5">{{ post.content }}</div>
    <div>
      <v-icon small>mdi-comment-processing-outline</v-icon>
      <span class="mx-1">{{ post.comments.length }}</span>
      <v-icon small>mdi-heart-outline</v-icon>
      <span class="mx-1">0</span>
      <div class="text--disabled float-right">서울시 송파구 방이동에서</div>
    </div>
    <VDivider class="my-3" />
    <CommentInput :post-id="post.id" />
  </div>
</template>

<script>
import CommentInput from "./CommentInput";
export default {
  name: "PostDetailPage",
  components: {
    CommentInput
  },
  data() {
    return {
      post: {
        id: 0,
        memberResponse: {
          id: 0,
          nickname: "",
        },
        content: "",
        address: "",
        comments: [],
      },
    };
  },
  async created() {
    this.post = await this.$store.dispatch(
      "post/loadPost",
      this.$route.params.id,
    );
  },
};
</script>

<style scoped>
.post-author {
  font-weight: bold;
}
</style>
