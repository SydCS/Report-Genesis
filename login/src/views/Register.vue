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

    <div id="poster">
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        class="register-container"
        lable-position="left"
        label-width="0px"
      >
        <div class="register_title" style="display: flex; font-size: 20px">
          <el-link
            @click="toLogin"
            style="margin-left: 80px; margin-right: 20px; font-size: 20px"
            >用户登录</el-link
          >
          <p>注册账户</p>
        </div>

        <el-form-item label="" prop="loginName">
          <el-input
            v-model="ruleForm.loginName"
            type="text"
            placeholder="请输入用户名"
            prefix-icon="el-icon-user"
          ></el-input>
        </el-form-item>

        <el-form-item label="" prop="password">
          <el-input
            type="password"
            v-model="ruleForm.password"
            autocomplete="off"
            placeholder="请输入密码"
            prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <el-form-item label="" prop="checkPass">
          <el-input
            type="password"
            v-model="ruleForm.checkPass"
            autocomplete="off"
            placeholder="再次确认密码"
            prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>

        <el-form-item label="" prop="verifyCode">
          <div style="display: flex">
            <el-input
              type="text"
              v-model="ruleForm.verifyCode"
              autocomplete="off"
              placeholder="输入验证码"
              style="width: 50%; margin-right: 25px"
            ></el-input>
            <img
              :src="imgUrl"
              alt="图片"
              style="width: 150px"
              @click="resetImg"
            />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            style="background-color: #333; border: none"
            @click="regist()"
            >注册</el-button
          >

          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: "Register",

  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      imgUrl: "http://localhost:8088/common/kaptcha",
      ruleForm: {
        loginName: "",
        password: "",
        checkPass: "",
        verifyCode: "",
      },
      rules: {
        loginName: [
          { required: true, message: "用户名不能为空", trigger: "blur" },
          { min: 2, max: 10, message: "长度为2-10个字符", trigger: "blur" },
        ],
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
      },
    };
  },
  methods: {
    resetImg() {
      // 处理点击事件
      this.imgUrl = "http://localhost:8088/common/kaptcha?d=" + new Date();
    },
    regist() {
      this.$axios
        .post(
          "/user/register",
          {
            name: this.ruleForm.loginName,
            password: this.ruleForm.password,
            code: this.ruleForm.verifyCode,
          },
          {
            withCredentials: true,
          }
        )
        .then(
          (successResponse) => {
            //回调函数当post成功后执行
            if (successResponse.data.code === 200) {
              //如果后端返回的状态码是200
              this.open3(); //调用第一个弹窗方法表示登录成功
              this.$router.replace({
                //路由替换为index
                path: "/Login",
              });
            } else {
              this.resetImg();
              this.$message.error(successResponse.data.msg);
            }
          },
          (error) => {
            this.open4();
            this.resetImg();
            console.log("错误: ", error.message);
          }
        );
    },
    open3() {
      this.$message({
        message: "注册成功，登录即可使用Report Genesis",
        type: "success",
      });
    },
    open4() {
      this.$message.error("注册失败");
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    toLogin() {
      this.$router.push({ path: "/Login" });
    },
  },
};
</script>

<style>
#poster {
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;

  position: absolute;

  /* 表单与顶部距离 */
  margin-top: 0;
}

.register-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;

  background: rgba(255, 255, 255, 0.2);
}

.register_title {
  max-width: 0px auto 40 auto;
  text-align: center;
  color: #505458;
}

.el-input__inner {
  opacity: 0.5;
}

.el-input__inner::placeholder,
.el-input__value {
  color: #333;
}
</style>
