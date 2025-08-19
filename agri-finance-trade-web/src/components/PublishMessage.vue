<template>
  <div class="publish-message">
    <el-form ref="formRef" :model="form" label-width="70px" style="margin-top:50px;">
      <el-form-item label="添加图片">
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
          :class="{ disUoloadSty: noneBtnImg }"
          ref="uploadRef">
          <span class="orders-img_el_upload_btn" @click.stop="submitUpload">添加图片</span>
        </el-upload>
      </el-form-item>
      <el-form-item label="标题">
        <el-input v-model="form.title"  style="width:800px;" placeholder="添加标题"></el-input>
      </el-form-item>
      <el-form-item label="内容">
        <el-input type="textarea"  style="width:800px;" v-model="form.content" placeholder="添加内容"></el-input>
      </el-form-item>
      <el-form-item label="定价">
        <el-input v-model="form.price" style="width:100px;"></el-input>
      </el-form-item>
    </el-form>
    <el-button type="success" :disabled="isDisabled" @click="publishClick">发布信息</el-button>
  </div>
</template>

<script setup>
import { addOrder } from "../api/order";
import { ref, computed, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

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
  fileList.value.pop()
  uploadDisabled.value = false
}

const handlePreview = (file) => {
  dialogImageUrl.value = file.url
  dialogVisible.value = true
}

const submitUpload = () => {
  uploadRef.value.submit()
}

const publishClick = () => {
  if ((form.picture == "")&&(props.ctype !== 'needs')) {
    alert("图片不能为空")
  } else {
    addOrder({
      title: form.title,
      content: form.content,
      price: form.price,
      type: props.ctype,
      picture: form.picture,
    }).then((res) => {
      if (res.flag == true) {
        ElMessage.success(res.message)
        router.push("/home/user/published" + props.ctype)
      } else {
        ElMessage.error(res.data)
      }
    })
    .catch((err) => {
      console.log("添加失败")
    })
  }
}
</script>

<style lang="less" scoped>
.disUoloadSty .el-upload--picture-card {
  display: none; /* 上传按钮隐藏 */
}
.publish-message {
  width: 1100px;
  margin: 20px auto;
  padding: 10px 20px;
  .orders-img_el_upload {
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
  .title {
    width: 1100px;
    height: 60px;
    margin-top: 50px;
    font-size: 22px;
    outline: none;
    border: none;
    border-bottom: 1px solid black;
  }
  .content {
    font-size: 18px;
    width: 1200px;
    resize: none;
    outline: none;
    border: none;
    border-bottom: 1px solid black;
  }
  .price {
    font-size: 20px;
    .price-input {
      width: 100px;
      outline: none;
      border: none;
      margin: 0 20px;
    }
  }
  .el-button {
    width: 120px;
    height: 50px;
    margin-top: 20px;
    margin-left: 540px;
    font-size: 20px;
  }
}
</style>