<template>
    <div>
      <div class="user-container">
        <div class="avatar-container">
          <img :src="avatar" @click="viewAvatar" class="avatar">
        </div>
        <div class="username-container">
          <label v-if="!editing">{{ username }}</label>
          <input v-else v-model="newUsername" class="input" />
          <div class="button-container">
            <button @click="editUsername" v-if="!editing" class="button">编辑</button>
            <button @click="saveUsername" v-else class="button">确定</button>
          </div>
        </div>
        <button @click="toggleConfigMode" class="button" v-if="!configMode">配置数据库</button>
        <button @click="saveConfig" class="button" v-else>保存配置</button>
      </div>
  
      <div class="database-list">
        <div v-for="(database, index) in databases" :key="index" class="database-row" :class="{ 'default-row': database.isDefault }">
          <div class="database-info">
            <div class="database-field-row">
              <div class="database-field">Host: {{ database.host }}</div>
              <div class="database-field">User: {{ database.user }}</div>
              <div class="database-field">Password: {{ database.password }}</div>
              <div class="database-field">Database: {{ database.database }}</div>
              <div class="database-field">Charset: {{ database.charset }}</div>
            </div>
          </div>
          <div class="database-actions">
            <button @click="setAsDefault(index)" v-if="!configMode && !database.isDefault" class="button">设置为默认</button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        avatar: '', // 用户头像的图片地址
        username: '用户名', // 用户名
        editing: false, // 是否处于编辑模式
        newUsername: '', // 编辑模式下的新用户名
        configMode: false, // 是否处于配置模式
        databases: [
          {
            host: 'localhost',
            user: 'root',
            password: 'mysql123',
            database: 'tysql',
            charset: 'utf8mb4',
            isDefault: 1,
          },
          {
            host: 'example.com',
            user: 'user1',
            password: 'pass1',
            database: 'db1',
            charset: 'utf8mb4',
            isDefault: 0,
          },
          {
            host: 'example.com',
            user: 'user2',
            password: 'pass2',
            database: 'db2',
            charset: 'utf8mb4',
            isDefault: 0,
          },
        ],
      };
    },
    methods: {
      // 点击用户头像，查看头像或更改头像
      viewAvatar() {
        // 根据需求实现查看头像的逻辑
      },
  
      // 点击编辑按钮，切换到编辑模式
      editUsername() {
        this.editing = true;
        this.newUsername = this.username;
      },
  
      // 点击确定按钮，保存用户名并退出编辑模式
      saveUsername() {
        // 向后端发送请求保存用户名
        // 如果保存成功，更新this.username，并退出编辑模式
        // 如果保存失败，弹出消息框提示用户
        this.username = this.newUsername;
        this.editing = false;
      },
  
      // 切换到/退出配置模式
      toggleConfigMode() {
        this.configMode = !this.configMode;
      },
  
      // 保存配置
      saveConfig() {
        // 向后端发送请求保存数据库配置
        // 如果保存成功，退出配置模式
        // 如果保存失败，弹出消息框提示用户
        this.configMode = false;
      },
  
      // 设置某个数据库为默认
      setAsDefault(index) {
        // 向后端发送请求更新数据库配置
        // 如果更新成功，重新加载数据库列表
        // 如果更新失败，弹出消息框提示用户
        this.databases.forEach((database, i) => {
          database.isDefault = i === index ? 1 : 0;
        });
        this.sortDatabases();
      },
  
      // 根据默认字段排序数据库列表
      sortDatabases() {
        this.databases.sort((a, b) => b.isDefault - a.isDefault);
      },
    },
  };
  </script>
  
  <style>
  .user-container {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    margin-bottom: 20px;
  }
  
  .avatar-container {
    display: flex;
    align-items: center;
    margin-right: 20px;
  }
  
  .avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    cursor: pointer;
  }
  
  .username-container {
    display: flex;
    align-items: center;
    margin-right: 20px;
  }
  
  .input {
    margin-right: 10px;
  }
  
  .button-container {
    display: flex;
    align-items: center;
  }
  
  .button {
    border: none;
    background-color: #f0f0f0;
    padding: 5px 10px;
    cursor: pointer;
  }
  
  .database-list {
    margin-top: 20px;
  }
  
  .database-row {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px;
    border: 1px solid #ccc;
  }
  
  .default-row {
    background-color: #eaf2ff;
  }
  
  .database-info {
    flex: 1;
  }
  
  .database-field-row {
    display: flex;
    flex-wrap: wrap;
  }
  
  .database-field {
    flex: 0 0 20%;
    margin-bottom: 5px;
  }
  
  .database-actions {
    margin-left: 10px;
  }
  
  </style>
  