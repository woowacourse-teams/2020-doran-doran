<template>
  <v-menu
    :close-on-content-click="false"
    max-height="290px"
    offset-y
    transition="scale-transition"
    v-model="menu"
  >
    <template v-slot:activator="{ on, attrs }">
      <VTextField
        :label="label"
        dense
        prepend-icon="mdi-calendar"
        readonly
        v-bind="attrs"
        v-model="date"
        v-on="on"
        class="pr-3"
      />
    </template>
    <VDatePicker @input="selectDate" no-title scrollable v-model="date" />
  </v-menu>
</template>

<script>
export default {
  name: "DatePickerMenu",
  props: {
    label: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      menu: false,
      date: "",
    };
  },
  methods: {
    selectDate() {
      this.menu = false;
      this.$emit("select-date", this.date);
    },
  },
};
</script>

<style scoped></style>
