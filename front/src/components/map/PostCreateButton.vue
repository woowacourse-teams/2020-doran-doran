<template>
  <div>
    <v-btn
      fixed
      right
      bottom
      large
      fab
      dark
      :color="buttonType.color"
      @click="changeMode"
    >
      <v-icon>{{ buttonType.icon }}</v-icon>
    </v-btn>
  </div>
</template>

<script>
import { MAP_MODE, DORAN_DORAN_COLORS } from "@/utils/constants";

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
  data() {
    return {
      buttonType: BUTTON_TYPE.DEFAULT,
    };
  },
  computed: {
    isDefaultMode() {
      return this.$store.getters["modal/isDefaultMode"];
    },
    isMarkerMode() {
      return this.$store.getters["modal/isMarkerMode"];
    },
    isPostMode() {
      return this.$store.getters["modal/isPostMode"];
    },
  },
  methods: {
    changeMode() {
      if (this.isDefaultMode) {
        this.$store.commit("modal/CHANGE_STATE", MAP_MODE.MARKER);
        this.$store.commit("appBar/TOGGLE_APP_BAR");
        this.buttonType = BUTTON_TYPE.MARKER;
      } else if (this.isMarkerMode) {
        this.$store.commit("modal/CHANGE_STATE", MAP_MODE.POST);
        this.$store.commit("appBar/TOGGLE_APP_BAR");
        this.buttonType = BUTTON_TYPE.DEFAULT;
      }
    },
  },
};
</script>

<style scoped></style>
