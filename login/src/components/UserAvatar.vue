<template>
  <!-- 用户头像和名称 -->
  <div>
    <el-popover placement="top-start" width="200" trigger="hover">
      <el-button type="text" @click="assure" style="margin-left: 45px"
        >退出登录</el-button
      >

      <div
        class="avatar-container"
        slot="reference"
        offset="[0,20]"
        style="position: relative; margin-top: 268px; font-size: 20px"
      >
        <i
          class="el-icon-user"
          style="margin-right: 10px; color: #fff"
          @click="logout"
        ></i>
        <span style="color: #fff; margin-left: 5px">{{ userName }}</span>
      </div>
    </el-popover>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userName: "",
    };
  },
  methods: {
    showLogoutPopover() {
      this.logoutPopoverVisible = true;
    },
    hideLogoutPopover() {
      this.logoutPopoverVisible = false;
    },

    assure() {
      this.$confirm("确认退出登录？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$router.push("/");
          this.$message({
            type: "success",
            message: "用户已退出",
          });
          localStorage.clear();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },

    logout() {
      // console.log('out')
      // localStorage.clear()清除上次登录用户名密码，若此时在地址栏输入项目网址相关页面，仍可以进入项目页面中，故加了下一行
      // localStorage.clear()
      //加入该行后会清除缓存，当点击退出按钮后，会清除登录信息缓存
      // window.sessionStorage.clear()
      // this.$router.push('/Login')

      axios
        .post("/logout")
        .then((response) => {
          // 清除本地存储的 token
          localStorage.removeItem("token");

          // 清空用户信息
          this.$store.commit("setUser", null);

          // 重定向到登录页面
          this.$router.push("/login");
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
  mounted() {
    this.userName = this.$store.state.username;
  },
};
</script>

<style>
.el-popover {
  width: 145px !important;
  margin-top: 530px !important;
  margin-left: 5px !important;
}
</style>
