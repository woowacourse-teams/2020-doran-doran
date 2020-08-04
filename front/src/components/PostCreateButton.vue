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
export default {
  name: "PostCreateButton",
  data() {
    return {
      mode: "",
      buttonColor: "amber accent-3",
      buttonIcon: "mdi-pencil",
    };
  },
  methods: {
    changeMode() {
      if (this.mode !== "markerMode") {
        this.buttonColor = "red";
        this.buttonIcon = "mdi-check";
        this.mode = "markerMode";
      } else {
        this.buttonColor = "amber accent-3";
        this.buttonIcon = "mdi-pencil";
        this.mode = "";
      }
    },
  },
};
</script>

<style scoped></style>
