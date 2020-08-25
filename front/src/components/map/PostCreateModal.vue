<template>
  <div class="d-flex flex-column modal-mask" @click.self="closeModal">
    <VSpacer />
    <transition name="bounce">
      <div v-if="rendered">
        <VTextarea
          type="text"
          v-model="content"
          placeholder="글 내용을 입력해주세요."
          maxlength="200"
          :counter="200"
          elevation="2"
          solo
          auto-grow
          class="pa-3 modal-container"
        />
        <div class="button-box">
          <v-btn
            color="grey lighten-4"
            class="my-6 mx-1 mt-2 rounded-pill"
            @click.prevent="closeModal"
          >
            취소
          </v-btn>
          <v-btn
            class="my-6 mx-1 mt-2 rounded-pill doran-doran-color"
            @click.prevent="createPost"
          >
            등록
          </v-btn>
        </div>
      </div>
    </transition>
    <VSpacer />
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
      rendered: false,
    };
  },
  mounted() {
    this.rendered = true;
  },
  methods: {
    async createPost() {
      if (
        !sessionStorage.getItem("accessToken") ||
        sessionStorage.getItem("accessToken") === "guest"
      ) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.LOGIN_REQUIRED);
        return;
      }

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
  text-align: center;
}

.modal-container {
  width: 90%;
  margin: auto;
  border-radius: 10px;
  background-color: #fff;
}

.button-box {
  position: absolute;
  right: 5%;
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
