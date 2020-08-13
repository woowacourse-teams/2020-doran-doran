<template>
  <div>
    <PostItem v-for="post in posts" :key="post.id" :post="post" />
    <div class="bottom-spacer" />
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
    await this.$store.dispatch("post/loadPosts");
    this.posts = this.$store.getters["post/getPosts"].reverse();
  },
};
</script>

<style scoped>
.bottom-spacer {
  width: 100%;
  height: 70px;
}
</style>
