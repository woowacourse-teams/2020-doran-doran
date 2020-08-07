<template>
  <div class="pa-4">
    <div class="mb-3">
      <v-icon x-large class="mr-3">mdi-account-circle</v-icon>
      <span class="font-weight-bold">{{ post.memberResponse.nickname }}</span>
      <div class="float-right mt-2">1분 전</div>
    </div>
    <div class="text--disabled post-address">서울시 중구 장충동에서 외침</div>
    <div class="my-5 text-break">{{ post.content }}</div>
    <div>
      <v-icon small>mdi-comment-processing-outline</v-icon>
      <span class="mx-1">{{ post.comments.length }}</span>
      <v-icon small>mdi-heart-outline</v-icon>
      <span class="mx-1">0</span>
      <div class="text--disabled float-right post-address">
        서울시 송파구 방이동에서
      </div>
    </div>
    <CommentList :comments="post.comments" />
    <div class="bottom-spacer" />
    <CommentInput :post-id="post.id" />
  </div>
</template>

<script>
import CommentInput from "@/components/post/CommentInput";
import CommentList from "@/components/post/CommentList";

export default {
  name: "PostDetailPage",
  components: {
    CommentList,
    CommentInput,
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
.post-address {
  font-size: 0.9rem;
}
.bottom-spacer {
  height: 60px;
}
</style>
