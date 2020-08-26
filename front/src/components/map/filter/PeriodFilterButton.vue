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
        background-color="white"
        rounded
        class="period-choices"
      >
        <v-btn value="24hours" small text @click="loadPostsWithinXhours(24)">
          24시간 이내
        </v-btn>
        <v-btn value="7days" small text @click="loadPostsWithinXdays(7)">
          7일 이내
        </v-btn>
        <v-btn value="30days" small text @click="loadPostsWithinXdays(30)">
          30일 이내
        </v-btn>
        <v-btn value="all" small text @click="loadAllPosts">
          전체
        </v-btn>
        <v-btn value="userInput" small text @click="openCalendarModal">
          직접 입력
        </v-btn>
      </v-btn-toggle>
      <v-sheet
        v-if="periodFilterChoice === 'userInput' && isCalendarOpen === true"
        class="user-input-modal rounded-lg"
        elevation="2"
      >
        <DatePickerMenu :label="'시작 날짜'" @select-date="inputStartDate" />
        <DatePickerMenu :label="'종료 날짜'" @select-date="inputEndDate" />
      </v-sheet>
    </template>
  </div>
</template>

<script>
import { DORAN_DORAN_COLORS, ERROR_MESSAGE } from "@/utils/constants";
import DatePickerMenu from "@/components/map/filter/DataPickerMenu";

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
      oneUserInputFilled: false,
    };
  },
  methods: {
    toggleSlider() {
      this.isSliderOpened = !this.isSliderOpened;
    },
    async loadPostsWithinXhours(x) {
      await this.$store.commit("filter/SET_FILTER_FROM_X_HOURS_AGO_TO_NOW", x);
      await this.filterPosts();
    },
    async loadPostsWithinXdays(x) {
      await this.$store.commit("filter/SET_FILTER_FROM_X_DAYS_AGO_TO_NOW", x);
      await this.filterPosts();
    },
    async loadAllPosts() {
      await this.$store.commit("filter/INITIALIZE_PERIOD_FILTER");
      await this.filterPosts();
    },
    async openCalendarModal() {
      this.isCalendarOpen = true;
    },
    async inputStartDate(date) {
      await this.$store.commit("filter/SET_START_DATE", date);
      await this.handleUserDateInput();
    },
    async inputEndDate(date) {
      await this.$store.commit("filter/SET_END_DATE", date);
      await this.handleUserDateInput();
    },
    async handleUserDateInput() {
      if (this.oneUserInputFilled === false) {
        this.oneUserInputFilled = true;
      } else {
        if (
          this.$store.getters["filter/startDate"] >
          this.$store.getters["filter/endDate"]
        ) {
          this.$store.commit(
            "snackbar/SHOW",
            ERROR_MESSAGE.INVALID_USER_DATE_INPUT,
          );
          this.oneUserInputFilled = false;
          this.isCalendarOpen = false;
        } else {
          await this.filterPosts();
          this.oneUserInputFilled = false;
          this.isCalendarOpen = false;
        }
      }
    },
    async filterPosts() {
      await this.$store.commit("post/CLEAR_POSTS");
      const filteredPosts = await this.$store.dispatch("filter/filterPosts");
      if (filteredPosts.length === 0) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.NO_POST_MESSAGE);
      }
      await this.$store.commit("post/SET_POSTS", filteredPosts);
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
.user-input-modal {
  max-width: 70%;
  position: fixed;
  top: 100px;
  left: 190px;
  z-index: 9998;
  background-color: white;
  height: 120px;
  padding-top: 15px;
  padding-left: 10px;
}
</style>
