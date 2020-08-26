<template>
  <div @click.self="close">
    <div class="pa-0 modal-container">
      <div class="button-box ma-0 pa-0">
        <v-card-text>정말 삭제하시겠어요?</v-card-text>
        <v-card-actions>
          <VSpacer />
          <v-btn color="red" text @click="deleteObject">삭제</v-btn>
          <v-btn text @click="closeModal">취소</v-btn>
        </v-card-actions>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ConfirmModal",
  props: {
    type: {
      type: String,
      required: true,
    },
  },
  methods: {
    async deleteObject() {
      if (this.type === "post") {
        this.$emit("delete-post");
      } else if (this.type === "comment") {
        this.$emit("delete-comment");
      }
    },
    closeModal() {
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
