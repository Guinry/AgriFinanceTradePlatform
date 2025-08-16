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
.pagination-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  margin-top: 50px;
}

.el-pagination {
  background: #fff; /* 背景色 */
  border-radius: 10px; /* 圆角 */
  padding: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); /* 阴影 */
}

.el-pagination .el-button {
  font-size: 14px; /* 字体大小 */
  color: #409EFF; /* 默认按钮颜色 */
  padding: 8px 15px;
  border-radius: 5px; /* 圆角 */
}

.el-pagination .el-button:hover {
  background-color: #409EFF;
  color: #fff;
}

.el-pagination .el-button.is-active {
  background-color: #409EFF;
  color: #fff;
  border-color: #409EFF;
}

.el-pagination .el-button.is-disabled {
  background-color: #f5f5f5;
  color: #c0c4cc;
  cursor: not-allowed;
}

.el-pagination .el-button.prev,
.el-pagination .el-button.next {
  font-weight: bold;
}

.el-pagination .el-pagination__pager {
  display: flex;
  gap: 10px;
}

.el-pagination .el-pagination__pager li {
  list-style: none;
}

.el-pagination .el-pagination__pager .active {
  background-color: #409EFF;
  color: #fff;
  border-radius: 50%;
}

.el-pagination .el-pagination__pager .prev {
  display: flex;
  align-items: center;
}

.el-pagination .el-pagination__pager .next {
  display: flex;
  align-items: center;
}
</style>
