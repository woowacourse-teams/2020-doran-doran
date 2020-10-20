const path = require("path");

module.exports = {
  transpileDependencies: ["vuetify"],
  configureWebpack: {
    resolve: {
      alias: {
        "@": path.join(__dirname, "src/"),
      },
    },
    optimization: {
      splitChunks: {
        minSize: 10000,
        maxSize: 500000,
      },
    },
  },
};
