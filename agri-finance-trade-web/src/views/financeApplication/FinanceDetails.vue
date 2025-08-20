<template>
  <NavigationBar />
  <div class="details-box2">
    <div class="bank-header">
      <h1 class="bank-title">{{ FinanceUserDetails.bankName }}</h1>
      <div class="bank-intro">
        <p>{{ FinanceUserDetails.introduce }}</p>
      </div>
    </div>

    <div class="loan-selection">
      <div class="selection-header">
        <h2>请选择贷款类型</h2>
        <p>根据您的需求选择合适的贷款方式</p>
      </div>
      
      <div class="loan-options">
        <el-card class="loan-card" shadow="hover">
          <div class="card-content">
            <div class="card-icon">
              <i class="el-icon-user-solid"></i>
            </div>
            <h3>个人贷款</h3>
            <p>适用于个人独立申请贷款</p>
            <el-button 
              type="primary" 
              plain 
              round 
              @click="individual" 
              :disabled="Already"
              class="loan-btn"
            >
              {{ Already ? '已申请' : '申请个人贷款' }}
            </el-button>
          </div>
        </el-card>
        
        <el-card class="loan-card" shadow="hover">
          <div class="card-content">
            <div class="card-icon">
              <i class="el-icon-user"></i>
              <i class="el-icon-plus"></i>
              <i class="el-icon-user"></i>
            </div>
            <h3>组合贷款</h3>
            <p>适用于多人联合申请贷款</p>
            <el-button 
              type="success" 
              plain 
              round 
              @click="combination" 
              :disabled="Already"
              class="loan-btn"
            >
              {{ Already ? '已申请' : '申请组合贷款' }}
            </el-button>
          </div>
        </el-card>
      </div>
    </div>

    <!--单人贷款 -->
    <el-dialog
      :title="title"
      v-model="showIndividual"
      width="600px"
      :before-close="closeIndividual"
      class="loan-dialog"
    >
      <el-form
        :model="FinanceUserDetails"
        ref="ruleForm"
        label-width="120px"
        class="loan-form"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名：" prop="name">
              <el-input v-model="FinanceUserDetails.name" placeholder="请输入姓名"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="融资额度：" prop="money">
              <el-input v-model="FinanceUserDetails.money" placeholder="请输入融资额度">
                <template #append>元</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="利息：" prop="rate">
              <el-input v-model="FinanceUserDetails.rate" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="意向借款期：" prop="repayment">
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
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="联系方式：" prop="phone">
              <el-input v-model="FinanceUserDetails.phone" placeholder="请输入联系方式"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证号：" prop="idNum">
              <el-input v-model="FinanceUserDetails.idNum" placeholder="请输入身份证号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="上传材料：" prop="photo">
          <el-upload
            class="upload-demo"
            drag
            :action="upurl"
            multiple
            :headers="upheaders"
            :on-change="handleChange"
            :on-success="handleSuccess"
            :file-list="fileList"
            ref="upload"
          >
            <el-icon class="el-icon--upload"><Upload /></el-icon>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <div class="el-upload__tip">
              请上传身份证、收入证明等相关材料
            </div>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeIndividual">取 消</el-button>
          <el-button type="primary" @click="handleApplyOne">申请</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 组合贷款 -->
    <el-dialog
      :title="title"
      v-model="showCombination"
      width="650px"
      :before-close="closeCombination"
      class="loan-dialog"
    >
      <el-form
        :model="UserDetailsMulti"
        ref="ruleForm"
        label-width="130px"
        class="loan-form"
      >
        <el-card class="form-section">
          <div slot="header" class="clearfix">
            <span>融资信息</span>
          </div>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="融资额度：" prop="money">
                <el-input v-model="UserDetailsMulti.money" placeholder="请输入融资额度">
                  <template #append>元</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="利息：" prop="rate">
                <el-input v-model="UserDetailsMulti.rate" disabled></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          
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
        </el-card>
        
        <el-card class="form-section">
          <div slot="header" class="clearfix">
            <span>主申请人信息</span>
          </div>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="姓名：" prop="realName">
                <el-input v-model="UserDetailsMulti.realName" placeholder="请输入姓名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系方式：" prop="phone">
                <el-input v-model="UserDetailsMulti.phone" placeholder="请输入联系方式"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-form-item label="身份证号：" prop="idNum">
            <el-input v-model="UserDetailsMulti.idNum" placeholder="请输入身份证号"></el-input>
          </el-form-item>
        </el-card>
        
        <el-card class="form-section">
          <div slot="header" class="clearfix">
            <span>联合申请人1信息</span>
          </div>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="姓名：" prop="combinationName1">
                <el-input v-model="UserDetailsMulti.combinationName1" placeholder="请输入姓名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系方式：" prop="combinationPhone1">
                <el-input v-model="UserDetailsMulti.combinationPhone1" placeholder="请输入联系方式"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-form-item label="身份证号：" prop="combinationIdnum1">
            <el-input v-model="UserDetailsMulti.combinationIdnum1" placeholder="请输入身份证号"></el-input>
          </el-form-item>
        </el-card>
        
        <el-card class="form-section">
          <div slot="header" class="clearfix">
            <span>联合申请人2信息（可选）</span>
          </div>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="姓名：" prop="combinationName2">
                <el-input v-model="UserDetailsMulti.combinationName2" placeholder="请输入姓名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系方式：" prop="combinationPhone2">
                <el-input v-model="UserDetailsMulti.combinationPhone2" placeholder="请输入联系方式"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-form-item label="身份证号：" prop="combinationIdnum2">
            <el-input v-model="UserDetailsMulti.combinationIdnum2" placeholder="请输入身份证号"></el-input>
          </el-form-item>
        </el-card>
        
        <el-form-item label="上传材料：" prop="photo">
          <el-upload
            class="upload-demo"
            drag
            :action="upurl"
            multiple
            :headers="upheaders"
            :on-change="handleChange"
            :on-success="handleSuccess"
            :file-list="fileList"
            ref="upload"
          >
            <el-icon class="el-icon--upload"><Upload /></el-icon>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <div class="el-upload__tip">
              请上传所有申请人的身份证、收入证明等相关材料
            </div>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeCombination">取 消</el-button>
          <el-button type="primary" @click="handleApplyMulti">申请</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
  <Footer />
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Upload } from '@element-plus/icons-vue'
import {
  selectFinanceUser,
  addFinance,
  addFinanceMulti,
} from "../../api/finance";
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

