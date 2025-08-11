<template>
  <div class="appointment-container">
    <el-form :model="ruleForm" :rules="rules" ref="ruleFormRef" label-width="150px" class="demo-ruleForm">
      <el-form-item label="地址" prop="address">
        <el-input v-model="ruleForm.address" placeholder="请输入地址"></el-input>
      </el-form-item>
      <el-form-item label="种植作物" prop="plantName">
        <el-input v-model="ruleForm.plantName" placeholder="请输入种植的作物"></el-input>
      </el-form-item>
      <el-form-item label="作物详细信息" prop="plantDetail">
        <el-input v-model="ruleForm.plantDetail" placeholder="请输入作物详细信息"></el-input>
      </el-form-item>
      <el-form-item label="作物条件" prop="plantCondition">
        <el-input v-model="ruleForm.plantCondition" placeholder="请输入作物生长状况"></el-input>
      </el-form-item>
      <el-form-item label="土壤条件" prop="soilCondition">
        <el-input v-model="ruleForm.soilCondition" placeholder="请输入土壤状况"></el-input>
      </el-form-item>
      <el-form-item label="面积" prop="area">
        <el-input v-model="ruleForm.area" placeholder="请输入面积"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="ruleForm.phone" placeholder="请输入电话"></el-input>
      </el-form-item>

      <el-form-item style="float:right;">
        <el-button type="success" plain round @click="onSubmit">去预约</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { addReserve } from '../../api/order'

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
  phone: [{ required: true, message: '请输入电话', trigger: 'blur' }],
  soilCondition: [{ required: true, message: '请输入土壤状况', trigger: 'blur' }],
  plantCondition: [{ required: true, message: '请输入作物生长状况', trigger: 'blur' }],
  plantDetail: [{ required: true, message: '请输入作物详细信息', trigger: 'blur' }],
  plantName: [{ required: true, message: '请输入种植的作物', trigger: 'blur' }],
  address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
  area: [{ required: true, message: '请输入面积', trigger: 'blur' }],
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
  // 需要访问store时可以使用useStore()
  // const store = useStore()
  // store.commit("updateActiveIndex", "5");
})

</script>

<style lang="less" scoped>
.appointment-container{
  width: 1100px;
  height: 100%;
  background: #fff;
  margin: 30px auto;
  padding: 10px 20px;
}
</style>
