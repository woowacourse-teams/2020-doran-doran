<template>
  <v-app class="app-container">
    <v-snackbar top timeout="1500" class="snackbar" v-model="snackbarWarning">
      {{ snackbarMessage }}
    </v-snackbar>
    <DoranAppBar @show-modal="changeVisibility" />
    <transition name="fade" mode="out-in">
      <RouterView />
    </transition>
    <MyPageSideBar />
  </v-app>
</template>

<script>
import DoranAppBar from "@/components/DoranAppBar";
import MyPageSideBar from "@/components/member/MyPageSideBar";

export default {
  name: "App",
  components: {
    DoranAppBar,
    MyPageSideBar,
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
* {
  letter-spacing: -0.7px;
}

html,
body {
  width: 100%;
  height: 100%;
}

.doran-doran-color {
  background: linear-gradient(
      320deg,
      rgba(252, 144, 0, 1) 30%,
      rgba(251, 203, 49, 1) 100%
  );
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
