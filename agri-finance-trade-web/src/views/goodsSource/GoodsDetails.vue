<!--从首页进入，关于商品细节的页面-->

<template>
  <NavigationBar />
  <div class="details-box">
    <div class="image-section">
      <img v-if="data.picture" :src="$store.state.imgShowRoad + '/file/upload/order/' + data.picture" alt="" />
      <img v-else src="../../assets/img/wutu.gif" alt="" class="no-image"/>
    </div>
    <div class="info">
      <h4 class="title">{{ data.title }}</h4>
      <div class="content" :title="data.content">{{ data.content }}</div>
      <span class="price">￥{{ data.price }}</span>
      <div class="time">
        <span style="margin-right:30px;">发布时间：{{ formatTime(data.createTime) }}</span>
        <span>最近修改时间：{{ formatTime(data.updateTime) }}</span>
      </div>
      <div class="item-style">
        <div class="operation">
          <div class="operation-item"><img src="../../assets/img/good.png" class="operation-img" alt="" /> 点赞</div>
          <div class="operation-item"><img src="../../assets/img/no-star.png" class="operation-img" alt="" /> 收藏</div>
          <div class="operation-item"><img src="../../assets/img/fill-in.png" class="operation-img" alt="" />评论</div>
        </div>
        <div class="btn-content">
          <!-- 修改：调整加入购物车按钮样式，增加尺寸和悬停效果 -->
          <el-button type="success" plain round @click="addShopcartClick" v-if="data.type === 'goods'" class="cart-button" size="large">加入购物车</el-button>
          <el-popover placement="right" width="320" trigger="hover">
            <div>
              <div class="item-sales">卖家姓名：<span class="sales-text">{{updateUserData.userName}}</span></div>
              <div class="item-sales">卖家地址：<span class="sales-text">{{updateUserData.address}}</span></div>
              <div class="item-sales">卖家手机号码：<span class="sales-text">{{updateUserData.phone}}</span></div>
              <div class="item-sales">更新时间：<span class="sales-text">{{ formatTimer(updateUserData.updateTime) }}</span></div>
            </div>
            <template #reference>
              <el-button type="danger" v-show="data.type === 'needs'">联系买家</el-button>
            </template>
          </el-popover>
        </div>
      </div>
    </div>
  </div>
  <Footer />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { addOrderToCart } from "../../api/cart";
import { selectOrderById } from "../../api/order";
import { selectUserByUsername } from "../../api/user";
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

const store = useStore()
const route = useRoute()
const router = useRouter()

const data = ref({})
const updateUserData = ref({})

// 格式化时间的函数（替代Vue 2的filters）
const formatTime = (time) => {
  if (!time) return ''
  return time.slice(0, 10);
}

const formatTimer = (value) => {
  if (!value) return ''
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

const addShopcartClick = () => {
  addOrderToCart({
    order_id: data.value.orderId,
  }).then((res) => {
      if (res.flag) {
        ElMessage.success(res.message);
      } else {
        ElMessage.error(res.message);
      }
    })
    .catch((err) => {
      // 修复错误处理逻辑，统一使用ElMessage显示错误信息
      if (err.response && err.response.status === 401) {
        ElMessage.error("添加失败,请先登录");
      } else {
        ElMessage.error("添加失败，请稍后重试");
      }
    });
}

const changeInfo = (item) => {
  store.commit("updateChangedOrderId", item);
  selectOrderById({
    order_id: store.state.changedOrderId,
  })
    .then((res) => {
      // 更新数据处理
    })
    .catch((err) => {
      console.log(err);
    });
}

const getSalesInfo = () => {
  selectUserByUsername({
    user_name: data.value.ownName,
  }).then((res) => {
    updateUserData.value = res.data;
  }).catch(err=>{
    console.log(err);
  })
}

onMounted(() => {
  selectOrderById({
    order_id: route.query.orderId,
  }).then((res) => {
    if (res) {
      data.value = res.data;
      getSalesInfo()
    } else {
      console.log("数据格式错误");
    }
  }).catch(err => {
    console.error("获取商品详情失败:", err);
  });
})
</script>

<style lang="less" scoped>
.details-box {
  width: 1100px;
  margin: 20px auto;
  padding: 30px;
  background: #fff;
  display: flex;
  gap: 30px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  
  .image-section {
    flex: 0 0 400px;
    display: flex;
    align-items: center;
    justify-content: center;
    
    img {
      width: 100%;
      max-width: 400px;
      height: auto;
      border-radius: 6px;
      object-fit: cover;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }
    
    .no-image {
      border: 1px solid #f2f2f2;
      border-radius: 6px;
    }
  }
  
  .info {
    flex: 1;
    position: relative;
    padding: 10px 20px;
    
    .title {
      font-size: 24px;
      font-weight: bold;
      color: #333;
      margin-bottom: 15px;
      line-height: 1.3;
    }
    
    .content {
      min-height: 100px;
      border: 1px dashed #f2f2f2;
      line-height: 23px;
      padding: 15px;
      /*超出的部分隐藏*/
      overflow: hidden;
      background-color: #fafafa;
      border-radius: 4px;
      margin-bottom: 20px;
      font-size: 15px;
      color: #555;
    }
    
    .price {
      color: #ff4f16;
      font-size: 28px;
      font-weight: bold;
      margin: 15px 0;
      display: block;
    }
    
    .time {
      margin-top: 5px;
      color: #999;
      display: flex;
      justify-content: flex-start;
      font-size: 14px;
      margin-bottom: 30px;
    }
    
    .item-style{
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
      position: absolute;
      bottom: 0;
      left: 20px;
      right: 20px;
      padding-top: 20px;
      border-top: 1px solid #eee;
    }
  }
  
  .operation {
    display: flex;
    margin-top: 10px;
    
    .operation-item{
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      margin-right: 25px;
      cursor: pointer;
      transition: all 0.3s;
      color: #666;
      font-size: 14px;
      
      &:hover {
        color: #ff4f16;
        transform: translateY(-2px);
      }
      
      .operation-img{
        width: 24px;
        height: 24px;
        margin-bottom: 5px;
        border-radius: 6px;
      }
    }
  }
  
  .btn-content{
    margin-top: 10px;
    display: flex;
    gap: 15px;
    justify-content: flex-end;
  }
  
  .item-sales{
    color: #333 !important;
    line-height: 30px;
    max-height: 30px;
    
    .sales-text{
      color: #666;
    }
  }
}

@media (max-width: 768px) {
  .details-box {
    flex-direction: column;
    
    .image-section {
      flex: none;
      width: 100%;
    }
  }
}
</style>