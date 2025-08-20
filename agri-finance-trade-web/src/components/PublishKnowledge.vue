<template>
  <div class="publish-message">
    <div class="header-section">
      <h2 class="page-title">发布农业知识</h2>
      <p class="page-description">分享您的农业经验和知识，帮助更多农户解决问题</p>
    </div>
    
    <el-form label-width="100px" :model="form" class="publish-form">
      <el-form-item label="知识标题">
        <el-input 
          v-model="form.title" 
          placeholder="请输入知识标题" 
          size="large"
          clearable
          class="title-input">
          <template #prefix>
            <el-icon><Document /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      
      <el-form-item label="知识内容">
        <el-input 
          type="textarea" 
          v-model="form.content" 
          placeholder="请输入详细内容，分享您的农业知识和经验..." 
          :rows="6"
          class="content-textarea">
        </el-input>
      </el-form-item>
      
      <el-form-item label="添加图片">
        <el-upload
          class="knowledge-img-upload"
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
          :class="{ disUoloadSty: noneBtnImg }"
          ref="uploadRef">
          <div class="upload-content">
            <el-icon><Plus /></el-icon>
            <span>上传图片</span>
          </div>
        </el-upload>
        <div class="upload-hint">
          <p>最多可上传3张图片，支持JPG、PNG格式</p>
        </div>
      </el-form-item>
    </el-form>
    
    <div class="form-actions">
      <el-button type="info" @click="resetForm" class="reset-btn">重置</el-button>
      <el-button 
        type="success" 
        :disabled="isDisabled" 
        @click="publishClick" 
        class="publish-btn"
        size="large">
        <el-icon><Upload /></el-icon>
        发布知识
      </el-button>
    </div>
    
    <el-dialog v-model="dialogVisible" class="preview-dialog">
      <img :src="dialogImageUrl" alt="预览图片" class="preview-image" />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Document, Plus, Upload } from '@element-plus/icons-vue'
import { addKnowledge } from "../api/knowledge";

// 使用 Composition API
const router = useRouter()

// 响应式数据
const dialogImageUrl = ref("")
const dialogVisible = ref(false)
const showBtnImg = ref(true)
const noneBtnImg = ref(false)
const limitCountImg = ref(3)
const fileList = ref([])
const imgurl = ref("")
const uploadRef = ref(null)

// 表单数据
const form = reactive({
  title: '',
  content: '',
  picPath: ''
})

// props
const props = defineProps({
  ctype: {
    type: String,
  },
})

// 计算属性
const isDisabled = computed(() => {
  return form.title == "" || form.content == ""
})

// store 相关数据（假设使用 vuex 或 pinia）
const store = {
  state: {
    fileUploadRoad: "http://localhost:9090" // 根据实际情况修改
  }
}

const upurl = computed(() => store.state.fileUploadRoad + "/file/upload/order")
const upheaders = computed(() => ({
  Authorization: window.localStorage.token,
}))

// 方法定义
const handleError = (err, file, fileList) => {
  ElMessage({
    message: "上传失败！",
    type: "error",
  });
  console.log(err);
}

const handleSuccess = (response, file, fileList) => {
  if (file.response.flag == true) {
    fileList.value = fileList;
    form.picPath = file.response.data;
    if (fileList.length >= 3) {
      // uploadDisabled.value = true;
    }
    ElMessage.success(file.response.message);
  } else {
    ElMessage.error(file.response.data);
  }
}

const handleChange = (file, fileList) => {
  noneBtnImg.value = fileList.length >= limitCountImg.value;
}

const handleRemove = (file, fileList) => {
  noneBtnImg.value = fileList.length >= limitCountImg.value;
  fileList.value.pop();
  // uploadDisabled.value = false;
}

const handlePreview = (file) => {
  dialogImageUrl.value = file.url;
  dialogVisible.value = true;
}

const submitUpload = () => {
  uploadRef.value.submit();
}

const resetForm = () => {
  form.title = '';
  form.content = '';
  form.picPath = '';
  fileList.value = [];
  noneBtnImg.value = false;
  ElMessage.info('表单已重置');
}

