<template>
  <v-form class="pa-3 fill-height">
    <v-container fluid class="map-container ma-0 pa-0 mb-6">
      <KakaoMap :usage="'create-post'" />
    </v-container>
    <VTextarea
      v-model="content"
      type="text"
      name="content"
      placeholder="글 내용을 입력해주세요."
      maxlength="200"
      :counter="200"
      required
      solo
      autofocus
      no-resize
    />
    <v-btn
      @click="createPost"
      class="float-right"
      color="amber accent-4"
      type="submit"
    >
      등록
    </v-btn>
  </v-form>
</template>

<script>
import KakaoMap from "./KakaoMap";

export default {
  name: "postCreate",
  components: {
    KakaoMap,
  },
  data() {
    return {
      content: "",
    };
  },
  methods: {
    createPost() {
      this.$store.dispatch("post/createPost", this.content);
    },
  },
};
</script>

<style scoped>
.map-container {
  height: 40vh;
}
</style>
