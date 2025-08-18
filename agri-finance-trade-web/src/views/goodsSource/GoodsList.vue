<template>
  <div class="goods-box">
    <div class="search2">
      <el-input placeholder="请输入内容" v-model="searchValue" maxlength="100" clearable style="width:250px;margin-right: 5px">
        <template #prefix>
          <i class="el-input__icon el-icon-search search-icon" @click="handleSearch"></i>
        </template>
      </el-input>

      <el-button-group>
        <el-button round @click="handleTopicDetail('')">全部</el-button>
        <el-button round @click="handleTopicDetail('苹果')"> 苹果</el-button>
        <el-button round @click="handleTopicDetail('新疆哈密瓜')" style="width: 120px">新疆哈密瓜</el-button>
        <el-button round @click="handleTopicDetail('樱桃')"> 樱桃</el-button>
        <el-button round @click="handleTopicDetail('西红柿')"> 西红柿</el-button>
        <el-button round @click="handleTopicDetail('水稻')"> 水稻</el-button>
        <el-button round @click="handleTopicDetail('玉米')"> 玉米</el-button>
        <el-button round @click="handleTopicDetail('赣南脐橙')" style="width: 110px">赣南脐橙</el-button>
      </el-button-group>
    </div>

    <!-- 添加无数据时的显示 -->
    <div v-if="cgoods.length === 0" class="no-goods-container">
      <img class="no-goods-img" src="../../assets/img/no-goods.png" alt="暂无商品" />
    </div>

    <div v-else class="goods" v-for="(item, index) in cgoods" :key="index" @click="detailsClick(item.orderId)">
      <div class="image-container">
        <el-tooltip class="box-item" effect="dark" :content="item.content" placement="top-start">
          <img class="goods-img" v-if="item.picture !== ''" :src="getImagePath(item.picture)" alt="" />
          <img class="goods-img" v-if="item.picture === ''" :src="getImagePath('/wutu.gif')" alt="" />
        </el-tooltip>
      </div>

      <div class="info">
        <!-- 修改: 使用 el-tooltip 显示完整标题 -->
        <el-tooltip class="box-item" effect="dark" :content="item.content" placement="top">
          <p class="content">{{ item.content }}</p>
        </el-tooltip>
        <div class="price-container">
          <span class="price">￥{{ item.price }}</span>
        </div>
      </div>
      
      <el-tooltip class="box-item" effect="dark" content="加入购物车" placement="top-start">
        <img class="icon" src="../../assets/img/adding.svg" @click.stop="addShopcartClick(item.orderId)" alt="加入购物车" />
      </el-tooltip>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { addOrderToCart } from "../../api/cart";

// 定义 props
const props = defineProps({
  cgoods: {
    type: Array,
    default: () => []
  }
})

// 定义 emits
const emit = defineEmits(['handleSearch'])

// 响应式数据
const searchValue = ref('')

// 使用 store 和 router
const store = useStore()
const router = useRouter()

// 方法定义
const getImagePath = (picturePath) => {
  const fullPath = store.state.imgShowRoad + '/file/' + picturePath;
  return fullPath;
};

const detailsClick = (item) => {
  store.commit("updateOrderId", item);
  router.push(`/goodsSource/goodsDetails?orderId=${item}`).catch((err) => err);
}

const handleSearch = () => {
  emit('handleSearch', searchValue.value)
}

const handleTopicDetail = (val) => {
  searchValue.value = val
  handleSearch()
}

const addShopcartClick = (val) => {
  addOrderToCart({
    order_id: val,
  })
      .then((res) => {
        if (res.flag) {
          ElMessage.success(res.message);
        } else {
          ElMessage.error(res.message);
        }
      })
      .catch((err) => {
        ElMessage.error("添加失败,请先登录");
      });
}
</script>

<style lang="less" scoped>
.search2 {
  height: 60px;
  background-color: white;
  padding: 10px 10px;
  margin-top: 10px;
  .search-icon {
    position: relative;
    bottom: 2px;
    left: -2px;
    cursor: pointer;
  }
}

.goods-box {
  background-color: #f9f9f9;
  width: 1100px;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: center; /* 使商品卡片水平居中 */
  gap: 30px; /* 增加卡片之间的间距 */
  padding: 20px 0;
}

// 添加无数据时的样式
.no-goods-container {
  width: 100%;
  text-align: center;
  padding: 50px 0;
  
  .no-goods-img {
    width: 200px;
    height: 200px;
    object-fit: contain;
  }
  
  .no-goods-text {
    font-size: 18px;
    color: #999;
    margin-top: 20px;
  }
}

.goods {
  width: 240px;
  height: 370px;
  background-color: white;
  border: 1px solid #d3d3d3;
  cursor: pointer;
  border-radius: 10px;
  position: relative;
  overflow: hidden; /* 防止溢出 */
  transition: all 0.3s ease;
  margin-top: 5px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);

  &:hover {
    transform: translateY(-5px) scale(1.02);
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
    border-color: #ff4f16;
  }

  .image-container {
    position: relative;
  }

  .goods-img {
    width: 100%;
    height: 240px;
    border-radius: 6px 6px 0 0;
    object-fit: cover;
    margin-bottom: 0; /* 减少图片和标题之间的间隔 */
    transition: transform 0.3s ease;
  }

  &:hover .goods-img {
    transform: scale(1.05);
  }

  .info {
    padding: 5px 10px;
    text-align: left;

    .content {
      font-size: 16px;
      font-family: "PingFang SC", sans-serif;
      font-weight: 600;
      color: #333;
      margin-bottom: 10px;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2; /* 限制为2行 */
      -webkit-box-orient: vertical;
      height: 45px; /* 限制最大高度，避免标题超出 */
    }

    .price-container {
      display: flex;
      justify-content: flex-start; /* 让价格靠左对齐 */
      align-items: center;
      margin-top: 15px;
    }

    .price {
      font-size: 18px;
      font-weight: bold;
      color: #ff4f16;
      margin-right: 5px;
      font-size: 20px;
    }
  }

  /* 修改：icon 使用图片，调整为右下角对齐并在图片范围内，减小尺寸 */
  .icon {
    position: absolute;
    bottom: 8px;
    right: 8px; /* 右下角对齐 */
    padding: 0.5em;
    width: 25px;
    height: 25px;
    background-color: rgba(255, 255, 255, 0.8); /* 添加背景色以便更好地显示 */
    border-radius: 50%;
    cursor: pointer;
    transition: all 0.3s ease;
    z-index: 10; /* 确保在图片上方 */
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

    &:hover {
      transform: rotate(15deg) scale(1.2);
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
      background-color: #fff8f0;
    }
  }
}

:deep(.el-input__inner) {
  height: 35px;
  line-height: 35px;
}

:deep(.el-button-group .el-button) {
  border-radius: 100px;
  margin: 0 5px;
  border: 1px solid #DCDFE6;
  &:hover {
    text-decoration: underline;
    background-color: #ffffff;
    color: black;
  }
  &:focus {
    color: #ff4f16;
    background-color: #ffffff;
  }
}
</style>