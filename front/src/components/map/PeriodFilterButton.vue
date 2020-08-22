<template>
  <div>
    <v-btn
      fixed
      small
      fab
      dark
      :color="buttonType.color"
      @click="toggleSlider"
      class="filter-button"
    >
      <v-icon small>{{ buttonType.icon }}</v-icon>
    </v-btn>
    <template v-if="isSliderOpened">
      <v-btn-toggle
        v-model="periodFilterChoice"
        mandatory
        borderless
        class="period-choices"
      >
        <v-btn value="24hours" small @click="changePeriodFilterTo24Hours">
          24시간 이내
        </v-btn>
        <v-btn value="1week" small @click="changePeriodFilterTo1Week">
          일주일 이내
        </v-btn>
        <v-btn value="1month" small @click="changePeriodFilterTo1Month">
          한 달 이내
        </v-btn>
        <v-btn value="all" small @click="changePeriodFilterToWholePeriod">
          전체
        </v-btn>
        <v-btn value="userInput" small @click="openCalendarModal">
          직접 입력
        </v-btn>
      </v-btn-toggle>
      <v-sheet
        v-if="periodFilterChoice === 'userInput' && isCalendarOpen === true"
        class="userInputModal rounded-lg"
        elevation="2"
      >
        <DatePickerMenu :label="'시작 날짜'" @select-date="inputStartDate" />
        <DatePickerMenu :label="'종료 날짜'" @select-date="inputEndDate" />
      </v-sheet>
    </template>
  </div>
</template>

<script>
import { DORAN_DORAN_COLORS } from "@/utils/constants";
import DatePickerMenu from "@/components/search/DataPickerMenu";

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
    DatePickerMenu,
  },
  data() {
    return {
      buttonType: BUTTON_TYPE.DEFAULT,
      isSliderOpened: false,
      periodFilterChoice: "24hours",
      isCalendarOpen: true,
    };
  },
  methods: {
    toggleSlider() {
      this.isSliderOpened = !this.isSliderOpened;
    },
    async changePeriodFilterTo24Hours() {
      await this.$store.dispatch("post/loadPostsIn24Hours");
    },
    async changePeriodFilterTo1Week() {
      await this.$store.dispatch("post/loadPostsIn1Week");
    },
    async changePeriodFilterTo1Month() {
      await this.$store.dispatch("post/loadPostsIn1Month");
    },
    async changePeriodFilterToWholePeriod() {
      await this.$store.dispatch("post/loadAllPosts");
    },
    async openCalendarModal() {
      this.isCalendarOpen = true;
    },
    async inputStartDate(date) {
      await this.$store.commit("filter/SET_START_DATE", date);
      if ((await this.$store.getters["filter/endDate"]) !== "") {
        await this.setPeriodFilter();
        this.isCalendarOpen = false;
        await this.$store.commit("filter/INITIALIZE_PERIOD_FILTER");
      }
    },
    async inputEndDate(date) {
      await this.$store.commit("filter/SET_END_DATE", date);
      if ((await this.$store.getters["filter/startDate"]) !== "") {
        await this.setPeriodFilter();
        this.isCalendarOpen = false;
        await this.$store.commit("filter/INITIALIZE_PERIOD_FILTER");
      }
    },
    async setPeriodFilter() {
      const data = {
        startDate: this.$store.getters["filter/startDate"],
        endDate: this.$store.getters["filter/endDate"],
      };
      await this.$store.dispatch("post/filterPosts", data);
    },
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
  top: 100px;
  left: 190px;
  z-index: 9998;
  background-color: white;
  height: 120px;
  max-width: 70%;
  padding-bottom: 20px;
}
</style>
