<template>
  <div>
    <v-btn
      @click="changeMode"
      fab
      dark
      large
      fixed
      right
      bottom
      :color="buttonType.color"
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
        this.$store.commit("appBar/MAP_PAGE_NON_DEFAULT_MODE", false);
        this.buttonType = BUTTON_TYPE.MARKER;
      } else if (this.isMarkerMode) {
        this.$store.commit("modal/CHANGE_STATE", MAP_MODE.POST);
        this.buttonType = BUTTON_TYPE.DEFAULT;
      }
    },
  },
};
</script>

<style scoped></style>
