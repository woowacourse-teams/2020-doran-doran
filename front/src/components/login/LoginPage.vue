<template class="background">
  <div class="background">
    <div class="app-title">도란도란</div>

    <div class="button-box ma-6">
      <router-link to="/map">
        <v-btn depressed block color="white">
          <div>로그인 없이 둘러보기</div>
        </v-btn>
      </router-link>
      <v-btn depressed block color="#FEE500" @click="kakaoLogin">
        <img src="@/assets/img/kakao-login.png" />
        <div>카카오톡으로 로그인하기</div>
      </v-btn>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginPage",
  created() {
    this.$store.commit("appBar/APP_BAR_INVISIBLE");
  },
  methods: {
    kakaoLogin() {
      console.log("카카오 로그인 구현이 아직 안 되었어요.");
    },
  },
};
</script>

<style scoped>
.background {
  width: 100%;
  height: 100vh;
  background: linear-gradient(
    320deg,
    rgba(234, 77, 44, 1) 0%,
    rgba(255, 166, 46, 1) 100%
  );
  /*background: linear-gradient(*/
  /*  320deg,*/
  /*  rgba(252, 144, 0, 1) 15%,*/
  /*  rgba(251, 203, 49, 1) 100%*/
  /*);*/
}
.app-title {
  margin: auto;
  width: 50%;
  text-align: center;
  padding-top: 30%;
  font-size: 2rem;
  font-weight: bold;
  font-family: "BM JUA_OTF", sans-serif;
  color: white;
}
.button-box {
  padding-top: 80%;
}
.button-box * {
  letter-spacing: -0.7px;
}
.button-box > * {
  margin-top: 10px;
}
img {
  width: 15px;
  margin-right: 10px;
}
</style>
