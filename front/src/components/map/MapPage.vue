<template>
  <v-container fill-height fluid class="pa-0">
    <KakaoMap />
    <PostCreateButton />
    <PostCreateModal v-if="this.isPostMode" />
  </v-container>
</template>

<script>
import KakaoMap from "@/components/map/KakaoMap";
import PostCreateButton from "@/components/map/PostCreateButton";
import PostCreateModal from "@/components/map/PostCreateModal";

export default {
  name: "MapPage",
  components: {
    KakaoMap,
    PostCreateButton,
    PostCreateModal,
  },
  created() {
    this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
  },
  computed: {
    isPostMode() {
      return this.$store.getters["modal/isPostMode"];
    },
  },
};
</script>

<style scoped></style>
