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
            <v-radio label="전체" value="radio-1" :color="buttonColor"></v-radio>
            <v-radio label="24시간 이내" value="radio-2" :color="buttonColor"></v-radio>
            <v-radio label="일주일 이내" value="radio-3" :color="buttonColor"></v-radio>
            <v-radio label="한 달 이내" value="radio-4" :color="buttonColor"></v-radio>
            <v-radio label="직접 입력" value="radio-5" :color="buttonColor">
              <template v-slot:label>
                <div>직접 입력</div>
              </template>
            </v-radio>
          </v-radio-group>
        </v-container>
      </v-sheet>
    </div>
    <v-btn
      @click.prevent="search"
      class="my-6 mx-1 rounded-pill"
      :color="buttonColor"
      fixed
      right="true"
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
  background-color: #f5f5f5;
}
.input-field {
  width: 94%;
  margin: auto;
}
.time-filter-wrapper {
  /*width: 90%;*/
  width: 94%;
  margin: 20px auto;
}
.time-choices {
  /*width: 94%;*/
  margin: 0 auto;
}
</style>
