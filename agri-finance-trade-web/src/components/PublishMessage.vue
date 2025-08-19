<template>
  <div class="publish-message">
    <el-card class="publish-card">
      <template #header>
        <div class="card-header">
          <span class="header-title">发布{{ props.ctype === 'needs' ? '需求' : '商品' }}</span>
          <el-tag :type="props.ctype === 'needs' ? 'primary' : 'success'">
            {{ props.ctype === 'needs' ? '需求信息' : '商品信息' }}
          </el-tag>
        </div>
      </template>
      
      <el-form ref="formRef" :model="form" label-width="80px" class="publish-form">
        <el-form-item label="标题" prop="title" required>
          <el-input 
            v-model="form.title" 
            placeholder="请输入标题" 
            clearable
            maxlength="50"
            show-word-limit
          >
            <template #prefix>
              <el-icon><Document /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        
        <el-form-item label="内容" prop="content" required>
          <el-input 
            type="textarea" 
            v-model="form.content" 
            placeholder="请输入详细内容" 
            :rows="4"
            maxlength="500"
            show-word-limit
          />
        </el-form-item>
        
        <el-form-item label="定价" prop="price" v-if="props.ctype !== 'needs'">
          <el-input 
            v-model="form.price" 
            style="width: 200px;" 
            placeholder="请输入价格"
          >
            <template #prepend>￥</template>
          </el-input>
          <span class="price-tip">请输入合理的价格</span>
        </el-form-item>
        
        <el-form-item label="图片" prop="picture">
          <div class="upload-container">
            <el-upload
              class="orders-img_el_upload"
              :action="upurl"
              :headers="upheaders"
              :limit="3"
              list-type="picture-card"
              :on-change="handleChange"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :on-success="handleSuccess"
              :on-error="handleError"
              :file-list="fileList"
              :class="{ disUploadSty: noneBtnImg }"
              ref="uploadRef"
            >
              <el-icon class="upload-icon"><Plus /></el-icon>
              <div class="upload-text">上传图片</div>
            </el-upload>
            <div class="upload-tip">
              <el-icon><InfoFilled /></el-icon>
              最多可上传3张图片，支持JPG、PNG格式
            </div>
          </div>
        </el-form-item>
      </el-form>
      
      <div class="form-footer">
        <el-button @click="resetForm">重置</el-button>
        <el-button 
          type="success" 
          :disabled="isDisabled" 
          @click="publishClick"
          class="publish-btn"
        >
          <el-icon><Promotion /></el-icon>
          发布信息
        </el-button>
      </div>
    </el-card>
    
    <el-dialog v-model="dialogVisible" title="图片预览">
      <img w-full :src="dialogImageUrl" alt="Preview Image" class="preview-image" />
    </el-dialog>
  </div>
</template>

<script setup>
import { addOrder } from "../api/order";
import { ref, computed, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Document, Plus, InfoFilled, Promotion } from '@element-plus/icons-vue'

// 定义 props
const props = defineProps({
  ctype: {
    type: String,
  },
})

// 使用 store 和 router
const store = useStore()
const router = useRouter()

// 响应式数据
const dialogImageUrl = ref("")
const dialogVisible = ref(false)
const showBtnImg = ref(true)
const noneBtnImg = ref(false)
const limitCountImg = ref(3)
const fileList = ref([])
const imgurl = ref("")
const uploadDisabled = ref(false)

const form = reactive({
  title: "",
  content: "",
  price: "",
  picture: "",
})

// refs
const uploadRef = ref(null)
const formRef = ref(null)

// 计算属性
const isDisabled = computed(() => {
  if(props.ctype === 'needs'){
    return form.title == "" || form.content == "";
  }else{
    return form.title == "" || form.content == "" || form.price == "";
  }
})

const upurl = computed(() => store.state.fileUploadRoad + "/file/upload/order")

const upheaders = computed(() => ({
  Authorization: window.localStorage.token,
}))

// 方法定义
const handleError = (err, file, fileList) => {
  ElMessage({
    message: "上传失败！",
    type: "error",
  })
  console.log(err)
}