// 使用 Composition API
const router = useRouter()
const route = useRoute()

// 添加一个全局状态变量来替代未定义的store
const globalState = reactive({
  mutiFile: "",
  fileUploadRoad: "http://localhost:9090" // 添加默认值，根据实际情况修改
})

// 响应式数据
const options = ref([
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
])
const value = ref("")
const dialogImageUrl = ref("")
const dialogVisible = ref(false)
const showBtnImg = ref(true)
const noneBtnImg = ref(false)
const limitCountImg = ref(1)
const fileInfo = ref("")
const upheaders = ref({
  Authorization: window.localStorage.token,
})
const fileList = ref([])
const imgurl = ref("")
const upurl = computed(() => globalState.fileUploadRoad + "/file/uploadInfo/info")
const data = ref([])
const Already = ref(false)
const AuthorizationUser = reactive({
  userName: "",
})
const AlreadyApplied = reactive({
  applied: "",
})
const title = ref("个人贷款信息")
const isAuthorization = ref(true)
const showIndividual = ref(false)
const showCombination = ref(false)
const showAdd = ref(false)
const FinanceUserDetails = reactive({
  bankName: "",
  name: "",
  money: "",
  rate: "",
  repayment: "",
  phone: "",
  idNum: "",
  address: "",
  introduce: "",
})
const UserDetailsMulti = reactive({
  money: "",
  rate: "",
  repayment: "",

  realName: "",
  phone: "",
  idNum: "",

  combinationName1: "",
  combinationPhone1: "",
  combinationIdnum1: "",

  combinationName2: "",
  combinationPhone2: "",
  combinationIdnum2: "",
})

// 表单引用
const ruleForm = ref(null)
const upload = ref(null)

// 方法定义
const handleApplyOne = () => {
  if (localStorage.getItem("token")) {
    console.log("信息");
    console.log(FinanceUserDetails);

    if (
      FinanceUserDetails.name == "" ||
      FinanceUserDetails.name == null
    ) {
      alert("姓名不能为空");
      return;
    }
    if (
      FinanceUserDetails.money == "" ||
      FinanceUserDetails.money == null
    ) {
      alert("融资额度不能为空");
      return;
    }
    if (value.value == "" || value.value == null) {
      alert("意向借款期不能为空");
      return;
    }
    if (
      FinanceUserDetails.phone == "" ||
      FinanceUserDetails.phone == null
    ) {
      alert("联系方式不能为空");
      return;
    }
    if (
      FinanceUserDetails.idNum == "" ||
      FinanceUserDetails.idNum == null
    ) {
      alert("身份证号不能为空");
      return;
    }
    if (
      globalState.mutiFile == "" ||
      globalState.mutiFile == null
    ) {
      alert("资料不能为空");
      return;
    }

    addFinance({
      bankId: window.localStorage.financeObj.substring(10, 14),
      realName: FinanceUserDetails.name,
      money: FinanceUserDetails.money,
      rate: FinanceUserDetails.rate,
      repayment: value.value,
      phone: FinanceUserDetails.phone,
      idNum: FinanceUserDetails.idNum,
      fileInfo: globalState.mutiFile
    })
      .then((res) => {
        if (res.flag) {
          ElMessage.success(res.message);
          // router.push("/home/financing").catch((err) => err);
          localStorage.removeItem("financeObj");
          Already.value = true;
          showIndividual.value = false;
          globalState.mutiFile = "";
        } else {
          ElMessage.error(res.message);
          globalState.mutiFile = "";
        }
      })
      .catch((err) => {
        console.log(err);
        ElMessage.error(err.message);
        globalState.mutiFile = "";
      });
  } else {
    ElMessage.error("请先登录");
    router.push(`/login`).catch((err) => err);
  }
}

