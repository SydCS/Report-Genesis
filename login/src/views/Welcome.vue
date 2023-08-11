<template>
  <div class="container">
    <div>
      <NavBar></NavBar>
    </div>

    <div class="carousel-map" id="first">
      <el-carousel
        :interval="3000"
        @change="changeImg"
        style="width: 100%; margin-top: 0px"
        trigger="click"
        height="100vh"
      >
        <el-carousel-item v-for="(item, index) in bannerList" :key="index">
          <div>
            <el-image
              :class="className"
              style="width: 100%; height: 100%"
              :src="item"
              fit="cover"
            >
            </el-image>
            <div class="carousel-item-text">
              <h1>Welcome to Report Genesis</h1>
              <el-button
                class="start"
                round
                @click="toLogin"
                style="background-color: transparent"
                >Get Started</el-button
              >
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- 关于我们部分 -->
    <!-- <div class="about-image" :style="{ backgroundImage: 'url(' + bgImage + ')' ,backgroundPosition: 'right'}">

        </div> -->
    <div class="about_us" id="about">
      <div class="about_title" style="padding-top: 30px; margin-top: 40px">
        <el-divider>
          <span class="divider-text"> 关于我们 </span>
        </el-divider>
      </div>
      <div class="about-text-image">
        <div class="about-text">
          <p
            style="
              display: block;
              font-size: 22px;
              color: #333333;
              margin-left: 100px;
              margin-right: 100px;
              line-height: 2;
              margin-top: 50px;
            "
          >
            Report
            Genesis是一款基于大模型的数据分析报告生成系统,用户可以通过选择或自行输入报告模板来生成数据分析报告。
            本系统还可以根据用户需求生成各种类型的图表，并支持用户查询数据库中的内容。
          </p>
          <p
            style="
              width: 100%;
              font-size: 23px;
              margin-top: 35px;
              text-align: center;
            "
          >
            Report Genesis的主要功能包括：
          </p>
          <div style="text-align: left; margin-left: 100px">
            <p class="fun-detail">
              1.数据库查询：用户可以使用 Report Genesis 的查询功能，与 ChatGPT
              进行对话，从数据库中查询需要的数据。
            </p>
            <p class="fun-detail">
              2.报告生成：用户可以将报告模板上传到 Report Genesis
              平台，系统会自动生成数据分析报告，并支持导出为 Word 格式。
            </p>
            <p class="fun-detail">
              3.图表生成：用户可以选择不同类型的图表，例如折线图、柱状图、饼图等，系统会自动生成对应的图表，并支持下载。
            </p>
          </div>

          <!-- <span style="
              font-size: 20px;
              line-height: 1.5;
              font-family: Arial, Helvetica, sans-serif;
              padding-top: 18px;
              display: inline-block;
              width: 80ch;
              word-wrap: break-word;
              max-width: 80%;
              word-break: break-all;
            ">
                        Report
                        Genesis是一个高度定制化的系统，旨在帮助用户根据自己的需求创建自己的报告模板。
                        <br />
                        无需手动编写和排版，用户只需输入报告模板并上传数据，系统将自动分析数据并生成报告。
                        <br />
                        用户可以在系统中预览报告，并选择将其下载为PDF或其他格式
                    </span> -->
        </div>
      </div>

      <div class="fea" style="padding-top: 20px; margin-top: 50px">
        <span class="ad-fea"> Advantages & Features</span>
        <div style="padding-top: 15px">
          <el-row>
            <el-col :span="8">
              <div>
                <p class="text-block">简单易用</p>
                <span class="fea-text">
                  用户只需输入报告模板和上传数据，系统将自动分析数据并生成报告。用户无需具备专业的数据分析和报告撰写技能。
                </span>
              </div>
            </el-col>

            <el-col :span="8">
              <div>
                <p class="text-block">高度定制化</p>
                <span class="fea-text">
                  用户可以根据自己的需求创建自己的报告模板，并选择需要在报告中包含的数据分析指标。用户还可以定义报告的格式和布局，以满足自己的需求。
                </span>
              </div>
            </el-col>
            <el-col :span="8">
              <div>
                <p class="text-block">数据安全</p>
                <span class="fea-text">
                  我们的系统严格遵守数据保护法规，用户上传的数据将受到保护，并仅用于报告生成。我们还采取了多项安全措施，以确保数据的安全性和保密性。
                </span>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>

    <!-- 联系我们部分 -->
    <div class="contactus" id="contact">
      <div class="contact_title" style="padding-top: 30px; margin-top: 50px">
        <el-divider>
          <span class="divider-text"> 联系我们 </span>
        </el-divider>
      </div>
      <div class="contact-text">
        <span
          >使用中遇到任何问题或希望为Report Genesis提出改进意见，请联系<a
            href="https://gitee.com/practical-training_111/report_gen"
            >我们</a
          >。</span
        >
        <br />
        <span
          >有关ChatGPT的问题，可以访问
          <a href="https://openai.com/">OpenAI</a> 的官方网站了解更多信息</span
        >
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "../components/NavBar.vue";

