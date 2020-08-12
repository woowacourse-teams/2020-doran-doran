<template>
  <div>
    <div class="input-field-wrapper">
      <VTextField
        v-model="keyword"
        class="input-field"
        solo
        clearable
        loader-height="10px"
        height="5px"
        placeholder="검색어를 입력해주세요"
        prepend-inner-icon="mdi-magnify"
      />
    </div>
    <div class="time-filter-wrapper">
      <div>시간</div>
      <v-sheet elevation="2" class="rounded-lg pa-3">
        <v-radio-group dense v-model="radios" :mandatory="false">
          <VRadio label="전체" value="date-all" :color="buttonColor" />
          <VRadio label="24시간 이내" value="date-day" :color="buttonColor" />
          <VRadio label="일주일 이내" value="date-week" :color="buttonColor" />
          <VRadio label="한 달 이내" value="date-month" :color="buttonColor" />
          <VRadio label="직접 입력" value="date-user" :color="buttonColor" />
          <template v-if="radios === 'date-user'">
            <v-menu
              v-model="startDateMenu"
              :close-on-content-click="false"
              transition="scale-transition"
              offset-y
              min-width="290px"
              max-height="300px"
            >
              <template v-slot:activator="{ on, attrs }">
                <VTextField
                  v-model="userStartDate"
                  prepend-icon="mdi-calendar"
                  label="시작 날짜"
                  readonly
                  v-bind="attrs"
                  v-on="on"
                />
              </template>
              <VDatePicker
                v-model="userStartDate"
                no-title
                scrollable
                @input="startDateMenu = false"
              />
            </v-menu>
            <v-menu
              v-model="endDateMenu"
              :close-on-content-click="false"
              transition="scale-transition"
              offset-y
              min-width="290px"
              max-height="330px"
            >
              <template v-slot:activator="{ on, attrs }">
                <VTextField
                  v-model="userEndDate"
                  prepend-icon="mdi-calendar"
                  label="종료 날짜"
                  readonly
                  v-bind="attrs"
                  v-on="on"
                />
              </template>
              <VDatePicker
                v-model="userEndDate"
                @input="endDateMenu = false"
                no-title
                scrollable
              />
            </v-menu>
          </template>
        </v-radio-group>
      </v-sheet>
    </div>
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

export default {
  name: "SearchPage",
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
  methods: {
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

<style scoped>
.input-field-wrapper {
  height: 60px;
}
.input-field {
  width: 84%;
  margin: auto;
}
.time-filter-wrapper {
  width: 84%;
  margin: 20px auto;
}
</style>
