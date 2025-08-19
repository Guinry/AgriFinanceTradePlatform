<template>
  <div class="goods-box5">
    <div class="goods" v-for="(item,index) in goods" :key="index" @click="DetailBtn(item)">
      <img class="imgs" :src="item.icon" alt="" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { selectBank } from "../../api/finance";
import bank1001 from '../../assets/img/bank1001.jpg'
import bank1002 from '../../assets/img/bank1002.jpg'
import bank1003 from '../../assets/img/bank1003.jpg'
import bank1004 from '../../assets/img/bank1004.jpg'
import bank1005 from '../../assets/img/bank1005.jpg'
import bank1006 from '../../assets/img/bank1006.jpg'
import bank1007 from '../../assets/img/bank1007.jpg'
import bank1008 from '../../assets/img/bank1008.jpg'
import bank1009 from '../../assets/img/bank1009.jpg'
import bank1010 from '../../assets/img/bank1010.jpg'

// 使用 Composition API
const router = useRouter()

// 响应式数据
const imgArray = ref([{
  id:'1001',
  icon:bank1001
},{
  id:'1002',
  icon:bank1002
},{
  id:'1003',
  icon:bank1003
},{
  id:'1004',
  icon:bank1004
},{
  id:'1005',
  icon:bank1005
},{
  id:'1006',
  icon:bank1006
},{
  id:'1007',
  icon:bank1007
},{
  id:'1008',
  icon:bank1008
},{
  id:'1009',
  icon:bank1009
},{
  id:'1010',
  icon:bank1010
}])

const goods = ref([])
const total = ref(0)
const pageSize = ref(30)
const url = ref("/order/goods/")
const goodsCount = ref(sessionStorage.getItem("/order/goods/pageCode")
  ? sessionStorage.getItem("/order/goods/pageCode")
  : 1)

// 方法定义
const pageClick = (item) => {
  goods.value = item;
}

const DetailBtn = (item) => {
  localStorage.setItem('financeObj',JSON.stringify(item))
  router.push(`/home/financingDetails?bankId=${item.bankId}`).catch((err) => err);
}

// 数据获取
const fetchData = () => {
  selectBank({}).then((res) => {
    if (res.flag) {
      let tmp = res.data;
      if(tmp&&tmp.length>0){
        tmp.map((item2)=>{
          imgArray.value.map((item1)=>{
            if(item2.bankId == item1.id){
              Object.assign(item2,item1);
              return item2;
            }
          })
        })
        goods.value = tmp
      }
    } else {
      goods.value = []
      ElMessage.error('您未登录，请先登录')
    }
  });
}

// 生命周期
onMounted(() => {
  fetchData()
})

// 组件注册 (在<script setup>中导入的组件会自动注册)
</script>

<style lang="less" scoped>

.goods-box5 {
  background-color: #f9f9f9;
  width: 1100px;
  margin: 0 auto;
  display: flex;
  flex-direction: row;
  // justify-content: center;
  align-items: flex-start;
  flex-wrap: wrap;
  .goods {
    width: 200px;
    height: 180px;
    background-color: white;
    border-radius: 6px;
    box-shadow: 0px 0px 20px 0px rgba(0,0,0,0.1);
    margin-top: 20px;
    margin-right: 20px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    .imgs {
      width: 150px;
      height: 140px;
      margin-right: 10px;
      border-radius: 6px;
    }
  }
}
</style>
