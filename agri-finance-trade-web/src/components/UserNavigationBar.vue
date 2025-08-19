<template>
  <div class="user-navigation-container">
    <el-row class="tac user-nav">
      <el-col :span="24">
        <div class="nav-header">
          <el-icon class="nav-header-icon"><User /></el-icon>
          <span class="nav-header-title">用户中心</span>
        </div>
        <el-menu
            :default-active="userActiveIndex"
            :active-text-color="'#409EFF'"
            class="el-menu-vertical-demo"
            background-color="#f5f7fa"
            text-color="#303133"
        >
          <el-sub-menu index="1">
            <template #title>
              <el-icon><UserFilled /></el-icon>
              <span class="submenu-title">我的信息</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1" @click="UserInfoClick" class="menu-item">
                <el-icon><InfoFilled /></el-icon>
                基本信息
              </el-menu-item>
              <el-menu-item
                  index="1-4"
                  v-if="isExpert"
                  @click="handleExperInfo"
                  class="menu-item"
              >
                <el-icon><Medal /></el-icon>
                专家信息
              </el-menu-item>
              <el-menu-item
                  index="1-2"
                  @click="UserAddressClick"
                  v-if="!isExpert && !isAdmin"
                  class="menu-item"
              >
                <el-icon><Location /></el-icon>
                收货地址
              </el-menu-item>
              <el-menu-item index="1-3" @click="UserPasswordClick" class="menu-item">
                <el-icon><Lock /></el-icon>
                修改密码
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <!-- 专家/管理员模块 -->
          <el-sub-menu
              index="2"
              v-if="!isExpert && !isAdmin"
          >
            <template #title>
              <el-icon><Document /></el-icon>
              <span class="submenu-title">我的发布</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="2-1" @click="PublishedGoodsClick" class="menu-item">
                <el-icon><Goods /></el-icon>
                我的商品
              </el-menu-item>
              <el-menu-item index="2-2" @click="PublishedNeedsClick" class="menu-item">
                <el-icon><DocumentAdd /></el-icon>
                我的需求
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>

          <!-- 专家管理 -->
          <el-sub-menu index="4" v-if="!isAdmin">
            <template #title>
              <el-icon><Management /></el-icon>
              <span class="submenu-title">专家管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="4-1" @click="handleExpertQuestion" class="menu-item">
                <el-icon><ChatLineRound /></el-icon>
                我的问答
              </el-menu-item>
              <el-menu-item index="4-2" @click="handleExpertAppoint" class="menu-item">
                <el-icon><Calendar /></el-icon>
                我的预约
              </el-menu-item>
              <el-menu-item
                  index="4-3"
                  v-if="isExpert"
                  @click="PublishedKnowledgeClick"
                  class="menu-item"
              >
                <el-icon><Reading /></el-icon>
                我的知识
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>

          <!-- 订单管理 -->
          <el-sub-menu
              index="3"
              v-if="!isExpert && !isAdmin"
          >
            <template #title>
              <el-icon><List /></el-icon>
              <span class="submenu-title">我的订单</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="3-1" @click="UserBuyClick" class="menu-item">
                <el-icon><ShoppingCart /></el-icon>
                我买的
              </el-menu-item>
              <el-menu-item index="3-2" @click="UserSellClick" class="menu-item">
                <el-icon><SoldOut /></el-icon>
                我卖的
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>

          <el-menu-item
              index="5"
              v-if="!isExpert && !isAdmin"
              @click="UserFinance"
              class="menu-item single-menu"
          >
            <template #title>
              <el-icon><Coin /></el-icon>
              <span>我的融资</span>
            </template>
          </el-menu-item>

          <el-menu-item
              index="6"
              v-if="isAdmin"
              @click="UserManageClick"
              class="menu-item single-menu"
          >
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
// 引入更多图标
import {
  User, UserFilled, InfoFilled, Location, Lock,
  Document, Goods, DocumentAdd, Management, ChatLineRound,
  Calendar, Reading, List, ShoppingCart, SoldOut,
  Coin, Medal
} from '@element-plus/icons-vue'

const router = useRouter()
const store = useStore()

// 获取 Vuex 中的状态
const isExpert = store.getters.isExpert
const isAdmin = store.getters.isAdmin
const userActiveIndex = store.state.userActiveIndex

// 跳转函数 - 修复路由路径以匹配路由配置
const UserInfoClick = () => router.push("/userInformation")
const UserAddressClick = () => router.push("/userAddress")
const UserPasswordClick = () => router.push("/userPassword")
const PublishedGoodsClick = () => router.push("/userGoods")
const PublishedNeedsClick = () => router.push("/userNeeds")
const PublishedKnowledgeClick = () => router.push("/home/user/publishedknowledge")
const UserBuyClick = () => router.push("/userBuy")
const UserSellClick = () => router.push("userSell")
const UserFinance = () => router.push("/home/user/userfinance")
const UserManageClick = () => router.push("/home/usermanage")
const handleExpertQuestion = () => router.push("/userQuestion")
const handleExpertAppoint = () => router.push("/userAppointment")
const handleExperInfo = () => router.push("/home/user/expertInfo")

// 更新 Vuex 中的 activeIndex
store.commit("updateUserActiveIndex", "1-1")
</script>

<style lang="less" scoped>
.user-navigation-container {
  background: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  overflow: hidden;
  // 设置固定宽度和高度
  width: 220px;
  height: 600px;
  // 居中显示
  margin: 0 auto;

  .nav-header {
    padding: 20px 0;
    text-align: center;
    background: linear-gradient(135deg, #409EFF, #1a73e8);
    color: white;

    .nav-header-icon {
      font-size: 24px;
      margin-bottom: 8px;
    }

    .nav-header-title {
      font-size: 18px;
      font-weight: 600;
    }
  }

  .user-nav {
    width: 100%;

    :deep(.el-menu) {
      border: none;

      .el-sub-menu__title {
        font-size: 15px;
        height: 50px;
        line-height: 50px;
        padding-left: 16px;  /* Align submenu title */
        &:hover {
          background-color: #e6f0ff !important;
        }

        .submenu-title {
          margin-left: 8px;
        }
      }

      .el-menu-item-group__title {
        padding: 0;
      }

      .menu-item {
        height: 45px;
        line-height: 45px;
        font-size: 14px;
        padding-left: 56px !important; /* Add padding to align text to the left */
        display: flex;
        align-items: center;

        &:hover {
          background-color: #e6f0ff !important;
        }

        &.is-active {
          background-color: #ecf5ff !important;
          border-right: 3px solid #409EFF;
        }
      }

      .single-menu {
        height: 50px;
        line-height: 50px;
        padding-left: 16px !important; /* Align "我的融资" with the rest */

        &:hover {
          background-color: #e6f0ff !important;
        }

        &.is-active {
          background-color: #ecf5ff !important;
          border-right: 3px solid #409EFF;
        }
      }
    }
  }
}

.user-content {
  width: 1100px;
  margin: 10px auto;

  .user-nav {
    float: left;
    width: 220px;
    margin-right: 20px;
  }
}
</style>