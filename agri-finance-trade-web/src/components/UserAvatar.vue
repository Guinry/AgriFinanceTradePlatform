<template>
  <div class="user-avatar">
    <img :src="$store.state.imgShowRoad + '/file/' + cUserAvatar" alt="" class="avatar"/>
    <el-form label-width="100px">
      <el-upload
        class="user-avatar_el_upload"
        :action="upurl"
        :headers="upheaders"
        :limit="1"
        list-type="picture-card"
        :on-change="handleChange"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-success="handleSuccess"
        :on-error="handleError"
        :file-list="fileList"
        :class="{ disUoloadSty: noneBtnImg }"
        ref="upload">
        
        <span class="user-avatar_el_upload_btn" @click.stop="submitUpload">上传头像</span>
      </el-upload>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

// 定义 props
const props = defineProps({
  cUserAvatar: {
    type: String,
    default: ''
  }
})

// 定义 emits
const emit = defineEmits(['update:cUserAvatar'])

// 使用 store
const store = useStore()

// 响应式数据
const dialogImageUrl = ref("")
const dialogVisible = ref(false)
const showBtnImg = ref(true)
const noneBtnImg = ref(false)
const limitCountImg = ref(1)
const fileList = ref([])
const imgurl = ref("")
const upload = ref(null)

// 计算属性
const upurl = computed(() => store.state.fileUploadRoad + "/file/upload/avatar")

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
    emit('update:cUserAvatar', file.response.data)
    console.log("头像：：：")
    console.log(file.response.data)
    console.log(fileList)
    if (fileList.length >= 1) {
      uploadDisabled.value = true
    }
    alert(file.response.message)
  } else {
    alert(file.response.data)
  }
}

const handleChange = (file, fileList) => {
  noneBtnImg.value = fileList.length >= limitCountImg.value
}

const handleRemove = (file, fileList) => {
  noneBtnImg.value = fileList.length >= limitCountImg.value
  fileList.value = []
  // form.photo = ""
  uploadDisabled.value = false
}

const handlePreview = (file) => {
  dialogImageUrl.value = file.url
  dialogVisible.value = true
}

const submitUpload = () => {
  upload.value.submit()
}

// 新增响应式数据
const uploadDisabled = ref(false)
</script>

<style lang="less" scoped>
.disUoloadSty .el-upload--picture-card {
  display: none; /* 上传按钮隐藏 */
}
.user-avatar {
  width: 1200px;
  height: 150px;
  .avatar {
    float: left;
    width: 148px;
    height: 148px;
    border-radius: 50%;
  }
  .user-avatar_el_upload {
    width: 1000px;
    float: left;
    height: 148px;
    .el-upload {
      //   width: 50px;
      //   height: 20px;
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      &:hover {
        border-color: #409eff;
      }
      .el-upload__input {
        position: absolute;
        left: -1000px;
      }
    }
  }
}
</style>