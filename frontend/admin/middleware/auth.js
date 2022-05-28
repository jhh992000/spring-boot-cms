export default function (context) {
  console.log(
    "~~~~~~~~~~~~~~~~~~middleware(check_auth.js)1111111~~~~~~~~~~~~~~~~~~~~~"
  );
  context.userAgent = process.server
    ? context.req.headers["user-agent"]
    : navigator.userAgent;
}
