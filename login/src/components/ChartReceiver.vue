<template>
  <div class="hello">
    <!-- <h1>{{ msg }}</h1> -->
    <!-- <button @click="generateChart">生成</button> -->
    <div ref="chart" style="height: 400px"></div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import axios from "axios";

const url = "http://localhost:8088/api/option";

export default {
  name: "HelloWorld",
  props: {
    msg: String,
  },
  // data() {
  //   return {
  //     chartOption: null,
  //   };
  // },
  // methods: {
  //   generateChart() {
  //     axios.get("http://localhost:8088/api/getChart").then((res) => {
  //       // this.chartOption = res.data;
  //       console.log(res.data);
  //       this.renderChart();
  //     });
  //   },
  //   renderChart() {
  //     // 在这里编写动态生成 ECharts 图表的代码
  //     // if (!this.chartOption) return;
  //     const chartDom = this.$refs.chart;
  //     const myChart = echarts.init(chartDom);
  //     this.chartOption = {
  //       tooltip: {
  //         trigger: "axis",
  //       },
  //       xAxis: {
  //         type: "category",
  //         data: ["20005", "20006", "20007", "20008", "20009"],
  //       },
  //       yAxis: {
  //         type: "value",
  //       },
  //       series: [
  //         {
  //           name: "Product Quantity",
  //           type: "bar",
  //           data: [200, 40, 400, 40, 750],
  //         },
  //       ],
  //     };
  //     myChart.setOption(this.chartOption);
  //   },
  // },
  data() {
    return {
      goal: "统计产品数量随订单编号的分布情况",
      title: "产品数量分布表",
      type: "折线图",
    };
  },
  mounted() {
    this.chart = echarts.init(this.$refs.chart);
    this.loadData();
  },
  methods: {
    loadData() {
      // axios
      //   .get("http://localhost:8088/api/getOption")
      //   .then((response) => {
      //     this.renderChart(response.data);
      //   })
      //   .catch((error) => {
      //     console.error(error);
      //   });
      // const jsonString =
      //   '{"tooltip": {"trigger": "axis"},"xAxis": {"type": "category","data": ["20005", "20006", "20007", "20008", "20009"]},"yAxis": {"type": "value"},"series": [{"name": "Product Quantity","type": "bar","data": [200, 40, 400, 40, 750]}]}';
      var that = this;
      axios
        .post(url, { goal: that.goal, title: this.title, type: this.type })
        .then((response) => {
          // 处理响应
          const option = response.data;
          console.log(typeof option);
          // 在这里可以使用获取到的 option 数据进行相应的操作，例如传递给 Vue 组件进行渲染等
          // const option = JSON.parse(res);
          this.renderChart(option);
        })
        .catch((error) => {
          // 处理错误
          console.error(error);
        });
    },
    renderChart(option) {
      this.chart.setOption(option);
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
