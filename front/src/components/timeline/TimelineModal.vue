<template>
  <div class="modal-mask" @click.self="slideDown">
    <transition name="slide-up" @after-leave="close">
      <div v-if="rendered" class="pa-3 modal-container">
        <div class="text-center">
          <span class="ma-auto text-center">타임라인</span>
          <v-icon class="close-btn" @click="slideDown">mdi-close</v-icon>
        </div>
        <PostItem v-for="post in posts" :key="post.id" :post="post" />
        <div class="mt-6 text-center text--disabled">더 이상 글이 없습니다</div>
      </div>
    </transition>
  </div>
</template>

<script>
import PostItem from "@/components/timeline/PostItem";

export default {
  name: "TimelineModal",
  components: {
    PostItem,
  },
  data() {
    return {
      rendered: false,
      flag: false,
    };
  },
  computed: {
    posts() {
      const kakaoBounds = this.$kakaoMap.getBounds();
      const bounds = {
        top: kakaoBounds.ja,
        bottom: kakaoBounds.ka,
        left: kakaoBounds.da,
        right: kakaoBounds.ia,
      };
      return this.$store.getters["post/postsInBounds"](bounds).reverse();
    },
  },
  created() {
    const preventRoute = this.$router.beforeEach((to, from, next) => {
      if (this.flag || to.path !== "/") {
        next(true);
      }
      this.rendered = false;
      next(false);
    });
    this.$once("hook:destroyed", preventRoute);
  },
  mounted() {
    this.rendered = true;
  },
  methods: {
    slideDown() {
      this.rendered = false;
    },
    close() {
      this.flag = true;
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9999;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-container {
  position: absolute;
  top: 20px;
  width: 100%;
  height: calc(100% - 20px);
  background-color: #fff;
  border-radius: 15px 15px 0 0;
}

.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
}

.slide-up-enter-active {
  animation: slide-up 0.4s;
}
.slide-up-leave-active {
  animation: slide-up 0.4s reverse;
}
@keyframes slide-up {
  0% {
    transform: translateY(100%);
  }
  100% {
    transform: translateY(0);
  }
}
</style>
