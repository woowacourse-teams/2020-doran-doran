<template>
  <div class="post-modal">
    <DoranAppBar />
    <div v-if="existMember" class="pa-4">
      <div class="mb-3">
        <v-icon x-large class="mr-3">mdi-account-circle</v-icon>
        <span class="font-weight-bold">{{ post.author.nickname }}</span>
        <div class="float-right mt-2">
          {{ postDate }}
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
      <div class="text--disabled font-size-small">
        <span class="post-address" @click="openMapModal">
          <v-icon size="large" color="#659FEC">mdi-map-marker-radius</v-icon>
          {{ postAddress }}
        </span>
        에 외침
      </div>
      <div class="my-5 text-break" v-html="postContent"></div>
      <div>
        <v-icon small @click="toggleLike" :color="likeButtonType.color">
          {{ likeButtonType.icon }}
        </v-icon>
        <span class="mx-1">{{ post.likes.length }}</span>
        <v-icon small>mdi-comment-processing-outline</v-icon>
        <span class="mx-1">{{ post.comments.length }}</span>
        <div class="float-right text--disabled font-size-small">
          {{ authorAddress }}에서
        </div>
      </div>
      <CommentList :comments="post.comments" @load-post="loadPost" />
      <VSpacer class="bottom-spacer" />
      <CommentInput :post-id="post.id" />
      <PostLocationModal
        v-if="isMapModalVisible"
        :location="post.location"
        @close="closeMapModal"
      />
      <OptionsModal
        v-if="isOptionsModalVisible"
        :is-mine="isMine"
        :remove="remove"
        @close="closeOptionsModal"
      />
    </div>
  </div>
</template>

<script>
import CommentInput from "@/components/post/CommentInput";
import CommentList from "@/components/post/CommentList";
import PostLocationModal from "@/components/post/PostLocationModal";
import OptionsModal from "@/components/post/OptionsModal";
import DoranAppBar from "@/components/DoranAppBar";
import { ERROR_MESSAGE, LIKE_BUTTON_TYPE } from "@/utils/constants";

const DELETE_POST_SUCCESS_MESSAGE = "👻 글이 삭제되었습니다.";
const DELETE_POST_FAIL_MESSAGE = "😭 글 삭제에 실패했습니다.";

export default {
  name: "PostModal",
  components: {
    DoranAppBar,
    CommentList,
    CommentInput,
    PostLocationModal,
    OptionsModal,
  },
  data() {
    return {
      isMapModalVisible: false,
      isOptionsModalVisible: false,
    };
  },
  computed: {
    existMember() {
      return localStorage.getItem("accessToken");
    },
    isMine() {
      return (
        this.post.author.id ===
        this.$store.getters["member/getMember"].id
      );
    },
    post: {
      get() {
        return this.$store.getters["post/post"];
      },
      set() {},
    },
    postContent() {
      return this.post.content.replace(/\n/g, "<br />");
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
    await this.$store.dispatch("post/loadPost", this.$route.params.id);
    this.$store.commit("appBar/POST_DETAIL_PAGE");
  },
  methods: {
    async remove() {
      await this.$store.dispatch("post/deletePost", this.post.id).catch((e) => {
        this.$store.commit("snackbar/SHOW", DELETE_POST_FAIL_MESSAGE);
        throw e;
      });
      this.$store.commit("snackbar/SHOW", DELETE_POST_SUCCESS_MESSAGE);
      this.$store.commit("post/CLEAR_POSTS");
      const filteredPosts = await this.$store.dispatch("postfilter/filterPosts");
      this.$store.commit("post/SET_POSTS", filteredPosts);
      this.$router.go(-1);
    },
    hasLike(like) {
      return (
        like.memberId === this.$store.getters["member/getMember"].id &&
        like.postId === this.post.id
      );
    },
    async toggleLike() {
      this.liked ? await this.deletePostLike() : await this.createPostLike();
    },
    async loadPost() {
      await this.$store.dispatch("post/loadPost", this.post.id);
    },
    async deletePostLike() {
      const data = this.post.likes.find(this.hasLike);
      await this.$store.dispatch("post/deletePostLike", data.id).catch((e) => {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.LOGIN_REQUIRED);
        throw e;
      });
      await this.loadPost();
    },
    async createPostLike() {
      const data = {
        postId: this.post.id,
      };
      await this.$store.dispatch("post/createPostLike", data).catch((e) => {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.LOGIN_REQUIRED);
        throw e;
      });
      await this.loadPost();
    },
    openMapModal() {
      this.isMapModalVisible = true;
    },
    closeMapModal() {
      this.isMapModalVisible = false;
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

<style scoped>
.post-modal {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 9999;
  height: 100%;
  width: 100%;
  background-color: white;
}

.post-address {
  color: #659fec;
}

.bottom-spacer {
  height: 60px;
}
</style>