const handleApplyMulti = () => {
  if (localStorage.getItem("token")) {
    if (
      UserDetailsMulti.money == "" ||
      UserDetailsMulti.money == null
    ) {
      alert("融资额度不能为空");
      return;
    }
    if (value.value == "" || value.value == null) {
      alert("意向借款期不能为空");
      return;
    }
    if (
      UserDetailsMulti.realName == "" ||
      UserDetailsMulti.realName == null
    ) {
      alert("姓名不能为空");
      return;
    }
    if (
      UserDetailsMulti.phone == "" ||
      UserDetailsMulti.phone == null
    ) {
      alert("联系方式不能为空");
      return;
    }
    if (
      UserDetailsMulti.idNum == "" ||
      UserDetailsMulti.idNum == null
    ) {
      alert("身份证号不能为空");
      return;
    }
    if (
      UserDetailsMulti.combinationName1 == "" ||
      UserDetailsMulti.combinationName1 == null
    ) {
      alert("用户2姓名不能为空");
      return;
    }
    if (
      UserDetailsMulti.combinationPhone1 == "" ||
      UserDetailsMulti.combinationPhone1 == null
    ) {
      alert("用户2联系方式不能为空");
      return;
    }
    if (
      UserDetailsMulti.combinationIdnum1 == "" ||
      UserDetailsMulti.combinationIdnum1 == null
    ) {
      alert("用户2身份证号不能为空");
      return;
    }
    if (
      globalState.mutiFile == "" ||
      globalState.mutiFile == null
    ) {
      alert("资料不能为空");
      return;
    }

    addFinanceMulti({
      bankId: window.localStorage.financeObj.substring(10, 14),
      money: UserDetailsMulti.money,
      rate: UserDetailsMulti.rate,
      repayment: value.value,

      realName: UserDetailsMulti.realName,
      phone: UserDetailsMulti.phone,
      idNum: UserDetailsMulti.idNum,

      combinationName1: UserDetailsMulti.combinationName1,
      combinationPhone1: UserDetailsMulti.combinationPhone1,
      combinationIdnum1: UserDetailsMulti.combinationIdnum1,

      combinationName2: UserDetailsMulti.combinationName2,
      combinationPhone2: UserDetailsMulti.combinationPhone2,
      combinationIdnum2: UserDetailsMulti.combinationIdnum2,

      fileInfo: globalState.mutiFile
    })
      .then((res) => {
        if (res.flag) {
          ElMessage.success(res.message);
          // router.push("/home/financing").catch((err) => err);
          localStorage.removeItem("financeObj");
          Already.value = true;
          showCombination.value = false;
          globalState.mutiFile = "";
        } else {
          ElMessage.error(res.message);
          globalState.mutiFile = "";
        }
      })
      .catch((err) => {
        console.log(err);
        ElMessage.error(err.message);
        globalState.mutiFile = "";
      });
  } else {
    ElMessage.error("请先登录");
    router.push(`/login`).catch((err) => err);
  }
}

const individual = () => {
  title.value = "个人贷款信息";
  globalState.mutiFile = ""; // 修复：使用globalState替代store
  selectFinanceUser({
    bank_id: window.localStorage.financeObj.substring(10, 14),
  }).then((res) => {
    showIndividual.value = true;
    showCombination.value = false;
    if (res.flag) {
      Object.assign(FinanceUserDetails, res.data);
      console.log(route.query.bankId);
    } else {
      console.log(route.query.bankId);
      ElMessage.error(res.data);
    }
  });
}

const select = () => {
  selectFinanceUser({
    bank_id: window.localStorage.financeObj.substring(10, 14),
  }).then((res) => {
    if (res.flag) {
      Object.assign(FinanceUserDetails, res.data);
      console.log(FinanceUserDetails);
    } else {
      console.log(window.localStorage.financeObj.substring(10, 14));
      ElMessage.error(res.data);
    }
  });
}

