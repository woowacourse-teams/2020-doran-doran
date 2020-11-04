<template>
  <v-menu
    v-model="menu"
    :close-on-content-click="false"
    :return-value.sync="joinDates"
    offset-y
    transition="scale-transition"
  >
    <template v-slot:activator="{ on, attrs }">
      <VTextField
        v-on="on"
        v-bind="attrs"
        v-model="joinDates"
        @click="clear"
        :placeholder="'날짜를 입력해주세요.'"
        prepend-icon="mdi-calendar"
        readonly
        single-line
        hide-details="false"
        dense
        color="black"
        class="font-size-x-small"
      />
    </template>
    <div class="flex-column">
      <VDatePicker v-model="dates" range no-title />
      <div class="d-flex flex-row-reverse mb-3 mr-1">
        <v-btn text @click="selected">선택</v-btn>
        <v-btn text @click="close">닫기</v-btn>
      </div>
    </div>
  </v-menu>
</template>

<script>
import { ERROR_MESSAGE } from "@/utils/constants";

export default {
  name: "DatePickerMenu",
  data() {
    return {
      dates: [],
      menu: false,
    };
  },
  computed: {
    joinDates: {
      get() {
        if (this.dates.length === 0) {
          return "";
        }
        return this.dates.join(" ~ ");
      },
      set() {},
    },
  },
  methods: {
    clear() {
      this.dates = [];
    },
    selected() {
      if (this.dates.length < 1) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.INVALID_DATE_COUNT);
        return;
      }

      if (this.dates.length === 1) {
        const oneDate = this.dates[0];
        this.dates = [oneDate, oneDate];
        return;
      }

      const startDate = new Date(this.dates[0]);
      const endDate = new Date(this.dates[1]);

      if (startDate < endDate.getMonth() - 1) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.INVALID_OVER_A_MONTH);
        this.dates = [];
        return;
      }

      if (startDate > endDate) {
        this.dates = [this.endDate, this.startDate];
      }

      this.$emit("select", this.dates);
      this.menu = false;
    },
    close() {
      this.dates = [];
      this.menu = false;
    },
  },
};
</script>

<style scoped>
.v-menu__content {
  height: 330px;
  background-color: white;
  margin-top: 5px;
  margin-left: -10px;
}
</style>
