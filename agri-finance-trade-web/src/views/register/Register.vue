<template>
  <div class="login">
    <div class="loginPart">
      <img :src="icon" id="icon" alt="logo" />
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleFormRef" label-width="70px" class="demo-ruleForm">
        <h2>用户注册</h2>
        <el-form-item label="账号" prop="username">
          <el-input placeholder="请输入账号" v-model="ruleForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input placeholder="请输入昵称" v-model="ruleForm.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input placeholder="请输入密码" type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input placeholder="请再次输入密码" type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>

        <!-- 修改按钮容器 -->
        <el-form-item class="form-actions">
          <el-button round class="submit-btn" @click="register(ruleFormRef)">注册</el-button>
          <el-button round class="reset-btn" @click="resetForm(ruleFormRef)">重置</el-button>
        </el-form-item>

        <div class="register-link">
          <el-link type="warning" @click="$router.push('/login')">已有账号？去登录</el-link>
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
import { userRegister } from "../../api/user";

const router = useRouter()
const store = useStore()
const ruleFormRef = ref()

const ruleForm = reactive({
  username: '',
  password: '',
  checkPass: '',
  nickname: '',
})

const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'));
  } else {
    callback();
  }
};

const validateNickname = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入昵称'));
  } else {
    callback();
  }
};

const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'));
  } else {
    if (ruleForm.checkPass !== '') {
      ruleFormRef.value.validateField('checkPass');
    }
    callback();
  }
};

const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'));
  } else if (value !== ruleForm.password) {
    callback(new Error('两次输入密码不一致!'));
  } else {
    callback();
  }
};

const rules = {
  password: [
    { validator: validatePass, trigger: 'blur' }
  ],
  checkPass: [
    { validator: validatePass2, trigger: 'blur' }
  ],
  username: [
    { validator: validateUsername, trigger: 'blur' }
  ],
  nickname: [
    { validator: validateNickname, trigger: 'blur' }
  ]
}

const resetForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields();
}

const register = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if (valid) {
      userRegister({
        userName: ruleForm.username,
        password: ruleForm.password,
        nickName: ruleForm.nickname,
        avatar: "rongxiaotong.gif",
      }).then((res) => {
        console.log(res);
        if (res.flag) {
          ElMessage.success(res.message);
          store.commit("updateLoginUserNickname", ruleForm.nickname);
          router.push("/login").catch((err) => err);
        } else {
          ElMessage.error(res.data);
        }
      }).catch((err) => {
        ElMessage.error(err.message || '注册失败');
      });
    } else {
      console.log('error submit!!');
      return false;
    }
  });
}
</script>

<style lang="less" scoped>
@import url("../../assets/bootstrap.css");

#icon {
  position: absolute;
  width: 100px;
  top: 0%;
  left: 50%;
  transform: translate(-50%, -50%);
}

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

    /* 按钮布局 */
    .form-actions {
      display: flex;
      justify-content: space-between; /* 按钮左右分布 */
      gap: 10px; /* 添加按钮之间的间距 */
      margin-top: 20px;
    }

    .el-button {
      border-radius: 25px;
      font-size: 16px;
      font-weight: 600;
      width: 48%; /* 保证按钮宽度适中 */
      flex: 1; /* 使按钮能够自适应并排显示 */
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
      text-align: right;
      color: white;
      margin-top: 15px;
      font-size: 14px;
    }
  }
}
</style>