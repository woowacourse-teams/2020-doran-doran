<template>
  <div class="modal-mask" @click.self="closeModal">
    <div class="pa-3 modal-container">

    </div>
  </div>
</template>

<script>
export default {
  name: "PostCreateModal",
  data() {
    return {
    };
  },
  methods: {
    async createPost() {
    },
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
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 90%;
  background-color: #fff;
  border-radius: 10px;
}
</style>