const combination = () => {
  title.value = "组合贷款信息";
  globalState.mutiFile = ""; // 修复：使用globalState替代store
  selectFinanceUser({
    bank_id: window.localStorage.financeObj.substring(10, 14),
  }).then((res) => {
    showCombination.value = true;
    showIndividual.value = false;
    if (res.flag) {
      Object.assign(UserDetailsMulti, res.data);
      console.log(route.query.bankId);
    } else {
      console.log(route.query.bankId);
      ElMessage.error(res.data);
    }
  });
}

const Authorization = () => {
  showAdd.value = true;
}

const closeAdd = () => {
  showAdd.value = false;
}

const closeIndividual = () => {
  showIndividual.value = false;
  globalState.mutiFile = "";
}

const closeCombination = () => {
  showCombination.value = false;
  globalState.mutiFile = "";
}

const toAuthorization = () => {
  if (AuthorizationUser.userName == "") {
    alert("用户名不能为空");
    return;
  }
  toAuthorizationUser({
    userName: AuthorizationUser.userName,
  }).then((res) => {
    if (res.flag) {
      ElMessage.success(res.message);
      showAdd.value = false;
      isAuthorization.value = false;
    } else {
      showAdd.value = false;
      ElMessage.error(res.data);
    }
  });
}

const handleError = (err, file, fileList) => {
  ElMessage({
    message: "上传失败！",
    type: "success",
  });
  console.log(err);
}

const handleSuccess = (response, file, fileList) => {
  if (file.response.flag == true) {
    fileList.value = fileList;
    globalState.mutiFile += file.response.data + " "; // 修复：使用globalState替代store
    console.log("上传文件=====")
    console.log(globalState.mutiFile) // 修复：使用globalState替代store
    console.log("=============")
    if (fileList.length >= 1) {
      // uploadDisabled.value = true;
    }
    alert(file.response.message);
  } else {
    alert(file.response.data);
  }
}

const handleChange = (file, fileList) => {
  noneBtnImg.value = fileList.length >= limitCountImg.value;
}

const handleRemove = (file, fileList) => {
  noneBtnImg.value = fileList.length >= limitCountImg.value;
  fileList.value = [];
  // form.photo = "";
  // uploadDisabled.value = false;
}

const handlePreview = (file) => {
  dialogImageUrl.value = file.url;
  dialogVisible.value = true;
}

const submitUpload = () => {
  upload.value.submit();
}

// 生命周期
onMounted(() => {
  select();
  // form = Object.assign(
  //   {},
  //   { ...JSON.parse(localStorage.getItem("financeObj")) }
  // );
})
</script>

<style lang="less" scoped>
.details-box2 {
  width: 1100px;
  margin: 20px auto;
  background: #fff;
  min-height: 100%;
  height: auto;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  
  .bank-header {
    text-align: center;
    padding: 20px 0;
    border-bottom: 1px solid #eee;
    margin-bottom: 30px;
    
    .bank-title {
      font-size: 28px;
      color: #333;
      margin-bottom: 15px;
    }
    
    .bank-intro {
      font-size: 16px;
      color: #666;
      line-height: 1.6;
      max-width: 800px;
      margin: 0 auto;
    }
  }
  
  .loan-selection {
    .selection-header {
      text-align: center;
      margin-bottom: 30px;
      
      h2 {
        font-size: 24px;
        color: #333;
        margin-bottom: 10px;
      }
      
      p {
        font-size: 16px;
        color: #666;
      }
    }
    
    .loan-options {
      display: flex;
      justify-content: center;
      gap: 40px;
      flex-wrap: wrap;
      
      .loan-card {
        width: 350px;
        border-radius: 10px;
        transition: all 0.3s ease;
        
        &:hover {
          transform: translateY(-5px);
          box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
        }
        
        .card-content {
          text-align: center;
          padding: 30px 20px;
          
          .card-icon {
            font-size: 40px;
            margin-bottom: 20px;
            
            i {
              margin: 0 5px;
              color: #409eff;
            }
          }
          
          h3 {
            font-size: 20px;
            margin-bottom: 15px;
            color: #333;
          }
          
          p {
            color: #666;
            margin-bottom: 25px;
            font-size: 14px;
          }
          
          .loan-btn {
            width: 150px;
          }
        }
      }
    }
  }
}

.loan-dialog {
  .loan-form {
    padding: 20px 0;
    
    .form-section {
      margin-bottom: 20px;
      border-radius: 6px;
      
      :deep(.el-card__header) {
        background-color: #f5f7fa;
        padding: 10px 20px;
        font-weight: bold;
      }
    }
    
    :deep(.el-form-item) {
      margin-bottom: 20px;
    }
    
    :deep(.el-upload-dragger) {
      width: 100%;
    }
    
    :deep(.el-upload__tip) {
      text-align: center;
      color: #999;
      font-size: 12px;
      margin-top: 7px;
    }
  }
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 15px;
  padding: 0 20px 20px;
}

</style>
