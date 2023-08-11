<template>
  <div class="navbar">
    <el-menu
      mode="horizontal"
      default-active="1"
      class="navbar"
      style="color: aliceblue"
    >
      <el-menu-item index="1" @click="scrollToTop"> 首页 </el-menu-item>
      <el-menu-item index="2" @click="scrollToAbout"> 关于我们 </el-menu-item>
      <el-menu-item index="3" @click="scrollToConnect"> 联系我们 </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import VueScrollTo from "vue-scrollto";

export default {
  name: "NavBar",

  data() {
    return {};
  },
  methods: {
    scrollToTop() {
      VueScrollTo.scrollTo("#first", 500, { offset: -50 });
    },
    scrollToAbout() {
      VueScrollTo.scrollTo("#about", 500, { offset: -50 });
    },
    scrollToConnect() {
      VueScrollTo.scrollTo("#contact", 500, { offset: -50 });
    },
  },
};
</script>

<style>
.navbar {
  height: 60px;
  text-align: right;
  display: flex;
  justify-content: flex-end;

  top: 0; /* 将导航栏定位到浏览器窗口的顶部 */
  width: 100%; /* 将导航栏宽度设置为100% */
  z-index: 999; /* 将导航栏置于页面顶部 */
  position: fixed;
}
.navbar .el-menu {
  background-color: rgba(44, 62, 80, 0.5);
}

.navbar .el-menu-item {
  margin-right: 20px;
  color: white;
  background-color: rgba(44, 62, 80, 0.5);
  text-align: right;
}
.navbar.el-menu--horizontal > .el-menu-item {
  font-size: 15px;
  color: #fff;
}

.navbar.el-menu--horizontal > .el-menu-item.is-active {
  color: #409eff !important;
}
/* .navbar .el-menu-item.is-active {
  background-color: green !important;
  color:#333;
  span {
    color: #333 !important;
  }
} */
</style>
