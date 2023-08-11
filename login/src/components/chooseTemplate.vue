<template>
  <div
    style="
      justify-content: center;
      align-items: center;
      margin-top: 25px;
      margin-left: 120px;
    "
  >
    <el-row
      :gutter="30"
      v-for="(o, irow) in 2"
      :key="o"
      style="margin-bottom: 15px"
    >
      <!-- :offset="index > 0 ? 2 : 0"   -->

      <el-col :span="7" v-for="(o, icol) in 3" :key="o">
        <el-card
          :body-style="{ padding: '0px' }"
          @click.native="choose(irow * 3 + icol)"
          style="border-radius: 12px; box-sizing: content-box"
        >
          <el-main class="container">
            <div class="templateItem">
              <span>模板{{ irow * 3 + icol + 1 }}</span>
              <div class="bottom clearfix">
                <span>{{ templates[irow * 3 + icol] }}</span>
              </div>
            </div>
          </el-main>
        </el-card>

        <div>
          <div class="mask" v-if="showModal" @click="showModal = false"></div>
          <el-card class="pop" v-if="showModal">
            <div class="sticky-header">
              <span>模板{{ curIndex + 1 }}</span>
            </div>
            <div class="detail">
              <a>{{ templates[curIndex] }}</a>
            </div>
            <div class="sticky-bottom">
              <el-button @click="goNext" class="btn">选择模板</el-button>
            </div>
          </el-card>
          <!-- <button @click="showModal=true" class="btn">点击出现弹框</button> -->
        </div>
      </el-col>
    </el-row>

    <div class="sticky-bottom1">
      <el-row>
        <el-button style="text-align: center; margin-right: 50px" @click="back"
          >上一步</el-button
        >

        <el-button style="text-align: center" @click="next"> 下一步 </el-button>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "chooseTemplate",
  data() {
    return {
      templates: [
        // "共有_种不同的产品，其中最贵的是____产品，它来自____供应商,这个供应商来自____",
        "共有3种不同的产品，其中最贵的是xxx，它来自____供应商",
        "收入最高的的供应商是xx，其营业额达到123231元，比第二名高出20%。",
        "最早的订单由消费者____发送，这个订单的发送地和接收地分别为_____",
        "发件最多的客户是____，这个客户2020年一共发送___件订单，发送了总金额____的产品",
        // "2020年，累计实现营业收入总额____万元。营业收入最高的是____供应商，它的收入较平均值高出__%",
        "2020年，累计营业收入总额总计231,659.69万元。收入最高的的供应商是23DB，营业额达到123231元，比第二名高出20%。",
        "编号____订单单价最高，它的单价较平均值高出___%。编号_____订单包含的产品数量最多",
      ],
      curIndex: 0,
      showModal: false,
      irow: 0,
      icol: 0,
    };
  },
  methods: {
    choose(index) {
      console.log(index);
      //emitter.emit('type', { name: 'line' });
      this.showModal = true;
      this.curIndex = index;
      //this.$emit("gotoNext",this.templates[index])
    },
    goNext() {
      console.log(22222);
      this.showModal = false;

      console.log(this.templates[this.curIndex]);
      this.$emit("gotoNext", this.templates[this.curIndex]);
    },
    next() {
      this.$emit("next", 1);
    },
    back() {
      this.$emit("back", 1);
    },
  },
};
</script>

<style>
@keyframes example {
  from {
    visibility: visible;
  }

  to {
    visibility: hidden;
  }
}
</style>
<style scoped>
.container {
  height: 220px;
  width: 100%;
}

.templateItem {
  height: 100%;
  padding: 0px;
  font-size: 20px;
  display: -webkit-box;
  word-wrap: break-word;
  word-break: break-all;
  overflow: hidden;
  text-overflow: ellipsis;
  /* 文本溢出时显示省略号 */
  -webkit-line-clamp: 7;
  /* 显示4行文本 */
  -webkit-box-orient: vertical;
}

.detail {
  height: 90%;
  padding: 10px;
  font-size: 20px;
  word-wrap: break-word;
  word-break: break-all;
  display: -webkit-box;
  -webkit-box-orient: vertical;

  /*-webkit-line-clamp:6; !* 显示4行文本 *!*/

  /*text-overflow: ellipsis; !* 文本溢出时显示省略号 *!*/
}

.mask {
  background-color: #000;
  opacity: 0.3;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
}

.pop {
  background-color: #fff;
  position: fixed;
  top: 100px;
  left: 300px;
  width: calc(100% - 600px);
  height: calc(100% - 200px);
  z-index: 2;
  overflow-y: auto;
}

.sticky-header {
  padding: 10px;
  width: 100%;
  height: 30px;
  font-size: 30px;
  position: sticky;
  top: 0;
  background-color: #fff;
  z-index: 99;
}

.sticky-bottom {
  padding: 10px;
  width: 100%;
  height: 20px;
  font-size: 16px;
  position: absolute;
  bottom: 30px;
  background-color: #fff;
  z-index: 99;
  text-align: center;
}

.btn {
  background-color: lightblue;
  border-radius: 0px;
  padding: 4px 12px;
  color: #fff;
  width: 150px;
  height: 50px;
  margin-left: 150px;
}

.sticky-bottom1 {
  margin-left: -228px;
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
</style>
