<template>
  <v-navigation-drawer v-model="drawer" absolute temporary>
    <v-icon @click="hideSidebar" class="float-right ma-4">
      mdi-window-close
    </v-icon>

    <div class="ma-7 mt-16">
      <img
        :src="member.picture"
        class="rounded-circle"
        width="60px"
        height="60px"
        alt="profile"
      />
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

<style scoped></style>
