<!--基本信息-->
<template>
  <div class="user-info">
    <user-avatar ref="avatar" :cUserAvatar="userinfo.avatar"></user-avatar>
    <el-form :model="userinfo" ref="ruleForm" label-width="80px" style="margin-top:40px">
      <el-form-item label="昵称：" prop="nickName">
        <el-input v-model="userinfo.nickName" style="width:300px"></el-input>
      </el-form-item>
      <el-form-item label="姓名：" prop="realName">
        <el-input v-model="userinfo.realName" style="width:300px"></el-input>
      </el-form-item>
      <el-form-item label="手机号：" prop="phone">
        <el-input v-model="userinfo.phone" style="width:300px"></el-input>
      </el-form-item>
      <el-form-item label="身份证：" prop="identityNum">
        <el-input v-model="userinfo.identityNum" style="width:300px"></el-input>
      </el-form-item>
      <el-form-item label="地址：" prop="address">
        <el-input v-model="userinfo.address" style="width:300px"></el-input>
      </el-form-item>
    </el-form>
    <el-button style="margin-left: 310px;" type="success" @click="updateInfo">修改</el-button>
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
      if (res.flag == true) {
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
.user-info {
  margin: 0px auto;
  width: 900px;
  float: left;
  padding-left: 20px;
  background: #fff;
  height: 100%;
  min-height: 600px;
}
</style>