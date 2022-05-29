import shrinkRay from 'shrink-ray-current';

export default {
  ssr: true,

  // Target: https://go.nuxtjs.dev/config-target
  // target: "static",

  loading: {
    color: 'blue',
    height: '3px',
    duration: 5000,
  },

  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    title: 'spring-boot-cms',
    htmlAttrs: {
      lang: 'ko',
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      {
        hid: 'description',
        name: 'description',
        content: 'Spring Boot CMS',
      },
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }],
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: ['~/static/css/global.css', '~/assets/scss/bootstrap.scss', '~/assets/scss/app.scss', '~/assets/scss/icons.scss'],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [
    { src: '~/plugins/vue-apexcharts.js', ssr: false },
    { src: '~/plugins/bootstrap.js', ssr: false },
    { src: '~/plugins/toastify.js', ssr: false },
    { src: '~/plugins/rater.js', ssr: false },
    { src: '~/plugins/perfect-scrollbar.js', ssr: false },
    { src: '~/plugins/vee-validate.js', ssr: true },
  ],

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: ['nuxt-purgecss', '@aceforth/nuxt-optimized-images', '@nuxtjs/google-fonts'],

  optimizedImages: {
    optimizeImages: true,
  },

  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [
    '@nuxtjs/axios',
    '@nuxtjs/auth-next',
    '@nuxtjs/proxy',
    ['@nuxtjs/dotenv', { filename: `.env.${process.env.NODE_ENV}` }],
    '@nuxtjs/component-cache',
    'vue-sweetalert2/nuxt',
    [
      'nuxt-lazy-load',
      {
        // These are the default values
        images: false,
        videos: false,
        audios: false,
        iframes: false,
        native: false,
        directiveOnly: false,

        // Default image must be in the static folder
        defaultImage: '/images/common/loading.gif',

        // To remove class set value to false
        loadingClass: 'isLoading',
        loadedClass: 'isLoaded',
        appendClass: 'lazyLoad',

        observerConfig: {
          // See IntersectionObserver documentation
        },
      },
    ],
  ],

  axios: {
    proxy: true,
  },

  proxy: {
    '/api/': 'http://localhost:8080',
  },

  auth: {
    // https://auth.nuxtjs.org/guide/setup/
    strategies: {
      local: {
        scheme: 'refresh',
        token: {
          property: 'accessToken',
          maxAge: 1800,
          global: true,
          // type: 'Bearer'
        },
        refreshToken: {
          property: 'refreshToken',
          data: 'refreshToken',
          maxAge: 60 * 60 * 24 * 30,
        },
        user: {
          property: 'user',
          // autoFetch: true
        },
        endpoints: {
          login: { url: '/api/login', method: 'post' },
          refresh: { url: '/api/refreshToken', method: 'post' },
          user: { url: '/api/account/myinfo', method: 'get' },
          logout: { url: '/api/logout', method: 'post' },
        },
        // autoLogout: false
      },
    },
  },
  // ...axiosConfig

  googleFonts: {
    families: {
      Nunito: true,
    },
  },

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {
    devtools: true,
    /*
     ** You can extend webpack config here
     */
    extractCSS: true,
    extend(config, ctx) {},
    transpile: ['vee-validate/dist/rules'],
  },

  render: {
    compressor: shrinkRay(),
  },

  router: {
    middleware: ['auth'],
    extendRoutes(routes, resolve) {
      /*
      routes.push({
        name: 'main',
        path: '/main',
        component: resolve(__dirname, 'pages/index.vue'),
      });
      */
    },
  },
};
