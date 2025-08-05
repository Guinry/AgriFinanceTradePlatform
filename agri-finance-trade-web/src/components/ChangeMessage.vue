<template>
  <div class="changemessage">
    <div class="modify-text" @click="dialogFormVisible = true">修改</div>
    <el-dialog v-model="dialogFormVisible" title="商品信息" class="modify-message">
      <el-form label-width="100px">
        <el-form-item label="商品图片" :label-width="formLabelWidth">
          <img
            :src="$store.state.imgShowRoad + '/file/' + cupdateGoodInfo.picture"
            alt=""
            class="order-img"/>
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
            ref="uploadRef"
          >
            <span class="orders-img_el_upload_btn" @click.stop="submitUpload"
              >上传图片</span
            >
          </el-upload>
        </el-form-item>
      </el-form>
      <el-form :model="form">
        <el-form-item label="商品标题" :label-width="formLabelWidth">
          <el-input
            v-model="cupdateGoodInfo.title"
            autocomplete="off"
            class="title"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品内容" :label-width="formLabelWidth">
          <textarea
            v-model="cupdateGoodInfo.content"
            autocomplete="off"
            class="content"
          ></textarea>
        </el-form-item>
        <el-form-item label="商品价格" :label-width="formLabelWidth">
          <el-input v-model="cupdateGoodInfo.price" autocomplete="off" class="price"></el-input>
          元
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="success" @click.stop="changeMessageClick"
            >确 定</el-button
          >
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, getCurrentInstance } from 'vue'
import { ElMessage } from 'element-plus'
import { updateOrderById } from "../api/order";

// 获取组件实例
const { proxy } = getCurrentInstance()

// 定义 props
const props = defineProps({
  cupdateGoodInfo: {
    type: Object,
  },
})

// 定义响应式数据
const dialogImageUrl = ref("")
const dialogVisible = ref(false)
const showBtnImg = ref(true)
const noneBtnImg = ref(false)
const limitCountImg = ref(3)
const upurl = ref("")
const uploadDisabled = ref(false)
const form = reactive({})
const fileList = ref([])
const picture = ref("")
const dialogFormVisible = ref(false)
const formLabelWidth = ref("120px")
const uploadRef = ref(null)

// headers 使用函数返回，确保能获取到最新的token
const upheaders = ref({
  Authorization: window.localStorage.getItem('token'),
})

// 初始化 upurl
onMounted(() => {
  upurl.value = proxy.$store.state.fileUploadRoad + "/file/upload/order"
})

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
    props.cupdateGoodInfo.picture = file.response.data
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

const changeMessageClick = () => {
  dialogFormVisible.value = false
  updateOrderById({
    order_id: proxy.$store.state.changedOrderId,
    title: props.cupdateGoodInfo.title,
    content: props.cupdateGoodInfo.content,
    price: props.cupdateGoodInfo.price,
    picture: props.cupdateGoodInfo.picture,
  })
    .then((res) => {
      if (res.flag == true) {
        proxy.reload()
        alert(res.message)
      } else {
        alert(res.data)
        proxy.reload()
      }
    })
    .catch((err) => {
      alert("修改失败")
    })
}
</script>

<style lang="less" scoped>
.changemessage {
  // margin-top: -10px;
  .modify-text {
    height: 25px;
    cursor: pointer;
    margin-right: 10px;
    color: #67C23A;
    &:hover{
      color: #035D1C;
    }
  }
  .el-dialog {
    width: 600px;
    .order-img {
      width: 148px;
      height: 148px;
      border-radius: 6px;
    }
    .orders-img_el_upload {
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
      width: 400px;
    }
    .content {
      width: 400px;
      height: 150px;
      resize: none;
      outline: none;
      line-height: 1.5;
      border: 1px solid #dcdfe6;
    }
    .price {
      width: 100px;
    }
  } // width: 1200px;
}
</style>