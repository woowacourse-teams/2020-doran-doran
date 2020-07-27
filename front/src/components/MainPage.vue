<template>
  <v-container class="fill-height fluid px-0 py-0">
    <KakaoMap />
    <PostCreateButton />
  </v-container>
</template>

<script>
import KakaoMap from "./KakaoMap";
import PostCreateButton from "./PostCreateButton";

export default {
  name: "MainPage",
  components: { PostCreateButton, KakaoMap },
};
</script>

<style scoped></style>
