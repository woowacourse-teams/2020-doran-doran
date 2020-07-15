<template>
  <div>
    <v-form class="form">
      <Map style="height: 200px;"/>
      <div class="content">
        <v-textarea
          type="text"
          name="content"
          placeholder="글 내용을 입력해주세요."
          maxlength="200"
          rows="10"
          :counter="200"
          required
          solo
          autofocus
          no-resize
        />
      </div>
      <v-btn class="register-button" color="amber accent-4" type="submit"
        >등록</v-btn
      >
    </v-form>
  </div>
</template>

<script>
import Map from "./Map";
export default {
  components: { Map },
};
</script>

<style>
.form {
  margin: 30px;
}
.content {
  margin-top: 30px;
}
.register-button {
  float:right;
}
</style>
