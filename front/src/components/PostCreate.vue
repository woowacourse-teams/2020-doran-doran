<template>
    <v-form class="pa-7">
      <PostCreateMap />
      <div class="content">
        <v-textarea
          v-model="content"
          type="text"
          name="content"
          placeholder="글 내용을 입력해주세요."
          maxlength="200"
          rows="10"
          :counter="200"
          required
          solo
          autofocus
          no-resize
        />
      </div>
      <v-btn
        @click="createPost"
        class="float-right"
        color="amber accent-4"
        type="submit"
        >등록</v-btn
      >
    </v-form>
</template>

<script>
import PostCreateMap from "./PostCreateMap";
export default {
  data() {
    return {
      content: "",
    };
  },
  components: { PostCreateMap },
  methods: {
    createPost() {
      this.$store.dispatch("post/createPost", this.content);
    },
  },
};
</script>

<style scoped>
.content {
  margin-top: 30px;
}
</style>
