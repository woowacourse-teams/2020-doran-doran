<template>
  <div>
    <PostItem v-for="post in posts" :key="post.id" :post="post" />
    <div class="bottom-spacer" />
    <PostCreateButton />
  </div>
</template>

<script>
import PostCreateButton from "@/components/map/PostCreateButton";
import PostItem from "@/components/timeline/PostItem";
export default {
  name: "SearchResultPage",
  components: {
    PostItem,
    PostCreateButton,
  },
  data() {
    return {
      posts: [],
    };
  },
  created() {
    this.posts = this.$store.getters["post/getSearchedPosts"].reverse();
  },
};
</script>

<style scoped></style>
