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
  async created() {
    const data = {
      keyword: this.$route.query.keyword,
      startDate: this.$route.query.startDate,
      endDate: this.$route.query.endDate,
    };
    await this.$store.dispatch("post/searchPost", data);
    this.posts = this.$store.getters["post/getSearchedPosts"].reverse();
  },
};
</script>

<style scoped></style>
