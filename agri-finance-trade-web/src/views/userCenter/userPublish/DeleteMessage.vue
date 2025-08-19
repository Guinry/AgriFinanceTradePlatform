<template>
  <div class="delete-message">
    <div class="delete-text" @click="dialogVisible = true">删除</div>

    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <span>确认删除该商品？</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="success" @click="deleteMessageClick">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue'
import { deleteOrderById } from "../../../api/order";
import { ElMessage } from 'element-plus'
import { useStore } from 'vuex'

// 使用 store
const store = useStore()

// 响应式数据
const dialogVisible = ref(false)

// 注入 reload 方法
const reload = inject("reload")

// 方法定义
const deleteMessageClick = () => {
  deleteOrderById({
    order_id: store.state.changedOrderId,
  })
    .then((res) => {
      reload();
      ElMessage.success("删除成功");
    })
    .catch((err) => {
      ElMessage.error("删除失败");
    });
}
</script>

<style lang="less" scoped>
.delete-message {
  .delete-text {
    height: 25px;
    cursor: pointer;
    color: #67C23A;
    &:hover{
      color: #035D1C;
    }
  }
}
</style>