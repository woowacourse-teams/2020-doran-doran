<template>
  <v-navigation-drawer
    class="fill-height foo"
    v-model="drawer"
    absolute
    temporary
  >
    <v-btn icon class="float-right ma-1">
      <v-icon @click.self="closeModal">mdi-window-close</v-icon>
    </v-btn>

    <v-list-item class="ma-5">
      <v-list-item-avatar>
          <img src="member.picture" />
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title>
          닉네임요기요기{{ member.nickName }}
        </v-list-item-title>
      </v-list-item-content>
    </v-list-item>

    <VDivider />

    <v-list>
      <v-list-item-group
        v-model="group"
        active-class="amber--text text--accent-4"
      >
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
  name: "MyPageModal",
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
          title: "이용약관",
          action: this.termsOfUse,
        },
        {
          title: "회원탈퇴",
          action: this.deleteMember,
        },
      ],
    };
  },
  props: {
    drawer: {
      type: Boolean,
      required: false,
      default: false,
    },
  },
  methods: {
    updateMember() {},
    logout() {},
    termsOfUse() {},
    deleteMember() {},
    closeModal() {
      this.$emit("show-modal", false);
    },
  },
  watch: {
    drawer(newVal) {
      this.drawer = newVal;
    },
    group() {
      this.$emit("show-modal", false);
    },
  },
};
</script>

<style scoped>
.foo {
  z-index: 9999;
  position: fixed;
  background-color: black;
}
</style>
