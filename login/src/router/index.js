import Vue from "vue";
import VueRouter from "vue-router";
// import VueDragVerifyImgChip from 'vue-drag-verify-img-chip';
import HomeView from "../views/HomeView.vue";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import Welcome from "../views/Welcome.vue";
import Chart from "../views/Chart.vue";
import HomePage from "../views/HomePage.vue";
import Require from "../views/Require.vue";
// import BackReport  from '../views/BackReport.vue'
import * as echarts from "echarts";

Vue.prototype.$echarts = echarts;
Vue.use(VueRouter);

const routes = [
  {
    path: "*",
    redirect: "/",
  },
  {
    path: "/",
    name: "Welcome",
    component: Welcome,
  },
  {
    path: "/Login",
    name: "Login",
    component: Login,
  },
  {
    path: "/Register",
    name: "Register",
    component: Register,
  },
  {
    path: "/HomeView",
    name: "HomeView",
    component: () => import("../views/HomeView.vue"),
    meta: { requiresAuth: true },
    children: [
      {
        path: "Require",
        name: "Require",
        component: () => import("../views/Require"),
        meta: { requiresAuth: true },
      },
      {
        path: "HomePage",
        name: "HomePage",
        component: () => import("../views/HomePage"),
        meta: { requiresAuth: true },
      },
      {
        path: "Chart",
        name: "Chart",
        component: () => import("../views/Chart"),
        meta: { requiresAuth: true },
      },
    ],
  },
  {
    path: "/HomePage",
    name: "HomePage",
    component: () => import("../views/HomePage"),
  },
];

// 解决跳转到当前路由报错问题
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};

const router = new VueRouter({
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth) {
    // 判断该页面是否需要身份验证
    const token = localStorage.getItem("token");
    if (!token) {
      next("/Login");
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
