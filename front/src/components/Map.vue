<template>
  <div id="map"></div>
</template>

<script>
import { KAKAO_MAP_APP_KEY } from "../secure/appkey";

export default {
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${KAKAO_MAP_APP_KEY}`;
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };

      const map = new kakao.maps.Map(container, options);
      map.setMapTypeId(kakao.maps.MapTypeId.ROADMAP);
    },
  },
};
</script>

<style>
#map {
  width: 400px;
  height: 300px;
}
</style>
