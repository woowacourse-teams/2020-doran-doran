<template>
  <div id="map" ref="map"></div>
</template>

<script>
export default {
  async mounted() {
    await this.$drawMap(this.$refs.map);
    this.$setLocation(await this.$getCurrentLocation());
  },
};
</script>

<style>
#map {
  width: auto;
  height: 500px;
}
</style>
