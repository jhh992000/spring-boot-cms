import http from './http';

export const loginApi = {
  findUser() {
    return http.get('/api/league/maintenance');
  },
};
