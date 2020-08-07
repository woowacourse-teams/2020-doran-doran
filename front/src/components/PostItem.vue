<template>
  <div class="mx-4 my-3" @click="routePage">
    <div class="my-2">
      <v-icon large class="mr-3">mdi-account-circle</v-icon>
      <span class="font-weight-bold">{{ post.memberResponse.nickname }}</span>
      <div class="float-right mt-1">1분 전</div>
    </div>
    <div class="text-break">{{ post.content }}</div>
    <div class="text-right">
      <v-icon small>mdi-comment-processing-outline</v-icon>
      <span class="mx-1">{{ post.comments.length }}</span>
      <v-icon small>mdi-heart-outline</v-icon>
      <span class="mx-1">0</span>
    </div>
    <VDivider class="my-2" />
  </div>
</template>

<script>
import router from "@/router";

export default {
  name: "PostItem",
  props: {
    post: {
      type: Object,
      required: true,
    },
  },
  methods: {
    routePage() {
      router.push("posts/" + this.post.id);
    },
  },
};
</script>

<style scoped></style>
