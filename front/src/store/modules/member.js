import api from "@/api/member";

export default {
  namespaced: true,
  state: {
    member: {
      id: 0,
      nickname: "로그인해주세요",
      picture: "",
    },
  },
  mutations: {
    SET_MEMBER(state, member) {
      state.member = member;
    },
    SET_LOGOUT_MEMBER(state) {
      localStorage.setItem("accessToken", "guest");
      state.member.id = 0;
      state.member.nickname = "로그인해주세요";
      state.member.picture = "";
    },
  },
  actions: {
    async loadMember({ commit }) {
      const member = await api.loadMember();
      commit("SET_MEMBER", member);
    },
  },
  getters: {
    getMember: (state) => {
      return state.member;
    },
    isGuest: (state) => {
      return (
        !localStorage.getItem("accessToken") ||
        localStorage.getItem("accessToken") === "guest" ||
        state.member.id === 0
      );
    },
  },
};
