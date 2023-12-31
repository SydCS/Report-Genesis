const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    proxy: {  //配置跨域
      '/api' : {
        target: 'http://127.0.0.1:8081', 
        changOrigin: true,  //允许跨域
        pathRewrite: {
          '^/api': '' 
         }
      },
    }
  },
})

