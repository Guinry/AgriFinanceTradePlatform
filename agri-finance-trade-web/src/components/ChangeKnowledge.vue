<template>
  <div class="changemessage">
    <div @click="dialogFormVisible = true" class="modify">修改</div>
    <el-dialog title="商品信息" v-model="dialogFormVisible" class="modify-message">
      <el-form label-width="100px">
        <el-form-item label="商品图片" :label-width="formLabelWidth">
          <img :src="imgShowRoad + '/file/' + cupdateKnowledgeInfo.picPath"
            alt="" class="order-img"/>
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
            <span class="orders-img_el_upload_btn" @click.stop="submitUpload">上传图片</span>
          </el-upload>
        </el-form-item>
      </el-form>
      <el-form :model="form">
        <el-form-item label="商品标题" :label-width="formLabelWidth">
          <el-input v-model="cupdateKnowledgeInfo.title"
            autocomplete="off" class="title"></el-input>
        </el-form-item>
        <el-form-item label="商品内容" :label-width="formLabelWidth">
          <textarea v-model="cupdateKnowledgeInfo.content"
            autocomplete="off" class="content"></textarea>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="success" @click.stop="changeMessageClick">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, inject } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
import { updateKnowledgeById } from "../api/knowledge";

// Props
const props = defineProps({
  cupdateKnowledgeInfo: {
    type: Object,
    required: true
  },
});

// Store
const store = useStore();

// Inject reload function
const reload = inject("reload");

// Reactive data
const dialogImageUrl = ref("")
const dialogVisible = ref(false)
const showBtnImg = ref(true)
const noneBtnImg = ref(false)
const limitCountImg = 3
const form = ref({})
const fileList = ref([])
const picPath = ref("")
const dialogFormVisible = ref(false)
const formLabelWidth = ref("120px")
const uploadRef = ref(null)

// Computed properties
const upurl = computed(() => store.state.fileUploadRoad + "/file/upload/order")
const imgShowRoad = computed(() => store.state.imgShowRoad)

const upheaders = computed(() => ({
  Authorization: window.localStorage.token,
}))

// Methods
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
    props.cupdateKnowledgeInfo.picture = file.response.data;
    if (fileList.length >= 3) {
      // this.uploadDisabled = true;
    }
    alert(file.response.message);
  } else alert(file.response.data);
}

const handleChange = (file, fileList) => {
  noneBtnImg.value = fileList.length >= limitCountImg;
}

const handleRemove = (file, fileList) => {
  noneBtnImg.value = fileList.length >= limitCountImg;
  fileList.value.pop();
  // this.form.photo = "";
  // this.uploadDisabled = false;
}

const handlePreview = (file) => {
  dialogImageUrl.value = file.url;
  dialogVisible.value = true;
}

const submitUpload = () => {
  uploadRef.value.submit();
}

const changeMessageClick = () => {
  dialogFormVisible.value = false;
  updateKnowledgeById({
    knowledgeId: store.state.changedKnowledgeId,
    title: props.cupdateKnowledgeInfo.title,
    content: props.cupdateKnowledgeInfo.content,
    picPath: props.cupdateKnowledgeInfo.picPath,
  })
    .then((res) => {
      if (res.flag == true) {
        reload();
        alert(res.message);
      } else {
        alert(res.data);
      }
    })
    .catch((err) => {
      alert("修改失败");
    });
}
</script>

<style lang="less" scoped>
.changemessage {
  .modify {
    width: 40px;
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