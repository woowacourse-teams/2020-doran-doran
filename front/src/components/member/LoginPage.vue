<template>
  <div>
    <div class="app-title">도란도란</div>
    <div d-block>
      <router-link to="/map">
        <v-btn outlined class="mx-3 my-1">로그인 없이 둘러보기</v-btn>
      </router-link>
    </div>
    <div d-block>
      <v-btn depressed color="amber accent-3" class="mx-3 my-1" @click="kakaoLogin">카카오톡으로 로그인하기</v-btn>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginPage",
  created() {
    this.$store.commit("appBar/APP_BAR_INVISIBLE")
  },
  methods: {
    kakaoLogin() {
      console.log("카카오 로그인 구현이 아직 안 되었어요.")
    },
  }
};
</script>

<style scoped>
  .app-title {
    position: fixed;
    left: 50%;
    margin-bottom: 70%;
  }
</style>
