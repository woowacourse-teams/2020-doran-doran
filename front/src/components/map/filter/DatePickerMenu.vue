<template>
  <v-menu v-model="menu" offset-y transition="scale-transition">
    <template v-slot:activator="{ on, attrs }">
      <VTextField
        v-on="on"
        v-bind="attrs"
        v-model="date"
        :label="label"
        prepend-icon="mdi-calendar"
        readonly
        dense
        color="black"
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
      this.$emit("select", this.date);
    },
  },
};
</script>

<style scoped>
.v-menu__content {
  min-height: 290px;
  height: 290px;
}
</style>