const handleSuccess = (response, file, fileList) => {
  if (file.response.flag == true) {
    fileList.value = fileList
    form.picture = file.response.data
    if (fileList.length >= 3) {
      uploadDisabled.value = true
    }
    ElMessage.success(file.response.message)
  } else {
    ElMessage.error(file.response.data)
  }
}

const handleChange = (file, fileList) => {
  noneBtnImg.value = fileList.length >= limitCountImg.value
}

const handleRemove = (file, fileList) => {
  noneBtnImg.value = fileList.length >= limitCountImg.value
  uploadDisabled.value = false
}

const handlePreview = (file) => {
  dialogImageUrl.value = file.url
  dialogVisible.value = true
}

const submitUpload = () => {
  uploadRef.value.submit()
}

const resetForm = () => {
  ElMessageBox.confirm(
    '确定要重置表单吗？所有已填写的内容将被清除',
    '确认重置',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(() => {
    form.title = ""
    form.content = ""
    form.price = ""
    form.picture = ""
    fileList.value = []
    noneBtnImg.value = false
    uploadDisabled.value = false
    ElMessage.info('表单已重置')
  }).catch(() => {
    // 用户取消重置
  })
}

const publishClick = () => {
  if ((form.picture == "")&&(props.ctype !== 'needs')) {
    ElMessage.warning("图片不能为空")
  } else {
    addOrder({
      title: form.title,
      content: form.content,
      price: form.price,
      type: props.ctype,
      picture: form.picture,
    }).then((res) => {
      console.log(res)
      if (res.flag) {
        ElMessage.success(res.message)
        router.push("/user" + props.ctype)
      } else {
        ElMessage.error(res.data)
      }
    }).catch((err) => {
      console.log("添加失败")
      ElMessage.error("发布失败，请稍后重试")
    })
  }
}
</script>

<style lang="less" scoped>
.disUploadSty .el-upload--picture-card {
  display: none; /* 上传按钮隐藏 */
}

.publish-message {
  width: 100%;
  max-width: 800px;
  padding: 0 20px;

  .publish-card {
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
    
    :deep(.el-card__header) {
      padding: 20px 30px;
      border-bottom: 1px solid #eee;
    }
    
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      
      .header-title {
        font-size: 20px;
        font-weight: 600;
        color: #333;
      }
    }
  }
  
  .publish-form {
    padding: 20px 30px;
    
    :deep(.el-form-item) {
      margin-bottom: 25px;
    }
    
    :deep(.el-form-item__label) {
      font-weight: 500;
      color: #555;
    }
    
    :deep(.el-input__inner) {
      border-radius: 8px;
    }
    
    :deep(.el-textarea__inner) {
      border-radius: 8px;
    }
  }
  
  .upload-container {
    .orders-img_el_upload {
      :deep(.el-upload--picture-card) {
        width: 120px;
        height: 120px;
        border-radius: 8px;
        border: 2px dashed #d9d9d9;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        transition: all 0.3s;
        
        &:hover {
          border-color: #409eff;
          transform: translateY(-2px);
        }
        
        .upload-icon {
          font-size: 24px;
          color: #999;
        }
        
        .upload-text {
          margin-top: 8px;
          font-size: 12px;
          color: #999;
        }
      }
      
      :deep(.el-upload-list__item) {
        width: 120px;
        height: 120px;
        border-radius: 8px;
      }
    }
    
    .upload-tip {
      display: flex;
      align-items: center;
      margin-top: 10px;
      font-size: 12px;
      color: #999;
      
      i {
        margin-right: 5px;
        font-size: 14px;
      }
    }
  }
  
  .price-tip {
    margin-left: 15px;
    font-size: 12px;
    color: #999;
  }
  
  .form-footer {
    display: flex;
    justify-content: flex-end;
    padding: 20px 30px;
    border-top: 1px solid #eee;
    
    .publish-btn {
      padding: 12px 30px;
      font-size: 16px;
      border-radius: 8px;
      
      i {
        margin-right: 5px;
      }
    }
  }
  
  .preview-image {
    width: 100%;
    max-height: 70vh;
    object-fit: contain;
  }
}
</style>