<template>
  <div class="modal-mask" @click.self="closeModal">
    <div class="pa-3 modal-container">
      <VTextarea
        type="text"
        v-model="content"
        placeholder="글 내용을 입력해주세요."
        maxlength="200"
        :counter="200"
        no-resize
        solo
      />

      <div class="float-right">
        <v-btn
          color="grey lighten-4"
          class="my-6 mx-1 rounded-pill"
          @click.prevent="closeModal"
        >
          취소
        </v-btn>
        <v-btn
          :color="buttonColor"
          class="my-6 mx-1 rounded-pill"
          @click.prevent="createPost"
        >
          등록
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { MAP_MODE } from "@/utils/constants";
import { DORAN_DORAN_COLORS } from "@/utils/constants";
import { ERROR_MESSAGE } from "@/utils/constants";

const CREATE_POST_SUCCESS_MESSAGE = "🎉 글이 등록되었습니다.";

export default {
  name: "PostCreateModal",
  data() {
    return {
      content: "",
      buttonColor: DORAN_DORAN_COLORS.POINT_COLOR,
    };
  },
  methods: {
    async createPost() {
      if (this.content === "") {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.NO_CONTENT_MESSAGE);
        return;
      }
      const postLocation = this.$kakaoMap.getCenterLocation();
      const authorLocation = await this.$kakaoMap
        .getCurrentLocation()
        .catch(() =>
          this.$store.commit(
            "snackbar/SHOW",
            ERROR_MESSAGE.UNIDENTIFIABLE_LOCATION,
          ),
        );
      if (!authorLocation) {
        return;
      }
      const data = {
        memberId: 1,
        content: this.content,
        location: postLocation,
        address: await this.$kakaoMap.getAddress(postLocation),
        authorAddress: await this.$kakaoMap.getAddress(authorLocation),
      };
      await this.$store.dispatch("post/createPost", data);
      this.$store.commit("snackbar/SHOW", CREATE_POST_SUCCESS_MESSAGE);
      this.closeModal();
    },
    closeModal() {
      this.content = "";
      this.$store.commit("mapMode/CHANGE_STATE", MAP_MODE.DEFAULT);
      this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
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
}

.modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 90%;
  border-radius: 10px;
  background-color: #fff;
}
</style>
