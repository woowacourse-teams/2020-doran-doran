<template>
  <div @click.self="closeConfirmModal">
    <div class="pa-0 modal-container">
      <div class="button-box ma-0 pa-0">
        <v-card-text class="text-left">정말 삭제하시겠어요?</v-card-text>
        <v-card-actions>
          <VSpacer />
          <v-btn color="red" text @click="remove">삭제</v-btn>
          <v-btn text @click="closeConfirmModal">취소</v-btn>
        </v-card-actions>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ConfirmModal",
  props: {
    remove: {
      type: Function,
      required: true,
    },
  },
  methods: {
    closeConfirmModal() {
      this.$emit("close");
    },
  },
};
</script>

<style scoped>
.modal-container {
  position: absolute;
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
