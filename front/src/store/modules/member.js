export default {
  namespaced: true,
  state: {
    loginMember: 1,
  },
  getters: {
    getMembers: (state) => {
      return state.loginMember;
    },
  },
};
