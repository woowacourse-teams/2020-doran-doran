<template>
  <div>
    <v-btn fixed small fab dark :color="buttonType.color" @click="toggleSlider" class="filter-button">
      <v-icon small>{{ buttonType.icon }}</v-icon>
    </v-btn>
    <template v-if="isSliderOpened">
      <v-btn-toggle v-model="choices" mandatory borderless class="period-choices">
        <v-btn value="24hours" small @click="changeTimeFilterTo24Hours"> 24시간 이내 </v-btn>
        <v-btn value="1week" small @click="changeTimeFilterTo1Week"> 일주일 이내 </v-btn>
        <v-btn value="1month" small @click="changeTimeFilterTo1Month"> 한 달 이내 </v-btn>
        <v-btn value="all" small @click="changeTimeFilterToAll"> 전체 </v-btn>
        <v-btn value="userInput" small > 직접 입력 </v-btn>
      </v-btn-toggle>
<!--      <div v-if="choices === 'userInput'" class="calendar-modal">-->
<!--        <DatePickerMenu :label="'시작 날짜'" @select-date="inputStartDate" />-->
<!--        <DatePickerMenu :label="'종료 날짜'" @select-date="inputEndDate" />-->
<!--      </div>-->
    </template>
  </div>
</template>

<script>
import { DORAN_DORAN_COLORS } from "@/utils/constants";
// import DatePickerMenu from "@/components/search/DataPickerMenu";

const BUTTON_TYPE = {
  DEFAULT: {
    color: DORAN_DORAN_COLORS.POINT_COLOR,
    icon: "mdi-camera-timer",
  },
  MARKER: {
    color: "red",
    icon: "mdi-check",
  },
};

export default {
  name: "PeriodFilterButton",
  components: {
    // DatePickerMenu,
  },
  data() {
    return {
      isSliderOpened: false,
      buttonType: BUTTON_TYPE.DEFAULT,
      choices: "24hours",
    };
  },
  methods: {
    toggleSlider() {
      this.isSliderOpened = !this.isSliderOpened;
    },
    async changeTimeFilterTo24Hours() {
      await this.$store.dispatch("post/loadPostsIn24Hours");
    },
    async changeTimeFilterTo1Week() {
      await this.$store.dispatch("post/loadPostsIn1Week");
    },
    async changeTimeFilterTo1Month() {
      await this.$store.dispatch("post/loadPostsIn1Month");
    },
    async changeTimeFilterToAll() {
      await this.$store.dispatch("post/loadAllPosts");
    },
    // async changeTimeFilterToUserInput() {
    //   this.showCalendar = true;
    //   await this.$store.dispatch("post/loadAllPosts");
    // },
  },
};
</script>

<style scoped>
.filter-button {
  top: 65px;
  left: 10px;
  height: 25px;
  width: 25px;
}
.period-choices {
  position: absolute;
  top: 65px;
  left: 45px;
  z-index: 9999;
  height: 30px;
  width: 86%;
  overflow-x: auto;
}
.theme--light.v-btn-toggle:not(.v-btn-toggle--group) {
  background: transparent;
}
.userInputModal {
  position: fixed;
  top: 50%;
  left: 50%;
  z-index: 9998;
  transform: translate(-50%, -50%);
  background-color: white;
  margin: 10px;
}
</style>
