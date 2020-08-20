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
  async created() {
    this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");

    const token = location.href.split("token=")[1];
    if (token) {
      sessionStorage.setItem("accessToken", token);
      location.href = "/";
    } else {
      await this.$store.dispatch("member/loadMember");
    }
  },
  computed: {
    isMarkerMode() {
      return this.$store.getters["postModal/isMarkerMode"];
    },
    isPostMode() {
      return this.$store.getters["postModal/isPostMode"];
    },
  },
};
</script>

<style scoped></style>
