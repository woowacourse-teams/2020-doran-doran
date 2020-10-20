<template>
  <div>
    <v-btn
      small
      fab
      dark
      class="filter-btn doran-doran-color"
      @click="toggleSlider"
    >
      <v-icon small>mdi-camera-timer</v-icon>
    </v-btn>
    <transition
      name="slide-right"
      @after-enter="openCalendar"
      @before-leave="closeCalendar"
    >
      <v-btn-toggle
        v-show="isSliderOpen"
        v-model="selected"
        mandatory
        rounded
        borderless
        class="period-choices"
      >
        <v-btn
          v-for="option in periodOptions"
          :key="option.order"
          :value="option"
          height="30"
          class="px-3 period-btn"
          @click="openCalendar"
        >
          {{ option.title }}
        </v-btn>
      </v-btn-toggle>
    </transition>

    <v-sheet v-show="isCalendarOpen" class="px-3 py-0 filter-calendar">
      <DatePickerMenu :label="'시작 날짜'" @select="inputStartDate" />
      <span class="mx-3 pt-2">~</span>
      <DatePickerMenu :label="'종료 날짜'" @select="inputEndDate" />
    </v-sheet>
  </div>
</template>

<script>
import { ERROR_MESSAGE } from "@/utils/constants";
import { PERIOD_OPTIONS, period } from "@/utils/period";
import DatePickerMenu from "@/components/map/filter/DatePickerMenu";

export default {
  name: "PeriodFilterButton",
  components: {
    DatePickerMenu,
  },
  data() {
    return {
      periodOptions: PERIOD_OPTIONS,
      selected: PERIOD_OPTIONS.DAY,
      isSliderOpen: false,
      isCalendarOpen: false,
      startDate: "",
      endDate: "",
    };
  },
  methods: {
    toggleSlider() {
      this.isSliderOpen = !this.isSliderOpen;
    },
    openCalendar() {
      if (this.selected === this.periodOptions.CUSTOM) {
        this.isCalendarOpen = true;
      }
    },
    closeCalendar() {
      this.isCalendarOpen = false;
    },
    loadPosts() {
      const startDate = this.selected.startDate();
      this.$store.commit("post/filter/SET_START_DATE", startDate);
      this.$store.commit("post/filter/RESET_END_DATE");
      this.filterPosts();
    },
    inputStartDate(date) {
      this.startDate = period.format(date + " 00:00:00");
      this.$store.commit("post/filter/SET_START_DATE", this.startDate);
      if (this.endDate) {
        this.handleUserInputFiltering();
      }
    },
    inputEndDate(date) {
      this.endDate = period.format(date + " 23:59:59");
      this.$store.commit("post/filter/SET_END_DATE", this.endDate);
      if (this.startDate) {
        this.handleUserInputFiltering();
      }
    },
    handleUserInputFiltering() {
      if (this.startDate > this.endDate) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.INVALID_DATE_ORDER);
        return;
      }

      const beforeOneMonth = this.$moment(this.endDate).subtract(1, "months");
      if (this.$moment(beforeOneMonth).isAfter(this.startDate)) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.INVALID_OVER_30_DAYS);
        return;
      }

      this.filterPosts();
    },
    async filterPosts() {
      await this.$store.dispatch("post/loadPosts");
      if (this.$store.getters["post/isEmpty"]) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.NO_POST_MESSAGE);
        return;
      }
      this.closeCalendar();
    },
  },
  watch: {
    async selected(val) {
      if (val === this.periodOptions.CUSTOM) {
        this.$store.commit("post/filter/SET_START_DATE", this.startDate);
        this.$store.commit("post/filter/SET_END_DATE", this.endDate);
        await this.filterPosts();
        this.openCalendar();
        return;
      }
      this.closeCalendar();
      this.loadPosts();
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
  max-width: 60%;
  overflow-x: auto;
  box-shadow: 1px 1px 8px silver;
}

.period-choices::-webkit-scrollbar {
  display: none;
}

.period-btn {
  background: white !important;
}

.filter-calendar {
  display: flex;
  position: absolute;
  top: 105px;
  left: 45px;
  z-index: 1;
  max-width: 60%;
  padding-top: 5px;
  padding-left: 10px;
  border-radius: 24px;
  box-shadow: 1px 1px 8px silver;
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
