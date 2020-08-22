<template>
  <v-container fill-height fluid class="pa-0">
    <KakaoMap />
    <PostCreateButton />
    <transition name="bounce">
      <PostCreateModal v-if="this.isPostMode" />
    </transition>
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
      return this.$store.getters["mapMode/isMarker"];
    },
    isPostMode() {
      return this.$store.getters["mapMode/isPost"];
    },
  },
};
</script>
<style scoped>
.bounce-enter-active {
  animation: bounce-in 0.4s;
}
.bounce-leave-active {
  animation: bounce-in 0.5s reverse;
}
@keyframes bounce-in {
  0% {
    transform: scale(0);
  }
  70% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
}
</style>
