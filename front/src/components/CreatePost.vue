<template>
  <div>
    <p>글</p>
    <textarea
      type="text"
      placeholder="글 내용을 입력해주세요."
      name="content"
      maxlength="20"
      cols="40"
      rows="20"
      required="true"
    />
    <p>글이 올라갈 위치</p>
    <div>
      <label for="radio-current-location">
        <input
          type="radio"
          id="radio-current-location"
          name="location-selection"
          value="1"
        />
        현재 위치
        <input value="서울시 송파구" readonly />
      </label>
      <label for="radio-map-location">
        <input
          type="radio"
          id="radio-map-location"
          name="location-selection"
          value="2"
        />
        지도 위치
        <input value="서울시 서초구" readonly />
      </label>
      <button>위치 변경</button>
    </div>
    <div>
      <button type="submit">등록</button>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style></style>
