<template>
  <div @click.self="close">
    <div class="pa-0 modal-container">
      <div class="button-box ma-0 pa-0">
        <v-card-text>정말 삭제하시겠어요?</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red" text @click="deleteObject">삭제</v-btn>
          <v-btn text @click="closeModal">취소</v-btn>
        </v-card-actions>
      </div>
    </div>
  </div>
</template>

<script>
  import { DORAN_DORAN_COLORS } from "@/utils/constants";
  import router from "@/router";

  const DELETE_POST_SUCCESS_MESSAGE = "👻 글이 삭제되었습니다.";

  export default {
    name: "ConfirmModal",
    props: {
      postId: {
        type: Number,
        required: true,
      },
    },
    data() {
      return {
        buttonColor: DORAN_DORAN_COLORS.POINT_COLOR,
      };
    },
    methods: {
      async deleteObject() {
        await this.$store.dispatch("post/deletePost", this.postId);
        this.$store.commit("snackbar/SHOW", DELETE_POST_SUCCESS_MESSAGE);
        router.go(-1);
      },
      closeModal() {
        this.$emit("close-modal");
      }
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
