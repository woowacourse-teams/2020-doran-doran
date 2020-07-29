<template>
  <div>
    <div>{{ post.memberResponse.nickname }}</div>
    <div>1분 전</div>
    <div>서울특별시 동대문구에서</div>
    <div>{{ post.content }}</div>
    <div>{{ post.address }}</div>
    <v-icon>mdi-comment-processing-outline</v-icon>
    <span>{{ post.comments.length }}</span>
    <v-icon>mdi-heart-outline</v-icon>
    <span>5</span>
  </div>
</template>

<script>
export default {
  name: "PostDetailPage",
  data() {
    return {
      post: {
        id: null,
        memberResponse: {
          id: null,
          nickname: null,
        },
        content: null,
        address: null,
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

<style scoped></style>
