<template>
  <div id="map" ref="map"></div>
</template>

<script>
export default {
  mounted() {
    this.$drawMap(this.$refs.map);
  },
};
</script>

<style>
#map {
  width: auto;
  height: 500px;
}
</style>
