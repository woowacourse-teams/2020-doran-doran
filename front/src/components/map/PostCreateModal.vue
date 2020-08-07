<template>
  <div class="modal-mask" @click.self="closeModal">
    <v-snackbar v-model="snackbarWarning" timeout="1500" top>
      {{ snackbarMessage }}
    </v-snackbar>
    <div class="pa-3 modal-container">
      <VTextarea
        v-model="content"
        type="text"
        name="content"
        placeholder="글 내용을 입력해주세요."
        maxlength="200"
        :counter="200"
        no-resize
        solo
      />

      <div class="float-right">
        <v-btn
          @click.prevent="closeModal"
          class="my-6 mx-1 rounded-pill"
          color="grey lighten-4"
        >
          취소
        </v-btn>
        <v-btn
          @click.prevent="createPost"
          class="my-6 mx-1 rounded-pill"
          :color="buttonColor"
        >
          등록
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { MAP_MODE } from "@/utils/constants";
import router from "@/router";
import { DORAN_DORAN_COLORS } from "@/utils/constants";
import { ERROR_MESSAGE } from "../../utils/constants";

export default {
  name: "PostCreateModal",
  data() {
    return {
      content: "",
      buttonColor: DORAN_DORAN_COLORS.POINT_COLOR,
      snackbarWarning: false,
      snackbarMessage: "🤔 내용을 입력해주세요.",
    };
  },
  methods: {
    checkLocation(location) {
      if (location === undefined) {
        this.snackbarMessage = ERROR_MESSAGE.UNIDENTIFIABLE_LOCATION;
        this.snackbarWarning = true;
        return false;
      } else {
        return true;
      }
    },
    async createPost() {
      if (this.content === "") {
        this.snackbarWarning = true;
        return;
      }
      const postLocation = this.$getCenterLocation();
      const authorLocation = await this.$getCurrentLocation();

      //   사용자의 위치를 받을 수 없는 경우
      if (await this.checkLocation(authorLocation)) {
        const data = {
          memberId: 1,
          content: this.content,
          location: postLocation,
          address: await this.$getAddress(postLocation),
          authorAddress: await this.$getAddress(authorLocation),
        };
        await this.$store
          .dispatch("post/createPost", data)
          .then(() => (this.content = ""))
          .then(() => this.closeModal())
          .then(() => router.go(0));
      }
    },
    closeModal() {
      this.$store.commit("modal/CHANGE_STATE", MAP_MODE.DEFAULT);
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
