<template>
  <NavigationBar />
  <div class="details-box">
    <!-- 商品标题 -->
    <h4 class="title">{{ data.title }}</h4>

    <!-- 商品时间信息 -->
    <div class="time">
      <span>发布时间：{{ formatChangeTime(data.createTime) }}</span>
      <span class="update-time">最近修改时间：{{ formatChangeTime(data.updateTime) }}</span>
    </div>

    <!-- 商品图片 -->
    <div class="image-container">
      <img :src="$store.state.imgShowRoad + '/file/' + data.picture" v-if="data.picture" alt="商品图片" />
      <img v-else src="../../assets/img/wutu.gif" alt="无图片" />
    </div>

    <!-- 商品描述 -->
    <div class="info">
      <div class="content" :title="data.content">{{ data.content }}</div>
    </div>

    <!-- 操作区：加入购物车或显示买家信息 -->
    <div class="item-style">
      <el-button type="success" @click="addShopcartClick" v-if="data.type === 'goods'" class="btn-cart">加入购物车</el-button>
      <el-popover placement="right" width="320" trigger="hover" v-if="data.type === 'needs'">
        <template #reference>
          <div class="contact-buyer" @click="changeInfo">
            <div class="buyer-icon">!</div> 买家信息
          </div>
        </template>
        <div class="item-sales">
          <div>买家姓名：<span class="sales-text">{{data.ownName}}</span></div>
          <div>买家地址：<span class="sales-text">{{data.address}}</span></div>
          <div>更新时间：<span class="sales-text">{{ formatTimer(data.updateTime) }}</span></div>
        </div>
      </el-popover>
    </div>
  </div>
  <Footer />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { addOrderToCart } from "../../api/cart";
import { selectOrderById } from "../../api/order";
import { selectUserByUsername } from "../../api/user";
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

const data = ref([]);
const store = useStore();
const route = useRoute();

// 格式化时间
const formatChangeTime = (time) => {
  if (time) {
    return time.slice(0, 10);
  }
  return '';
}

const formatTimer = (value) => {
  if (!value) return '';
  let date = new Date(value);
  return date.toLocaleString();
}

// 添加到购物车
const addShopcartClick = () => {
  addOrderToCart({
    order_id: data.value.orderId,
  }).then((res) => {
    if (res.flag) {
      ElMessage.success(res.message);
    } else {
      ElMessage.error(res.message);
    }
  }).catch(() => {
    ElMessage.error("添加失败，请先登录");
  });
}

// 获取买家信息
const changeInfo = () => {
  selectOrderById({ order_id: route.query.orderId })
      .then((res) => {
        if (res.flag) {
          selectUserByUsername({ user_name: res.data.ownName })
              .then((userRes) => {
                // 处理买家数据
              });
        }
      }).catch((err) => {
    console.error(err);
  });
}

// 页面加载时请求数据
onMounted(() => {
  selectOrderById({ order_id: route.query.orderId })
      .then((res) => {
        if (res.flag) {
          data.value = res.data;
        }
      }).catch((err) => {
    console.error('数据获取失败:', err);
  });
});
</script>

<style lang="less" scoped>
.details-box {
  width: 100%;
  max-width: 1200px;
  margin: 30px auto;
  padding: 30px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
}

.title {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
  text-align: center;
}

.time {
  width: 100%;
  display: flex;
  justify-content: space-between;
  color: #999;
  font-size: 14px;
}

.update-time {
  margin-left: 50px;
}

.image-container {
  width: 360px;
  height: 300px;
  margin: 20px 0;
  overflow: hidden;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.image-container img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.info {
  width: 100%;
  padding: 15px;
  margin: 20px 0;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.content {
  font-size: 16px;
  line-height: 1.6;
  color: #666;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
}

.item-style {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.btn-cart {
  padding: 12px 25px;
  font-size: 16px;
  font-weight: 500;
  background-color: #4CAF50;
  color: white;
  border-radius: 25px;
  border: none;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease, transform 0.3s ease;
}

.btn-cart:hover {
  background-color: #45a049;
  transform: scale(1.05);
}

.contact-buyer {
  display: flex;
  align-items: center;
  cursor: pointer;
  color: #fc7373;
}

.buyer-icon {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: #fc7373;
  color: white;
  text-align: center;
  line-height: 20px;
  margin-right: 10px;
}

.item-sales {
  font-size: 14px;
  color: #333;
}

.sales-text {
  color: #777;
}
</style>
