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
    <MemberUpdateModal v-if="isInitialMember" @close="closeMemberUpdateModal" />
  </v-app>
</template>

<script>
import DoranAppBar from "@/components/DoranAppBar";
import MemberSidebar from "@/components/member/MemberSidebar";
import MemberUpdateModal from "@/components/member/MemberUpdateModal";

export default {
  name: "App",
  components: {
    DoranAppBar,
    MemberSidebar,
    MemberUpdateModal,
  },
  data() {
    return {
      showMemberSidebar: false,
      isInitialMember: false,
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
  async created() {
    this.checkUrl();
    this.checkUrlToken();
    await this.checkToken();
    this.preventRoute();
    this.isInitialMember = this.$store.getters["member/isInitialMember"];
  },
  methods: {
    checkUrl() {
      const isNotMapPage = this.$route.name !== this.$pages.map.name;
      const isNotPostModal = this.$route.name !== this.$pages.post().name;
      if (isNotMapPage && isNotPostModal) {
        this.$router.push(this.$pages.map.path);
      }
    },
    checkUrlToken() {
      const urlToken = location.href.split("token=")[1];
      if (urlToken) {
        localStorage.setItem("accessToken", urlToken);
        this.$router.push(this.$pages.map.path);
      }
    },
    async checkToken() {
      const storageToken = localStorage.getItem("accessToken");
      if (storageToken && storageToken !== "guest") {
        await this.$store.dispatch("member/loadMember");
      } else if (!storageToken) {
        await this.$router.push(this.$pages.login.path);
      }
    },
    preventRoute() {
      this.$router.beforeEach(async (to, from, next) => {
        if (to.path === this.$pages.map.path || to.path === this.$pages.timeline.path) {
          this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
          await this.$store.dispatch("post/loadPosts");
        }
        next(true);
      });
    },
    closeMemberUpdateModal() {
      this.isInitialMember = false;
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