export default {
  name: "Welcome",
  components: {
    NavBar,
  },
  data() {
    return {
      bgImage: require("../assets/report3.png"), // 使用 require 方法获取图片路径
      bannerList: [
        require("../assets/report5.png"),
        require("../assets/report.png"),
        require("../assets/report3.png"),
      ],
      className: "", //轮播图名字
    };
  },

  mounted() {
    this.className = "lun-img";
    setTimeout(() => {
      this.className = "lun-img-two";
    }, 300);
  },
  methods: {
    //轮播图切换
    changeImg(e) {
      // console.log(e, "当前下标");
      this.className = "lun-img";
      setTimeout(() => {
        this.className = "lun-img-two";
      }, 300);
    },

    toLogin() {
      this.$router.push({ path: "/Login" });
    },

    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>

<style lang="scss" scoped>
.fun-detail {
  width: 80%;
  margin-left: 100px;
  font-size: 18px;
  margin-bottom: 22px;
}
.max {
  image {
    z-index: -1;
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
  }
}

.carousel-map {
  width: 100%;
  height: 100%;
  overflow: hidden;
  flex: 1;
  position: relative;

  .lun-img {
    transform: scale(1.5);
  }

  .lun-img-two {
    transition: all 3s;
    transform: scale(1);
  }

  .el-carousel__item.is-animating {
    transition: all 0.6s;
  }
}

.start {
  font-size: 20px;
  font-weight: bold;
  color: #2c3e50;
  text-align: center;
}

.carousel-item-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 24px;
}

.container {
  display: flex;
  flex-direction: column;
}

.ad-fea {
  padding-top: 20px;
  font-size: 32px;
  font-family: "Times New Roman", Times, serif;
  color: #106085;
  text-align: center;
}

.text-block {
  font-size: 23px;
  color: #106085;
  font-weight: 400;
}

.fea-text {
  max-width: 80%;
  /* 设置最大宽度为父元素宽度的 80% */
  padding-top: 20px;
  color: #333333;
  font-size: 18px;
  font-family: "Times New Roman", Times, serif;
  line-height: 1.5;
  display: inline-block;
  width: 40ch;
  word-break: break-all;
  /* 超出宽度时自动断行 */
  word-wrap: break-word;
}

.contact-text {
  padding-top: 20px;
  margin-bottom: 50px;
  font-size: 20px;
}

.divider-text {
  font-size: 35px;
  color: #666666;
  font-family: Arial, Helvetica, sans-serif;
  margin-top: 60px;
}

// .about-image{
//     position: relative; /* 设置为相对定位 */
//     width: 100%; /* 设置宽度为 100% */
//   height: 600px; /* 设置高度为 500px */
//   background-repeat: no-repeat; /* 禁止背景图重复 */
//   background-size: cover; /* 按比例缩放背景图，使其完全覆盖 */
// }

// .about-text{
//     position: absolute; /* 设置为绝对定位 */
//     top: 50%; /* 设置顶部位置为元素高度的一半 */
//   left: 50%; /* 设置左侧位置为元素宽度的一半 */
//   transform: translate(-50%, -50%); /* 使用 transform 属性调整位置 */
//   text-align: center; /* 设置文本居中对齐 */
//   z-index: 1; /* 设置 z-index 值为 1，使元素显示在背景图片*/
// }
</style>
