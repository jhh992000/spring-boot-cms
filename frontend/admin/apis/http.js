import axios from 'axios';
import consola from 'consola';

const isLocal = () => {
  return process.env.NODE_ENV === 'local';
};

const http = axios.create({
  baseURL: isLocal() ? 'http://localhost:8080/' : `${process.env.BASE_URL}/`,
  timeout: 3000,
  headers: {
    'Cache-Control': 'no-store, no-cache, must-revalidate',
    Pragma: 'no-cache',
    Expires: '-1',
    'Context-Type': 'application/json',
  },
  withCredentials: true,
});

http.interceptors.request.use(
  config => {
    if (process.client) {
      config.headers['x-forwarded-for'] = localStorage.getItem('clientIp');
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

http.interceptors.response.use(
  res => {
    return res;
  },
  error => {
    consola.error(error);

    return Promise.reject(error);
  }
);

export default http;
