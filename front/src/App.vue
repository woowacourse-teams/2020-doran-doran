<template>
  <div>
    <router-link to="/">Map</router-link>
    <router-link to="/create-post">글 작성</router-link>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "app",
  components: {},
};
</script>

<style>
@import "./assets/styles/post-overlay.css";
</style>
