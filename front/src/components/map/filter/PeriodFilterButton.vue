<template>
  <div>
    <v-btn
      small
      fab
      dark
      :color="buttonType.color"
      @click="toggleSlider"
      class="filter-btn"
    >
      <v-icon small>{{ buttonType.icon }}</v-icon>
    </v-btn>
    <template v-if="isSliderOpened">
      <v-btn-toggle
        v-model="periodFilterChoice"
        mandatory
        rounded
        class="period-choices"
      >
        <v-btn
          value="24hours"
          small
          class="period-btn"
          @click="loadPostsWithinXhours(24)"
        >
          24시간 이내
        </v-btn>
        <v-btn
          value="7days"
          small
          class="period-btn"
          @click="loadPostsWithinXdays(7)"
        >
          7일 이내
        </v-btn>
        <v-btn
          value="30days"
          small
          class="period-btn"
          @click="loadPostsWithinXdays(30)"
        >
          30일 이내
        </v-btn>
        <v-btn value="all" small class="period-btn" @click="loadAllPosts">
          전체
        </v-btn>
        <v-btn
          value="userInput"
          small
          depressed
          color="period-btn"
          @click="openCalendarModal"
        >
          직접 입력
        </v-btn>
      </v-btn-toggle>
      <v-sheet
        v-if="periodFilterChoice === 'userInput' && isCalendarOpen === true"
        elevation="2"
        class="pa-3 rounded-lg user-input-modal"
      >
        <DatePickerMenu :label="'시작 날짜'" @select-date="inputStartDate" />
        <DatePickerMenu :label="'종료 날짜'" @select-date="inputEndDate" />
      </v-sheet>
    </template>
  </div>
</template>

<script>
import { DORAN_DORAN_COLORS, ERROR_MESSAGE } from "@/utils/constants";
import DatePickerMenu from "@/components/map/filter/DatePickerMenu";

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
      isInputStartDateFilled: false,
      isInputEndDateFilled: false,
      previousStartDateFilter: "",
      previousEndDateFilter: "",
      previousFilterChoice: "",
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
      this.isInputStartDateFilled = true;
      if (this.isInputEndDateFilled === true) {
        await this.handleUserInputFiltering();
      }
    },
    async inputEndDate(date) {
      await this.$store.commit("filter/SET_END_DATE", date);
      this.isInputEndDateFilled = true;
      if (this.isInputStartDateFilled === true) {
        await this.handleUserInputFiltering();
      }
    },
    async handleUserInputFiltering() {
      if (this.$store.getters["filter/startDate"] > this.$store.getters["filter/endDate"]) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.INVALID_USER_DATE_INPUT);
        this.rollBackPeriodFilter();
        this.isInputStartDateFilled = false;
        this.isInputEndDateFilled = false;
        this.isCalendarOpen = false;
      } else {
        await this.filterPosts();
        this.isInputStartDateFilled = false;
        this.isInputEndDateFilled = false;
        this.isCalendarOpen = false;
      }
    },
    rollBackPeriodFilter() {
      this.$store.commit("filter/SET_START_DATE", this.previousStartDateFilter);
      this.$store.commit("filter/SET_END_DATE", this.previousEndDateFilter);
      this.periodFilterChoice = this.previousFilterChoice;
    },
    async filterPosts() {
      const filteredPosts = await this.$store.dispatch("filter/filterPosts");
      if (filteredPosts.length === 0) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.NO_POST_MESSAGE);
      }
      await this.$store.commit("post/CLEAR_POSTS");
      await this.$store.commit("post/SET_POSTS", filteredPosts);
      this.previousFilterChoice = this.periodFilterChoice;
      this.previousStartDateFilter = this.$store.getters["filter/startDate"];
      this.previousEndDateFilter = this.$store.getters["filter/endDate"];
    },
  },
};
</script>

<style scoped>
.filter-btn {
  position: absolute;
  top: 65px;
  left: 10px;
  z-index: 9998;
  width: 25px;
  height: 25px;
}

.period-choices {
  position: absolute;
  top: 65px;
  left: 45px;
  z-index: 999;
  overflow-x: auto;
}

.period-choices::-webkit-scrollbar {
  display: none;
}

.period-btn {
  background: white !important;
}

.user-input-modal {
  position: absolute;
  top: 100px;
  left: 150px;
  z-index: 9998;
  max-width: 60%;
  background-color: white;
}
</style>
