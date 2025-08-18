<template>
  <div>
    <NavigationBar />
    <div class="font-background">
      <img src="../../assets/page3/fontback3.png" alt="">
      <!-- 修改: 调整图片容器，确保文字在图片之上显示 -->
      <div class="image-overlay">
        <img src="../../assets/img/fontback.png" alt="" />
        <div class="title-text">健康生态绿色种植好米好味道</div>
      </div>
    </div>
    <Subtitle subtitle="需求列表" description="买到就是赚到哦~"  style="margin-top:10px;"/>
    <div class="home-purchase">
      <purchase-list :cneeds="needs" @handleSearch="handleSearch"></purchase-list>
      <pagination
          @item-click="pageClick"
          :cUrl="url"
          :cTotal="total"
          :cPageSize="pageSize"></pagination>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import {selectGoodsPage, selectNeedsPage} from "../../api/order";
import PurchaseList from "./PurchaseList.vue";
import Pagination from "../../components/Pagination.vue";
import Subtitle from "../../components/Subtitle.vue";
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

// 响应式数据
const needs = ref([])
const total = ref(0)
const pageSize = ref(30)
const searchValue = ref("")
const url = ref("/order/needs/")
const needsCount = ref(sessionStorage.getItem("/order/needs/pageCode")
    ? sessionStorage.getItem("/order/needs/pageCode")
    : 1)

// 使用 store
const store = useStore()

// 方法定义
const getData = () => {
  selectNeedsPage({
    pageNum: needsCount.value,
    keys: searchValue.value
  }).then((res) => {
    if (res.list) {
      needs.value = res.list;
      total.value = res.total;
    } else {
      alert('数据格式错误');
    }
  }).catch((error) => {
    console.error('API 调用失败:', error);
  });
}

const pageClick = (item) => {
  needs.value = item;
}

const handleSearch = (val) => {
  searchValue.value = val
  getData()
}

// 生命周期钩子
onMounted(() => {
  getData()
})
</script>

<style lang="less" scoped>
.home-purchase {
  width: 1500px;
  margin: 0 auto;
}

.font-background{
  background-image: url("../../assets/page3/background3.jpg");
  height: 350px;
  width: 100%;
  max-width: 100%;
  padding-left: 350px;
  padding-right: 800px;
  padding-top: 70px;
  position: relative;
  background-repeat: no-repeat;
  background-size: cover;
  box-sizing: border-box;
  overflow: hidden; /* 超出部分隐藏 */
}

// 修改: 创建图片叠加容器
.image-overlay {
  position: relative;
  display: inline-block;
  margin-top: 20px;
}

// 修改: 调整文字样式，确保在图片上正确显示
.title-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #4ab344;
  font: 24px 思源黑体heavy;
  z-index: 2;
  text-align: center;
  width: 100%;
  text-shadow: 0 0 5px rgba(255, 255, 255, 0.8);
}
</style>