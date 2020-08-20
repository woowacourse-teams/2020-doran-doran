<template>
  <div class="pa-6">
    <VTextField
      v-model="keyword"
      placeholder="검색어를 입력해주세요"
      prepend-inner-icon="mdi-magnify"
      solo
      clearable
    />
    시간
    <v-sheet elevation="2" class="rounded-lg pa-3">
      <v-radio-group v-model="radios" dense>
        <VRadio label="전체" value="date-all" :color="buttonColor" />
        <VRadio label="24시간 이내" value="date-day" :color="buttonColor" />
        <VRadio label="일주일 이내" value="date-week" :color="buttonColor" />
        <VRadio label="한 달 이내" value="date-month" :color="buttonColor" />
        <VRadio label="직접 입력" value="date-user" :color="buttonColor" />
      </v-radio-group>
      <div v-if="radios === 'date-user'" class="d-flex flex-row">
        <DatePickerMenu :label="'시작 날짜'" @select-date="inputStartDate" />
        <DatePickerMenu :label="'종료 날짜'" @select-date="inputEndDate" />
      </div>
    </v-sheet>
    <v-btn
      @click.prevent="search"
      class="my-6 mx-1 rounded-pill"
      :color="buttonColor"
      fixed
      right
      bottom
    >
      검색
    </v-btn>
  </div>
</template>

<script>
import { DORAN_DORAN_COLORS } from "@/utils/constants";
import { DATE_FILTER_TYPE } from "@/utils/time-filter-type";
import DatePickerMenu from "@/components/search/DataPickerMenu";

export default {
  name: "SearchPage",
  components: {
    DatePickerMenu,
  },
  data() {
    return {
      keyword: "",
      radios: "date-all",
      buttonColor: DORAN_DORAN_COLORS.POINT_COLOR,
      userStartDate: "",
      userEndDate: "",
      startDateMenu: false,
      endDateMenu: false,
    };
  },
  created() {
    this.$store.commit("appBar/SEARCH_PAGE");
  },
  methods: {
    inputStartDate(date) {
      this.userStartDate = date;
    },
    inputEndDate(date) {
      this.userEndDate = date;
    },
    async search() {
      const data = {
        keyword: this.keyword,
      };
      const dateType = Object.values(DATE_FILTER_TYPE()).find(
        (dateType) => dateType.name === this.radios,
      );
      if (!dateType) {
        data.startDate = this.userStartDate + " 00:00:00";
        data.endDate = this.userEndDate + " 23:59:59";
      } else {
        data.startDate = dateType.startDate;
        data.endDate = this.$moment().format("YYYY-MM-DD HH:mm:ss");
      }
      const params = new URLSearchParams(data).toString();
      await this.$router.push("/searchResult?" + params);
    },
  },
};
</script>

<style scoped></style>
