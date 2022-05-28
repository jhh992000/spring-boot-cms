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
    consola.info("res.headers['content-type'] : " + res.headers['content-type']);
    /*
    if (res.headers['content-type']) {
      if (res.headers['content-type'].includes('text/html')) {
        /!* eslint-disable no-undef *!/
        if (process.client) {
          $nuxt.error({ statusCode: 999, contents: res.data });
        }

        return Promise.reject(res);
      }
    }
*/
    const response = res.data;
    consola.info('response.header : ' + response.header);
    /*
    if (!response.header.isSuccessful) {
      const statusCode = response.header.resultCode;
      if (statusCode === 403 || statusCode === 404) {
        if (process.client) {
          $nuxt.error({ statusCode });
        }
      }
      if (statusCode === 401) {
        if (process.client) {
          $nuxt.$member.logout();
        }
      }
      return Promise.reject(response.header);
    }
    */
    return response.body;
  },
  error => {
    consola.error(error);

    return Promise.reject(error);
  }
);

export default http;
