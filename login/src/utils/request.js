// 导入axios
import axios from "axios";
import router from "@/router";

// 创建 axios 实例
const instance = axios.create({
  baseURL: "http://localhost:8088", // 设置请求的基础 URL
  timeout: 200000, // 设置请求超时时间（单位：毫秒）
  headers: {
    // 设置请求头
    "Content-Type": "application/json",
  },
});

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    // 在发送请求之前可以做一些处理，如添加 token 等
    const token = localStorage.getItem("token");
    if (token) {
      config.headers["X-Authorization-With"] = token;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  (response) => {
    // 在响应数据之前可以做一些处理，如统一处理错误信息等
    if (response.data.code === 401) {
      // 返回登录页面
      console.log("用户未登录, 直接跳转至登录页面");
      router.replace("/Login");
      return response;
    } else {
      return response;
    }
  },
  (error) => {
    console.log("err: " + error);
    return Promise.reject(error);
  }
);

export default instance;
