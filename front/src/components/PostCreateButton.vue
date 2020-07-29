<template>
  <router-link to="/create-post">
    <v-btn fab dark large fixed right bottom color="amber accent-4">
      <v-icon>mdi-pencil</v-icon>
    </v-btn>
  </router-link>
</template>

<script>
export default {
  name: "PostCreateButton",
};
</script>

<style scoped></style>
