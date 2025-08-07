<template>
  <div>
    <NavigationBar />
    <div class="font-background">
      <img src="../../assets/page3/fontback3.png" alt="">
      <img src="../../assets/img/fontback.png" alt="" style="margin-top: 20px">
      <div style="margin-top:-65px;margin-left:35px;color: #4ab344;font: 24px 思源黑体heavy;">健康生态绿色种植好米好味道</div>
    </div>
    <Subtitle subtitle="需求列表" description="买到就是赚到哦~"  style="margin-top:100px;"/>
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
import { selectNeedsPage } from "../../api/order";
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
    if (res.flag == true) {
      needs.value = res.data.list;
      total.value = res.data.total;
    }
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
  store.commit("updateActiveIndex", "3");
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
  height: 300px;
  width: 1920px;
  padding-left: 350px;
  padding-right: 800px;
  padding-top: 90px;
}
</style>;
