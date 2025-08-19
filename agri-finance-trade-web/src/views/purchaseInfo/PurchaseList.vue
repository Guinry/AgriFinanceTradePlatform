<template>
  <div class="needs-box">
    <div class="search3">
      <el-input  placeholder="请输入内容" v-model="searchValue" maxlength="100" clearable style="width:300px;margin-right: 20px">
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
      <div class="no-purchase-subtext">当前没有符合条件的求购信息，请尝试其他搜索条件</div>
    </div>

    <div v-else class="purchase-content">
      <div v-for="(item, index) in cneeds"
      :key="index" class="purchase-item"
      @click="detailsClick(item.orderId)">
        <div class="title-section">
          <div class="title">
            <span class="title-icon"><i class="el-icon-sunrise-1"></i></span>
            <span class="title-content"> {{item.content}}</span>
          </div>
          <div class="content">{{item.title}}</div>
        </div>
        <div class="info-section">
          <div class="update-time">{{ formatTimer(item.updateTime) }}</div>
          <div class="arrow-icon">
            <i class="el-icon-arrow-right"></i>
          </div>
        </div>
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
    height: auto;
    background: linear-gradient(135deg, #4CAF50 0%, #2E7D32 100%);
    padding: 20px 30px;
    margin: 15px auto;
    width: 1100px;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);

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
    padding: 80px 0;
    background: #fff;
    border-radius: 12px;
    margin: 0 auto;
    width: 1100px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

    .no-purchase-img {
      width: 200px;
      height: 200px;
      object-fit: contain;
    }

    .no-purchase-text {
      font-size: 22px;
      color: #666;
      margin-top: 20px;
      font-weight: 500;
    }
    
    .no-purchase-subtext {
      font-size: 16px;
      color: #999;
      margin-top: 10px;
    }
  }

  .purchase-content{
    background: #fff;
    padding: 25px;
    width: 1100px;
    margin: 0 auto;
    border-radius: 12px;
    font-family: "PingFang SC";
    font-weight: 500;
    font-size: 17px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

    .purchase-item{
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 20px 15px;
      border-bottom: 1px solid #f0f0f0;
      transition: all 0.3s ease;
      cursor: pointer;
      
      &:hover {
        background-color: #f9f9f9;
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
        border-radius: 8px;
      }
      
      &:last-child {
        border-bottom: none;
      }
      
      .title-section {
        flex: 1;
        
        .title{
          width: 100%;
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
          margin-bottom: 8px;
          
          .title-icon {
            color: #409EFF;
            margin-right: 10px;
          }
          
          .tips{
            color: #000000;
            font-weight: bold;
          }
          .title-content{
            cursor: pointer;
            color: #333;
            font-size: 18px;
            font-weight: 600;
            
            &:hover{
              color: #409EFF;
            }
          }
        }
        
        .content{
          width: 100%;
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
          color: #666;
          font-size: 15px;
        }
      }
      
      .info-section {
        display: flex;
        align-items: center;
        
        .update-time{
          color: #999;
          font-size: 14px;
          margin-right: 15px;
          min-width: 100px;
          text-align: right;
        }
        
        .arrow-icon {
          color: #ccc;
          font-size: 18px;
        }
      }
    }
  }
}

.search3 :deep(.el-input--suffix .el-input__inner){
  height: 42px;
  line-height: 42px;
  border-radius: 25px;
  border: none;
  padding-left: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

:deep(.el-button-group>.el-button) {
  border-radius: 20px;
  margin: 0 5px;
  border: none;
  background: rgba(255, 255, 255, 0.85);
  color: #333;
  font-weight: 500;
  transition: all 0.3s ease;

  &:hover{
    background: #fff;
    color: #409EFF;
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  &:focus{
    color: #409EFF;
    background: #fff;
    border-color: #409EFF;
  }
  
  &:first-child {
    border-top-left-radius: 20px;
    border-bottom-left-radius: 20px;
  }
  
  &:last-child {
    border-top-right-radius: 20px;
    border-bottom-right-radius: 20px;
  }
}

:deep(.el-button-group>.el-button:not(:first-child):not(:last-child)) {
  border-radius: 20px;
}
</style>