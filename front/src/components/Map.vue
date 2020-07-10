<template>
  <div id="map" ref="map"></div>
</template>

<script>
export default {
  async mounted() {
    await this.$drawMap(this.$refs.map);
    const currentLocation = await this.$getCurrentLocation();
    this.$setLocation(currentLocation);
    this.$setMarker(currentLocation);
  },
};
</script>

<style>
#map {
  width: auto;
  height: 500px;
}
</style>
