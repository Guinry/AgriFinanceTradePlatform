<template>
  <NavigationBar />
  <div class="finance-container">
    <div class="page-header">
      <h1 class="page-title">农业金融产品</h1>
      <p class="page-description">选择合适的银行和金融产品，助力您的农业发展</p>
    </div>
    <div class="goods-box5">
      <div
        class="goods"
        v-for="(item,index) in goods"
        :key="index"
        @click="DetailBtn(item)"
      >
        <img class="imgs" :src="item.icon" :alt="item.bankName" />
        <div class="bank-info">
          <h3 class="bank-name">{{ item.bankName }}</h3>
        </div>
      </div>
    </div>
  </div>
  <Footer />
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
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

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
  router.push(`/financeDetails?bankId=${item.bankId}`).catch((err) => err);
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
.finance-container {
  min-height: calc(100vh - 200px);
  background-color: #f5f7fa;
  padding: 20px 0;
}

.page-header {
  width: 1100px;
  margin: 0 auto 30px;
  text-align: center;

  .page-title {
    font-size: 28px;
    color: #333;
    margin-bottom: 10px;
    font-weight: 600;
  }

  .page-description {
    font-size: 16px;
    color: #666;
  }
}

.goods-box5 {
  width: 1100px;
  margin: 0 auto;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: flex-start;
  flex-wrap: wrap;
  gap: 25px;

  .goods {
    width: 220px;
    background-color: white;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    transition: all 0.3s ease;
    cursor: pointer;
    overflow: hidden;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 12px 20px rgba(0, 0, 0, 0.15);
    }

    .imgs {
      width: 100%;
      height: 140px;
      object-fit: contain;
    }

    .bank-info {
      padding: 15px;

      .bank-name {
        font-size: 18px;
        color: #333;
        margin: 0 0 8px;
        font-weight: 600;
        text-align: center;
      }

      .bank-id {
        font-size: 14px;
        color: #999;
        margin: 0;
        text-align: center;
      }
    }
  }
}
</style>