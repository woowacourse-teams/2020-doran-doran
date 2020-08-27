<template>
  <div class="d-flex flex-column modal-mask" @click.self="bounceOut">
    <VSpacer @click.self="bounceOut" />
    <transition name="bounce" @after-leave="closeModal">
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
            @click="bounceOut"
          >
            취소
          </v-btn>
          <v-btn
            class="my-6 mx-1 mt-2 rounded-pill doran-doran-color"
            @click="createPost"
          >
            등록
          </v-btn>
        </div>
      </div>
    </transition>
    <VSpacer @click.self="bounceOut" />
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
      location: this.$kakaoMap.getCenterLocation(),
      buttonColor: DORAN_DORAN_COLORS.POINT_COLOR,
      rendered: false,
    };
  },
  mounted() {
    this.rendered = true;
  },
  methods: {
    async createPost() {
      if (this.content === "") {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.NO_CONTENT_MESSAGE);
        return;
      }
      const postLocation = this.location;
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
      await this.$store.dispatch("post/createPost", data).catch((e) => {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.LOGIN_REQUIRED);
        throw e;
      });
      this.$store.commit("snackbar/SHOW", CREATE_POST_SUCCESS_MESSAGE);
      this.$store.commit("post/CLEAR_POSTS");
      this.$store.commit("filter/SET_END_DATE_TO_NOW");
      const filteredPosts = await this.$store.dispatch("filter/filterPosts");
      this.$store.commit("post/SET_POSTS", filteredPosts);
      this.bounceOut();
    },
    bounceOut() {
      this.content = "";
      this.rendered = false;
    },
    closeModal() {
      this.$store.commit("mapMode/CHANGE_STATE", MAP_MODE.DEFAULT);
      this.$store.commit("appBar/MAP_PAGE_DEFAULT_MODE");
    },
  },
};
</script>

<style scoped>
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
