<template>
  <div class="pa-4">
    <div class="mb-3">
      <v-icon x-large class="mr-3">mdi-account-circle</v-icon>
      <span class="font-weight-bold">{{ post.memberResponse.nickname }}</span>
      <div class="float-right mt-2">{{ postDate }}</div>
    </div>
    <div class="text--disabled font-size-small">
      <span style="color: #659fec;" @click="openMapModal">
        <v-icon size="large" color="#659FEC">mdi-map-marker-radius</v-icon>
        {{ postAddress }}
      </span>
      에 외침
    </div>
    <div class="my-5 text-break">{{ post.content }}</div>
    <div>
      <v-icon small>mdi-comment-processing-outline</v-icon>
      <span class="mx-1">{{ post.comments.length }}</span>
      <v-icon small @click="toggleLike" :color="likeButtonType.color">
        {{ likeButtonType.icon }}
      </v-icon>
      <span class="mx-1">{{ post.likes.length }}</span>
      <div class="float-right text--disabled font-size-small">
        {{ authorAddress }}에서
      </div>
    </div>
    <CommentList :comments="post.comments" @load-post="loadPost" />
    <div class="bottom-spacer" />
    <CommentInput :post-id="post.id" />
    <PostDetailPageLocationMapModal
      v-if="this.isMapModalVisible"
      :location="post.location"
      @close-modal="closeMapModal"
    />
  </div>
</template>

<script>
import CommentInput from "@/components/post/CommentInput";
import CommentList from "@/components/post/CommentList";
import PostDetailPageLocationMapModal from "@/components/post/PostDetailPageLocationMapModal";
import { ERROR_MESSAGE, LIKE_BUTTON_TYPE } from "@/utils/constants";

export default {
  name: "PostDetailPage",
  components: {
    CommentList,
    CommentInput,
    PostDetailPageLocationMapModal,
  },
  data() {
    return {
      isMapModalVisible: false,
    };
  },
  computed: {
    post: {
      get() {
        return this.$store.getters["post/getPost"];
      },
      set() {},
    },
    postDate() {
      return this.$moment(this.post.createdAt).fromNow();
    },
    liked() {
      return this.post.likes.some(this.hasLike);
    },
    likeButtonType() {
      return this.liked ? LIKE_BUTTON_TYPE.LIKED : LIKE_BUTTON_TYPE.DEFAULT;
    },
    postAddress() {
      return Object.values(this.post.address).join(" ");
    },
    authorAddress() {
      return Object.values(this.post.authorAddress).join(" ");
    },
  },
  async created() {
    this.post = await this.$store.dispatch(
      "post/loadPost",
      this.$route.params.id,
    );
    this.$store.commit("appBar/POST_DETAIL_PAGE");
  },
  methods: {
    hasLike(like) {
      return (
        like.memberId === this.$store.getters["member/getMember"].id &&
        like.postId === this.post.id
      );
    },
    async toggleLike() {
      if (this.$store.getters["member/isGuest"]) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.LOGIN_REQUIRED);
        return;
      }
      this.liked ? await this.deletePostLike() : await this.createPostLike();
    },
    async loadPost() {
      this.post = await this.$store.dispatch(
        "post/loadPost",
        this.$route.params.id,
      );
    },
    async deletePostLike() {
      const data = this.post.likes.find(this.hasLike);
      await this.$store.dispatch("post/deletePostLike", data.id);
      await this.loadPost();
    },
    async createPostLike() {
      const data = {
        postId: this.post.id,
      };
      await this.$store.dispatch("post/createPostLike", data);
      await this.loadPost();
    },
    openMapModal() {
      this.isMapModalVisible = true;
    },
    closeMapModal() {
      this.isMapModalVisible = false;
    },
  },
};
</script>

<style scoped>

.bottom-spacer {
  height: 60px;
}
</style>
