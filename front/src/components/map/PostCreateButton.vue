<template>
  <div>
    <v-btn
      fixed
      right
      bottom
      fab
      dark
      large
      elevation="10"
      class="doran-doran-color"
      @click="changeMode"
    >
      <v-icon>{{ buttonType.icon }}</v-icon>
    </v-btn>
  </div>
</template>

<script>
import { MAP_MODE, DORAN_DORAN_COLORS } from "@/utils/constants";

const MARKER_MODE_MESSAGE = "✏️ 글을 작성할 위치를 선택해주세요.";

const BUTTON_TYPE = {
  DEFAULT: {
    color: DORAN_DORAN_COLORS.POINT_COLOR,
    icon: "mdi-pencil",
  },
  MARKER: {
    color: "red",
    icon: "mdi-check",
  },
};

export default {
  name: "PostCreateButton",
  computed: {
    isDefaultMode() {
      return this.$store.getters["mapMode/isDefault"];
    },
    isMarkerMode() {
      return this.$store.getters["mapMode/isMarker"];
    },
    isPostMode() {
      return this.$store.getters["mapMode/isPost"];
    },
    buttonType() {
      return this.isMarkerMode ? BUTTON_TYPE.MARKER : BUTTON_TYPE.DEFAULT;
    },
  },
  methods: {
    changeMode() {
      if (this.isDefaultMode) {
        this.$store.commit("mapMode/CHANGE_STATE", MAP_MODE.MARKER);
        this.$store.commit("snackbar/SHOW", MARKER_MODE_MESSAGE);
        this.$store.commit("appBar/MAP_PAGE_MARKER_MODE");
      } else if (this.isMarkerMode) {
        this.$store.commit("mapMode/CHANGE_STATE", MAP_MODE.POST);
      }
    },
  },
};
</script>

<style scoped></style>
