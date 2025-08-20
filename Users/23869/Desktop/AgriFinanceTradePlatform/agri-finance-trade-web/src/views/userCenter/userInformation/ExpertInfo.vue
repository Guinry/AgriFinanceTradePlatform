<template>
  <div class="expert-info-container">
    <el-form label-width="80px" :model="form">
      <el-form-item label="真实姓名">
        <el-input v-model="form.realName" style="width:290px;" placeholder="请输入真实姓名"></el-input>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="form.phone" style="width:290px;" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item label="从事专业">
        <el-input v-model="form.profession" style="width:290px;" placeholder="请输入从事专业"></el-input>
      </el-form-item>
      <el-form-item label="职位">
        <el-input v-model="form.position" style="width:290px;" placeholder="请输入职位"></el-input>
      </el-form-item>
      <el-form-item label="所属单位">
        <el-input v-model="form.belong" style="width:290px;" placeholder="请输入所属单位"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align:center;margin-right:130px;">
    </div>
  </div>
</template>

<script setup>
</script>

<style lang="less" scoped>
.expert-info-container{
  width: 900px;
  min-height: 500px;
  background: #fff;
  padding: 30px 40px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  
  .el-form {
    margin-top: 20px;
    
    .el-form-item {
      margin-bottom: 25px;
      
      :deep(.el-form-item__label) {
        font-weight: 500;
        color: #333;
      }
      
      .el-input {
        box-shadow: 0 1px 4px rgba(0,0,0,0.1);
        border-radius: 4px;
      }
    }
  }
  
  .submit-btn {
    margin-top: 30px;
    padding: 12px 40px;
    font-size: 16px;
    border-radius: 20px;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    transition: all 0.3s ease;
    
    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0,0,0,0.15);
    }
  }
}

// 添加响应式设计
@media (max-width: 768px) {
  .expert-info-container {
    width: 95%;
    padding: 20px;
    margin: 0 auto;
  }
}
</style>