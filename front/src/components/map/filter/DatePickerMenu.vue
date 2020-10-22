<template>
  <v-menu
    refs="menu"
    v-model="menu"
    :close-on-content-click="false"
    :return-value.sync="dates"
    offset-y
    transition="scale-transition"
  >
    <template v-slot:activator="{ on, attrs }">
      <VTextField
        v-on="on"
        v-bind="attrs"
        v-model="joinDates"
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
      <VDatePicker v-model="dates" range no-title @input="selectDate" />
      <div class="d-flex flex-row-reverse mb-3 mr-1">
        <v-btn text @click="$refs.menu.save(dates)">선택</v-btn>
        <v-btn text @click="menu = false">닫기</v-btn>
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
    joinDates() {
      if (this.dates.length === 0) {
        return "";
      }
      return this.dates.join(" ~ ");
    },
  },
  methods: {
    selectDate() {
      if (this.dates.length < 2) {
        return;
      }

      const startDate = this.$moment(this.dates[0]);
      const endDate = this.$moment(this.dates[1]);

      if (startDate.isAfter(endDate)) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.INVALID_DATE_ORDER);
        this.dates = [];
        return;
      }

      if (startDate.isBefore(endDate.subtract(1, "months"))) {
        this.$store.commit("snackbar/SHOW", ERROR_MESSAGE.INVALID_OVER_30_DAYS);
        this.dates = [];
        return;
      }

      this.$emit("select", this.dates);
    },
  },
};
</script>

<style scoped>
.v-menu__content {
  min-height: 330px;
  height: 330px;
  background-color: white;
}
</style>
