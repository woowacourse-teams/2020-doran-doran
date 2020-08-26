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
@import "assets/index.css";

.app-container {
  flex-direction: column;
  height: 100%;
  width: 100%;
  max-width: 500px;
  margin: auto;
  overflow-x: hidden;
}

.app-container > div {
  min-height: 100%;
  height: 100%;
}
</style>
