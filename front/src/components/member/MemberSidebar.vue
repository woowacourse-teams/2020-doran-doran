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

    <MemberUpdateModal v-if="isUpdating" @close="closeMemberUpdateModal" />

    <v-dialog v-model="isDeleting" persistent>
      <v-card>
        <v-card-text class="pa-5">정말 탈퇴하시겠어요?</v-card-text>
        <v-card-actions>
          <VSpacer />
          <v-btn text @click="closeMemberDeleteDialog">
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

const LOGOUT_SUCCESS_MESSAGE = "성공적으로 로그아웃 되었습니다.";
const FAILED_TO_DELETE_MEMBER_MESSAGE =
  "탈퇴에 실패했습니다. 다시 요청해주세요.";
const DELETE_MEMBER_SUCCESS = "성공적으로 탈퇴를 완료했습니다.";

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
          action: this.openMemberUpdateModal,
        },
        {
          title: "로그아웃",
          action: this.logout,
        },
        {
          title: "회원탈퇴",
          action: this.openMemberDeleteDialog,
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
    openMemberUpdateModal() {
      this.isUpdating = true;
    },
    closeMemberUpdateModal() {
      this.isUpdating = false;
    },
    logout() {
      this.$store.commit("member/SET_LOGOUT_MEMBER");
      this.$store.commit("snackbar/SHOW", LOGOUT_SUCCESS_MESSAGE);
      this.$router.push("/login");
    },
    openMemberDeleteDialog() {
      this.isDeleting = true;
    },
    closeMemberDeleteDialog() {
      this.isDeleting = false;
    },
    async deleteMember() {
      await api.deleteMember().catch((err) => {
        if (err.response.status === 500) {
          this.$store.commit("snackbar/SHOW", FAILED_TO_DELETE_MEMBER_MESSAGE);
          this.closeMemberDeleteDialog();
        }
      });
      if (this.isDeleting) {
        this.$store.commit("member/SET_LOGOUT_MEMBER");
        this.$store.commit("snackbar/SHOW", DELETE_MEMBER_SUCCESS);
        await this.$router.push("/login");
        this.closeMemberDeleteDialog();
      }
    },
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
