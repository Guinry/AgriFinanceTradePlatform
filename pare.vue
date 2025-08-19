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
  router.push(`/expertGuidance/askQuestion?id=${item.userName}`).catch((err) => err);
}

const handleAppoint = (item) => {
  router.push(`/expertGuidance/makeAppointment?id=${item.userName}`).catch((err) => err);
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

<template>
  <div class="goods-box3">
    <!-- 搜索框和筛选按钮 -->
    <div class="search-container">
      <el-input placeholder="有问题直接搜" v-model="searchValue" maxlength="100" clearable class="search-input">
        <template #prefix>
          <el-icon class="el-input__icon search-icon" @click="handleSearch">
            <Search />
          </el-icon>
        </template>
      </el-input>
      <el-button-group class="button-group">
        <el-button round @click="handleTopicDetail('')">全部</el-button>
        <el-button round @click="handleTopicDetail('苹果果树种植方法')">苹果</el-button>
        <el-button round @click="handleTopicDetail('玉米苗灌溉')">玉米</el-button>
        <el-button round @click="handleTopicDetail('北方草莓种植品种')">草莓</el-button>
      </el-button-group>
    </div>

    <!-- 问题列表 -->
    <div class="goods">
      <div class="info" v-for="(item, index) in cgoods" :key="index">
        <div class="content">
          <div class="question-header">
            <div class="question-text">问</div>
            <div class="question-content" @click="handleDetail(item)">{{ item.title }}</div>
          </div>
          <div class="answer-container">
            <div class="answer-text">答</div>
            <div class="answer-content" v-if="item.status == 1">{{ item.answer }}</div>
          </div>
        </div>
        <div class="person-contents">
          <span>提问者：{{ item.questioner }}</span>&nbsp;&nbsp;&nbsp;
          <span>专家：{{ item.expertName }}</span>
        </div>
      </div>
    </div>

    <!-- 分页 -->
    <Pagination @item-click="pageClick" :cUrl="url" :cTotal="total" :cPageSize="pageSize"></Pagination>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { Search } from '@element-plus/icons-vue'
import Pagination from "../../components/Pagination.vue"

const store = useStore()
const router = useRouter()

const searchValue = ref('')

const props = defineProps({
  cgoods: {
    type: Array,
  },
  total: {
    type: Number
  },
  pageSize: {
    type: Number
  },
  url: {
    type: String
  }
})

const emit = defineEmits(['pageClick', 'handleSearch'])

const detailsClick = (item) => {
  store.commit("updateOrderId", item)
  router.push(`/home/details?id=${item.id}`).catch((err) => err)
}

const pageClick = (val) => {
  emit('pageClick', val)
}

const handleSearch = () => {
  emit('handleSearch', searchValue.value)
}

const handleDetail = (item) => {
  router.push(`/expertGuidance/guideDetails/${item.id}`)
}

const handleTopicDetail = (val) => {
  searchValue.value = val
  handleSearch()
}
</script>

<style lang="less" scoped>
/* 整体搜索和筛选容器 */
.search-container {
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  align-items: center;
}

.search-input {
  width: 700px;
  padding: 10px 20px;
  border-radius: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.search-icon {
  cursor: pointer;
}

.button-group {
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

/* 商品展示列表 */
.goods {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.info {
  background-color: #fff;
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
}

.content {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

.question-header {
  display: flex;
  align-items: center;
}

.question-text {
  background-color: #ff4f16;
  color: white;
  font-weight: bold;
  border-radius: 50%;
  padding: 10px;
  width: 30px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
}

.question-content {
  font-weight: bold;
  cursor: pointer;
  color: #333;
  &:hover {
    text-decoration: underline;
    color: #035D1C;
  }
}

.answer-container {
  display: flex;
  margin-top: 10px;
}

.answer-text {
  background-color: #23c6c6;
  color: white;
  font-weight: bold;
  border-radius: 50%;
  padding: 10px;
  width: 30px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
}

.answer-content {
  color: #333;
  font-size: 14px;
  line-height: 20px;
  width: 100%;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}

.person-contents {
  font-size: 14px;
  color: #666;
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
  align-items: center;
}

/* 分页样式 */
.pagination {
  margin-top: 30px;
  display: flex;
  justify-content: center;
  padding: 20px;
}

.goods-box3 {
  margin-top: 40px;
  width: 100%;
  padding: 0 20px;
}
</style>





<template>
  <div>
    <Subtitle subtitle="专家列表" description="欢迎咨询专业人士 ~" style="margin-top:10px;" />

    <div class="allExpert-page">
      <expert-source :cgoods="goods" @handleSearch="handleSearch"></expert-source>
      <pagination @item-click="pageClick" :cUrl="url" :cTotal="total" :cPageSize="pageSize"></pagination>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { selectExpert } from "../../api/order";
import ExpertSource from "./ExpertSource.vue";
import Pagination from "../../components/Pagination.vue";
import Subtitle from "../../components/Subtitle.vue";

// 使用store
const store = useStore()

// 响应式数据
const goods = ref([])
const total = ref(0)
const pageSize = ref(30)
const searchValue = ref('')
const url = ref("/order/goods/")
const goodsCount = ref(sessionStorage.getItem("/order/goods/pageCode")
    ? sessionStorage.getItem("/order/goods/pageCode")
    : 1)

// 方法定义
const pageClick = (item) => {
  goods.value = item;
}

const handleSearch = (val) => {
  searchValue.value = val
  getData()
}

const getData = () => {
  selectExpert({
    pageNum: goodsCount.value,
    keys: searchValue.value
  }).then((res) => {
    if (res.flag) {
      goods.value = res.data.list;
      total.value = res.data.total;
    } else {
      // alert(res.data.data);
    }
  });
}

// 生命周期钩子
onMounted(() => {
  getData()
})

// 组件注册
// 在<script setup>中导入的组件会自动注册
</script>

<style lang="less" scoped>
.allExpert-page {
  width: 100%;
  margin: 0 auto;

  // float: left;
}
</style>