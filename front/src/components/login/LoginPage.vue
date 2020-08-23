<template class="background">
  <div class="background doran-doran-color">
    <div class="app-title">도란도란</div>

    <div class="button-box">
      <router-link to="/map">
        <v-btn depressed block color="white" elevation="3">
          <div>로그인 없이 둘러보기</div>
        </v-btn>
      </router-link>
      <v-btn depressed block color="#FEE500" @click="kakaoLogin" elevation="3">
        <img id="kakao-icon" src="@/assets/img/kakao-login.png" />
        <div>카카오톡으로 로그인하기</div>
      </v-btn>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginPage",
  created() {
    this.$store.commit("appBar/HIDE");
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
}

.app-title {
  position: fixed;
  top: 30%;
  left: 50%;
  transform: translate(-50%);
  width: 50%;
  margin: auto;
  text-align: center;
  font-size: 2rem;
  font-weight: bold;
  font-family: BM_JUA, sans-serif;
  color: white;
}

.button-box {
  position: fixed;
  bottom: 20%;
  left: 50%;
  transform: translate(-50%);
  width: 80%;
  max-width: 400px;
}

.button-box > * {
  margin-top: 10px;
}

#kakao-icon {
  width: 15px;
  margin-right: 10px;
}
</style>
