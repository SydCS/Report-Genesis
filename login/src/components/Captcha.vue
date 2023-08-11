<template>
  <div>
    <input
      type="text"
      v-model="inputCode"
      placeholder="请输入验证码"
      required
    />
    <img
      class="pointer"
      :src="captchaUrl"
      @click="refreshCaptcha"
      alt="单击图片刷新！"
    />
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      inputCode: "",
      captchaUrl: "/common/kaptcha", // 验证码图片的初始URL
    };
  },
  methods: {
    refreshCaptcha() {
      axios
        .get(`/common/kaptcha?d=${new Date() * 1}`)
        .then((response) => {
          this.captchaUrl = response.data.imageUrl; // 获取返回的验证码图片URL
          this.inputCode = ""; // 清空输入框
        })
        .catch((error) => {
          console.error("获取验证码失败", error);
        });
    },
  },
};
</script>
