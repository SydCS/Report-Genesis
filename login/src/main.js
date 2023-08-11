import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueDragVerifyImgChip from "vue-drag-verify-img-chip";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import instance from "./utils/request.js";

import VueScrollTo from "vue-scrollto";
import VueParticles from "vue-particles"; //粒子

Vue.use(VueParticles);

Vue.use(ElementUI);
Vue.use(VueScrollTo);
Vue.config.productionTip = false;

// axios = require('axios')//引用axios
// axios.defaults.baseURL = 'http://localhost:8088/api'//设置一个基础请求地址
// Vue.prototype.$axios = axios// 全局注册，之后可在其他组件中通过 this.$axios 发送数据

// 将 axios 实例挂载到 Vue.prototype 上，这样在组件中可以直接使用 this.$axios 发送请求
Vue.prototype.$axios = instance;

Vue.prototype.$store = store;

new Vue({
  router,
  store,
  VueDragVerifyImgChip,
  render: (h) => h(App),
}).$mount("#app");
