<template>
  <div>
    <PostItem v-for="post in posts" :key="post.id" :post="post" />
    <div class="bottom-spacer" />
  </div>
</template>

<script>
import PostItem from "@/components/timeline/PostItem";

export default {
  name: "SearchResultPage",
  components: {
    PostItem,
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
    await this.$store.dispatch("post/searchPosts", data);
    this.$store.commit("appBar/SEARCH_RESULT_PAGE");
    this.posts = this.$store.getters["post/getSearchedPosts"].reverse();
  },
};
</script>

<style scoped></style>
