<template>
  <div class="finance-container">
    <div v-for="(item, index) in FinanceAllData" :key="index" class="finance-card">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span class="card-title">我的融资信息 {{ index + 1 }}</span>
            <div class="card-actions">
              <el-button
                type="warning"
                size="small"
                @click="updateFinanceShow(item.financeId)"
                plain
              >
                <i class="el-icon-edit"></i> 修改
              </el-button>
              <el-button
                type="danger"
                size="small"
                @click="deleteFinance(item.financeId)"
                plain
              >
                <i class="el-icon-delete"></i> 删除
              </el-button>
            </div>
          </div>
        </template>
        
        <el-descriptions
          class="margin-top"
          :column="2"
          :size="size"
          border
        >
          <el-descriptions-item>
            <template #label>
              <i class="el-icon-user"></i>
              姓名
            </template>
            {{ item.realName }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <i class="el-icon-mobile-phone"></i>
              手机号
            </template>
            {{ item.phone }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <i class="el-icon-tickets"></i>
              身份证号
            </template>
            {{ item.idNum }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template #label>
              <i class="el-icon-money"></i>
              融资金额
            </template>
            <span class="money-text">¥{{ item.money }}</span>
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <i class="el-icon-office-building"></i>
              银行
            </template>
            {{ getBankName(item.bankId) }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <i class="el-icon-s-release"></i>
              利率
            </template>
            {{ item.rate }}%
          </el-descriptions-item>

          <el-descriptions-item>
            <template #label>
              <i class="el-icon-warning-outline"></i>
              申请状态
            </template>
            <template>
              <el-tag type="warning" v-show="item.status === 0">申请中</el-tag>
              <el-tag type="danger" v-show="item.status === 1">驳回</el-tag>
              <el-tag type="success" v-show="item.status === 2">通过</el-tag>
            </template>
          </el-descriptions-item>

          <el-descriptions-item>
            <template #label>
              <i class="el-icon-user"></i>
              联合融资人1姓名
            </template>
            {{ item.combinationName1 || '无' }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <i class="el-icon-mobile-phone"></i>
              联合融资人1手机号
            </template>
            {{ item.combinationPhone1 || '无' }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <i class="el-icon-tickets"></i>
              联合融资人1身份证
            </template>
            {{ item.combinationIdnum1 || '无' }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template #label>
              <i class="el-icon-user"></i>
              联合融资人2姓名
            </template>
            {{ item.combinationName2 || '无' }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <i class="el-icon-mobile-phone"></i>
              联合融资人2手机号
            </template>
            {{ item.combinationPhone2 || '无' }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <i class="el-icon-tickets"></i>
              联合融资人2身份证
            </template>
            {{ item.combinationIdnum2 || '无' }}
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </div>

    <el-dialog
      title="修改融资信息"
      v-model="showUpdate"
      width="650px"
      :before-close="closeUpdate"
    >
      <el-form
        :model="FinanceData"
        ref="ruleForm"
        label-width="120px"
        class="demo-ruleForm"
      >
        <el-divider content-position="left">
          <strong style="color: #409eff">修改融资信息</strong>
        </el-divider>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="融资额度：" prop="money">
              <el-input v-model="FinanceData.money" placeholder="请输入融资额度">
                <template #prepend>¥</template>
              </el-input>
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="意向借款期：" prop="repaymentPeriod">
              <el-select v-model="value" placeholder="请选择借款期" style="width: 100%">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">
          <strong style="color: #67c23a">用户1信息</strong>
        </el-divider>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名：" prop="realName">
              <el-input v-model="FinanceData.realName" placeholder="请输入姓名"></el-input>
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="联系方式：" prop="phone">
              <el-input v-model="FinanceData.phone" placeholder="请输入联系方式"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="身份证号：" prop="idNum">
          <el-input v-model="FinanceData.idNum" placeholder="请输入身份证号"></el-input>
        </el-form-item>

        <el-divider content-position="left">
          <strong style="color: #e6a23c">联合融资人1信息</strong>
        </el-divider>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名：" prop="combinationName1">
              <el-input v-model="FinanceData.combinationName1" placeholder="请输入姓名"></el-input>
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="联系方式：" prop="combinationPhone1">
              <el-input v-model="FinanceData.combinationPhone1" placeholder="请输入联系方式"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="身份证号：" prop="combinationIdnum1">
          <el-input v-model="FinanceData.combinationIdnum1" placeholder="请输入身份证号"></el-input>
        </el-form-item>

        <el-divider content-position="left">
          <strong style="color: #f56c6c">联合融资人2信息</strong>
        </el-divider>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名：" prop="combinationName2">
              <el-input v-model="FinanceData.combinationName2" placeholder="请输入姓名"></el-input>
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="联系方式：" prop="combinationPhone2">
              <el-input v-model="FinanceData.combinationPhone2" placeholder="请输入联系方式"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="身份证号：" prop="combinationIdnum2">
          <el-input v-model="FinanceData.combinationIdnum2" placeholder="请输入身份证号"></el-input>
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeUpdate">取 消</el-button>
          <el-button type="primary" @click="updateFinance">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {selectByName, updateById, deleteById, selectFinanceUser} from "../../../api/finance.js";

const size = ref("")
const FinanceAllData = ref([])
const showUpdate = ref(false)
const value = ref("")

const FinanceData = reactive({
  financeId: "",
  bankName: "",
  bankId: "",
  realName: "",
  phone: "",
  idNum: "",
  money: "",
  repayment: "",
  combinationName1: null,
  combinationPhone1: null,
  combinationIdnum1: null,
  combinationName2: null,
  combinationPhone2: null,
  combinationIdnum2: null,
})

const options = [
  {
    value: 6,
    label: "六个月",
  },
  {
    value: 12,
    label: "一年",
  },
  {
    value: 24,
    label: "两年",
  },
  {
    value: 36,
    label: "三年",
  },
]

const getBankName = (bankId) => {
  if (bankId == "1005") {
    return "日照银行";
  }
  if (bankId == "1001") {
    return "青岛银行";
  }
  if (bankId == "1002") {
    return "中国银行";
  }
  if (bankId == "1004") {
    return "中国工商银行";
  }
  if (bankId == "1006") {
    return "华夏银行";
  }
  if (bankId == "1007") {
    return "中国建设银行";
  }
  if (bankId == "1008") {
    return "浦发银行";
  }
  if (bankId == "1009") {
    return "中国平安银行";
  }
  if (bankId == "1010") {
    return "中国民生银行";
  }
  return "未知银行";
}

const getData = () => {
  selectByName({}).then((res) => {
    if (res.flag == true) {
      FinanceAllData.value = res.data;
      console.log("bankId");
      console.log(res.data.bankId);
      if (res.data.bankId === "1005") {
        FinanceAllData.value.bankName = "日照银行";
      }
    } else {
      ElMessage.error(res.data);
    }
  });
}

const updateFinanceShow = (item) => {
  showUpdate.value = true;
  FinanceData.financeId = item;
  selectFinanceUser({id:FinanceData.financeId}).then((res) => {
    if (res.flag == true) {
      Object.assign(FinanceData, res.data);
      console.log("bankId");
      console.log(res.data.bankId);
    } else {
      ElMessage.error(res.data);
    }
  });
}

const updateFinance = () => {
  if (
      FinanceData.money == "" ||
      FinanceData.money == null
    ) {
      alert("融资额度不能为空");
      return;
    }
    if (value.value == "" || value.value == null) {
      alert("意向借款期不能为空");
      return;
    }
    if (
      FinanceData.realName == "" ||
      FinanceData.realName == null
    ) {
      alert("姓名不能为空");
      return;
    }
    if (
      FinanceData.phone == "" ||
      FinanceData.phone == null
    ) {
      alert("联系方式不能为空");
      return;
    }
    if (
      FinanceData.idNum == "" ||
      FinanceData.idNum == null
    ) {
      alert("身份证号不能为空");
      return;
    }

  updateById({
    rate: 1.2,
    financeId: FinanceData.financeId,
    realName: FinanceData.realName,
    phone: FinanceData.phone,
    idNum: FinanceData.idNum,
    money: FinanceData.money,
    repayment: value.value,
    combinationName1: FinanceData.combinationName1,
    combinationPhone1: FinanceData.combinationPhone1,
    combinationIdnum1: FinanceData.combinationIdnum1,
    combinationName2: FinanceData.combinationName2,
    combinationPhone2: FinanceData.combinationPhone2,
    combinationIdnum2: FinanceData.combinationIdnum2,
  })
    .then((res) => {
      if (res.flag == true) {
        ElMessage.success(res.message);
        getData();
        showUpdate.value = false;
      } else {
        ElMessage.error(res.data);
      }
    })
    .catch((err) => {
      console.log("修改失败");
    });
}

const deleteFinance = (item) => {
  ElMessageBox.confirm("确认删除该信息?", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
  }).then(() => {
    deleteById({
      financeId: item,
    })
      .then((res) => {
        if (res.flag == true) {
          ElMessage.success(res.message);
          getData();
        } else {
          ElMessage.error(res.data);
        }
      })
      .catch((err) => {
        console.log("删除失败");
      });
  }).catch(() => {
    ElMessage({
      type: "info",
      message: "已取消删除",
    });
  });
}

const closeUpdate = () => {
  showUpdate.value = false;
}

onMounted(() => {
  getData();
});
</script>

<style scoped>
.finance-card {
  margin-bottom: 20px;
}

.box-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s;
}

.box-card:hover {
  box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.15);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-title {
  font-size: 18px;
  font-weight: bold;
  color: #303133;
}

.card-actions {
  display: flex;
  gap: 10px;
}

.money-text {
  font-weight: bold;
  color: #67c23a;
  font-size: 16px;
}

.el-divider {
  margin: 20px 0;
}

.el-form-item {
  margin-bottom: 18px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

:deep(.el-descriptions__body) {
  background-color: #fff;
}

:deep(.el-card__header) {
  background-color: #f2f6fc;
  border-bottom: 1px solid #ebeef5;
}
</style>