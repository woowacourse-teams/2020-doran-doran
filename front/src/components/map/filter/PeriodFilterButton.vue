<template>
  <div>
    <v-btn
      small
      fab
      dark
      :color="buttonColor"
      class="filter-btn"
      @click="toggleSlider"
    >
      <v-icon small>mdi-camera-timer</v-icon>
    </v-btn>
    <transition
      name="slide-right"
      @after-enter="openCalendar"
      @before-leave="closeCalender"
    >
      <template v-if="isSliderOpened">
        <v-btn-toggle
          v-model="periodFilterChoice"
          mandatory
          rounded
          borderless
          class="period-choices"
        >
          <v-btn
            value="24hours"
            height="30"
            class="period-btn px-3"
            @click="loadPostsWithinXhours(24)"
          >
            24시간 이내
          </v-btn>
          <v-btn
            value="7days"
            height="30"
            class="period-btn px-3"
            @click="loadPostsWithinXdays(7)"
          >
            7일 이내
          </v-btn>
          <v-btn
            value="30days"
            height="30"
            class="period-btn px-3"
            @click="loadPostsWithinXdays(30)"
          >
            30일 이내
          </v-btn>
          <v-btn
            value="all"
            height="30"
            class="period-btn px-3"
            @click="loadAllPosts"
          >
            전체
          </v-btn>
          <v-btn
            value="userInput"
            height="30"
            class="period-btn px-3"
            @click="openCalendar"
          >
            직접 입력
          </v-btn>
        </v-btn-toggle>
      </template>
    </transition>

    <v-sheet
      v-show="isCalendarOpened"
      elevation="2"
      class="pa-3 rounded-lg user-input-modal"
    >
      <DatePickerMenu :label="'시작 날짜'" @select="inputStartDate" />
      <DatePickerMenu :label="'종료 날짜'" @select="inputEndDate" />
    </v-sheet>
  </div>
</template>

<script>
import { DORAN_DORAN_COLORS, ERROR_MESSAGE } from "@/utils/constants";
import DatePickerMenu from "@/components/map/filter/DatePickerMenu";

export default {
  name: "PeriodFilterButton",
  components: {
    DatePickerMenu,
  },
  data() {
    return {
      buttonColor: DORAN_DORAN_COLORS.POINT_COLOR,
      isSliderOpened: false,
      isCalendarOpened: false,
      periodFilterChoice: "24hours",
      startDate: "",
      endDate: "",
    };
  },
  methods: {
    toggleSlider() {
      this.isSliderOpened = !this.isSliderOpened;
    },
    openCalendar() {
      if (this.periodFilterChoice === "userInput") {
        this.isCalendarOpened = true;
      }
    },
    closeCalender() {
      this.isCalendarOpened = false;
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
    async inputStartDate(date) {
      this.startDate = date;
      this.$store.commit("filter/SET_START_DATE", date);
      if (this.endDate) {
        await this.handleUserInputFiltering();
      }
    },
    async inputEndDate(date) {
      this.endDate = date;
      this.$store.commit("filter/SET_END_DATE", date);
      if (this.startDate) {
        await this.handleUserInputFiltering();
      }
    },
    async handleUserInputFiltering() {
      if (this.startDate > this.endDate) {
        this.$store.commit(
          "snackbar/SHOW",
          ERROR_MESSAGE.INVALID_USER_DATE_INPUT,
        );
        return;
      }
      await this.filterPosts();
    },
    async filterPosts() {
      this.$store.commit("post/CLEAR_POSTS");
      const filteredPosts = await this.$store.dispatch("filter/filterPosts");
      this.$store.commit("post/SET_POSTS", filteredPosts);
      if (filteredPosts.length === 0) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.NO_POST_MESSAGE);
        return;
      }
      this.isCalendarOpened = false;
    },
  },
  watch: {
    periodFilterChoice(val) {
      this.isCalendarOpened = val === "userInput";
    },
  },
};
</script>

<style scoped>
.filter-btn {
  position: absolute;
  top: 65px;
  left: 10px;
  z-index: 1;
  width: 30px;
  height: 30px;
}

.period-choices {
  position: absolute;
  top: 66px;
  left: 45px;
  z-index: 1;
  max-width: 65%;
  overflow-x: auto;
  box-shadow: 1px 1px 8px silver;
}

.period-choices::-webkit-scrollbar {
  display: none;
}

.period-btn {
  background: white !important;
}

.user-input-modal {
  position: absolute;
  top: 105px;
  left: 190px;
  z-index: 1;
  max-width: 60%;
  padding-top: 15px;
  padding-left: 10px;
  background-color: white;
}

.slide-right-enter-active {
  animation: slide-right 0.4s;
}
.slide-right-leave-active {
  animation: slide-right 0.4s reverse;
}
@keyframes slide-right {
  0% {
    width: 0;
  }
  100% {
    width: 330px;
  }
}
</style>
