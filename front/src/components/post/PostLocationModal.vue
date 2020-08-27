<template>
  <div class="modal-mask" @click.self="closeModal">
    <div class="pa-1 modal-container">
      <v-icon
        color="grey lighten-4"
        class="my-2 mx-1 rounded-pill close-btn"
        @click.prevent="closeModal"
      >
        mdi-close
      </v-icon>
      <VContainer ref="postMap" fill-height fluid class="map-container" />
    </div>
  </div>
</template>

<script>
const MAP_MARKER_IMAGE =
  "https://dorandoran.s3.ap-northeast-2.amazonaws.com/project/map/blue_marker_with_shadow.png";

export default {
  name: "PostDetailPageLocationMapModal",
  props: {
    location: {
      type: Object,
      required: true,
    },
  },
  async mounted() {
    await this.$kakaoMap.drawMap(this.$refs.postMap);
    await this.$kakaoMap.setCenterLocation(this.location);
    await this.$kakaoMap.setMarker(this.location, MAP_MARKER_IMAGE);
  },
  methods: {
    closeModal() {
      this.$emit("close");
    },
  },
};
</script>

<style scoped>
.modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 90%;
  height: 40%;
  background-color: #fff;
  border-radius: 10px;
}

.close-btn {
  position: absolute;
  top: -40px;
  right: 0;
}

.map-container {
  border-radius: 7px;
}
</style>
