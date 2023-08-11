<template>
  <div class="container">
    <el-row>
      <el-card class="report" id="download">
        <span>
          {{ report }}
        </span>
      </el-card>
      <el-button style="text-align: center; margin-right: 50px" @click="back"
        >上一步</el-button
      >

      <el-button style="text-align: center" @click="next"> 下载报告 </el-button>
    </el-row>
  </div>
</template>

<script>
import * as echarts from "echarts";

import { exportWord } from "mhtml-to-word";
import Docxtemplater from "docxtemplater";
import PizZip from "pizzip";
import PizZipUtils from "pizzip/utils/index.js";
import { saveAs } from "file-saver";

export function getModelHtml(mhtml, style) {
  return `
                  Content-Type: text/html; charset="utf-8"
                      <!DOCTYPE html>
                      <html>
                      <head>
                      <style>
                          ${style}
                      </style>
                      </head>
                      <body>
                          ${mhtml}
                      </body>
                      </html>
                  `;
}
export function getHtml(dom) {
  let _dom = dom || document;
  console.log("dom");
  console.log(_dom);
  //获取HTML页面中所有echarts(canvas对象),并将其放在canvass数组中,echarts的标签以canvas开头
  var canvass = _dom.querySelectorAll("canvas");
  console.log(canvass);
  //
  var imgRepalce = _dom.querySelectorAll(".imgRepalce");
  let imageList = [];
  //canvass 将echarts图表转为图片
  for (let k4 = 0; k4 < canvass.length; k4++) {
    //toDataURL():canvas对象的一种方法，用于将canvas对象转换为base64位编码
    //            参数type:指定转换为base64编码后图片的格式
    let imageURL = canvass[k4].toDataURL("image/png");
    let img = document.createElement("img");
    //按照序号设置img标签的src
    img.src = imageURL;
    //设置img标签的width,height......
    img.width = 300 + "px";
    //读出img标签对象的String
    imageList.push(img.outerHTML);
  }
  console.log(imageList);
  //做分页
  //style="page-break-after: always"
  let pages = _dom.querySelectorAll(".result");
  for (let k5 = 0; k5 < pages.length; k5++) {
    pages[k5].setAttribute("style", "page-break-after: always");
  }
  //提取文档根节点的子节点，因为_dom没有outerHTML的属性，故不能用_dom.outerHTML,要去其子节点的outerHTML
  let result = _dom.getElementById("download").outerHTML;
  // console.log(app.outerHTML);
  // console.log(result);
  // console.log(_dom);
  //result = result.replace(/<colgroup>(.*?)<\/colgroup>/gi, '')
  //result = result.replace(/<canvas (.*?)><\/canvas>/gi, '')
  // for(let i = 0; i < imgRepalce.length; i++) {
  //   result = result.replace(imgRepalce[i].outerHTML,
  //       '<div class="imgRepalce">' + imageList[i] + '</div>')
  // }
  for (let i = 0; i < canvass.length; i++) {
    //将原来的canvass数组替换成对应的src为base64编码的img标签对象，并为之设置imgReplce格式
    result = result.replace(
      canvass[i].outerHTML,
      '<div class="imgRepalce">' + imageList[i] + "</div>"
    );
  }
  console.log(result);

  if (result != null) {
    result = result.replace(/<img (.*?)>/gi, "<img $1></img>");
    result = result.replace(/<br>/gi, "<br></br>");
    result = result.replace(/<hr>/gi, "<hr></hr>");
  }

  return (
    "<body printmarginleft='72' printmarginright='72' printmargintop='56' printmarginbottom='56'>" +
    result +
    "</body>"
  );
}
export function getStyle(notPrint) {
  var str = '<head><meta charset="utf-8"></meta>',
    styles = document.querySelectorAll("style");
  for (var i = 0; i < styles.length; i++) {
    str += styles[i].outerHTML;
  }
  str +=
    "<style>" + (notPrint ? notPrint : ".no-print") + "{display:none;}</style>";
  str +=
    "<style>.results{width:100%!important;} .result .title{width:100%;}</style>";
  str += "<style>.imgRepalce{width:200px} </style>";
  str += "<style>table{border-collapse: collapse;table-layout: fixed}</style>";
  str += "<style>table thead tr{ background-color: #f3f4f9;}</style>";
  str +=
    "<style>table td,th{ font-size: 14px;padding: 1px 1px;border-width: 1px;border-style: solid;border-color: #d0d0d0;word-break: keep-all;white-space: nowrap;}</style>";
  str += "<style>h5{font-color: #2fb89e;}</style>";
  str += "</head>";
  return str;
}
export default {
  name: "showTemplate",
  props: {
    report: {
      type: String,
      required: false,
    },
  },
  data() {
    return {
      //   report:"报告模版哈哈哈哈11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
    };
  },
  methods: {
    next() {
      this.exportWord();
      this.$emit("next", 3);
    },
    back() {
      this.$emit("back", 3);
    },
    exportWord() {
      let myContainer = document.getElementById("downloadContainer");
      // 把当前 vue 所展示的页面对应的 html 转换成一个字符串，这里用到了上面的三个函数，所以，如果是写在外面的话要引用进来
      //this.$refs.print意思是为传入当前页面
      let html = getModelHtml(getHtml(this.myContainer), getStyle());
      // 使用我们刚刚准备好的html模板并创建Blob对象
      let blob = new Blob([html], { type: "application/msword;charset=utf-8" });
      // 调用FileSaver.saveAs导出下载word
      saveAs(blob, "报告.doc");
    },
  },
};
</script>

<style>
.report {
  width: 90%;
  height: 550px;
  padding: 0px;
  font-size: 20px;
  /* display: -webkit-box; */
  overflow: auto;
  word-wrap: break-word;
  word-break: break-all;
  /* -webkit-box-orient: vertical; */
  display: inline-block;
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
.container {
  top: 100px;
}
</style>
