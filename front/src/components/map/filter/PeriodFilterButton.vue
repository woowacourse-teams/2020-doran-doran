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
      <template v-if="isSliderOpen">
        <v-btn-toggle
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
      </template>
    </transition>

    <v-sheet
      v-show="isCalendarOpen"
      elevation="2"
      class="pa-3 rounded-lg user-input-modal"
    >
      <DatePickerMenu :label="'시작 날짜'" @select="inputStartDate" />
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
      this.$store.commit("filter/SET_START_DATE", startDate);
      this.$store.commit("filter/SET_END_DATE_TO_NOW");
      this.filterPosts();
    },
    inputStartDate(date) {
      this.startDate = date + " 00:00:00";
      this.$store.commit(
        "filter/SET_START_DATE",
        period.format(this.startDate),
      );
      if (this.endDate) {
        this.$store.commit("filter/SET_END_DATE", period.format(this.endDate));
        this.handleUserInputFiltering();
      }
    },
    inputEndDate(date) {
      this.endDate = date + " 23:59:59";
      this.$store.commit("filter/SET_END_DATE", period.format(this.endDate));
      if (this.startDate) {
        this.$store.commit(
          "filter/SET_START_DATE",
          period.format(this.startDate),
        );
        this.handleUserInputFiltering();
      }
    },
    handleUserInputFiltering() {
      if (this.startDate > this.endDate) {
        this.$store.commit(
          "snackbar/SHOW",
          ERROR_MESSAGE.INVALID_USER_DATE_INPUT,
        );
        return;
      }
      this.filterPosts();
    },
    async filterPosts() {
      this.$store.commit("post/CLEAR_POSTS");
      const filteredPosts = await this.$store.dispatch("filter/filterPosts");
      this.$store.commit("post/SET_POSTS", filteredPosts);
      if (filteredPosts.length === 0) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.NO_POST_MESSAGE);
        return;
      }
      this.isCalendarOpen = false;
    },
  },
  watch: {
    selected(val) {
      if (val === this.periodOptions.CUSTOM) {
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
