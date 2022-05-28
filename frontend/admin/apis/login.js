import http from './http';

export const loginApi = {
  findUser(username, password) {
    return http.post('/login', { username, password });
  },
};