const publishClick = () => {
  addKnowledge({
    title: form.title,
    content: form.content,
    picPath: form.picPath,
  })
    .then((res) => {
      if (res.flag == true) {
        ElMessage.success(res.message);
        router.push("/home/user/published" + props.ctype).catch((err) => err);
      } else {
        ElMessage.error(res.message);
      }
    })
    .catch((err) => {
      console.log("添加失败");
      ElMessage.error("发布失败，请稍后重试");
    });
}
</script>

<style lang="less" scoped>
.publish-message {
  margin: 20px auto;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  
  .header-section {
    text-align: center;
    margin-bottom: 30px;
    padding-bottom: 20px;
    border-bottom: 1px solid #eee;
    
    .page-title {
      font-size: 28px;
      color: #333;
      margin-bottom: 10px;
      font-weight: 600;
    }
    
    .page-description {
      font-size: 16px;
      color: #666;
    }
  }
  
  .publish-form {
    :deep(.el-form-item) {
      margin-bottom: 25px;
      
      .el-form-item__label {
        font-weight: 500;
        color: #333;
        font-size: 16px;
      }
    }
    
    .title-input {
      :deep(.el-input__wrapper) {
        box-shadow: 0 1px 4px rgba(0,0,0,0.1);
        border-radius: 8px;
      }
      
      :deep(.el-input__prefix) {
        i {
          font-size: 18px;
          color: #999;
        }
      }
    }
    
    .content-textarea {
      :deep(.el-textarea__inner) {
        box-shadow: 0 1px 4px rgba(0,0,0,0.1);
        border-radius: 8px;
        font-size: 15px;
        line-height: 1.6;
        padding: 15px;
        
        &:focus {
          box-shadow: 0 1px 4px rgba(103, 194, 58, 0.3);
        }
      }
    }
    
    .upload-hint {
      margin-bottom: 15px;
      
      p {
        color: #999;
        font-size: 14px;
        margin: 0;
      }
    }
    
    .knowledge-img-upload {
      :deep(.el-upload--picture-card) {
        border: 2px dashed #d9d9d9;
        border-radius: 10px;
        transition: all 0.3s ease;
        
        &:hover {
          border-color: #67C23A;
          transform: translateY(-2px);
        }
      }
      
      .upload-content {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100%;
        
        i {
          font-size: 28px;
          color: #999;
          margin-bottom: 5px;
        }
        
        span {
          color: #999;
          font-size: 14px;
        }
      }
    }
  }
  
  .form-actions {
    display: flex;
    justify-content: center;
    gap: 30px;
    margin-top: 40px;
    
    .reset-btn {
      padding: 12px 30px;
      font-size: 16px;
      border-radius: 25px;
    }
    
    .publish-btn {
      padding: 12px 30px;
      font-size: 16px;
      border-radius: 25px;
      background: linear-gradient(135deg, #67C23A, #5daf34);
      border: none;
      transition: all 0.3s ease;
      
      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 6px 16px rgba(103, 194, 58, 0.3);
      }
      
      i {
        margin-right: 8px;
      }
    }
  }
  
  .preview-dialog {
    :deep(.el-dialog__body) {
      padding: 20px;
      text-align: center;
    }
    
    .preview-image {
      max-width: 100%;
      max-height: 80vh;
      border-radius: 8px;
    }
  }
}

.disUoloadSty .el-upload--picture-card {
  display: none; /* 上传按钮隐藏 */
}

// 响应式设计
@media (max-width: 1150px) {
  .publish-message {
    width: 95%;
    padding: 20px;
  }
}

@media (max-width: 768px) {
  .publish-message {
    .header-section {
      .page-title {
        font-size: 24px;
      }
      
      .page-description {
        font-size: 14px;
      }
    }
    
    .publish-form {
      :deep(.el-form-item__label) {
        font-size: 15px;
      }
    }
    
    .form-actions {
      flex-direction: column;
      gap: 15px;
    }
  }
}
</style>