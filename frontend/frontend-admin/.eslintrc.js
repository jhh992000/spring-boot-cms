module.exports = {
  root: true,
  env: {
    browser: true,
    node: true,
    jquery: true,
  },
  parserOptions: {
    parser: '@babel/eslint-parser',
    requireConfigFile: false,
  },
  extends: ['@nuxtjs', 'plugin:nuxt/recommended', 'prettier'],
  plugins: [],
  // add your custom rules here
  rules: {
    'nuxt/no-cjs-in-config': 'off',
    'no-shadow': 'off',
    semi: 0,
    'comma-dangle': 'off',
    'space-before-function-paren': 'off',
    'vue/attributes-order': 'off',
    'vue/name-property-casing': 'off',
    'vue/no-v-html': 'off',
    'vue/comment-directive': 'off',
    'vue/no-lone-template': [
      'warn',
      {
        ignoreAccessible: true,
      },
    ],
    'nuxt/no-globals-in-created': 'off',
    'dot-notation': 'error',
    'linebreak-style': 'off',
    'no-console': process.env.NODE_ENV === 'release' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'release' ? 'warn' : 'off',
    // 'prettier/prettier': ['error', { endOfLine: 'crlf' }],
  },
};
