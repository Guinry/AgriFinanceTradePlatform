<template>
  <header class="header">
    <div class="logo-nav-container">
      <img src="../icons/favicon.ico" alt="农产品融销一体平台标志" class="logo-image">
      <nav>
        <ul>
          <li><router-link to="/home">首页</router-link></li>
          <li><router-link to="/goodsSource">商品货源</router-link></li>
          <li><router-link to="/purchaseInfo">求购需求</router-link></li>
          <li><router-link to="/agriculturalKnowledge">农业知识</router-link></li>
          <li><router-link to="/expertGuidance">专家指导</router-link></li>
          <li><router-link to="/shoppingCart">购物车</router-link></li>
          <li class="dropdown">
            <el-dropdown @command="handleFinancingCommand">
              <span class="el-dropdown-link">
                融资申请<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="smartMatch">智能匹配</el-dropdown-item>
                  <el-dropdown-item command="financing">融资申请</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </li>
        </ul>
      </nav>
      <div class="actions" v-if="loginUserNickname === ''">
        <button><router-link to="/login">登录</router-link></button>
        <button><router-link to="/register">注册</router-link></button>
      </div>
      <div class="actions" v-else>
        <button><a href="javascript:void(0)" @click="userPage">个人中心</a></button>
        <button><a href="javascript:void(0)" @click="logout">退出</a></button>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

// let Base64 = require("js-base64");

// 使用 Composition API
const store = useStore()
const router = useRouter()

// 响应式数据
const avatar = ref("")
const loginUserNickname = ref("")

// 方法定义
const handleSelect = (key, keyPath) => {}

const Login = () => {
  router.push("/login")
}

const Register = () => {
  router.push("/register")
}

const logout = () => {
  store.commit("updateLoginUserNickname", "")
  store.commit("removeStorage")
  loginUserNickname.value = ""
  router.push("/home").catch((err) => err)
  if (sessionStorage.getItem("/order/goods/pageCode")) {
    sessionStorage.removeItem("/order/goods/pageCode")
  }
  if (sessionStorage.getItem("/order/needs/pageCode")) {
    sessionStorage.removeItem("/order/needs/pageCode")
  }
  if (sessionStorage.getItem("/knowledge/pageCode")) {
    sessionStorage.removeItem("/knowledge/pageCode")
  }
  if (sessionStorage.getItem("/user/search/pageCode")) {
    sessionStorage.removeItem("/user/search/pageCode")
  }
}

const userPage = () => {
  router.push("/home/user").catch((err) => err)
}

// 添加融资申请相关的处理函数
const handleFinancingCommand = (command) => {
  switch (command) {
    case 'smartMatch':
      router.push("/smartMatch").catch((err) => err)
      break
    case 'financing':
      router.push("/financingApplication").catch((err) => err)
      break
  }
}

// 生命周期钩子
onMounted(() => {
  if (window.localStorage.token) {
    let token = window.localStorage.token
    let arr = token.split(".")
    let res = Base64.decode(arr[1])
    const nickname = JSON.parse(res).nickname
    const avatar = JSON.parse(res).avatar
    const role = JSON.parse(res).role

    store.commit("updateLoginUserNickname", nickname)
    store.commit("updateLoginUserAvatar", avatar)
    store.commit("updateRole", role)

    loginUserNickname.value = nickname
  }
  console.log(loginUserNickname.value)
})
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 80px; /* 修改: 增加左右内边距 */
  background-color: #f8f9fa; /* 更改背景颜色 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

/* 修改: 图标样式 */
.logo-image {
  height: 40px; /* 调整图片高度以适应布局 */
  width: auto; /* 自动调整宽度保持比例 */
  margin-right: 80px; /* 修改: 进一步增加 logo 和导航栏之间的间距 */
}

/* 修改: 导航容器样式 */
.logo-nav-container {
  display: flex;
  align-items: center;
  flex-grow: 1;
}

nav {
  display: flex;
  align-items: center;
}

nav ul {
  display: flex;
  list-style: none;
  margin: 0;
  padding: 0;
}

nav ul li {
  margin-left: 20px;
  display: flex;
  align-items: center;
}

nav ul li a {
  text-decoration: none; /* 移除下划线 */
  color: #2c3e50; /* 设置初始颜色 */
  font-size: 16px;
  transition: color 0.3s ease;
  display: block;
  height: 100%;
  line-height: 60px;
}

nav ul li a:hover {
  color: #4CAF50; /* 链接悬停时的颜色 */
  text-decoration: none; /* 悬停时也移除下划线 */
}

/* 添加下拉菜单样式 */
.dropdown {
  position: relative;
  display: flex;
  align-items: center;
  margin-left: 20px;
  height: 60px;
}

.el-dropdown-link {
  cursor: pointer;
  color: #2c3e50;
  font-size: 16px;
  transition: color 0.3s ease;
  display: flex;
  align-items: center;
  height: 100%;
}

.el-dropdown-link:hover {
  color: #4CAF50;
}

/* 添加：处理 .actions 内部的 <router-link> 样式 */
.actions button a {
  text-decoration: none;
  color: inherit; /* 继承父元素颜色 */
}

.actions {
  display: flex;
  align-items: center;
  margin-left: auto; /* 修改: 确保 actions 右对齐 */
}

.actions button {
  border: 2px solid #4CAF50; /* 修改: 边框颜色改为绿色 */
  padding: 8px 16px; /* 修改: 缩小按钮大小 */
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 14px; /* 修改: 减小字体大小 */
  margin: 4px 8px; /* 修改: 增加按钮间距 */
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.actions button:first-child { /* 登录按钮 */
  background-color: white; /* 修改: 登录按钮背景颜色改为白色 */
  color: #4CAF50; /* 修改: 登录按钮文本颜色改为绿色 */
}

.actions button:last-child { /* 注册按钮 */
  background-color: #4CAF50; /* 修改: 注册按钮背景颜色改为绿色 */
  color: white; /* 修改: 注册按钮文本颜色改为白色 */
}

.actions button:hover {
  background-color: #45a049; /* 按钮悬停时的颜色 */
}
</style>