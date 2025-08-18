<template>
  <div class="pagination-container">
    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="cPageSize"
        :total="cTotal"
        :current-page="pageCode"
        @current-change="currentChange"
        v-if="cTotal !== 0"
    >
    </el-pagination>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
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

// 监听 props 变化
watch(() => props.cUrl, (newVal, oldVal) => {
  if (newVal !== oldVal) {
    pageCode.value = parseInt(sessionStorage.getItem(newVal + "pageCode")) || 1;
  }
})

// 方法定义
const currentChange = (current) => {
  pageCode.value = current;
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
.pagination-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  margin: 30px 0;
  padding: 20px 0;
}

.el-pagination {
  background: #fff; /* 背景色 */
  border-radius: 10px; /* 圆角 */
  padding: 15px 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1); /* 阴影 */
  display: flex;
  align-items: center;
}

:deep(.el-pagination .btn-prev),
:deep(.el-pagination .btn-next),
:deep(.el-pagination .el-pager li) {
  min-width: 36px;
  height: 36px;
  line-height: 36px;
  border-radius: 50%;
  margin: 0 5px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.3s;
}

:deep(.el-pagination .btn-prev:hover),
:deep(.el-pagination .btn-next:hover),
:deep(.el-pagination .el-pager li:hover) {
  color: #ff6b35;
  background-color: #fff5f0;
}

:deep(.el-pagination .el-pager li.active) {
  background-color: #ff6b35;
  color: #fff;
  border-radius: 50%;
}

:deep(.el-pagination .el-pager li.disabled) {
  color: #c0c4cc;
}

:deep(.el-pagination .el-pagination__total) {
  margin-right: 20px;
  color: #666;
}
</style>