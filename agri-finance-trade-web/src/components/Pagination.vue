<template>
  <div class="pagination">
    <el-pagination
      background
      layout="prev, pager, next"
      :page-size="cPageSize"
      :total="cTotal"
      :current-page="pageCode"
      @current-change="currentChange"
      v-if="cTotal != 0">
    </el-pagination>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { pagination } from "../api/pagination";

// 定义 props
const props = defineProps({
  cUrl: {
    type: String,
  },
  cTotal: {
    type: Number,
  },
  cPageSize: {
    type: Number,
  },
})

// 定义 emits
const emit = defineEmits(['item-click'])

// 响应式数据
const pageGoods = ref([])
const pageCode = ref(parseInt(sessionStorage.getItem(props.cUrl + "pageCode"))
  ? parseInt(sessionStorage.getItem(props.cUrl + "pageCode"))
  : 1)

// 方法定义
const currentChange = (current) => {
  sessionStorage.setItem(props.cUrl + "pageCode", current);
  pagination({
    cUrl: props.cUrl,
    pageNum: current, // 使用传入的 current 参数而不是 pageCode
  }).then((res) => {
    if (res.flag == true) {
      pageGoods.value = res.data.list;
      emit("item-click", pageGoods.value);
    }
  });
}

</script>

<style lang="less" scoped>
.pagination {
  box-sizing: border-box;
  padding-left: 600px;
  width: 1500px;
  margin-top: 50px;
  // position: absolute;
  // left: 50%;
  // transform: translateX(-50%);
  bottom: 50px;
  .el-pagination {
    width: 40px;
    height: 40px;
  }
}
</style>