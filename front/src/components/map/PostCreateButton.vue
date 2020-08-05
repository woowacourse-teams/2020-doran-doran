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
      :color="buttonColor"
    >
      <v-icon v-html="buttonIcon"></v-icon>
    </v-btn>
  </div>
</template>

<script>
import { MAP_MODE } from "@/utils/constants";
export default {
  name: "PostCreateButton",
  data() {
    return {
      buttonColor: "amber accent-3",
      buttonIcon: "mdi-pencil",
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
        this.buttonColor = "red";
        this.buttonIcon = "mdi-check";
      } else if (this.isMarkerMode) {
        this.$store.commit("modal/CHANGE_STATE", MAP_MODE.POST);
        this.buttonColor = "amber accent-3";
        this.buttonIcon = "mdi-pencil";
      }
    },
  },
};
</script>

<style scoped></style>
