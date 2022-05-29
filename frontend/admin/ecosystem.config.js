module.exports = {
  apps: [
    {
      name: 'SpringBootCMS',
      script: 'npm',
      args: 'run start',
      instances: 1,
      autorestart: true,
      watch: false,
      // max_memory_restart: '1G',
      // pm2 start ecosystem.config.js
      env: {
        HOST: '0.0.0.0',
        PORT: 3000,
        NODE_ENV: 'development',
      },
      // pm2 start ecosystem.config.js --env release
      env_release: {
        HOST: '0.0.0.0',
        PORT: 3000,
        NODE_ENV: 'production',
      },
      // output: '/data/logs/nuxtlog/spring-boot-cms.admin.fe.log',
      // error: '/data/logs/nuxtlog/spring-boot-cms.admin.fe.error.log',
    },
  ],
};
