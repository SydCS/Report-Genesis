<!--主页面  header, 引入commonaside侧边栏，main-->

<template>
  <div
    class="home"
    :style="{
      width: '100vw',
      height: '100vh',
      // backgroundImage:'url(' +require('../assets/report5.png') + ')',
      // backgroundSize: '100% 100%',
      // backgroundRepeat: 'no-repeat',
      // backgroundPosition: 'fixed',
      position: 'fixed',
    }"
  >
    <el-container>
      <!-- <el-header style="position: relative; width: 200px;">
        <p style=" display: inline-block; font-family: 'Times New Roman', Times, serif; position: absolute; 
   width:200px; height: 50px;  margin-top:5px; left: 0;line-height: 50px;
   font-weight: bolder;font-size: 30px; ">
          <span>
             Report Genesis
          </span>
        </p>

      </el-header> -->

      <el-container>
        <el-aside width="200px">
          <p
            style="
              width: 200px;
              height: 150px;
              font-size: 27px;
              color: #fff;
              position: relative;
              margin-top: -38px;
            "
          >
            Report Genesis
          </p>
          <CommonAside></CommonAside>
        </el-aside>

        <el-main class="homeMain">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src
//导入组件
import CommonAside from "../components/CommonAside.vue";

export default {
  name: "HomeView",

  components: {
    CommonAside,
    //注册组件
  },
};
</script>

<style>
.el-header {
  width: 16%;
  background-color: #d6d9d0;
  color: #333333;
  text-align: center;
  height: 120px;
}

.el-aside {
  width: 250px;
  background-color: #213548;
  color: #e9eef3;
  text-align: center;
  line-height: 200px;
}

/* #building{
  background:url("../assets/pexels3.png");
width:100%;			
height:100%;			
position:fixed;
background-size:100% 100%;
} */
</style>
<style scoped>
.homeMain {
  margin-top: 0px;
  background-color: rgba(233, 238, 243, 0.4);
  /* #E9EEF3; */
  color: #333333;
  text-align: center;
  /* line-height: 75px; */
  height: 712px;
  /* opacity:0.6; */
}
</style>
