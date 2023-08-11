<!--查询-->
<template>
  <div class="chat-container">
    <div class="chat-messages" ref="msglength">
      <div v-for="(item, i) in messageList" :key="i">
        <div v-if="item.status == 0" class="sent-message">
          <div class="message-content">{{ item.message }}</div>
        </div>

        <div v-else class="received-message">
          <div class="message-content">{{ item.message }}</div>
        </div>
      </div>
    </div>

    <div>
      <div style="position: fixed; width: 50%; left: 460px; top: 620px">
        <el-input
          :disabled="false"
          placeholder="输入查询内容"
          prefix-icon="el-icon-search"
          v-model="inputsearch"
        >
          <el-button
            style="background-color: #74b9be; color: #fff"
            slot="append"
            icon="el-icon-right"
            @click="sendMessage"
          ></el-button>
        </el-input>
      </div>
    </div>
  </div>
</template>

<script>
import CommonAside from "@/components/CommonAside.vue";

export default {
  name: "Require",
  comments: CommonAside,

  data() {
    return {
      messageList: [
        { message: "欢迎", status: 1 },
        { message: "xiaofan2", status: 1 },
        { message: "xiaofan3", status: 0 },
        { message: "xiaofan4", status: 1 },
      ],
      inputsearch: "",
    };
  },

  methods: {
    sendMessage() {
      if (!this.inputsearch) {
        return;
      }
      const newmessage = {
        message: this.inputsearch,
        status: 0,
      };
      this.messageList.push(newmessage);

      this.$nextTick(() => {
        // 等待 Vue 更新 DOM
        this.$refs.msglength.scrollTop = this.$refs.msglength.scrollHeight;
      });

      this.$axios.post("/api/query", this.inputsearch).then(
        (response) => {
          const backmessage = response.data.data;
          const resp = {
            message: backmessage,
            status: 1,
          };
          this.messageList.push(resp);
          this.$nextTick(() => {
            // 等待 Vue 更新 DOM
            this.$refs.msglength.scrollTop = this.$refs.msglength.scrollHeight;
          });

          console.log("/messages", response.data);
        },
        (error) => {
          if ((error.data.code = 401)) {
            this.$message.error("您没有管理员权限");
          } else {
            this.$message.error("查询失败");
          }
          console.log("错误", error.message);
        }
      );

      this.inputsearch = "";
    },
  },
};
</script>

<style>
.el-button:hover:active {
  background-color: burlywood;
}

.chat-container {
  display: flex;
  flex-direction: column;
  height: 85vh;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
}

.chat-messages .sent-message {
  display: flex;
  justify-content: flex-end;
  margin: 20px 20px 10px;
}

.chat-messages .sent-message .message-content {
  background-color: #9ad7dc;
  color: black;
  line-height: 30px;
  font-size: 18px;
  padding: 10px;
  border-radius: 10px;
}

.chat-messages .received-message {
  display: flex;
  justify-content: flex-start;
  margin: 20px;
  margin-left: 10px;
}

.chat-messages .received-message .message-content {
  font-size: 18px;
  background-color: #e2ebf3;
  color: black;
  line-height: 30px;
  padding: 10px;
  border-radius: 10px;
}
</style>
