import consola from 'consola';

export default function (context) {
  consola.info('~~~~~~~~~~~~~~~~~~middleware(check_auth.js)~~~~~~~~~~~~~~~~~~~~~');
  if (process.client) {
    context.userAgent = process.server ? context.req.headers['user-agent'] : navigator.userAgent;
    consola.info('userAgent :' + context.userAgent);
  }

  // if (ctx.app.$auth.$state.loggedIn) {
  //  return ctx.app.$auth.redirect('home')
  // }

  // context.redirect('/components/alert');
}
