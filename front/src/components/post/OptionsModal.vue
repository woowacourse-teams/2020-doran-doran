<template>
  <div class="modal-mask" @click.self="closeModals">
    <div class="pa-0 modal-container">
      <div class="button-box ma-0 pa-0">
        <v-btn v-if="isMine" block text @click="openConfirmModal">삭제</v-btn>
        <v-btn v-if="!isMine" block text @click="report">신고</v-btn>
        <v-btn block text @click="closeModals">취소</v-btn>
      </div>
    </div>
    <ConfirmModal
      v-if="isConfirmModalVisible"
      :remove="remove"
      @close="closeModals"
    />
  </div>
</template>

<script>
import ConfirmModal from "@/components/post/ConfirmModal";

export default {
  name: "OptionsModal",
  components: {
    ConfirmModal,
  },
  props: {
    isMine: {
      type: Boolean,
      required: true,
    },
    remove: {
      type: Function,
      required: true,
    },
  },
  data() {
    return {
      isConfirmModalVisible: false,
    };
  },
  methods: {
    openConfirmModal() {
      this.isConfirmModalVisible = true;
    },
    closeModals() {
      this.isConfirmModalVisible = false;
      this.$emit("close");
    },
    report() {
      this.$store.commit(
        "snackbar/SHOW",
        "👩🏻‍💻아직 신고 기능이 구현되지 않았어요.",
      );
      this.closeModals();
    },
  },
};
</script>

<style scoped>
.modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 90%;
  background-color: #fff;
  border-radius: 10px;
}

.button-box * {
}
</style>
