<template>
  <div id="map" ref="map"></div>
</template>

<script>
export default {
  created() {
    this.$store.dispatch("post/loadPosts");
  },
  async mounted() {
    await this.$drawMap(this.$refs.map);
    const currentLocation = await this.$getCurrentLocation();
    this.$setLocation(currentLocation);
    this.$setCurrentLocationMarker(currentLocation);
    this.drawPosts();
  },
  methods: {
    drawPosts() {
      this.$store.getters["post/getPosts"].forEach((post) => {
        this.$setPostOverlay(post.content, post.location);
      });
    },
  },
};
</script>

<style>
#map {
  width: auto;
  height: 500px;
}
</style>
