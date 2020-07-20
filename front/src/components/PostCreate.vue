<template>
  <div>
    <v-form class="form">
      <PostCreateMap style="height: 200px;" />
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
        @click="addPost"
        class="register-button"
        color="amber accent-4"
        type="submit"
        >등록</v-btn
      >
    </v-form>
  </div>
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
    addPost() {
      this.$store.dispatch("post/addPost", this.content);
    },
  },
};
</script>

<style scoped>
.form {
  margin: 30px;
}
.content {
  margin-top: 30px;
}
.register-button {
  float: right;
}
</style>
