import api from "@/api/member";

export default {
  namespaced: true,
  state: {
    member: {
      id: 0,
      nickname: "",
    },
  },
  mutations: {
    SET_MEMBER(state, member) {
      state.member = member;
    },
  },
  actions: {
    async loadMember({ commit }) {
      const member = await api.loadMember();
      commit("SET_MEMBER", member);
    },
  },
  getters: {
    getMembers: (state) => {
      return state.member;
    },
  },
};
