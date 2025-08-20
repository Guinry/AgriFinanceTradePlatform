<template>
  <div class="user-manage-container">
    <el-card class="user-manage-card">
      <template #header>
        <div class="card-header">
          <span class="card-title">用户管理</span>
          <el-button type="primary" @click="refreshData">
            <el-icon><Refresh /></el-icon>
            刷新
          </el-button>
        </div>
      </template>
      
      <el-table
        ref="multipleTableRef"
        tooltip-effect="dark"
        style="width: 100%"
        :data="userData"
        border
        stripe
        highlight-current-row
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column prop="userName" label="用户名" min-width="120">
          <template #default="scope">
            <el-tag type="primary">{{ scope.row.userName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="nickName" label="昵称" min-width="120">
        </el-table-column>
        <el-table-column
          prop="identityNum"
          label="身份证"
          width="180"
          show-overflow-tooltip
        >
        </el-table-column>
        <el-table-column prop="role" label="角色" width="140">
          <template #default="scope">
            <el-tag :type="scope.row.role === '管理员' ? 'danger' : 'success'">
              {{ scope.row.role }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="电话" width="120"> </el-table-column>
        <el-table-column prop="address" label="地址" show-overflow-tooltip min-width="150">
        </el-table-column>
        <el-table-column label="创建日期" width="120" align="center">
          <template #default="scope">{{
            scope.row.createTime ? scope.row.createTime.slice(0, 10) : '-'
          }}</template>
        </el-table-column>
        <el-table-column label="修改日期" width="120" align="center">
          <template #default="scope">{{ 
            scope.row.updateTime ? scope.row.updateTime.slice(0, 10) : '-' 
          }}</template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right" align="center">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button type="primary" :icon="Edit" circle
                @click="managerChangeInfo(scope.row.userName)"></el-button>
              <el-button type="danger" :icon="Delete" circle
                @click="deleteUserNameClick(scope.row.userName)"></el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <el-dialog v-model="dialogVisible" title="删除确认" width="400px" center>
      <div class="delete-confirm">
        <el-icon class="delete-icon"><Warning /></el-icon>
        <span class="confirm-text">确认删除用户 "{{ deleteUserName }}" 吗？</span>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="danger" @click="managerDeleteUser(deleteUserName)">确 定</el-button>
        </span>
      </template>
    </el-dialog>
    
    <el-dialog
      v-model="dialogFormVisible"
      title="编辑用户信息"
      class="manager-change-user-info"
      width="500px"
    >
      <el-form class="user-info" label-position="right" label-width="100px">
        <el-form-item
          label="用户名"
          class="manager-change-name"
        >
          <el-input
            autocomplete="off"
            v-model="updateUserData.userName"
            disabled
          >
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item
          label="昵称"
          class="manager-change-phone"
        >
          <el-input
            autocomplete="off"
            v-model="updateUserData.nickName"
          >
            <template #prefix>
              <el-icon><UserFilled /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item
          label="身份证号"
          class="manager-change-phone"
        >
          <el-input
            autocomplete="off"
            v-model="updateUserData.identityNum"
          >
            <template #prefix>
              <el-icon><Document /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item
          label="角色"
          class="manager-change-role"
        >
          <el-select 
            v-model="updateUserData.role" 
            placeholder="请选择角色"
            style="width: 100%"
          >
            <el-option label="普通用户" value="普通用户"></el-option>
            <el-option label="管理员" value="admin"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="电话"
          class="manager-change-phone"
        >
          <el-input
            autocomplete="off"
            v-model="updateUserData.phone"
          >
            <template #prefix>
              <el-icon><Phone /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item
          label="地址"
          class="manager-change-address"
        >
          <el-input
            autocomplete="off"
            v-model="updateUserData.address"
            type="textarea"
            :rows="2"
          >
            <template #prefix>
              <el-icon><Location /></el-icon>
            </template>
          </el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitCHangeInfo">确 定</el-button>
        </div>
      </template>
    </el-dialog>
    
    <div class="pagination-container">
      <pagination
        :c-url="url"
        :c-total="total"
        :c-page-size="pageSize"
        @item-click="pageClick"
      ></pagination>
    </div>
  </div>
