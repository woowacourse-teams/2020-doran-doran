<template>
  <div>
    <div class="input-field-wrapper">
      <v-text-field
        class="input-field"
        solo
        clearable
        loader-height="10px"
        height="5px"
        placeholder="검색어를 입력해주세요"
        autofocus
        prepend-inner-icon="mdi-magnify"
      >
      </v-text-field>
    </div>
    <div class="time-filter-wrapper">
      <div>시간</div>
      <v-sheet elevation="2" class="time-choices rounded-lg">
        <v-container fluid>
          <v-radio-group dense v-model="radios" :mandatory="false">
            <v-radio
              label="전체"
              value="radio-1"
              :color="buttonColor"
            ></v-radio>
            <v-radio
              label="한 달 이내"
              value="radio-2"
              :color="buttonColor"
            ></v-radio>
            <v-radio
              label="일주일 이내"
              value="radio-3"
              :color="buttonColor"
            ></v-radio>
            <v-radio
              label="24시간 이내"
              value="radio-4"
              :color="buttonColor"
            ></v-radio>
            <v-radio
              label="직접 입력"
              value="radio-5"
              :color="buttonColor"
            ></v-radio>
            <template v-if="radios === 'radio-5'">
              <v-menu
                v-model="startDateMenu"
                :close-on-content-click="false"
                transition="scale-transition"
                offset-y
                min-width="290px"
                max-height="300px"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                    v-model="startDate"
                    prepend-icon="mdi-calendar"
                    label="시작 날짜"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                  v-model="startDate"
                  no-title
                  scrollable
                  @input="startDateMenu = false"
                ></v-date-picker>
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
                  <v-text-field
                    v-model="endDate"
                    prepend-icon="mdi-calendar"
                    label="종료 날짜"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                  v-model="endDate"
                  @input="endDateMenu = false"
                  no-title
                  scrollable
                >
                </v-date-picker>
              </v-menu>
            </template>
          </v-radio-group>
        </v-container>
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

export default {
  name: "SearchPage",
  data() {
    return {
      radios: "radio-1",
      buttonColor: DORAN_DORAN_COLORS.POINT_COLOR,
      startDate: new Date().toISOString().substr(0, 10),
      endDate: new Date().toISOString().substr(0, 10),
      startDateMenu: false,
      endDateMenu: false,
    };
  },
  methods: {
    search() {
      console.log("hello");
    },
  },
};
</script>

<style scoped>
.input-field-wrapper {
  height: 60px;
}
.input-field {
  width: 94%;
  margin: auto;
}
.time-filter-wrapper {
  width: 94%;
  margin: 20px auto;
}
.time-choices {
  margin: 0 auto;
}
</style>
