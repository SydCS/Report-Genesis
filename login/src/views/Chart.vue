<template>
  <div>
    <div class="myBox">
      <div id="silderLeft">
        <el-card style="margin: 20px">
          <div
            v-if="isLoading"
            style="margin-top: 50px; color: #409eff; font-size: 30px"
          >
            <i class="el-icon-loading"></i>
          </div>
          <div
            id="speeds"
            ref="lineRef"
            :style="{ width: '100%', height: '500px' }"
          ></div>
          <a>{{ analysis }}</a>
        </el-card>

        <el-row>
          <el-button @click="Export">截图</el-button>
        </el-row>
        <div class="moveBtn" v-move></div>
      </div>
      <div class="silderRight" id="right">
        <div id="echartsWarp">
          <el-form :model="detailForm">
            <el-form-item label="分析目标"> </el-form-item>
            <el-form-item>
              <el-input
                v-model="detailForm.goal"
                type="textarea"
                placeholder="请输入分析目标"
                :rows="8"
              />
            </el-form-item>
            <!-- <div v-if="loading" class="loading-overlay">
              <div class="loading-spinner"></div>
            </div> -->
            <el-form-item label="图表名称"> </el-form-item>
            <el-form-item>
              <el-input
                v-model="detailForm.title"
                placeholder="请输入图表名称"
                style="height: 50px"
              />
            </el-form-item>
            <el-form-item label="图表类型"> </el-form-item>
            <el-form-item>
              <el-dropdown size="large" split-button @command="handleCommand">
                <span class="el-dropdown-link" :placeholder="请选择表格类型">
                  {{ this.detailForm.type }}
                </span>
                <template slot="dropdown">
                  <el-dropdown-menu>
                    <el-dropdown-item command="折线图">折线图</el-dropdown-item>
                    <el-dropdown-item command="柱状图">柱状图</el-dropdown-item>
                    <el-dropdown-item command="饼图">饼图</el-dropdown-item>
                    <el-dropdown-item command="散点图">散点图</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="created()"> Create </el-button>
              <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import elementResizeDetectorMaker from "element-resize-detector";

import * as echarts from "echarts";