</template>

<script setup>
import {
  selectUserByUsername,
  deleteUserByUsername,
  updateUserByUsername,
  selectAllUserPage,
} from "../../../api/user";
import Pagination from "../../../components/Pagination.vue";
import { ref, onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { Edit, Delete, Refresh, Warning, User, UserFilled, Document, Phone, Location } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const multipleTableRef = ref()
const store = useStore()

const userData = ref([])
const dialogVisible = ref(false)
const dialogFormVisible = ref(false)
const formLabelWidth = ref("120px")
const deleteUserName = ref("")
const total = ref(0)
const pageSize = ref(9)
const url = ref("/user/search/")
const usersCount = sessionStorage.getItem("/user/search/pageCode")
  ? sessionStorage.getItem("/user/search/pageCode")
  : 1

const updateUserData = reactive({})

const pageClick = (item) => {
  userData.value = item;
}

const refreshData = () => {
  selectAllUserPage({
    pageNum: usersCount,
  }).then((res) => {
    userData.value = res.data.list;
    total.value = res.data.total;
    ElMessage.success('数据刷新成功');
  })
  .catch((err) => {
    ElMessage.error('数据刷新失败');
    console.log(err);
  });
}

const managerChangeInfo = (userName) => {
  dialogFormVisible.value = true;
  selectUserByUsername({
    user_name: userName,
  }).then((res) => {
    Object.assign(updateUserData, res.data);
  });
}

const deleteUserNameClick = (userName) => {
  deleteUserName.value = userName;
  dialogVisible.value = true;
}

const managerDeleteUser = (userName) => {
  dialogVisible.value = false;
  deleteUserByUsername({
    user_name: userName,
  }).then((res) => {
    if (res.flag == true) {
      ElMessage.success(res.message);
      refreshData();
    } else {
      ElMessage.error(res.data);
    }
  });
}

const submitCHangeInfo = () => {
  dialogFormVisible.value = false;
  updateUserByUsername({
    user_name: updateUserData.userName,
    nickName: updateUserData.nickName,
    identityNum: updateUserData.identityNum,
    phone: updateUserData.phone,
    address: updateUserData.address,
    role: updateUserData.role,
  }).then((res) => {
      if (res.flag == true) {
        ElMessage.success(res.message);
        refreshData();
      } else {
        ElMessage.error(res.data);
      }
    })
    .catch((err) => {
      ElMessage.error("修改失败");
    });
}

onMounted(() => {
  store.commit("updateUserActiveIndex", "5");
    selectAllUserPage({
      pageNum: usersCount,
    }).then((res) => {
      userData.value = res.data.list;
      total.value = res.data.total;
    })
    .catch((err) => {
      console.log(err);
    });
})
</script>

<style lang="less" scoped>
.user-manage-container {
  width: 100%;
  padding: 20px;
  min-height: calc(100vh - 100px);

  .user-manage-card {
    border-radius: 8px;
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      
      .card-title {
        font-size: 18px;
        font-weight: bold;
        color: #303133;
      }
    }
  }
  
  .operation-buttons {
    display: flex;
    justify-content: center;
    gap: 10px;
  }
  
  .delete-confirm {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px 0;
    
    .delete-icon {
      font-size: 48px;
      color: #f56c6c;
      margin-bottom: 15px;
    }
    
    .confirm-text {
      font-size: 16px;
      color: #606266;
    }
  }
  
  .manager-change-user-info {
    .user-info {
      padding: 0 20px;
      
      :deep(.el-input__prefix) {
        display: flex;
        align-items: center;
      }
    }
  }
  
  .pagination-container {
    display: flex;
    justify-content: center;
    margin-top: 0;
    margin-bottom: 0;
    border-radius: 4px;
  }
}
</style>