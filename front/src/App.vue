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
  data() {
    return {
      showMemberSidebar: false,
    };
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
  created() {
    this.checkUrl();
    this.checkToken();
    this.$router.beforeEach((to, from, next) => {
      if (to.path === "/" || to.path === "/timeline") {
        this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
      }
      next(true);
    });
  },
  methods: {
    checkUrl() {
      const urlToken = location.href.split("token=")[1];
      if (urlToken) {
        localStorage.setItem("accessToken", urlToken);
        location.href = "/";
      }
    },
    async checkToken() {
      const storageToken = localStorage.getItem("accessToken");
      if (storageToken && storageToken !== "guest") {
        await this.$store.dispatch("member/loadMember");
      } else if (!storageToken) {
        this.$router.push("/login");
      }
    },
  },
};
</script>

<style>
@import "assets/index.css";

.snackbar {
  z-index: 10000 !important;
}

.app-container {
  flex-direction: column;
  width: 100%;
  height: 100%;
  max-width: 500px;
  margin: auto;
  overflow-x: hidden;
  box-shadow: 0 0 40px 2px gray;
}

.app-container > div {
  min-height: 100%;
  height: 100%;
}
</style>
