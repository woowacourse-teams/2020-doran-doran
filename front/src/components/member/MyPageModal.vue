<template>
  <div class="modal-mask" @click.self="closeModal">
    <div class="pa-3 modal-container">
      <v-btn icon class="float-right ma-1">
        <v-icon @click.self="closeModal">mdi-window-close</v-icon>
      </v-btn>

      <img class="profile-image" src="member.picture" />
      <span class="font-weight-bold">닉네임요기요기{{ member.nickName }}</span>
      <v-list flat>
        <v-list-item-group>
          <v-list-item v-for="(item, i) in myPageItems" :key="i">
            <v-list-item-content>
              <v-list-item-title @click="item.action">
                {{ item.title }}
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </div>
  </div>
</template>

<script>
export default {
  name: "PostCreateModal",
  data() {
    return {
      myPageItems: [
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
      member: {
        nickName: "",
        picture: "",
      },
    };
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
};
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}
.modal-container {
  position: relative;
  width: 80%;
  height: 200%;
  background-color: #fff;
}
.profile-image {
  margin: 30px;
  width: 60px;
  height: 60px;
  border-radius: 100%;
}
</style>
