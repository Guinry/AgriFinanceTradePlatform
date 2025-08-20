<template>
  <div class="user-info">
    <!-- 用户头像 -->
    <user-avatar ref="avatar" :cUserAvatar="userinfo.avatar"></user-avatar>

    <!-- 用户信息表单 -->
    <el-form :model="userinfo" ref="ruleForm" label-width="100px" class="form-container">
      <el-form-item label="昵称：" prop="nickName">
        <el-input v-model="userinfo.nickName" class="input-field"></el-input>
      </el-form-item>
      <el-form-item label="姓名：" prop="realName">
        <el-input v-model="userinfo.realName" class="input-field"></el-input>
      </el-form-item>
      <el-form-item label="手机号：" prop="phone">
        <el-input v-model="userinfo.phone" class="input-field"></el-input>
      </el-form-item>
      <el-form-item label="身份证：" prop="identityNum">
        <el-input v-model="userinfo.identityNum" class="input-field"></el-input>
      </el-form-item>
      <el-form-item label="地址：" prop="address">
        <el-input v-model="userinfo.address" class="input-field"></el-input>
      </el-form-item>
    </el-form>

    <!-- 修改按钮 -->
    <el-button type="success" @click="updateInfo" class="update-btn">修改</el-button>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
import { loginUpdateByUsername, loginSelectByUsername } from "../../../api/user";
import UserAvatar from "../../../components/UserAvatar.vue";

// 使用 store
const store = useStore()

// 响应式数据
const userinfo = reactive({
  avatar: "",
  nickName: "",
  realName: "",
  phone: "",
  identityNum: "",
  address: "",
})

const avatar = ref(null)
const ruleForm = ref(null)

// 方法定义
const updateInfo = () => {
  if (userinfo.nickName == "") {
    ElMessage.warning("昵称不能为空")
    return;
  }
  if (userinfo.realName == "") {
    ElMessage.warning("真实姓名不能为空")
    return;
  }

  // 获取头像数据
  userinfo.avatar = avatar.value?.cUserAvatar || userinfo.avatar

  loginUpdateByUsername({
    realName: userinfo.realName,
    nickName: userinfo.nickName,
    phone: userinfo.phone,
    identityNum: userinfo.identityNum,
    address: userinfo.address,
    avatar: userinfo.avatar,
  })
      .then((res) => {
        if (res.flag) {
          console.log('res', res)
          console.log('this.userinfo', userinfo)
          store.commit("removeStorage");
          store.commit("setToken", res.data);
          store.commit(
              "updateLoginUserNickname",
              userinfo.nickName
          );
          store.commit("updateLoginUserAvatar", userinfo.avatar);
          ElMessage.success(res.message);
        } else {
          ElMessage.error(res.data);
        }
      })
      .catch((err) => {
        ElMessage.error("更新失败");
      });
}

// 生命周期钩子
onMounted(() => {
  store.commit("updateUserActiveIndex", "1-1");
  loginSelectByUsername({}).then((res) => {
    Object.assign(userinfo, res.data);
    if (res.data.avatar != "") {
      // this.fileList;
    }
  });
})
</script>

<style lang="less" scoped>
.form-container {
  margin-top: 20px;
}

.input-field {
  width: 100%;
  max-width: 400px;
  margin-bottom: 20px;
  border-radius: 4px;
}

.update-btn {
  display: block;
  width: 150px;
  margin: 20px auto;
  background-color: #67C23A;
  border-radius: 4px;
  padding: 10px;
  font-size: 16px;
  transition: background-color 0.3s;
}

.update-btn:hover {
  background-color: #4e9c2e;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-form-item label {
  font-weight: 500;
  font-size: 16px;
  color: #333;
}

.el-form-item .el-input__inner {
  border-radius: 4px;
  height: 38px;
  font-size: 14px;
  color: #555;
}

.el-input:focus {
  border-color: #67C23A;
  box-shadow: 0 0 0 2px rgba(103, 194, 58, 0.2);
}

.el-button {
  font-size: 14px;
}
</style>
