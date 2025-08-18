<!--专家指导-->
<template>
  <NavigationBar />
  <div class="font-background">
    <img src="../../assets/page2/fontback5.png" alt="">
    <img src="../../assets/page2/fontback6.png" alt="" style="margin-left: 20px;">
    <div style="color:rgb(82,181,72);font-family: 'PingFang SC';font-size: 80px;margin-top: 10px">农资专家齐聚</div>
  </div>
  <Questions />
  <div class="home-guide-container">
      <guide-source :cgoods="goods" :pageSize="pageSize" :url="url" :total="total" @handleSearch="handleSearch" @pageClick="pageClick"></guide-source>
  </div>
  <AllExpert />
  <Footer />
</template>
<script setup>
import { ref, onMounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import {selectQuestions, selectExpert, selectGoodsPage} from "../../api/order";
import GuideSource from "./GuideSource.vue";
import Subtitle from "../../components/Subtitle.vue";
import Questions from "./Questions.vue";
import AllExpert from "./AllExpert.vue";
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

// 使用 Vue 3 Composition API
const store = useStore()
const router = useRouter()

// 响应式数据
const goods = ref([])
const total = ref(0)
const searchValue = ref('')
const pageSize = ref(30)
const url = ref("/order/goods/")
const expertCount = ref(1)
const guideCount = ref(sessionStorage.getItem("/order/goods/pageCode")
  ? sessionStorage.getItem("/order/goods/pageCode")
  : 1)
const expertArray = ref([])

// 方法定义
const pageClick = (item) => {
  goods.value = item;
}

const handleSearch = (val) => {
  searchValue.value = val
  getData()
}

const getData = () => {
  selectQuestions({
    pageNum: guideCount.value,
    keys: searchValue.value
  }).then((res) => {
    if (res.flag) {
      goods.value = res.data.list;
      total.value = res.data.total;
    } else {
      alert('数据格式错误');
    }
  }).catch((error) => {
    console.error('API 调用失败:', error);
  });
}

const getExpertData = () => {
  selectExpert({
    pageNum: expertCount.value,
    keys: searchValue.value
  }).then(res => {
    if(res.flag){
      expertArray.value = res.data.list
    }else{
      ElMessage.error(res.message);
    }
  }).catch(err=>{
    console.log(err)
  })
}

const handleQuestion = (item) => {
  if(localStorage.getItem('token')){
    router.push(`/home/question?id=${item.userName}`).catch((err) => err);
  }else{
    ElMessage.error('请先登录')
    router.push(`/login`).catch((err) => err);
  }
}

const handleAppointment = (item) => {
  if(localStorage.getItem('token')){
    router.push(`/home/appointment?id=${item.userName}`).catch((err) => err);
  }else{
    ElMessage.error('请先登录')
    router.push(`/login`).catch((err) => err);
  }
}

// 生命周期钩子
onMounted(() => {
  if (store.state.token == "") {
    // 原代码中这里似乎有问题，只写了 alert;
  }
  getData()
  getExpertData()
})

// 组件注册 (在<script setup>中导入的组件会自动注册)
</script>

<style lang="less" scoped>
.font-background {
  background-image: url("../../assets/page5/background5.jpg");
  height: 350px;
  width: 100%;
  max-width: 100%;
  padding-left: 350px;
  padding-right: 800px;
  padding-top: 30px;
  position: relative;
  background-repeat: no-repeat;
  background-size: cover;
  box-sizing: border-box;
  overflow: hidden; /* 超出部分隐藏 */
}

.home-guide-container{
  width: 1100px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
}

.epert {
  min-height: 80px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 20px;
  align-items: center;
  // padding: 10px 10px;
  img {
    width: 80px;
    height: 80px;
    margin-right: 10px;
    border-radius: 6px;
  }
  .item-style{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
  p {
    margin: 5px;
  }
  .content{
    color:#333;
    font-size:12px;
    font-style:normal;
    width: 120px;
    line-height: 25px;
  }
  .item-content{
    width: 220px;
    line-height: 25px;
    /*超出的部分隐藏*/
    overflow: hidden;
    /*文字用省略号替代超出的部分*/
    text-overflow: ellipsis;
    word-break: break-all;
    /*弹性伸缩盒子模型显示*/
    display: -webkit-box;
    /*限制在一个块元素显示文本的行数*/
    -webkit-line-clamp: 2;
    /*设置或检索伸缩盒对象的子元素排列方式*/
    -webkit-box-orient: vertical;
  }
  .item-btn{
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    margin-bottom: 10px;
    margin-right: 10px;
    color: #67C23A;
    .btn-item{
      margin-left: 10px;
      cursor: pointer;
      &:hover{
        text-decoration: underline;
        color: #035D1C;
      }
    }
  }
}
</style>
