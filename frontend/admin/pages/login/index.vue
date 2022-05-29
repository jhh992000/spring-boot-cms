<template>
  <div id="auth">
    <div class="row h-100">
      <div class="col-lg-5 col-12">
        <div id="auth-left">
          <!--
          <div class="auth-logo">
            <a href="index.html"><img src="/images/logo/logo.svg" alt="Logo" data-not-lazy></a>
          </div>
          -->
          <h1 class="auth-title mb-5">Administrator Page.</h1>

          <!-- <p class="auth-subtitle mb-5">Log in with your data that you entered during registration.</p> -->

          <ValidationObserver tag="form" @submit.prevent="userLogin" v-slot="{ invalid }">
            <div class="form-group position-relative has-icon-left mb-4">
              <validation-provider v-slot="{ errors }" rules="required|limit:4,20">
                <input type="text" class="form-control form-control-xl" placeholder="Id" name="아이디" v-model="login.username" />
                <span>{{ errors[0] }}</span>
              </validation-provider>
              <div class="form-control-icon">
                <i class="bi bi-person"></i>
              </div>
            </div>
            <div class="form-group position-relative has-icon-left mb-4">
              <validation-provider v-slot="{ errors }" rules="required|limit:4,20">
                <input type="password" class="form-control form-control-xl" placeholder="Password" name="비밀번호" v-model="login.password" />
                <span>{{ errors[0] }}</span>
              </validation-provider>
              <div class="form-control-icon">
                <i class="bi bi-shield-lock"></i>
              </div>
            </div>
            <!--
            <div class="form-check form-check-lg d-flex align-items-end">
              <input class="form-check-input me-2" type="checkbox" value="" id="flexCheckDefault" />
              <label class="form-check-label text-gray-600" for="flexCheckDefault"> Keep me logged in </label>
            </div>
            -->
            <button :disabled="invalid" type="submit" class="btn btn-primary btn-block btn-lg shadow-lg mt-2">Log in</button>
          </ValidationObserver>
          <!--
          <div class="text-center mt-5 text-lg fs-4">
            <p class="text-gray-600">Don't have an account? <a href="auth-register.html" class="font-bold">Sign
              up</a>.</p>
            <p><a class="font-bold" href="auth-forgot-password.html">Forgot password?</a>.</p>
          </div>
          -->
        </div>
      </div>
      <div class="col-lg-7 d-none d-lg-block">
        <div id="auth-right"></div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'LoginPage',
  layout: 'none',
  data() {
    return {
      login: {
        username: '',
        password: '',
      },
    };
  },
  mounted() {
    console.log('process.env.NODE_ENV : ' + process.env.NODE_ENV);
    console.log('process.env.BASE_URL : ' + process.env.BASE_URL);
  },
  methods: {
    async userLogin() {
      try {
        const response = await this.$auth.loginWith('local', { data: this.login });
        console.log(response);
      } catch (err) {
        console.log(err);
      }

      /*

      this.accessToken = await loginApi.findUser(this.login.username, this.login.password);
      console.log('accessToken:' + JSON.stringify(this.accessToken));

      if (this.accessToken) {
        // TODO - accessToken 저장

        // go to main page
        this.$router.push('/');
      } else {
        alert('로그인 실패');
      }
      */
    },
  },
};
</script>

<style scoped></style>
