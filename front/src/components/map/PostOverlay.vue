<template>
  <div class="speech-bubble" ref="post" @click.prevent="routePage">
    {{ shortContent }} {{ contentTail }}
    <span class="red--text text-caption"> [{{ post.comments.length }}]</span>
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
    this.$setPostOverlay(this.$refs.post, this.post.location);
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
  background: white;
  border-radius: 1em;
  padding: 10px;
  bottom: 32px;
  box-shadow: 1px 1px 5px grey;
  min-width: 35px;
  text-align: center;
}

.speech-arrow {
  position: absolute;
  background-color: white;
  left: 50%;
  width: 10px;
  height: 10px;
  bottom: -6px;
  margin-left: -5px;
  transform: rotate(45deg);
  box-shadow: -5px 0 0 white, 0 -5px 0 white, -5px -5px 0 white,
    1px 1px 5px gray;
}
</style>
