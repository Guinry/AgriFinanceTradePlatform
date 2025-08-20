<template>
  <div class="delete-message">
    <div @click="dialogVisible = true" class="delete">删除</div>
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
import { deleteKnowledgeById } from "../api/knowledge";
import { ElMessage } from 'element-plus'

// 使用inject获取reload方法
const reload = inject("reload");

// 响应式数据
const dialogVisible = ref(false);

// 方法定义
const deleteMessageClick = () => {
  dialogVisible.value = false;
  deleteKnowledgeById({
    knowledgeId: localStorage.getItem('changedKnowledgeId'),
  })
    .then((res) => {
      reload();
      ElMessage.success("删除成功");
    })
    .catch((err) => {
      ElMessage.error("删除失败");
    });
};
</script>

<style lang="less" scoped>
.delete-message {
  .delete {
    height: 25px;
    cursor: pointer;
    margin-right: 10px;
    color: #67C23A;
    &:hover{
      color: #035D1C;
    }
  }
}
</style>