export default {
  name: "Chart",
  directives: {
    move(el, bindings) {
      el.onmousedown = function (e) {
        var init = e.clientX;
        var parent = document.getElementById("silderLeft");

        //左边部分的宽度
        var initWidth = parent.offsetWidth;

        //console.log("parent Width")
        // console.log(parent.offsetWidth)
        //移动以后左边部分的宽度
        document.onmousemove = function (e) {
          var end = e.clientX;

          var newWidth = end - init + initWidth;
          parent.style.width = newWidth + "px";
          this.widthLeng = document.body.clientWidth - newWidth;

          console.log(newWidth, 666, document.body.clientWidth, this.widthLeng);
          //myChart.resize();

          // console.log(chart.offsetWidth)
        };
        document.onmouseup = function () {
          document.onmousemove = document.onmouseup = null;
        };
      };
    },
  },
  data() {
    return {
      isLoading: false,
      msg: "我是第二页",
      widthLeng: "",
      detailForm: {
        goal: "",
        title: "",
        type: "",
      },
      analysis: "",
    };
  },
  computed: {},
  methods: {
    //监听盒子大小,改变echarts宽度,实现echarts自适应
    changeEchartsWidthApi() {
      // 创建实例，无参数
      var erd = elementResizeDetectorMaker(); //使用默认选项（将使用基于对象的方法）
      // 创建实例带参数
      // 使用基于超快速滚动的方法。
      // 这是推荐的策略。
      var erdUltraFast = elementResizeDetectorMaker({
        strategy: "scroll",
        callOnAdd: true, //callOnAdd选项，用于确定在添加侦听器时是否应调用它们。默认为true。
        //如果为true，则确保在添加侦听器后将对其进行调用。如果为false，则在添加侦听器时将不保证其
        //被调用（不会阻止其被调用）
        debug: true,
      });
      //监听class为staticNextMain的元素 大小变化
      var self = this;
      //侦听元素的调整大小事件，并使用元素作为调整大小事件的参数来调用侦听器函数。传递给函数的选项将
      //覆盖实例选项
      erd.listenTo(document.getElementById("echartsWarp"), function (element) {
        //今日车速
        self.speedChartsApi();
      });
    },
    //今日车速
    speedChartsApi() {
      // 基于准备好的dom，初始化echarts实例
      this.$nextTick((_) => {
        const dom = document.getElementById("speeds");
        // dom.style.width = dom.parentNode.parentNode.clientWidth + "px";
        dom.style.width = this.widthLeng + "px";
        //  let myChart = this.$echarts.init(document.getElementById("speeds"));
        // console.log(dom.style.width,888)
        let myChart = echarts.init(dom);
        const _this = this;
        let erd = elementResizeDetectorMaker();
        erd.listenTo(this.$refs.lineRef, () => {
          _this.$nextTick(() => {
            myChart.resize();
          });
        });

        myChart.resize();
      });
    },
    getlineData() {
      //有的话就获取已有echarts实例的DOM节点。
      let myChart = echarts.getInstanceByDom(document.getElementById("line"));
      if (myChart == null) {
        // 如果不存在，就进行初始化
        myChart = echarts.init(document.getElementById("line"));
      }
      myChart.setOption(this.lineOption, true);
    },
    // 导出单个图表图片
    Export() {
      var img = new Image();
      const MyChart = echarts.init(document.getElementById("speeds"));
      img.src = MyChart.getDataURL({
        type: "png",
        pixelRatio: 1, //放大2倍
        backgroundColor: "#fff",
      });
      img.onload = function () {
        var canvas = document.createElement("canvas");
        canvas.width = img.width;
        canvas.height = img.height;
        var ctx = canvas.getContext("2d");
        ctx.drawImage(img, 0, 0);
        var dataURL = canvas.toDataURL("image/png");

        var a = document.createElement("a");
        var event = new MouseEvent("click");
        a.download = "图片.png" || "下载图片名称";
        // 将生成的URL设置为a.href属性
        a.href = dataURL;
        // 触发a的单击事件
        a.dispatchEvent(event);
        a.remove();
      };
    },
    handleCommand(type) {
      this.detailForm.type = type;
    },
    created() {
      this.isLoading = true; // 设置为 true 显示加载框

      this.$axios
        .post("api/option", this.detailForm)
        .then((response) => {
          this.analysis = response.data.data.analysis;
          const option_string = response.data.data.option;
          // 在这里可以使用获取到的 option 数据进行相应的操作，例如传递给 Vue 组件进行渲染等
          const option = JSON.parse(option_string);
          this.renderChart(option);
        })
        .catch((error) => {
          // 处理错误
          console.error(error);
        })
        .finally(() => {
          this.isLoading = false; // 设置为 false 隐藏加载框
        });
    },
    renderChart(option) {
      let myChart = echarts.init(document.getElementById("speeds"));
      myChart.setOption(option);
    },
  },
  mounted() {
    this.detailForm.type = "折线图";
    // this.getlineData();
    this.widthLeng = document.body.clientWidth - 100 + "px";
    //初始化数据
    this.changeEchartsWidthApi();
    // 监听sonEmit事件

    this.chart = echarts.init(this.$refs.chart);

    console.log("11111");
  },

  setup() {},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->

<style scoped>
.main {
  padding: 0px;
}
.myBox {
  width: 100%;
  height: 800px;
  /*border: 1px solid red;*/
  display: flex;
}
#silderLeft {
  width: 300px;
  height: 100%;
  background-color: #f3f4fa;
  position: relative;
  /* overflow-y: auto; */
}
/* 拖动条 */
.moveBtn {
  height: 100%;
  width: 10px;
  /* opacity: 0; */
  position: absolute;
  right: 0px;
  top: 0;
  cursor: col-resize;
  background-color: lightblue;
}
.menuList {
  background-color: #f3f4fa;
  height: 120%;
}
.silderRight {
  padding: 20px;
  width: 100%;
  height: 100%;
  background-color: #fff;
  flex: 1;
}
</style>
