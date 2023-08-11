<template>
  <!--侧边栏-->

  <div>
    <el-menu
      router
      default-active="2"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      style="background-color: rgba(44, 62, 80, 0); width: 200px; color: #fff"
    >
      <el-menu-item
        :index="item.path"
        style="height: 60px"
        v-for="item in noChildren"
        :key="item.path"
      >
        <i :class="'el-icon-' + item.icon"></i>
        <span slot="title">{{ item.label }}</span>
      </el-menu-item>

      <UserAvatar></UserAvatar>
    </el-menu>
  </div>
</template>

<script>
import UserAvatar from "../components/UserAvatar.vue";

export default {
  data() {
    return {
      isCollapse: false,
      menu: [
        {
          path: "/Homeview/Require",
          name: "Require",
          label: "查询",
          icon: "search",
          url: "/Homeview/Require",
        },
        {
          path: "/Homeview/HomePage",
          name: "HomePage",
          label: "报告",
          icon: "edit-outline",
          url: "/Homeview/HomePage",
        },
        {
          path: "/Homeview/Chart",
          name: "Chart",
          label: "图表",
          icon: "pie-chart",
          url: "/Homeview/Chart",
        },
      ],
    };
  },
  components: {
    UserAvatar,
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
  },
  computed: {
    //没有子菜单
    noChildren() {
      return this.menu.filter((item) => !item.children);
    },
    //有子菜单
  },
};
</script>

<style>
.el-menu-vertical-demo {
  background-color: rgba(44, 62, 80, 0.7);
}

.el-menu {
  width: 250px;
  height: 651px;
  background-color: rgba(44, 62, 80, 0.7);
}
.el-menu-item {
  font-size: 18px !important;
  color: #fff !important;
}
/* 鼠标悬浮和点击样式一样 */
.el-menu-item:hover,
.el-menu-item:hover {
  background-color: #597d9e !important;
  color: #fff;
}
/* .el-menu-item.is-active {
    background-color: blue;
    color: yellow;
    
  } */

.el-menu-item.is-active:focus {
  background-color: #597d9e; /* 修改背景颜色 */
  color: #fff;
}
</style>
