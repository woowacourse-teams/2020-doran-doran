<template>
  <v-container> {{ text }} </v-container>
</template>

<script>
export default {
  name: "ReportPage",
  data() {
    return {
      text: "신고된 글",
    };
  },
};
</script>
