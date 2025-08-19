<template>
  <NavigationBar />
  <div class="appointment-container">
    <el-form :model="ruleForm" :rules="rules" ref="ruleFormRef" label-width="150px" class="form-container">
      <el-form-item label="地址" prop="address">
        <el-input v-model="ruleForm.address" placeholder="请输入地址" class="input-field"></el-input>
      </el-form-item>
      <el-form-item label="种植作物" prop="plantName">
        <el-input v-model="ruleForm.plantName" placeholder="请输入种植的作物" class="input-field"></el-input>
      </el-form-item>
      <el-form-item label="作物详细信息" prop="plantDetail">
        <el-input v-model="ruleForm.plantDetail" placeholder="请输入作物详细信息" class="input-field"></el-input>
      </el-form-item>
      <el-form-item label="作物条件" prop="plantCondition">
        <el-input v-model="ruleForm.plantCondition" placeholder="请输入作物生长状况" class="input-field"></el-input>
      </el-form-item>
      <el-form-item label="土壤条件" prop="soilCondition">
        <el-input v-model="ruleForm.soilCondition" placeholder="请输入土壤状况" class="input-field"></el-input>
      </el-form-item>
      <el-form-item label="面积" prop="area">
        <el-input v-model="ruleForm.area" placeholder="请输入面积" class="input-field"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="ruleForm.phone" placeholder="请输入电话" class="input-field"></el-input>
      </el-form-item>

      <el-form-item class="form-buttons">
        <el-button type="success" class="submit-btn" @click="onSubmit">去预约</el-button>
      </el-form-item>
    </el-form>
  </div>
  <Footer />
</template>

<script setup>
import {ref, reactive, onMounted} from 'vue'
import {useRouter, useRoute} from 'vue-router'
import {ElMessage} from 'element-plus'
import {addReserve} from '../../api/order'
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

const router = useRouter()
const route = useRoute()

const ruleFormRef = ref()

const ruleForm = reactive({
  phone: '',
  soilCondition: '',
  plantCondition: '',
  plantDetail: '',
  plantName: '',
  address: '',
  area: '',
  status: 0,
  expertName: route.query.id,
})

const rules = {
  phone: [{required: true, message: '请输入电话', trigger: 'blur'}],
  soilCondition: [{required: true, message: '请输入土壤状况', trigger: 'blur'}],
  plantCondition: [{required: true, message: '请输入作物生长状况', trigger: 'blur'}],
  plantDetail: [{required: true, message: '请输入作物详细信息', trigger: 'blur'}],
  plantName: [{required: true, message: '请输入种植的作物', trigger: 'blur'}],
  address: [{required: true, message: '请输入地址', trigger: 'blur'}],
  area: [{required: true, message: '请输入面积', trigger: 'blur'}],
}

const onSubmit = () => {
  if (localStorage.getItem('token')) {
    ruleFormRef.value.validate((valid) => {
      if (valid) {
        addReserve(ruleForm).then(res => {
          ElMessage.success('预约成功！')
          router.push("/home/guide").catch((err) => err);
        }).catch(err => {
          console.log(err)
        })
      } else {
        console.log('error submit!!');
        return false;
      }
    });
  } else {
    ElMessage.error('请先登录')
  }
}

onMounted(() => {
  // 如果需要使用store，可以这样引入：
  // const store = useStore()
  // store.commit("updateActiveIndex", "5");
})
</script>

<style lang="less" scoped>
.appointment-container {
  max-width: 900px;
  margin: 30px auto;
  padding: 30px;
  background: #f8f9fa;
  border-radius: 10px;
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.1);
}

.form-container {
  .el-form-item {
    margin-bottom: 20px;
  }

  .el-input__inner {
    border-radius: 10px;
    padding: 12px 15px;
    border: 1px solid #dcdfe6;
    font-size: 16px;
    transition: border-color 0.3s, box-shadow 0.3s;
  }

  .el-input__inner:focus {
    border-color: #409eff;
    box-shadow: 0 0 5px rgba(64, 158, 255, 0.5);
  }

  .input-field {
    font-size: 16px;
    color: #333;
  }
}

.form-buttons {
  display: flex;
  justify-content: flex-end;
}

.submit-btn {
  font-size: 16px;
  font-weight: bold;
  background-color: #409eff;
  color: white;
  padding: 12px 20px;
  border-radius: 6px;
  border: none;
  transition: background-color 0.3s, transform 0.3s ease-in-out;
}

.submit-btn:hover {
  background-color: #66b1ff;
  transform: translateY(-2px);
}

.submit-btn:focus {
  outline: none;
}

.el-form-item label {
  font-weight: 600;
  color: #333;
  font-size: 16px;
}
</style>
