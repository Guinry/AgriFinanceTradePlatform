<template>
  <div class="add-address">
    <div class="header-section">
      <h2 class="title">我的收货地址</h2>
      <el-button type="success" round class="add-btn" @click="handleAdd">
        <el-icon><Plus /></el-icon>
        添加新地址
      </el-button>
    </div>

    <div class="address-list">
      <div v-for="(item, index) in allAddressData" :key="index" class="address-card">
        <div class="address-content">
          <div class="address-info">
            <div class="info-row">
              <el-tag v-if="item.isDefault" type="success" effect="dark" class="default-tag">默认地址</el-tag>
              <span class="consignee">{{ item.consignee }}</span>
              <span class="phone">{{ item.phone }}</span>
            </div>
            <div class="address-detail">
              <el-icon><Location /></el-icon>
              {{ item.addressDetail }}
            </div>
          </div>
          <!-- 编辑与删除按钮放在地址卡片的底部 -->
          <div class="address-actions">
            <el-button link type="primary" @click="handleEdit(item)">编辑</el-button>
            <el-button link type="danger" @click="handleDel(item)">删除</el-button>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加/编辑地址弹窗 -->
    <el-dialog
        :title="title"
        v-model="showAdd"
        width="580px"
        :before-close="closeAdd"
        class="address-dialog"
    >
      <el-form
          :model="addressData"
          ref="ruleForm"
          label-width="100px"
          class="address-form"
      >
        <el-form-item label="收货人：" prop="consignee" required>
          <el-input v-model="addressData.consignee" placeholder="请输入收货人姓名"></el-input>
        </el-form-item>
        <el-form-item label="手机号：" prop="phone" required>
          <el-input v-model="addressData.phone" placeholder="请输入收货人手机号"></el-input>
        </el-form-item>
        <el-form-item label="收货地址：" prop="addressDetail" required>
          <el-input
              v-model="addressData.addressDetail"
              type="textarea"
              :rows="3"
              placeholder="请输入详细收货地址"
          ></el-input>
        </el-form-item>
        <el-form-item label="设为默认：">
          <el-switch
              v-model="addressData.isDefault"
              active-text="设为默认地址"
          ></el-switch>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeAdd">取 消</el-button>
          <el-button type="success" @click="updateAddress">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Location } from '@element-plus/icons-vue'
import {
  defaultAddressInfoUpdate,
  selectDefaultByOwnName,
  addAddress,
  selectAllAddressByUsername,
  deleteAddressById
} from "../../../api/address";

// 响应式数据
const addressData = reactive({
  consignee: "",
  phone: '',
  addressDetail: '',
  isDefault: false
});

const allAddressData = ref([]);
const showAdd = ref(false);
const title = ref('添加地址');
const ruleForm = ref(null);

// 方法定义
const getAllAdress = () => {
  selectAllAddressByUsername().then(res => {
    allAddressData.value = res.data;
  }).catch(err => {
    console.log(err);
    ElMessage.error('获取地址列表失败');
  });
};

const handleAdd = () => {
  showAdd.value = true;
  title.value = '添加地址';
  // 重置表单数据
  Object.keys(addressData).forEach(key => {
    if (key === 'isDefault') {
      addressData[key] = false;
    } else {
      addressData[key] = "";
    }
  });
};

const closeAdd = () => {
  showAdd.value = false;
};

const updateAddress = () => {
  if (!addressData.consignee) {
    ElMessage.warning("收货人不能为空");
    return;
  } else if (!addressData.phone) {
    ElMessage.warning("手机号不能为空");
    return;
  } else if (!addressData.addressDetail) {
    ElMessage.warning("收货地址不能为空");
    return;
  }

  const addressPayload = {
    consignee: addressData.consignee,
    phone: addressData.phone,
    addressDetail: addressData.addressDetail,
    isDefault: addressData.isDefault,
  };

  const request = title.value === '编辑地址'
      ? defaultAddressInfoUpdate({ id: addressData.id, ...addressPayload })
      : addAddress(addressPayload);

  request.then((res) => {
    if (res.flag) {
      getAllAdress();
      ElMessage.success(res.message || (title.value === '编辑地址' ? '地址更新成功' : '地址添加成功'));
      showAdd.value = false;
    } else {
      ElMessage.error(res.message || '操作失败');
    }
  }).catch(() => {
    ElMessage.error('操作失败，请稍后重试');
  });
};

const handleEdit = (item) => {
  showAdd.value = true;
  title.value = '编辑地址';
  Object.assign(addressData, { ...item });
};

const handleDel = (item) => {
  ElMessageBox.confirm(
      `确定要删除收货人"${item.consignee}"的地址吗？`,
      '删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    deleteAddressById({ addressId: item.id })
        .then(res => {
          if(res.flag) {
            getAllAdress();
            ElMessage.success('删除成功!');
          } else {
            ElMessage.error(res.message || '删除失败');
          }
        })
        .catch(() => {
          ElMessage.error('删除失败，请稍后重试');
        });
  }).catch(() => {
    ElMessage.info('已取消删除');
  });
};

// 生命周期钩子
onMounted(() => {
  getAllAdress();
});
</script>

<style lang="less" scoped>
.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

  .title {
    margin: 0;
    font-size: 20px;
    font-weight: 600;
    color: #333;
  }

  .add-btn {
    padding: 12px 20px;
    font-size: 14px;
  }
}

.address-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 20px;
}

.address-card {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  position: relative;

  &:hover {
    box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.15);
    transform: translateY(-2px);
  }

  .address-content {
    padding: 20px;
    display: flex;
    flex-direction: column;
    height: 100%;
  }

  .address-info {
    flex: 1;
  }

  .info-row {
    display: flex;
    align-items: center;
    margin-bottom: 15px;

    .default-tag {
      margin-right: 10px;
    }

    .consignee {
      font-size: 16px;
      font-weight: 600;
      margin-right: 15px;
    }

    .phone {
      color: #666;
    }
  }

  .address-detail {
    display: flex;
    align-items: flex-start;
    color: #666;
    line-height: 1.5;

    i {
      margin-right: 8px;
      margin-top: 3px;
      color: #999;
    }
  }

  .address-actions {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
    padding-top: 15px;
    border-top: 1px solid #eee;

    :deep(.el-button) {
      margin-left: 15px;
    }
  }
}

.address-dialog {
  :deep(.el-dialog__header) {
    border-bottom: 1px solid #eee;
  }

  .address-form {
    padding: 20px 0;
  }

  :deep(.el-form-item) {
    margin-bottom: 22px;
  }

  :deep(.el-form-item__label) {
    font-weight: 500;
  }

  :deep(.el-textarea__inner) {
    resize: none;
  }
}

.dialog-footer {
  text-align: right;
}
</style>
