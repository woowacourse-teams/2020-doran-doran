<template>
  <div>
    <VDivider class="my-2" />
    <div class="mx-2">
      <div class="mb-1">
        <v-icon class="mr-2">mdi-account-circle</v-icon>
        <span class="font-weight-bold">{{ comment.author.nickname }}</span>
        <div class="float-right text--disabled font-size-x-small">
          {{ comment.distance.toFixed(2) }} km 밖에서
          <v-icon
            color="black"
            size="large"
            class="mb-1"
            @click="openOptionsModal"
          >
            mdi-dots-vertical
          </v-icon>
        </div>
      </div>
      <div>
        {{ comment.content }}
      </div>
      <div>
        <span class="text--disabled font-size-x-small">{{ commentDate }}</span>
        <span class="float-right">
          <v-icon small @click="toggleLike" :color="likeButtonType.color">
            {{ likeButtonType.icon }}
          </v-icon>
          <span class="mx-1">{{ comment.likes.length }}</span>
        </span>
      </div>
    </div>
    <OptionsModal
      v-if="isOptionsModalVisible"
      :is-mine="isMine"
      :remove="remove"
      @close="closeOptionsModal"
    />
  </div>
</template>

<script>
import { ERROR_MESSAGE, LIKE_BUTTON_TYPE } from "@/utils/constants";
import OptionsModal from "@/components/post/OptionsModal";

const DELETE_COMMENT_SUCCESS_MESSAGE = "👻 댓글이 삭제되었습니다.";
const DELETE_COMMENT_FAIL_MESSAGE = "😭 댓글 삭제에 실패했습니다.";

export default {
  name: "CommentItem",
  components: {
    OptionsModal,
  },
  data() {
    return {
      isOptionsModalVisible: false,
    };
  },
  props: {
    comment: {
      type: Object,
      required: true,
    },
  },
  computed: {
    isMine() {
      return (
        this.comment.author.id === this.$store.getters["member/getMember"].id
      );
    },
    commentDate() {
      return this.$moment(this.comment.createdAt).fromNow();
    },
    liked() {
      return this.comment.likes.some(this.hasLike);
    },
    likeButtonType() {
      return this.liked ? LIKE_BUTTON_TYPE.LIKED : LIKE_BUTTON_TYPE.DEFAULT;
    },
  },
  methods: {
    async remove() {
      await this.$store
        .dispatch("comment/deleteComment", this.comment.id)
        .catch((e) => {
          this.$store.commit("snackbar/SHOW", DELETE_COMMENT_FAIL_MESSAGE);
          throw e;
        });
      this.$store.commit("snackbar/SHOW", DELETE_COMMENT_SUCCESS_MESSAGE);
      await this.$store.dispatch("post/loadPost", this.$route.params.id);
    },
    hasLike(like) {
      return (
        like.memberId === this.$store.getters["member/getMember"].id &&
        like.commentId === this.comment.id
      );
    },
    async toggleLike() {
      this.liked
        ? await this.deleteCommentLike()
        : await this.createCommentLike();
    },
    async deleteCommentLike() {
      const data = this.comment.likes.find(this.hasLike);
      await this.$store
        .dispatch("comment/deleteCommentLike", data.id)
        .catch((e) => {
          this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.LOGIN_REQUIRED);
          throw e;
        });
      await this.$store.dispatch("post/loadPost", this.comment.postId);
    },
    async createCommentLike() {
      const data = {
        commentId: this.comment.id,
      };
      await this.$store
        .dispatch("comment/createCommentLike", data)
        .catch((e) => {
          this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.LOGIN_REQUIRED);
          throw e;
        });
      await this.$store.dispatch("post/loadPost", this.comment.postId);
    },
    deleteComment() {
      this.$emit("delete", this.comment.id);
    },
    openOptionsModal() {
      this.isOptionsModalVisible = true;
    },
    closeOptionsModal() {
      this.isOptionsModalVisible = false;
    },
  },
};
</script>

<style scoped></style>
