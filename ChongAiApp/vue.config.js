// vite.config.js
import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      "/api": {
        target: "http://122.228.237.118:53627",
        changeOrigin: true,
        secure: false, // 忽略证书验证
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
});
