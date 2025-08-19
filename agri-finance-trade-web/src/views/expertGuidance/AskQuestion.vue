<template>
  <NavigationBar />
  <div class="question-container">
    <el-form ref="formRef" :model="form" :rules="ruleForm" label-width="150px" class="form-container">
      <el-form-item label="标题：" prop="title">
        <el-input v-model="form.title" placeholder="请输入问题的标题"></el-input>
      </el-form-item>
      <el-form-item label="内容：" prop="question">
        <el-input type="textarea" v-model="form.question" placeholder="请输入问题的详细描述"></el-input>
      </el-form-item>
      <el-form-item label="农作物名称：" prop="plantName">
        <el-input type="textarea" v-model="form.plantName" placeholder="请输入农作物名称"></el-input>
      </el-form-item>
      <el-form-item label="手机号：" prop="phone">
        <el-input type="text" v-model="form.phone" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item class="form-buttons">
        <el-button type="success" class="submit-btn" @click="onSubmit">去提问</el-button>
      </el-form-item>
    </el-form>
  </div>
  <Footer />
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { addQuestion } from "../../api/order";
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

const router = useRouter()
const route = useRoute()

const formRef = ref()

const form = reactive({
  title: '',
  plantName: '',
  phone: '',
  expertName: route.query.id,
  status: 0,
  question: ''
})

const ruleForm = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  plantName: [{ required: true, message: '请输入农作物名称', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur'}],
  question: [{required: true, message: '请输入内容', trigger: 'blur'}],
}

const onSubmit = () => {
  if (localStorage.getItem('token')) {
    formRef.value.validate((valid) => {
      if (valid) {
        addQuestion(form).then(res => {
          ElMessage.success('提问成功！')
          router.push("/home/guide").catch((err) => err);
        }).catch(err => {
          console.log(err)
        })
      } else {
        console.log('error submit!!');
        return false;
      }
    })
  } else {
    ElMessage.error('请先登录')
  }
}
</script>

<style lang="less" scoped>
.question-container {
  max-width: 800px;
  margin: 20px auto;
  padding: 30px;
  background-color: #f7f8fa;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.form-container {
  .el-form-item {
    margin-bottom: 20px;
  }

  .el-input__inner {
    border-radius: 8px;
    font-size: 16px;
    padding: 10px;
    border: 1px solid #dcdfe6;
    transition: border-color 0.3s;
  }

  .el-input__inner:focus {
    border-color: #409eff;
    box-shadow: 0 0 5px rgba(64, 158, 255, 0.5);
  }

  .el-textarea__inner {
    height: 120px;
    border-radius: 8px;
    padding: 10px;
    border: 1px solid #dcdfe6;
  }
}

.submit-btn {
  width: 100%;
  font-size: 16px;
  font-weight: bold;
  padding: 15px;
  border-radius: 6px;
  background-color: #409eff;
  color: white;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
  border: none;
}

.submit-btn:hover {
  background-color: #66b1ff;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
}

.submit-btn:focus {
  outline: none;
}

.form-buttons {
  display: flex;
  justify-content: flex-end;
}

.el-form-item label {
  color: #333;
  font-weight: 600;
  font-size: 16px;
}

</style>
