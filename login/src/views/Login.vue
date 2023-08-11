<template>
  <div>
    <!--动态粒子-->
    <vue-particles
      color="#fff"
      :particleOpacity="0.7"
      :particlesNumber="60"
      shapeType="circle"
      :particleSize="4"
      linesColor="#fff"
      :linesWidth="1"
      :lineLinked="true"
      :lineOpacity="0.4"
      :linesDistance="150"
      :moveSpeed="2"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="push"
      class="lizi"
      :style="{
        width: '100vw',
        height: '100vh',
        backgroundImage: 'url(' + require('../assets/pexels5.png') + ')',
        backgroundSize: '100% 100%',
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'fixed',
        position: 'fixed',
      }"
    >
    </vue-particles>

    <!--输入表单-->
    <div id="poster">
      <el-form class="login-container" lable-position="left" label-width="0px">
        <div class="login_title" style="display: flex">
          <p style="font-size: 20px; margin-left: 75px; margin-right: 30px">
            用户登录
          </p>
          <el-link @click="toRegister" style="font-size: 20px; color: #333"
            >注册账户</el-link
          >
        </div>

        <el-form-item label="">
          <el-input
            type="text"
            v-model="Loginform.loginName"
            placeholder="请输入用户名"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <el-form-item label="">
          <el-input
            type="password"
            v-model="Loginform.password"
            placeholder="请输入密码"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <slider-verify-code
          v-model="aaa"
          @change="handlerLock"
        ></slider-verify-code>

        <el-form-item style="width: 100%; margin-top: 20px">
          <el-button
            type="primary"
            style="background-color: #333; width: 100%; border: none"
            @click="Login"
            >登录</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import sliderVerifyCode from "@/components/slider-verify-code.vue";

import Captcha from "../components/Captcha.vue";

export default {
  name: "Login",
  components: {
    Captcha,
    sliderVerifyCode,
  },

  data() {
    return {
      dialogVisible: false,
      aaa: false,
      isPassing7: false,
      Loginform: {
        loginName: "",
        password: "",
      },

      isPassing: false,
      value: "",
      isDrag: false,
    };
  },
  methods: {
    handleVerifyResult(result) {
      if (result) {
        window.location.href = "/HomeView/HomePage"; // 替换成你要跳转的页面路径
      } else {
        this.$refs.verify.refresh(); // 验证失败，刷新滑块
      }
    },

    passcallback() {
      this.$message({
        message: "验证通过",
        type: "success",
      });
    },

    handlerLock() {
      this.isDrag = true;
      console.log("aaa");
    },

    Login() {
      if (this.isDrag === false) {
        this.$message.error("先进行滑块验证");
      } else {
        this.$axios //用this.$axios就代表main.js中的Vue.prototype.$axios = axios的axios
          .post("user/login", {
            //用post方法传 输入框输入的用户名和密码
            name: this.Loginform.loginName,
            password: this.Loginform.password,
          })
          .then((successResponse) => {
            console.log(successResponse);
            // this.$loginUserName = this.Loginform.loginName;
            this.$store.commit("setUsername", this.Loginform.loginName);

            //回调函数当post成功后执行
            if (successResponse.data.code == 200) {
              //如果后端返回的状态码是200
              // const token = successResponse.headers.get("X-Authorization-With");
              const token = successResponse.data.data;
              localStorage.setItem("token", token);

              this.open1(); //调用第一个弹窗方法表示登录成功
              this.$router.replace({
                //路由替换为index
                path: "/HomeView/HomePage",
              });
            } else {
              this.$message.error(successResponse.data.msg);
            }
          })
          .catch((failResponse) => {
            this.open2();
          });

        console.log("submit!", this.Loginform);
      }
    },

    open1() {
      this.$message({
        message: "登录成功",
        type: "success",
      });
    },

    open2() {
      this.$message.error("登录失败，请先注册");
    },

    //未注册用户先注册
    toRegister() {
      this.$router.push({ path: "/Register" });
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    passcallback3() {
      this.isDrag = true;
      this.$message({
        message: "验证通过",
        type: "success",
      });
    },
    reset() {
      this.isPassing7 = false;
      this.$refs.dragVerify7.reset();
    },
  },
};
</script>

<style scoped>
#poster {
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: absolute;
  margin-top: 0;
}

body {
  margin: 0px;
  padding: 0px;
}

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  /* background-color: #fff;
  border: 1px #eaeaea;
  box-shadow: 0 0 25px #cac6c6; */
  background: rgba(255, 255, 255, 0.2);
}

.login_title {
  max-width: 0px auto 40 auto;
  text-align: center;
  color: #333;
}

/* ::v-deep .dv_text{
  width: 300px !important; 
}
::v-deep .dv_progress_bar{
  width: 400px !important; 
} */

/* ::v-deep .dv_handler{
  left: 100px !important;
}*/

.el-input__inner {
  opacity: 0.5;
}

.el-input__inner::placeholder,
.el-input__value {
  color: #333;
}
</style>
