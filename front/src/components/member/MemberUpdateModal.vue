<template>
  <div class="d-flex flex-column modal-mask" @click.self="closeModal">
    <VSpacer />
    <transition name="bounce">
      <div v-if="rendered" class="pa-6 pb-1 modal-container">
        <v-text-field
          label="새 닉네임을 입력하세요."
          color="grey darken-2"
          :value="member.nickname"
        >
        </v-text-field>
        <div class="button-box">
          <v-btn
            text
            class="mb-2 text-subtitle-1 font-weight-bold grey--text text--darken-1"
            @click.prevent="closeModal"
          >
            취소
          </v-btn>
          <v-btn
              text
            class="mb-2 text-subtitle-1 font-weight-bold amber--text text--accent-4"
            @click.prevent="updateMember"
          >
            저장
          </v-btn>
        </div>
      </div>
    </transition>
    <VSpacer />
  </div>
</template>

<script>
export default {
  name: "PostCreateModal",
  data() {
    return {
      newNickname: "",
      rendered: false,
    };
  },
  mounted() {
    this.rendered = true;
  },
  computed: {
    member() {
      return this.$store.getters["member/getMember"];
    },
  },
  methods: {
    async updateMember() {},
    closeModal() {
      this.$emit("close-modal");
    },
  },
};
</script>

<style scoped>
.modal-mask {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 9998;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  text-align: center;
}

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
