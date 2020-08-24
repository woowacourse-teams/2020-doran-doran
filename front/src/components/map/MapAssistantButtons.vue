<template>
  <div class="assistant-buttons">
    <v-btn small fab @click="setMapToCurrentLocation">
      <v-icon>mdi-radiobox-marked</v-icon>
    </v-btn>
    <v-btn small fab class="mt-2" @click="refreshPosts">
      <v-icon>mdi-refresh</v-icon>
    </v-btn>
  </div>
</template>

<script>
export default {
  name: "MapAssistantButtons",
  methods: {
    setMapToCurrentLocation() {

    },
    refreshPosts() {

    },
  },
};
</script>

<style scoped>
.assistant-buttons {
  display: flex;
  flex-direction: column;
  position: fixed;
  left: 15px;
  bottom: 20px;
  z-index: 9998;
}
</style>
