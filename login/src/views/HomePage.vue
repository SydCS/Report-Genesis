<template>
  <div class="main">
    <el-steps :active="active" style="text-align: left; padding-top: -20px">
      <el-step title="步骤一" description="选择模板"></el-step>
      <el-step title="步骤二" description="编辑模板"></el-step>
      <el-step title="步骤三" description="生成报告"></el-step>
    </el-steps>
    <choose-template
      @gotoNext="goNext"
      v-if="active == 1"
      @next="Next"
      @back="Back"
    ></choose-template>

    <!--  -->
    <EditTemplate
      @report="recieve"
      @next="Next"
      @back="Back"
      v-if="active == 2"
      :content="templateContent"
    ></EditTemplate>
    <!-- <div id="download"> -->
    <show-template
      @next="Next"
      @back="Back"
      v-if="active == 3"
      :report="reportContent"
    ></show-template>
    <!-- </div> -->
  </div>
</template>

<script>
import ChooseTemplate from "../components/chooseTemplate.vue";

import EditTemplate from "@/components/editTemplate";
import ShowTemplate from "@/components/showTemplate";

export default {
  name: "ReportTemplate",
  components: { ShowTemplate, EditTemplate, ChooseTemplate },
  data() {
    return {
      active: 1,
      templateContent: "",
      nextStep: "下一步",
      reportContent: "",
    };
  },
  mounted() {
    console.log(this.active);
  },
  methods: {
    Next(index) {
      console.log("index");
      console.log(index);
      if (index == 1) {
        this.active = 2;
        console.log(this.active);
        console.log(this.active == 2);
      }
      if (index == 2) {
        this.active = 3;
      }
    },
    Back(index) {
      console.log(index);
      if (index == 2) {
        this.active = 1;
      }
      if (index == 3) {
        this.active = 2;
      }
    },
    goNext(value) {
      // console.log(value)
      this.templateContent = value;
      console.log(this.templateContent);

      if (this.active == 1) {
        this.active++;
      }
    },
    recieve(value) {
      this.reportContent = value;
    },
  },
};
</script>

<style scoped>
.main {
  height: 600px;
}

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
::v-deep .el-step__description {
  font-size: 15px !important;
}
</style>
