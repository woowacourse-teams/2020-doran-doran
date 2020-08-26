<template>
  <div ref="post" class="speech-bubble" @click.prevent="goToPostDetailPage">
    {{ shortContent }} {{ contentTail }}
    <span class="text-caption red--text"> [{{ post.comments.length }}]</span>
    <div class="new-icon" v-if="isRecentPost">N</div>
    <div class="speech-arrow"></div>
  </div>
</template>

<script>
const CONTENT_LENGTH = 15;

export default {
  name: "PostOverlay",
  props: {
    post: {
      type: Object,
      require: true,
    },
  },
  computed: {
    isRecentPost() {
      const halfAnHourAgo = this.$moment()
        .subtract(30, "minutes")
        .format("YYYY-MM-DD HH:mm:ss");
      const postCreatedAt = this.$moment(this.post.createdAt).format(
        "YYYY-MM-DD HH:mm:ss",
      );
      return halfAnHourAgo < postCreatedAt;
    },
    shortContent() {
      return this.post.content.substring(0, CONTENT_LENGTH).trim();
    },
    contentTail() {
      return this.post.content.length > CONTENT_LENGTH ? "..." : "";
    },
  },
  mounted() {
    this.$kakaoMap.setPostOverlay(this.$refs.post, this.post.location);
  },
  methods: {
    goToPostDetailPage() {
      this.$router.push("posts/" + this.post.id);
    },
  },
  watch: {
    post() {
      this.$kakaoMap.setPostOverlay(this.$refs.post, this.post.location);
    },
  },
};
</script>

<style scoped>
.new-icon {
  position: absolute;
  top: -6px;
  right: -6px;
  width: 20px;
  height: 20px;
  padding: 1px;
  border-radius: 15px;
  background-color: red;
  color: white;
  font-size: 12px;
}

.speech-bubble {
  position: relative;
  bottom: 32px;
  padding: 10px;
  min-width: 35px;
  border-radius: 1em;
  box-shadow: 1px 1px 5px grey;
  background: white;
  text-align: center;
}

.speech-arrow {
  position: absolute;
  left: 50%;
  bottom: -6px;
  width: 10px;
  height: 10px;
  margin-left: -5px;
  transform: rotate(45deg);
  background-color: white;
  box-shadow: -5px 0 0 white, 0 -5px 0 white, -5px -5px 0 white,
    1px 1px 5px gray;
}
</style>
