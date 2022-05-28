export const state = () => ({
  userId: '',
});

export const mutations = {
  login(state, { userId }) {
    state.userId = userId;
  },
};

export const getters = {
  isLogin(state) {
    return false;
  },
};

export const actions = {};
