<template>
  <div class="modal-mask" @click.self="closeModal">
    <div class="pa-0 modal-container">
      <div class="button-box ma-0 pa-0">
        <v-btn v-if="this.isMine" block text @click="openModal"
          >삭제</v-btn
        >
        <v-btn v-if="!this.isMine" block text>신고</v-btn>
        <v-btn block text @click="closeModal">취소</v-btn>
      </div>
    </div>
    <ConfirmModal
      v-if="this.isConfirmModalVisible"
      @close-modal="closeModal"
      :post-id="postId"
    ></ConfirmModal>
  </div>
</template>

<script>
import ConfirmModal from "@/components/post/ConfirmModal";

export default {
  name: "OptionsModal",
  components: {
    ConfirmModal,
  },
  data() {
    return {
      isConfirmModalVisible: false,
    };
  },
  props: {
    isMine: {
      type: Boolean,
      required: true,
    },
    postId: {
      type: Number,
      required: true,
    },
  },
  methods: {
    openModal() {
      this.isConfirmModalVisible = true;
    },
    closeModal() {
      this.isConfirmModalVisible = false;
      this.$emit("close-modal");
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
