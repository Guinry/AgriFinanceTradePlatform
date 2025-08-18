<template>
  <div class="login">
    <div class="loginPart">
      <img :src="icon" id="icon" alt="logo" />
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleFormRef" label-width="50px" class="demo-ruleForm">
        <h2>用户登录</h2>
        <el-form-item label="账号" prop="username">
          <el-input v-model="ruleForm.username" placeholder="请输入账号" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" placeholder="请输入密码" v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button round class="submit-btn" @click="submitForm(ruleFormRef)">登录</el-button>
          <el-button round class="reset-btn" @click="resetForm(ruleFormRef)">重置</el-button>
        </el-form-item>
        <div class="register-link">
          <el-link type="warning" @click="$router.push('/register')">没有账号？去注册</el-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
import icon from '../../assets/img/yellowlogo.png'

import { userLogin } from "../../api/user";

const router = useRouter()
const store = useStore()
const ruleFormRef = ref()

const ruleForm = reactive({
  username: '',
  password: '',
})

const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'));
  } else {
    callback();
  }
};

const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'));
  } else {
    callback();
  }
};

const rules = {
  password: [{ validator: validatePass, trigger: 'blur' }],
  username: [{ validator: validateUsername, trigger: 'blur' }],
}

const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if (valid) {
      userLogin({
        username: ruleForm.username,
        password: ruleForm.password,
      }).then((res) => {
        if (res.flag) {
          store.commit("setToken", res.data);
          router.push("/home").catch((err) => err);
        } else {
          ElMessage.error(res.message);
        }
      }).catch((err) => {
        console.log(err);
      });
    } else {
      console.log('error submit!!');
      return false;
    }
  });
}

const resetForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>

<style lang="less" scoped>
@import url("../../assets/bootstrap.css");

.login {
  box-sizing: border-box;
  height: 100vh;
  background: url("../../assets/img/Login.jpg") no-repeat center center;
  background-size: cover;
  padding-top: 150px;

  .loginPart {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 400px;
    padding: 30px;
    background: rgba(0, 0, 0, 0.7);
    border-radius: 15px;
    box-shadow: 0px 0px 20px rgb(26, 26, 26);

    h2 {
      text-align: center;
      color: #fff;
      font-size: 26px;
      font-weight: bold;
      margin-bottom: 20px;
    }

    .el-form-item {
      margin-bottom: 20px;
    }

    .el-input__inner {
      border-radius: 10px;
      border: 1px solid #dba155;
      background-color: rgba(255, 255, 255, 0.2);
      color: white;
      font-size: 16px;
    }

    .el-button {
      border-radius: 25px;
      font-size: 16px;
      font-weight: 600;
      width: 48%;
      margin: 10px 1%;
    }

    .submit-btn {
      background-color: #dba155;
      color: white;
      border: 1px solid #dba155;
    }

    .reset-btn {
      background-color: rgba(255, 255, 255, 0.2);
      color: #dba155;
      border: 1px solid #dba155;
    }

    .submit-btn:hover {
      background-color: #ffc377;
    }

    .reset-btn:hover {
      background-color: rgba(255, 255, 255, 0.5);
    }

    .register-link {
      text-align: left;
      color: white;
      margin-top: 15px;
      font-size: 14px;
    }
  }

  #icon {
    position: absolute;
    width: 100px;
    top: 0%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
}
</style>
