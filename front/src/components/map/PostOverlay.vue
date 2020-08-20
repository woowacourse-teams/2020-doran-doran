<template>
  <div ref="post" class="speech-bubble" @click.prevent="routePage">
    {{ shortContent }} {{ contentTail }}
    <span class="text-caption red--text"> [{{ post.comments.length }}]</span>
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
    routePage() {
      this.$router.push("posts/" + this.post.id);
    },
  },
};
</script>

<style scoped>
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
  height: 10px;
  width: 10px;
  margin-left: -5px;
  transform: rotate(45deg);
  background-color: white;
  box-shadow: -5px 0 0 white, 0 -5px 0 white, -5px -5px 0 white,
    1px 1px 5px gray;
}
</style>
