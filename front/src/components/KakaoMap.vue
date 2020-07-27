<template>
  <v-container fill-height fluid ref="map">
    <v-icon
      class="center-marker"
      color="blue"
      size="50"
      v-if="this.isCreatePost"
    >
      mdi-map-marker
    </v-icon>
  </v-container>
</template>

<script>
export default {
  name: "kakaoMap",
  props: {
    usage: {
      type: String,
      default: "default-map",
    },
  },
  created() {
    this.$store.dispatch("post/loadPosts");
  },
  async mounted() {
    await this.$drawMap(this.$refs.map);
    if (!this.isCreatePost) {
      await this.initMainMap();
    }
  },
  computed: {
    isCreatePost() {
      return this.usage === "create-post";
    },
  },
  methods: {
    async initMainMap() {
      const currentLocation = await this.$getCurrentLocation();
      this.$setLocation(currentLocation);
      this.$setCurrentLocationMarker(currentLocation);
      this.drawPosts();
    },
    drawPosts() {
      this.$store.getters["post/getPosts"].forEach((post) => {
        this.$setPostOverlay(post.content, post.location);
      });
    },
  },
};
</script>

<style scoped>
.center-marker {
  position: relative;
  z-index: 2;
  left: 50%;
  margin-left: -20px;
}
</style>
