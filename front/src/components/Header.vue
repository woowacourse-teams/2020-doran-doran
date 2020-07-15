<template>
  <div>
    <v-app-bar flat="true">
      <a href="#">
        <i class="mdi mdi-chevron-left"></i>
      </a>
      <div class="location-title">
        <i class="mdi mdi-map-marker pr-1"></i>
        <v-toolbar-title id="current-location"
          >서울특별시 송파구 가락본동</v-toolbar-title
        >
      </div>
      <div></div>
    </v-app-bar>
  </div>
</template>

<script>
export default {};
</script>

<style>
.v-toolbar__content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.mdi.mdi-chevron-left {
  color: black;
}
#current-location {
  font-size: 1rem;
}
.location-title {
  display: flex;
  flex-direction: row;
  align-items: center;
}
</style>
