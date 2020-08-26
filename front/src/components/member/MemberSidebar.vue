<template>
  <v-navigation-drawer v-model="drawer" absolute temporary class="drawer">
    <v-icon @click="hideSidebar" class="float-right ma-4">
      mdi-window-close
    </v-icon>

    <div class="ma-7 mt-16" @click="login">
      <v-avatar>
        <v-icon size="36">mdi-account-circle</v-icon>
      </v-avatar>
      <span class="mx-3 font-weight-bold">
        {{ member.nickname }}
      </span>
    </div>

    <VDivider />

    <v-list>
      <v-list-item
        v-for="item in items"
        :key="item.title"
        link
        @click="hideSidebar(item.action)"
      >
        {{ item.title }}
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
import { API_BASE_URL } from "@/utils/constants";

export default {
  name: "MemberSidebar",
  data() {
    return {
      selected: false,
      items: [
        {
          title: "정보수정",
          action: this.updateMember,
        },
        {
          title: "로그아웃",
          action: this.logout,
        },
        {
          title: "회원탈퇴",
          action: this.deleteMember,
        },
      ],
    };
  },
  computed: {
    member() {
      return this.$store.getters["member/getMember"];
    },
    drawer: {
      get() {
        return this.$store.getters["memberSidebar/visible"];
      },
      set(val) {
        this.$store.commit("memberSidebar/SET", val);
      },
    },
  },
  methods: {
    updateMember() {},
    login() {
      if (this.$store.getters["member/isGuest"]) {
        window.location.href = API_BASE_URL.EC2 + "/oauth2/authorization/kakao";
      }
    },
    logout() {},
    deleteMember() {},
    hideSidebar(action) {
      this.$store.commit("memberSidebar/HIDE");
      if (action instanceof Function) {
        action();
      }
    },
  },
  watch: {
    selected() {
      this.selected = true;
      this.$store.commit("memberSidebar/HIDE");
    },
  },
};
</script>

<style scoped>
.drawer {
  z-index: 9999;
}
</style>
