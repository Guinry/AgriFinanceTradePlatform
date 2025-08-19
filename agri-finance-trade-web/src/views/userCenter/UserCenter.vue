<template>
  <NavigationBar />
  <div class="user-content">
    <UserNavigationBar />
    <!-- 在两个组件之间添加间距 -->
    <div class="right-content">
      <router-view></router-view>
    </div>
  </div>
  <Footer />
</template>

<script setup>
import UserNavigationBar from "../../components/UserNavigationBar.vue";
import { useRouter } from 'vue-router'; // 使用 useRouter 替代 onBeforeRouteEnter
import { onMounted } from 'vue';
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue"; // 添加 onMounted 钩子

const router = useRouter();

// 使用 onMounted 替代 onBeforeRouteEnter 进行路由守卫
onMounted(() => {
  let token = window.localStorage.token;
  if (!token) {
    router.push("/login");
  }
});
</script>

<style lang="less" scoped>
.user-content {
  width: 1100px;
  height: 100%;
  margin: 0 auto;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

// 添加内容包装器样式，用于创建组件间距
.right-content {
  min-height: 100%;
  padding-top: 20px;
  height: auto;
  background: #fff;
  // 添加左侧边距，避免与导航栏遮挡
  width: calc(100% - 240px); // 220px导航栏宽度 + 20px间距
  margin-left: 20px;
}
</style>
