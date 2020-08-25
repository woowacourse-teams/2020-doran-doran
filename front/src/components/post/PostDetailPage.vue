<template>
  <div class="pa-4">
    <div class="mb-3">
      <v-icon x-large class="mr-3">mdi-account-circle</v-icon>
      <span class="font-weight-bold">{{ post.memberResponse.nickname }}</span>
      <div class="float-right mt-2">{{ postDate }}</div>
    </div>
    <div class="text--disabled post-address">
      <span @click="openMapModal">
        <v-icon size="large" color="#659FEC">mdi-map-marker-radius</v-icon>
        <span style="color: #659fec;">
          {{ post.address.depth1 }}
          {{ post.address.depth2 }}
          {{ post.address.depth3 }}</span
        >에 외침
      </span>
    </div>
    <div class="my-5 text-break">{{ post.content }}</div>
    <div>
      <v-icon small>mdi-comment-processing-outline</v-icon>
      <span class="mx-1">{{ post.comments.length }}</span>
      <v-icon small @click="toggleLike" :color="likeButtonType.color">
        {{ likeButtonType.icon }}
      </v-icon>
      <span class="mx-1">{{ post.likes.length }}</span>
      <div class="float-right text--disabled post-address">
        {{ post.authorAddress.depth1 }}
        {{ post.authorAddress.depth2 }}
        {{ post.authorAddress.depth3 }}에서
      </div>
    </div>
    <CommentList :comments="post.comments" @load-post="loadPost" />
    <div class="bottom-spacer" />
    <CommentInput :post-id="post.id" />
    <PostDetailPageLocationMapModal
      v-if="this.isMapModalMode"
      :location="post.location"
      @close-modal="closeMapModal"
    />
  </div>
</template>

<script>
import CommentInput from "@/components/post/CommentInput";
import CommentList from "@/components/post/CommentList";
import PostDetailPageLocationMapModal from "@/components/post/PostDetailPageLocationMapModal";
import { LIKE_BUTTON_TYPE } from "@/utils/constants";

export default {
  name: "PostDetailPage",
  components: {
    CommentList,
    CommentInput,
    PostDetailPageLocationMapModal,
  },
  data() {
    return {
      isMapModalMode: false,
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
      this.isMapModalMode = true;
    },
    closeMapModal() {
      this.isMapModalMode = false;
    },
  },
};
</script>

<style scoped>
.post-address {
  font-size: 0.9rem;
}

.bottom-spacer {
  height: 60px;
}
</style>
