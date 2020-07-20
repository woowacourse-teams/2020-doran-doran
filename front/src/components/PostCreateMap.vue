<template>
  <div id="map" ref="map">
    <v-icon class="center-marker" color="blue" size="50">mdi-map-marker</v-icon>
  </div>
</template>

<script>
export default {
  async mounted() {
    await this.$drawMap(this.$refs.map);
    // TODO 사용자가 설정한 위치로 지도 중심을 지정하도록 변경
    const currentLocation = await this.$getCurrentLocation();
    this.$setLocation(currentLocation);
    this.$setCurrentLocationMarker(currentLocation);
  },
};
</script>

<style scoped>
#map {
  width: auto;
  height: 200px;
}
.center-marker {
  position: relative;
  left: 50%;
  top: 50%;
  z-index: 2;
  margin-top: -25px;
  margin-left: -25px;
}
</style>
