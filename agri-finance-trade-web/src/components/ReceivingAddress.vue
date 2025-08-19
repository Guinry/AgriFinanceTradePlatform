<template>
  <div class="receiving-address">
    <div class="title">我的收货地址</div>
    <div class="default-address-container">
      <div>
        <!-- 添加 v-if 判断确保 addressData 存在 -->
        <div class="address-item" v-if="addressData && addressData.consignee">收货人：{{ addressData.consignee }}</div>
        <div class="address-item" v-if="addressData && addressData.addressDetail">收货地址：{{ addressData.addressDetail }}</div>
        <div class="address-item" v-if="addressData && addressData.phone">收货手机号：{{ addressData.phone }}</div>
      </div>

      <div class="address-actions">
        <el-button type="success" @click="handleChange" style="margin-right: 10px;">更换地址</el-button>
        <el-button type="primary" @click="handleAdd">新增地址</el-button>
      </div>
    </div>
    <el-dialog v-model="dialogVisible" title="更换地址" width="580px" :before-close="handleClose">
      <el-table :data="allAddressData" tooltip-effect="dark" style="width: 100%" @select="handleSelectionChange"
                ref="listTableRef">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="收货人" prop="consignee" width="120" show-overflow-tooltip></el-table-column>
        <el-table-column prop="addressDetail" label="收获地址" show-overflow-tooltip></el-table-column>
        <el-table-column prop="phone" label="收获手机号" width="120" show-overflow-tooltip></el-table-column>
      </el-table>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleClose">取 消</el-button>
          <el-button type="success" @click="submitChange">确 定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 新增地址对话框 -->
    <el-dialog v-model="addDialogVisible" title="新增地址" width="580px" :before-close="handleAddClose">
      <el-form :model="addressForm" label-width="100px">
        <el-form-item label="收货人">
          <el-input v-model="addressForm.consignee" placeholder="请输入收货人姓名"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="addressForm.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="收货地址">
          <el-input v-model="addressForm.addressDetail" placeholder="请输入详细地址"></el-input>
        </el-form-item>
        <el-form-item label="设为默认">
          <el-switch v-model="addressForm.isDefault"></el-switch>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleAddClose">取 消</el-button>
          <el-button type="primary" @click="submitAdd">确 定</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- <el-checkbox
      v-model="addressData.isDefault"
      @change="changeChecked"
      class="default-address">设为默认地址</el-checkbox>
    <el-button type="primary" class="update-info" @click="updateAddress">确认收货地址</el-button> -->
  </div>
</template>

<script setup>
import {
  defaultAddressInfoUpdate,
  selectDefaultByOwnName,
  selectAllAddressByUsername,
  addAddress
} from "../api/address";
import { ref, onMounted } from 'vue'

// 初始化 addressData 为一个空对象而不是数组
const addressData = ref({})
const allAddressData = ref([])
const dialogVisible = ref(false)
const addDialogVisible = ref(false) // 新增地址对话框可见性
const listTableRef = ref(null)

// 新增地址表单数据
const addressForm = ref({
  consignee: '',
  phone: '',
  addressDetail: '',
  isDefault: false
})

const changeChecked = (e) => {
  console.log(e);
  addressData.value.isDefault = e;
}

const getAllAdress = () => {
  selectAllAddressByUsername().then(res => {
    allAddressData.value = res.data
  }).catch(err => {
    console.log(err)
  })
}

const handleChange = () => {
  dialogVisible.value = true
}

const handleAdd = () => {
  // 重置表单数据
  addressForm.value = {
    consignee: '',
    phone: '',
    addressDetail: '',
    isDefault: false
  }
  addDialogVisible.value = true
}

const submitChange = () => {
  dialogVisible.value = false
}

const handleClose = () => {
  dialogVisible.value = false
}

const handleAddClose = () => {
  addDialogVisible.value = false
}

const submitAdd = () => {
  // 这里应该调用新增地址的API
  console.log('新增地址:', addressForm.value)
  addAddress(addressForm.value).then(() => {
    getAllAdress() // 重新加载地址列表
    handleAddClose() // 关闭对话框
  })
  handleAddClose()
}

const handleSelectionChange = (row, column) => {
  //只能选择一行，选择其他，清除上一行
  if (row.length > 1) {
    let del_row = row.shift()
    listTableRef.value.toggleRowSelection(del_row, false) //设置这一行取消选中
    addressData.value = Object.assign({}, { ...row[0] })
  } else {
    addressData.value = Object.assign({}, { ...row[0] })
  }
}

onMounted(() => {
  getAllAdress()
  selectDefaultByOwnName({})
      .then((res) => {
        // 确保即使返回数据为 null 也初始化为对象
        addressData.value = res.data || {};
      })
      .catch((err) => {
        console.log(err);
        // 出错时也确保 addressData 是一个对象
        addressData.value = {};
      });
})
</script>

<style lang="less" scoped>
.receiving-address {
  position: relative;

  .title {
    border-bottom: 2px solid #f2f2f2;
    padding: 10px 0px;
    font-size: 18px;
  }
}

.default-address-container {
  border: 1px dashed darkseagreen;
  border-radius: 6px;
  padding: 10px 20px;
  display: flex;
  margin-top: 20px;
  align-items: center;
  justify-content: space-between;

  .address-item {
    line-height: 30px;
    height: 30px;
    margin-right: 50px;
  }

  .address-actions {
    display: flex;
  }
}
</style>