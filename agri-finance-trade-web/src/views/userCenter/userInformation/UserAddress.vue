<template>
  <div class="add-address">
    <div class="title">我的收货地址</div>
    <el-button type="success" round plain style="margin-top:10px;" @click="handleAdd">添加</el-button>
    <div v-for="(item,index) in allAddressData" :key="index"  class="default-address-container">
      <div>
        <div class="address-item">收货人：{{item.consignee}}</div>
        <div class="address-item">收货地址：{{item.addressDetail}}</div>
        <div class="address-item">收货手机号：{{item.phone}}</div>
      </div>
      <div class="right-btn">
        <el-tag style="position:relative;top:-40px;right:-138px;margin-right: 20px;" v-if="item.isDefault" type="success">默认收货地址</el-tag>
        <!-- <div class="marginR20" @click="handleDetail(item)">详情</div>   -->
        <div class="marginR20" @click="handleEdit(item)">编辑</div>
        <div class="marginR20" @click="handleDel(item)">删除</div>
      </div>
    </div>
    <el-dialog title="更换地址" v-model="dialogVisible" width="580px" :before-close="handleClose">
      <el-table ref="multipleTable" :data="allAddressData" tooltip-effect="dark" style="width: 100%"
       @select="handleSelectionChange">
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
    <el-dialog :title="title" v-model="showAdd" width="580px" :before-close="closeAdd">
      <el-form :model="addressData" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="收货人：" prop="consignee">
          <el-input v-model="addressData.consignee"></el-input>
        </el-form-item>
        <el-form-item label="收货地址：" prop="addressDetail">
          <el-input v-model="addressData.addressDetail"></el-input>
        </el-form-item>
        <el-form-item label="收货手机号：" prop="phone">
          <el-input v-model="addressData.phone"></el-input>
        </el-form-item>
        <el-checkbox style="float:right;" v-model="addressData.isDefault">设为默认地址</el-checkbox>
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
const dialogVisible = ref(false);
const showAdd = ref(false);
const title = ref('添加地址');
const multipleTable = ref(null);
const ruleForm = ref(null);

// 方法定义
const changeChecked = (e) => {
  console.log(e);
  addressData.isDefault = e;
};

const getAllAdress = () => {
  selectAllAddressByUsername().then(res => {
    console.log("data--------------");
    console.log('ressss', res);
    allAddressData.value = res.data;
  }).catch(err => {
    console.log(err);
  });
};

const handleChange = () => {
  dialogVisible.value = true;
};

const submitChange = () => {
  dialogVisible.value = false;
};

const handleClose = () => {
  dialogVisible.value = false;
};

const handleSelectionChange = (row, column) => {
  Object.assign(addressData, ...row);
};

const handleAdd = () => {
  showAdd.value = true;
  title.value = '添加地址';
};

const closeAdd = () => {
  showAdd.value = false;
  Object.keys(addressData).forEach(key => {
    addressData[key] = "";
  });
};

const updateAddress = () => {
  if (addressData.consignee == "") {
    ElMessage.warning("收货人不能为空");
    return;
  } else if (addressData.phone == "") {
    ElMessage.warning("手机号不能为空");
    return;
  } else if (addressData.addressDetail == "") {
    ElMessage.warning("收货地址不能为空");
    return;
  }
  
  if(title.value === '编辑地址'){
    defaultAddressInfoUpdate({
      id: addressData.id,
      consignee: addressData.consignee,
      phone: addressData.phone,
      addressDetail: addressData.addressDetail,
      isDefault: addressData.isDefault,
    }).then((res) => {
      if (res.flag == true) {
        getAllAdress();
        ElMessage.success(res.message);
        showAdd.value = false;
      } else {
        showAdd.value = false;
        ElMessage.error(res.data);
      }
    });
  } else {
    addAddress({
      consignee: addressData.consignee,
      phone: addressData.phone,
      addressDetail: addressData.addressDetail,
      isDefault: addressData.isDefault,
    }).then((res) => {
      if (res.flag == true) {
        getAllAdress();
        ElMessage.success(res.message);
        showAdd.value = false;
      } else {
        showAdd.value = false;
        ElMessage.error(res.data);
      }
    });
  }
};

const handleDetail = (item) => {
  // 未实现功能
};

const handleEdit = (item) => {
  showAdd.value = true;
  title.value = '编辑地址';
  Object.assign(addressData, {...item});
};

const handleDel = (item) => {
  ElMessageBox.confirm('确认删除该信息?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
  }).then(() => {
    deleteAddressById({
      addressId: item.id
    }).then(res => {
      if(res.flag == true){
        getAllAdress();
        ElMessage.success('删除成功!');
      } else {
        ElMessage.error(res.message);
      }
    }).catch(err => {
      ElMessage.error('删除失败!');
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
.add-address {
  width: 900px;
  float: left;
  padding: 20px;
  background: #fff;
  min-height: 100%;
  .title {
    border-bottom: 1px solid #f2f2f2;
    padding: 10px 0px;
    font-size: 18px;
  }
}
.default-address-container{
  border: 1px dashed #ccc;
  border-radius: 6px;
  padding: 10px 20px;
  display: flex;
  margin-top: 20px;
  align-items: center;
  justify-content: space-between;
  .address-item{
    line-height: 30px;
    height: 30px;
    margin-right: 50px;
  }
  .right-btn{
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    .marginR20{
      margin-right: 20px;
      cursor: pointer;
      color: #67C23A;
      &:hover{
        color: #035D1C;
        text-decoration: underline;
      }
    }
  }
}
</style>