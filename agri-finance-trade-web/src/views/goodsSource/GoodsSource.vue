<template>
  <NavigationBar />

  <div class="font-background">
    <img src="../../assets/page2/fontback5.png" alt="">
    <img src="../../assets/page2/fontback6.png" alt="" style="margin-left: 20px;">
    <div style="color:#FCFCFC;font-family: 'PingFang SC';font-size: 80px;margin-top: 10px;margin-bottom: 0">农资农科新时代</div>
  </div>

  <Subtitle subtitle="商品列表" description="买到就是赚到哦~"  style="margin-top:10px;"/>

  <div class="home-goods">
    <GoodsList :cgoods="goods" @handleSearch="handleSearch" />
    <div class="pagination-container">
      <Pagination @item-click="pageClick" :cUrl="url" :cTotal="total" :cPageSize="pageSize" />
    </div>
  </div>
  <Footer />
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import { selectGoodsPage } from "../../api/order";
import GoodsList from "./GoodsList.vue";
import Pagination from "../../components/Pagination.vue";
import Subtitle from "../../components/Subtitle.vue";
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

// 响应式数据
const goods = ref([])
const total = ref(0)
const pageSize = ref(30)
const searchValue = ref('')
const url = ref("/order/goods/")

const goodsCount = ref(sessionStorage.getItem("/order/goods/pageCode")
    ? sessionStorage.getItem("/order/goods/pageCode")
    : 1)

const store = useStore()
const route = useRoute()

const getData = () => {
  selectGoodsPage({
    pageNum: goodsCount.value,
    keys: searchValue.value
  }).then((res) => {
    if (res.flag) {
      goods.value = res.data.list;
      total.value = res.data.total;
    } else {
      alert('数据格式错误');
    }
  }).catch((error) => {
    console.error('API 调用失败:', error);
  });
}


const pageClick = (item) => {
  goods.value = item;
}

const handleSearch = (val) => {
  searchValue.value = val
  getData()
}

// 组件挂载时执行
onMounted(() => {
  getData()
})

// 监听路由变化
watch(route, (val, oldVal) => {
  console.log(val);
}, { deep: true })
</script>

<style lang="less" scoped>
.home-goods {
  width: 1100px;
  margin: 0 auto;
}

.font-background {
  background-image: url("../../assets/page2/background2.jpg");
  height: 350px;
  width: 100%;
  max-width: 100%;
  padding-left: 350px;
  padding-right: 800px;
  padding-top: 30px;
  position: relative;
  background-repeat: no-repeat;
  background-size: cover;
  box-sizing: border-box;
  overflow: hidden; /* 超出部分隐藏 */
}

.pagination-container {
  clear: both;
  display: flex;
  justify-content: center;
}
</style>
