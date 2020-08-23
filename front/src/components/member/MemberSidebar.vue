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
        닉네임요기요기{{ member.nickName }}
      </span>
    </div>

    <VDivider />

    <v-list>
      <v-list-item-group v-model="group">
        <v-list-item v-for="item in items" :key="item.title" link>
          <v-list-item-content>
            <v-list-item-title @click="item.action">
              {{ item.title }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  name: "MemberSidebar",
  data() {
    return {
      group: null,
      member: {
        nickName: "",
        picture: "",
      },
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
    hideSidebar() {
      this.$store.commit("memberSidebar/HIDE");
    },
  },
  watch: {
    group() {
      this.$store.commit("memberSidebar/HIDE");
    },
  },
};
</script>

<style scoped></style>
