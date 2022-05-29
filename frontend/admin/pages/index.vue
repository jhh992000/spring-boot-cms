<template>
  <div>
    <dash-board></dash-board>
  </div>
</template>

<script>
import DashBoard from '../components/Main/DashBoard';

export default {
  name: 'IndexPage',
  components: { DashBoard },
  mounted() {
    console.log('this.$auth.user : ' + this.$auth.user);
  },
};
</script>
