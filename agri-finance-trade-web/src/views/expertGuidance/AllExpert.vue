<template>
  <div>
    <Subtitle subtitle="专家列表" description="欢迎咨询专业人士 ~" style="margin-top:10px;" />

    <div class="allExpert-page">
      <expert-source :cgoods="goods" @handleSearch="handleSearch"></expert-source>
      <pagination @item-click="pageClick" :cUrl="url" :cTotal="total" :cPageSize="pageSize"></pagination>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { selectExpert } from "../../api/order";
import ExpertSource from "../../components/ExpertSource.vue";
import Pagination from "../../components/Pagination.vue";
import Subtitle from "../../components/Subtitle.vue";

// 使用store
const store = useStore()

// 响应式数据
const goods = ref([])
const total = ref(0)
const pageSize = ref(30)
const searchValue = ref('')
const url = ref("/order/goods/")
const goodsCount = ref(sessionStorage.getItem("/order/goods/pageCode") 
  ? sessionStorage.getItem("/order/goods/pageCode") 
  : 1)

// 方法定义
const pageClick = (item) => {
  goods.value = item;
}

const handleSearch = (val) => {
  searchValue.value = val
  getData()
}

const getData = () => {
  selectExpert({
    pageNum: goodsCount.value,
    keys: searchValue.value
  }).then((res) => {
    if (res.flag) {
      goods.value = res.data.list;
      total.value = res.data.total;
    } else {
      // alert(res.data.data);
    }
  });
}

// 生命周期钩子
onMounted(() => {
  getData()
})

// 组件注册
// 在<script setup>中导入的组件会自动注册
</script>

<style lang="less" scoped>
.allExpert-page {
  width: 100%;
  margin: 0 auto;

  // float: left;
}
</style>