<template>
  <div class="user-password">
    <el-form
      :model="ruleForm"
      ref="ruleForm"
      label-width="150px"
      class="demo-ruleForm"
    >
      <el-form-item label="原密码：" prop="name">
        <el-input
          v-model="ruleForm.oldPassword"
          style="width: 300px" show-password
        ></el-input>
      </el-form-item>
      <el-form-item label="新密码：" prop="name">
        <el-input
          v-model="ruleForm.newPassword"
          style="width:300px" show-password
        ></el-input>
      </el-form-item>
      <el-form-item label="确认新密码：" prop="name">
        <el-input
          v-model="ruleForm.confirmNewPassword"
          style="width: 300px" show-password
        ></el-input>
      </el-form-item>
    </el-form>
    <el-button
      style="margin-left: 300px"
      type="success" round plain
      @click="changePasswordClick"
      >修改密码</el-button
    >
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { updateUserPassword } from "../../../api/user";

const store = useStore()
const router = useRouter()
const ruleForm = reactive({
  oldPassword: "",
  newPassword: "",
  confirmNewPassword: "",
})
const ruleFormRef = ref(null)

const changePasswordClick = () => {
  if (ruleForm.newPassword === ruleForm.confirmNewPassword) {
    if (ruleForm.newPassword === "") {
      ElMessage.warning("新密码不能为空")
    }
    updateUserPassword({
      oldPassword: ruleForm.oldPassword,
      newPassword: ruleForm.newPassword,
    }).then((res) => {
      if (res.flag == true) {
        ElMessage.success(res.message)
        //修改密码后退出登录
        store.commit("updateLoginUserNickname", "")
        store.commit("removeStorage")
        router.push("/home").catch((err) => err)
        if (sessionStorage.getItem("/order/needs/pageCode")) {
          sessionStorage.removeItem("/order/needs/pageCode")
        }
        if (sessionStorage.getItem("/order/goods/pageCode")) {
          sessionStorage.removeItem("/order/goods/pageCode")
        }
        if (sessionStorage.getItem("/user/search/pageCode")) {
          sessionStorage.removeItem("/user/search/pageCode")
        }
      } else {
        ElMessage.error(res.data)
      }
    })
  } else {
    ElMessage.warning("新密码与确认密码不一致")
  }
}

import { onMounted } from 'vue'

onMounted(() => {
  store.commit("updateUserActiveIndex", "1-3")
})
</script>

<style lang="less" scoped>
.user-password {
  width: 900px;
  float: left;
  padding: 20px;
  background: #fff;
  height: 100%;
}


:deep(.el-form-item__label){
  color: #000000;
  font-family:"PingFang SC";
  font-size: 16px;
  font-weight:100;
}
</style>
