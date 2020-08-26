<template>
  <div>
    <v-navigation-drawer v-model="drawer" absolute temporary class="drawer">
      <v-icon @click="hideSidebar" class="float-right ma-4">
        mdi-window-close
      </v-icon>

      <div class="ma-5 mt-10 mb-5" @click="login">
        <v-avatar>
          <v-icon size="36">mdi-account-circle</v-icon>
        </v-avatar>
        <span class="mx-3 font-weight-bold">
          {{ member.nickname }}
        </span>
      </div>

      <v-list v-show="!isGuest">
        <VDivider />
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

    <MemberUpdateModal
      v-if="this.isUpdating"
      @close-modal="closeMemberUpdateModal"
    />

    <v-dialog v-model="isDeleting" persistent class="a">
      <v-card>
        <v-card-text class="pa-5">정말 탈퇴하시겠어요?</v-card-text>
        <v-card-actions>
          <VSpacer />
          <v-btn text @click="isDeleting = false">
            아니오
          </v-btn>
          <v-btn text @click="deleteMember">네</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { API_BASE_URL } from "@/utils/constants";
import MemberUpdateModal from "@/components/member/MemberUpdateModal";
import api from "@/api/member";

export default {
  name: "MemberSidebar",
  components: {
    MemberUpdateModal,
  },
  data() {
    return {
      selected: false,
      items: [
        {
          title: "정보수정",
          action: this.openUpdateMemberModal,
        },
        {
          title: "로그아웃",
          action: this.logout,
        },
        {
          title: "회원탈퇴",
          action: this.openDeleteMemberDialog,
        },
      ],
      isUpdating: false,
      isDeleting: false,
    };
  },
  computed: {
    member() {
      return this.$store.getters["member/getMember"];
    },
    isGuest() {
      return this.$store.getters["member/isGuest"];
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
    login() {
      if (this.isGuest) {
        window.location.href = API_BASE_URL.EC2 + "/oauth2/authorization/kakao";
      }
    },
    openUpdateMemberModal() {
      this.isUpdating = true;
    },
    logout() {
      this.$store.commit("member/SET_LOGOUT_MEMBER");
      this.$store.commit("snackbar/SHOW", "성공적으로 로그아웃 되었습니다.");
      this.$router.push("/login");
    },
    openDeleteMemberDialog() {
      this.isDeleting = true;
    },
    async deleteMember() {
      await api.deleteMember().catch((err) => {
        if (err.response.status === 500) {
          this.$store.commit(
            "snackbar/SHOW",
            "탈퇴에 실패했습니다. 다시 요청해주세요.",
          );
          this.isDeleting = false;
        }
      });
      if (this.isDeleting) {
        this.$store.commit("member/SET_LOGOUT_MEMBER");
        this.$store.commit("snackbar/SHOW", "성공적으로 탈퇴를 완료했습니다.");
        this.$router.push("/login");
        this.isDeleting = false;
      }
    },
    hideSidebar(action) {
      this.$store.commit("memberSidebar/HIDE");
      if (action instanceof Function) {
        action();
      }
    },
    closeMemberUpdateModal() {
      this.isUpdating = false;
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
