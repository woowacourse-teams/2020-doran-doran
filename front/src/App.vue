<template>
  <v-app class="app-container">
    <v-snackbar top timeout="1500" class="snackbar" v-model="snackbarWarning">
      {{ snackbarMessage }}
    </v-snackbar>
    <DoranAppBar />
    <transition name="fade" mode="out-in">
      <RouterView />
    </transition>
    <MemberSidebar />
  </v-app>
</template>

<script>
import DoranAppBar from "@/components/DoranAppBar";
import MemberSidebar from "@/components/member/MemberSidebar";

export default {
  name: "App",
  components: {
    DoranAppBar,
    MemberSidebar,
  },
  computed: {
    snackbarMessage: {
      get() {
        return this.$store.getters["snackbar/message"];
      },
      set(val) {
        this.$store.commit("snackbar/SET_MESSAGE", val);
      },
    },
    snackbarWarning: {
      get() {
        return this.$store.getters["snackbar/visible"];
      },
      set(val) {
        this.$store.commit("snackbar/SET_VISIBLE", val);
      },
    },
  },
  data() {
    return {
      showMemberSidebar: false,
    };
  },
};
</script>

<style>
@font-face {
  font-family: BM_JUA;
  src: url("./assets/BM_JUA.woff") format("woff");
}

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

.font-size-x-large {
  font-size: 2rem;
}

.font-size-regular {
  font-size: 1rem;
}

.font-size-small {
  font-size: 0.9rem;
}

.font-size-x-small {
  font-size: 0.8rem;
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
