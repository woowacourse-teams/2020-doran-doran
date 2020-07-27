<template>
  <router-link to="/create-post">
    <v-btn
      class="mb-12"
      fab
      dark
      large
      absolute
      right
      bottom
      color="amber accent-4"
    >
      <v-icon dark>mdi-pencil</v-icon>
    </v-btn>
  </router-link>
</template>

<script>
export default {
  name: "PostCreateButton",
};
</script>

<style scoped></style>
