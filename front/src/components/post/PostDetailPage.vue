<template>
  <div class="pa-4">
    <div class="mb-3">
      <v-icon x-large class="mr-3">mdi-account-circle</v-icon>
      <span class="font-weight-bold">{{ post.memberResponse.nickname }}</span>
      <div class="float-right mt-2">{{ postDate }}</div>
    </div>
    <div class="text--disabled post-address">
      {{ post.address.depth1 }}
      {{ post.address.depth2 }}
      {{ post.address.depth3 }}에 외침
    </div>
    <div class="my-5 text-break">{{ post.content }}</div>
    <div>
      <v-icon small>mdi-comment-processing-outline</v-icon>
      <span class="mx-1">{{ post.comments.length }}</span>
      <v-icon small @click="toggleLike" :color="likeButtonType.color">
        {{ likeButtonType.icon }}
      </v-icon>
      <span class="mx-1">{{ post.likes.length }}</span>
      <div class="text--disabled float-right post-address">
        {{ post.authorAddress.depth1 }}
        {{ post.authorAddress.depth2 }}
        {{ post.authorAddress.depth3 }}에서
      </div>
    </div>
    <CommentList :comments="post.comments" @load-post="loadPost" />
    <div class="bottom-spacer" />
    <CommentInput :post-id="post.id" />
  </div>
</template>

<script>
import CommentInput from "@/components/post/CommentInput";
import CommentList from "@/components/post/CommentList";
import { LIKE_BUTTON_TYPE } from "@/utils/constants";

export default {
  name: "PostDetailPage",
  components: {
    CommentList,
    CommentInput,
  },
  data() {
    return {
      post: {
        id: 0,
        memberResponse: {
          id: 0,
          nickname: "",
        },
        content: "",
        address: {
          depth1: "",
          depth2: "",
          depth3: "",
        },
        authorAddress: {
          depth1: "",
          depth2: "",
          depth3: "",
        },
        likes: [],
        createdAt: "",
        comments: [],
      },
    };
  },
  computed: {
    postDate() {
      return this.$moment(this.post.createdAt).fromNow();
    },
    liked() {
      return this.post.likes.some(
        (like) =>
          like.memberId === this.$store.getters["member/getMembers"] &&
          like.postId === this.post.id,
      );
    },
    likeButtonType() {
      return this.liked
        ? this.$store.getters["like/liked"]
        : this.$store.getters["like/notLiked"];
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
      const data = this.post.likes.find(
        (like) =>
          like.memberId === this.$store.getters["member/getMembers"] &&
          like.postId === this.post.id,
      );
      await this.$store.dispatch("post/deletePostLike", data.id);
      await this.loadPost();
    },
    async createPostLike() {
      const data = {
        memberId: this.post.memberResponse.id,
        postId: this.post.id,
      };
      await this.$store.dispatch("post/createPostLike", data);
      await this.loadPost();
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
