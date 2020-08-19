<template>
  <v-app class="app-container">
<<<<<<< HEAD
    <v-snackbar top timeout="1500" class="snackbar" v-model="snackbarWarning">
      {{ snackbarMessage }}
    </v-snackbar>
    <DoranAppBar @show-modal="changeVisibility"/>
=======
    <DoranAppBar @show-modal="changeVisibility" />
>>>>>>> feature/104 feat: MyPageModal에 트랜지션 적용
    <transition name="fade" mode="out-in">
      <RouterView />
    </transition>
    <MyPageModal :drawer="showMyPageModal" @show-modal="changeVisibility" />
  </v-app>
</template>

<script>
import DoranAppBar from "@/components/DoranAppBar";
import MyPageModal from "@/components/member/MyPageModal";

export default {
  name: "App",
  components: {
    DoranAppBar,
    MyPageModal,
  },
  computed: {
    snackbarMessage: {
      get() {
        return this.$store.getters["snackbar/snackbarMessage"];
      },
      set(val) {
        this.$store.commit("snackbar/SET_SNACKBAR_MESSAGE", val);
      },
    },
    snackbarWarning: {
      get() {
        return this.$store.getters["snackbar/snackbarWarning"];
      },
      set(val) {
        this.$store.commit("snackbar/SET_SNACKBAR_WARNING", val);
      },
    },
  },
  data() {
    return {
      showMyPageModal: false,
    };
  },
  methods: {
    changeVisibility(value) {
      this.showMyPageModal = value;
    },
  },
};
</script>

<style>
html,
body {
  width: 100%;
  height: 100%;
  letter-spacing: -0.7px;
}

a {
  text-decoration: none;
}

.app-container {
  width: 100%;
  height: 100%;
  flex-direction: column;
}

.app-container > div {
  height: 100%;
}

.snackbar {
  z-index: 9999 !important;
}

.fade-enter-active,
.fade-leave-active {
  transition-duration: 0.2s;
  transition-property: opacity;
  transition-timing-function: ease;
}

.fade-enter,
.fade-leave-active {
  opacity: 0;
}
</style>
