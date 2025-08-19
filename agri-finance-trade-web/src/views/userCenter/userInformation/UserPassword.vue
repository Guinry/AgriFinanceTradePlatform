<template>
  <div class="user-password">
    <div class="password-header">
      <h2>修改密码</h2>
      <p>为了账户安全，请定期更换密码</p>
    </div>

    <el-card class="password-card">
      <el-form
          :model="ruleForm"
          ref="ruleForm"
          label-width="150px"
          class="demo-ruleForm"
      >
        <el-form-item label="原密码：" prop="oldPassword">
          <el-input
              v-model="ruleForm.oldPassword"
              style="width: 300px"
              show-password
              placeholder="请输入原密码"
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码：" prop="newPassword">
          <el-input
              v-model="ruleForm.newPassword"
              style="width:300px"
              show-password
              placeholder="请输入新密码"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认新密码：" prop="confirmNewPassword">
          <el-input
              v-model="ruleForm.confirmNewPassword"
              style="width: 300px"
              show-password
              placeholder="请再次输入新密码"
          ></el-input>
        </el-form-item>
      </el-form>

      <div class="password-tips">
        <p>密码设置建议：</p>
        <ul>
          <li>长度为8~20个字符</li>
          <li>至少包含字母、数字、符号中的两种</li>
          <li>不建议使用生日、姓名等易被猜测的信息</li>
        </ul>
      </div>

      <el-button
          class="change-password-btn"
          type="success"
          round
          @click="changePasswordClick"
      >修改密码</el-button>
    </el-card>
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
.password-header {
  margin-bottom: 20px;

  h2 {
    margin: 0 0 10px 0;
    font-size: 24px;
    color: #333;
    font-weight: 600;
  }

  p {
    margin: 0;
    color: #666;
    font-size: 14px;
  }
}

.password-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

  :deep(.el-card__body) {
    padding: 30px;
  }
}

:deep(.el-form-item__label) {
  color: #000000;
  font-family: "PingFang SC";
  font-size: 16px;
  font-weight: 500;
}

.password-tips {
  background-color: #f5f7fa;
  border-radius: 4px;
  padding: 15px;
  margin: 20px 0;

  p {
    margin: 0 0 10px 0;
    font-weight: 600;
    color: #666;
  }

  ul {
    margin: 0;
    padding-left: 20px;

    li {
      color: #999;
      font-size: 13px;
      margin-bottom: 5px;

      &:last-child {
        margin-bottom: 0;
      }
    }
  }
}

.change-password-btn {
  display: block;
  margin: 20px auto 0;
  padding: 12px 40px;
  font-size: 16px;
  font-weight: 500;
}
</style>