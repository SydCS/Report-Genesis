<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>

import CommonAside from "@/components/CommonAside"
export default {
  name: "App",
  components: {
    CommonAside
  }
}
</script>


<style lang="less">
#app {
  //welcome图片铺满
  width: 100%;
  position:absolute;
  left: 0;
  top: 0;

  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
