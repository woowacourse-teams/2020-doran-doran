<template>
  <v-menu
    v-model="menu"
    max-height="290px"
    offset-y
    transition="scale-transition"
    :close-on-content-click="false"
  >
    <template v-slot:activator="{ on, attrs }">
      <VTextField
        v-on="on"
        v-bind="attrs"
        v-model="date"
        :label="label"
        prepend-icon="mdi-calendar"
        readonly
        dense
        class="pr-3"
      />
    </template>
    <VDatePicker v-model="date" no-title scrollable @input="selectDate" />
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
