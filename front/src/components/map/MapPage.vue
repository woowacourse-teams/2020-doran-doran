<template>
  <v-container fill-height fluid class="pa-0">
    <v-snackbar top timeout="1500" v-model="snackbarWarning">
      {{ snackbarMessage }}
    </v-snackbar>
    <KakaoMap />
    <PostCreateButton @marker-mode="showSnackbar" />
    <PostCreateModal v-if="this.isPostMode" @create-post="showSnackbar" />
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
  data() {
    return {
      snackbarWarning: false,
      snackbarMessage: "",
    };
  },
  created() {
    this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
  },
  computed: {
    isPostMode() {
      return this.$store.getters["modal/isPostMode"];
    },
  },
  methods: {
    showSnackbar(message) {
      this.snackbarMessage = message;
      this.snackbarWarning = true;
    },
  },
};
</script>

<style scoped></style>
