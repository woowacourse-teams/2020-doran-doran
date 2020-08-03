<template>
  <div>
    <PostItem v-for="post in posts" :key="post.id" :post="post" />
    <VSpacer class="bottom-spacer" />
    <PostCreateButton />
  </div>
</template>

<script>
import PostCreateButton from "./PostCreateButton";
import PostItem from "./PostItem";

export default {
  name: "TimelinePage",
  components: {
    PostItem,
    PostCreateButton,
  },
  data() {
    return {
      posts: [],
    };
  },
  async created() {
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
