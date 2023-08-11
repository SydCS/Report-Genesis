<template>
  <div class="container">
    <el-input
      v-model="temContent"
      type="textarea"
      :rows="15"
      style="margin-top: 50px; width: 800px; font-size: 20px"
    />
    <div
      v-if="isLoading"
      style="margin-top: 10px; color: #409eff; font-size: 30px"
    >
      <i class="el-icon-loading"></i>
    </div>
    <div class="sticky-bottom">
      <el-row>
        <el-button style="text-align: center; margin-right: 50px" @click="back"
          >上一步</el-button
        >

        <el-button style="text-align: center" @click="next">
          生成报告</el-button
        >
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "editTemplate",
  props: {
    content: {
      type: String,
      required: false,
    },
    ispost: {
      type: Boolean,
      required: false,
    },
  },
  data() {
    return {
      isLoading: false,
      temContent: "",
    };
  },

  mounted() {
    this.temContent = this.content;
    console.log(this.content);
  },
  methods: {
    next() {
      this.isLoading = true; // 设置为 true 显示加载框

      this.$axios.post("/api/report", this.temContent).then(
        (response) => {
          this.$emit("report", response.data.data);
          this.$emit("next", 2);
        },
        (error) => {
          this.$message.error("报告发送失败");
          this.isLoading = false;
          console.log("错误", error.message);
        }
      );
    },
    back() {
      this.$emit("back", 2);
    },
  },
};
</script>

<style scoped>
.sticky-bottom {
  margin-left: -100px;
  padding: 10 px;
  width: 100%;
  height: 20px;
  font-size: 20px;
  position: absolute;
  bottom: 40px;
  background-color: rgba(233, 238, 243, 0);
  z-index: 0;
  text-align: center;
}
.container {
  top: 100px;
}
</style>
