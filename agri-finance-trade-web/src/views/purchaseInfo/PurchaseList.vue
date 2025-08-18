<template>
  <div class="needs-box">
    <div class="search3">
      <el-input  placeholder="请输入内容" v-model="searchValue" maxlength="100" clearable style="width:250px;margin-right: 15px">
        <template #prefix>
          <i class="el-input__icon el-icon-search search-icon" @click="handleSearch"></i>
        </template>
      </el-input>

      <el-button-group>
        <el-button round @click="handleTopicDetail('')">全部 </el-button>
        <el-button round @click="handleTopicDetail('苹果')"> 苹果 </el-button>
        <el-button round @click="handleTopicDetail('新疆哈密瓜')" style="width: 120px">新疆哈密瓜 </el-button>
        <el-button round @click="handleTopicDetail('樱桃')"> 樱桃</el-button>
        <el-button round @click="handleTopicDetail('西红柿')"> 西红柿 </el-button>
        <el-button round @click="handleTopicDetail('水稻')"> 水稻 </el-button>
        <el-button round @click="handleTopicDetail('玉米')"> 玉米 </el-button>
        <el-button round @click="handleTopicDetail('赣南脐橙')" style="width: 110px"> 赣南脐橙 </el-button>
      </el-button-group>


    </div>
    <!-- 添加无数据时的显示 -->
    <div v-if="cneeds.length === 0" class="no-purchase-container">
      <img class="no-purchase-img" src="../../assets/img/no-purchase.png" alt="暂无求购" />
      <div class="no-purchase-text">暂无求购信息</div>
    </div>

    <div v-else class="purchase-content">
      <div v-for="(item, index) in cneeds"
      :key="index" class="purchase-item"
      @click="detailsClick(item.orderId)">
        <div class="title">
          <span class="title-icon"><i class="el-icon-sunrise-1"></i></span>
          <span class="title-content"> {{item.content}}</span>
        </div>
        <div class="content">{{item.title}}</div>
        <div class="update-time">{{ formatTimer(item.updateTime) }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

// 定义 props
const props = defineProps({
  cneeds: {
    type: Array,
    default: () => []
  },
})

// 定义 emits
const emit = defineEmits(['handleSearch'])

// 响应式数据
const searchValue = ref('')

// 使用 store 和 router
const store = useStore()
const router = useRouter()

// 方法定义
const detailsClick = (item) => {
  store.state.orderId = item;
  router.push(`/purchaseInfo/PurchaseDetails?orderId=${item}`).catch((err) => err);
}

const handleSearch = () => {
  emit('handleSearch', searchValue.value)
}

const handleTopicDetail = (val) => {
  searchValue.value = val
  handleSearch()
}

const formatTimer = (value) => {
  if (!value) return '';
  let date = new Date(value);
  let y = date.getFullYear();
  let MM = date.getMonth() + 1;
  MM = MM < 10 ? "0" + MM : MM;
  let d = date.getDate();
  d = d < 10 ? "0" + d : d;
  let h = date.getHours();
  h = h < 10 ? "0" + h : h;
  let m = date.getMinutes();
  m = m < 10 ? "0" + m : m;
  let s = date.getSeconds();
  s = s < 10 ? "0" + s : s;
  return y + "-" + MM + "-" + d + " ";
}
</script>

<style lang='less' scoped>
.needs-box{
  width: 100%;
  .search3 {
    height: 60px;
    background-color: white;
    padding: 10px 20px;
    margin: 10px auto;
    width: 1100px;

    .tag-item{
      margin-right: 10px;
      cursor: pointer;
    }
    .search-icon{
      position:relative;
      bottom:2px;
      left: -2px;
      cursor: pointer;
    }
  }

  // 添加无数据时的样式
  .no-purchase-container {
    width: 100%;
    text-align: center;
    padding: 50px 0;

    .no-purchase-img {
      width: 200px;
      height: 200px;
      object-fit: contain;
    }

    .no-purchase-text {
      font-size: 18px;
      color: #999;
      margin-top: 20px;
    }
  }

  .purchase-content{
    background: #fff;
    padding: 20px;
    width: 1100px;
    margin: 0 auto;
    border-radius: 10px;
    font-family: "PingFang SC";
    font-weight: 540;
    font-size: 17px;
    text-shadow: 0px 0px 1px #c7c7c7;

    .purchase-item{
      height: 70px;
      display: flex;
      align-items: center;
      flex-direction: row;
      justify-content: space-between;
      padding: 10px 0;
      border-bottom: 1px solid #dedddd;
      .title{
        width: 620px;
        /*超出的部分隐藏*/
        overflow: hidden;
        /*文字用省略号替代超出的部分*/
        text-overflow: ellipsis;
        /*弹性伸缩盒子模型显示*/
        display: -webkit-box;
        /*限制在一个块元素显示文本的行数*/
        -webkit-line-clamp: 1;
        /*设置或检索伸缩盒对象的子元素排列方式*/
        -webkit-box-orient: vertical;
        .tips{
          color: #000000;
          font-weight: bold;
        }
        .title-content{
          cursor: pointer;
          &:hover{
            color: #108fcb;
            text-decoration: underline;
          }
        }
      }
      .content{
        width: 300px;
        margin-left: 20px;
        /*超出的部分隐藏*/
        overflow: hidden;
        /*文字用省略号替代超出的部分*/
        text-overflow: ellipsis;
        /*弹性伸缩盒子模型显示*/
        display: -webkit-box;
        /*限制在一个块元素显示文本的行数*/
        -webkit-line-clamp: 1;
        /*设置或检索伸缩盒对象的子元 素排列方式*/
        -webkit-box-orient: vertical;
      }
      .update-time{
        width: 100px;
        text-align: right;
      }
    }
  }
}

.search3 :deep(.el-input--suffix .el-input__inner){
  height: 35px;
  line-height: 35px;
}

:deep(.el-button-group>.el-button) {
  border-radius: 100px;
  margin:0px 5px;
  border: 1px solid #DCDFE6;

  &:hover{
    text-decoration: underline;
    background-color: #ffffff;
    color: black;
  }
  &:focus{
    color: #ff4f16;
    background-color: #ffffff;
  }
}

:deep(.el-button-group>.el-button:not(:first-child):not(:last-child)) {
  border-radius: 100px;
}
</style>