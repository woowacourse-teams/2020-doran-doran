<template>
  <div class="modal-mask" @click.self="closeModal">
    <div class="pa-1 modal-container">
      <v-container fill-height fluid ref="map" class="map-container">
      </v-container>

      <div class="float-right">
        <v-btn
          @click.prevent="closeModal"
          class="my-2 mx-1 rounded-pill"
          color="grey lighten-4"
        >
          X
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { POST_MODE } from "@/utils/constants";

const MAP_MARKER_IMAGE = "https://dorandoran.s3.ap-northeast-2.amazonaws.com/project/map/blue_marker_with_shadow.png";

export default {
  name: "PostMapModal",
  props: {
    location: {
      type: Object,
      required: true,
    },
  },
  async mounted() {
    await this.$kakaoMap.drawMap(this.$refs.map);
    await this.$kakaoMap.setCenterLocation(this.location);
    await this.$kakaoMap.setMarker(
      this.location,
      MAP_MARKER_IMAGE,
    );
  },
  computed: {
    isMapModalMode() {
      return this.$store.getters["postDetailModal/isMapModalMode"];
    },
  },
  methods: {
    closeModal() {
      this.$store.commit("postDetailModal/CHANGE_MODE", POST_MODE.DEFAULT);
    },
  },
};
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

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

.map-container {
  border-radius: 7px;
}
</style>
