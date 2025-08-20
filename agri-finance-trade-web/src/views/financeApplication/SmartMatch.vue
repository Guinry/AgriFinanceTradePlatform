<template>
  <NavigationBar />
  <div class="details-box2">
    <smart-match-user></smart-match-user>
  </div>
  <Footer />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { selectRecommend } from "../../api/finance";
import SmartMatchUser from '../../components/SmartMatchUser.vue';
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

// 响应式数据
const intentionData = ref({
  amount: '',
  application: '',
  item: '',
  repaymentPeriod: '',
  address: ''
});

const allRecommendData = ref([]);

// 计算属性替代 filters
const changeTime = (time) => {
  return time.slice(0, 10);
};

// 方法定义
const getAllRecommend = () => {
  selectRecommend().then(res => {
    if (res.flag) {
      console.log('ressss', res);
      allRecommendData.value = res.data;
    } else {
      ElMessage.error('您未登录，请先登录');
    }
  }).catch(err => {
    console.log(err);
  });
};

// 生命周期钩子
onMounted(() => {
  // getAllRecommend();
  // 注意：这里的form变量在原代码中未定义，需要根据实际需求处理
  // form = Object.assign({}, {...JSON.parse(localStorage.getItem('financeObj'))});
});

</script>

<style lang="less" scoped>
/* 页面整体布局优化 */
.details-box2 {
  max-width: 1200px;
  margin: 30px auto;
  padding: 30px;
  background-color: #ffffff;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  overflow: hidden;
  position: relative;
}

.details-box2 img {
  width: 300px;
  height: 300px;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  margin-right: 20px;
}

/* 卡片内容优化 */
.title {
  font-size: 22px;
  font-weight: 600;
  color: #333;
  margin-bottom: 20px;
  border-bottom: 2px solid #f2f2f2;
  padding-bottom: 15px;
}

.introduce {
  color: #666;
  line-height: 1.6;
  max-height: 120px;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-bottom: 20px;
}

/* 调整每一项内容 */
.item-content {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.item-title {
  font-weight: 700;
  color: #333;
  margin-right: 10px;
}

.item-text {
  color: #777;
  font-size: 16px;
}

/* 商品列表样式 */
.goods-box {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 20px;
  margin-top: 30px;
}

.goods {
  width: calc(33.333% - 20px);
  background-color: #fff;
  border-radius: 15px;
  padding: 15px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
  cursor: pointer;
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}

.goods:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.2);
}

.goods-img {
  width: 90px;
  height: 90px;
  margin-bottom: 15px;
  border-radius: 50%;
  object-fit: cover;
  margin: 0 auto;
}

.goods .info {
  font-size: 14px;
  color: #444;
}

.goods .initiator1 {
  color: #999;
}

/* 交互按钮样式 */
.operation {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  justify-content: center;
  gap: 15px;
}

.like,
.collection,
.comment {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  background-color: #f2f2f2;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.like:hover,
.collection:hover,
.comment:hover {
  background-color: #ebebeb;
}

.add-shopcart {
  position: absolute;
  right: 30px;
  bottom: 10px;
}

.add-btn {
  font-size: 16px;
  padding: 10px 20px;
  background-color: #67c23a;
  color: white;
  border-radius: 50px;
  transition: background-color 0.3s ease;
}

.add-btn:hover {
  background-color: #58a72a;
}
</style>
