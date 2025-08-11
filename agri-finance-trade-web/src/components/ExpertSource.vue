<template>
  <div class="goods-box4">
    <div class="search">
      <el-input  placeholder="查找专家" v-model="searchValue" maxlength="100" clearable style="width:290px;">
        <template #prefix>
          <el-icon class="el-input__icon search-icon" @click="handleSearch"><Search /></el-icon>
        </template>
      </el-input>
    </div>

    <div class="goods" v-for="(item, index) in cgoods" :style="index===cgoods.length-1?'':'border-bottom:1px solid #f2f2f2;'" :key="index">
      <img :src="imgShowRoad + '/file/' + 'experta.png'" alt="" />
      <div class="info">
        <p class="content">
          <span style="margin-right:50px;">姓名：{{item.realName}}</span>
          <span>职称：{{item.position}}</span>
        </p>
        <p class="content">
          <span>从事专业：{{item.profession}}</span>
          <span>联系电话：{{item.phone}}</span>
        </p>
        <p class="content">
          <span>单位：{{item.belong}}</span>
        </p>
      </div>
      <div class="info2">
        <span class="question-btn" @click="handleQuestion(item)" style="margin-right:20px;">向我提问</span>
        <span class="question-btn" @click="handleAppoint(item)">线下预约</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { Search } from '@element-plus/icons-vue'

// 使用 Composition API
const store = useStore()
const router = useRouter()

// 定义 props
const props = defineProps({
  cgoods: {
    type: Array,
  },
})

// 定义 emits
const emit = defineEmits(['handleSearch'])

// 响应式数据
const searchValue = ref('')

// 计算属性替代 filters
const imgShowRoad = computed(() => store.state.imgShowRoad)

// 格式化时间函数
const formatTimer = (value) => {
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
  return y + "-" + MM + "-" + d + " " + h + ":" + m;
}

// 方法定义
const detailsClick = (item) => {
  store.commit("updateOrderId", item);
  router.push("/home/details").catch((err) => err);
}

const handleQuestion = (item) => {
  router.push(`/home/question?id=${item.userName}`).catch((err) => err);
}

const handleAppoint = (item) => {
  router.push(`/home/appointment?id=${item.userName}`).catch((err) => err);
}

const handleSearch = () => {
  emit('handleSearch', searchValue.value)
}
</script>

<style lang="less" scoped>
.search {
  height: 60px;
  background-color: #ffffff;
  border-top-left-radius:15px;
  border-top-right-radius:15px;
  padding: 10px 20px;
  margin-top: 10px;
  .search-icon{
    position:relative;
    bottom:2px;
    left: -2px;
    cursor: pointer;
  }
}

.goods-box4 {
  border-radius:15px;
  background-color: #ffffff;
  width: 1100px;
  margin: 0 auto;

  .goods {
    width: 1100px;
    height: 150px;
    padding: 10px 20px;
    background-color: white;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    img {
      width: 100px;
      height: 100px;
      margin-right: 20px;
      border-radius: 6px;
    }

    .info {
      width: 800px;
      margin-top: 10px;
      font-size: 16px;


      text-shadow: 0px 0px 2px #c7c7c7;
      .initiator {
        color: #666;
      }

      .title {
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
      }

      .content {
        height: 20px;
        /*超出的部分隐藏*/
        overflow: hidden;
        /*文字用省略号替代超出的部分*/
        text-overflow: ellipsis;
        /*弹性伸缩盒子模型显示*/
        display: -webkit-box;
        /*限制在一个块元素显示文本的行数*/
        -webkit-line-clamp: 7;
        /*设置或检索伸缩盒对象的子元素排列方式*/
        -webkit-box-orient: vertical;
      }

      .price {
        font-weight: bold;
        display: block;
        color: red;
      }
    }
  }
}
.info2{
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: flex-end;
  .question-btn{
    cursor: pointer;
    color: #67C23A;
    &:hover{
      text-decoration: underline;
      color: #035D1C;
    }
  }
}
.search :deep(.el-input--suffix .el-input__inner){
  height: 35px;
  line-height: 35px;
}
</style>