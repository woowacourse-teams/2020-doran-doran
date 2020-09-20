<template>
  <div class="d-flex flex-column modal-mask" @click.self="closeModal">
    <VSpacer @click.self="closeModal" />
    <transition name="bounce" @after-leave="closeModal">
      <div v-if="rendered" class="pa-6 pb-1 modal-container">
        <VTextField
          label="새 닉네임을 입력하세요."
          color="grey darken-2"
          maxlength="15"
          :counter="15"
          v-model="newNickname"
          :rules="Object.values(rules)"
          @keypress.enter="updateMember"
        />
        <div class="button-box">
          <v-btn
            text
            class="mb-2 text-subtitle-1 font-weight-bold grey--text text--darken-1"
            @click="bounceOut"
          >
            취소
          </v-btn>
          <v-btn
            text
            :disabled="nicknameUpdateDisabled()"
            class="mb-2 text-subtitle-1 font-weight-bold amber--text text--accent-4"
            @click="updateMember"
          >
            저장
          </v-btn>
        </div>
      </div>
    </transition>
    <VSpacer @click.self="closeModal" />
  </div>
</template>

<script>
import api from "@/api/member";

const NICKNAME_REGEX = "^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣0-9]+$";

export default {
  name: "MemberUpdateModal",
  data() {
    return {
      rendered: false,
      newNickname: "",
      rules: {
        violated: (newVal) =>
          !!newVal.match(NICKNAME_REGEX) ||
          "닉네임은 숫자/한글/영어로 이루어져야 합니다.",
      },
    };
  },
  computed: {
    member() {
      return this.$store.getters["member/getMember"];
    },
  },
  mounted() {
    this.rendered = true;
    this.newNickname = this.member.nickname;
  },
  methods: {
    async updateMember() {
      if (!this.newNickname) {
        this.$store.commit("snackbar/SHOW", "❗️ 닉네임을 입력해주세요.");
        return;
      }
      const updatedMember = await api.updateMember(this.newNickname);
      this.$store.commit("member/SET_MEMBER", updatedMember);
      this.$store.commit("snackbar/SHOW", "🥳 성공적으로 변경되었습니다.");
      this.bounceOut();
    },
    closeModal() {
      this.$emit("close");
    },
    nicknameUpdateDisabled() {
      return (
        this.member.nickname === this.newNickname ||
        !this.newNickname.match(NICKNAME_REGEX)
      );
    },
    bounceOut() {
      this.rendered = false;
    },
  },
};
</script>

<style scoped>
.modal-container {
  width: 90%;
  bottom: 50%;
  margin: auto;
  border-radius: 10px;
  background-color: #fff;
}

.button-box {
  float: right;
}

.bounce-enter-active {
  animation: bounce-in 0.4s;
}
.bounce-leave-active {
  animation: bounce-in 0.5s reverse;
}
@keyframes bounce-in {
  0% {
    transform: scale(0);
  }
  70% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
}
</style>
