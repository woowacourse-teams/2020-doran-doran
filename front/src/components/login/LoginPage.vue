<template>
  <div class="background doran-doran-color">
    <div class="app-title font-size-x-large">도란도란</div>

    <div class="button-box">
      <v-btn depressed block color="white" @click="guest" elevation="3">
        <div>로그인 없이 둘러보기</div>
      </v-btn>
      <v-btn depressed block color="#FEE500" @click="kakaoLogin" elevation="3">
        <img id="kakao-icon" src="@/assets/img/kakao-login.png" />
        <div>카카오톡으로 로그인하기</div>
      </v-btn>
    </div>
  </div>
</template>

<script>
import { API_BASE_URL } from "@/utils/constants";

export default {
  name: "LoginPage",
  methods: {
    guest() {
      localStorage.setItem("accessToken", "guest");
      this.$router.push("/");
    },
    kakaoLogin() {
      window.location.href = API_BASE_URL.EC2 + "/oauth2/authorization/kakao";
    },
  },
};
</script>

<style scoped>
.background {
  position: absolute;
  width: 100%;
  height: 100vh;
  z-index: 9999;
}

.app-title {
  position: absolute;
  top: 30%;
  left: 50%;
  transform: translate(-50%);
  margin: auto;
  text-align: center;
  font-weight: bold;
  font-family: BM_JUA, sans-serif;
  color: white;
}

.button-box {
  position: absolute;
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
