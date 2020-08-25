<template>
  <div>
    <PostItem v-for="post in posts" :key="post.id" :post="post" />
  </div>
</template>

<script>
import PostItem from "@/components/timeline/PostItem";

export default {
  name: "TimelinePage",
  components: {
    PostItem,
  },
  data() {
    return {
      posts: [],
    };
  },
  async created() {
    this.$store.commit("appBar/TIMELINE_PAGE");
    const bounds = {
      leftBound: this.$route.query.leftBound,
      rightBound: this.$route.query.rightBound,
      upperBound: this.$route.query.upperBound,
      lowerBound: this.$route.query.lowerBound,
    };
    await this.$store.dispatch("post/loadPostsInBounds", bounds);
    this.posts = await this.$store.getters["post/getPostsInBounds"].reverse();
  },
};
</script>

<style scoped></style>
