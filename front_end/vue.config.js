module.exports = {
  devServer: {
    proxy: {
      '^/api': {
        target: 'http://localhost:8080/',
        ws: true,
        changeOrigin: true
      },
      '^/account': {
        target: 'http://localhost:8081/',
        ws: true,
        changeOrigin: true
      }
    }
  },
  transpileDependencies: [
    'vuetify'
  ]
}
