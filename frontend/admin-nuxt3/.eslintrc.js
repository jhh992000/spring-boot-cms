module.exports = {
  root: true,
  env: {
    browser: true,
    es2021: true,
    node: true,
    jquery: true,
  },
  extends: ['@antfu', '@nuxtjs/eslint-config-typescript', 'plugin:nuxt/recommended', 'plugin:vue/vue3-essential', 'prettier'],
  parserOptions: {
    ecmaVersion: 2021,
  },
  plugins: ['vue', '@typescript-eslint'],
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-return-assign': 'off',
    'no-tabs': 'off',
    'no-unused-expressions': 'off',
    'vue/no-v-html': 'off',
    'vue/no-v-model-argument': 'off',
    'vue/multi-word-component-names': 'off',
    'comma-dangle': 'off',
    '@typescript-eslint/comma-dangle': ['error'],
  },
  overrides: [
    {
      files: ['**/__tests__/*.{j,t}s?(x)', '**/tests/unit/**/*.spec.{j,t}s?(x)'],
      env: {
        jest: true,
      },
    },
  ],
};
