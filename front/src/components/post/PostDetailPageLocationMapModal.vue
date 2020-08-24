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
import { POST_MODE } from "@/utils/constants";

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

.close-btn {
  position: absolute;
  top: -40px;
  right: 0;
}

.map-container {
  border-radius: 7px;
}
</style>
