<template>
  <div class="details-box">
    <h4 class="title">{{ data.title }}</h4>
    <div class="time">
      <span>发布时间：{{ formatChangeTime(data.createTime) }}</span>
      <span style="margin-left: 50px;">最近修改时间：{{ formatChangeTime(data.updateTime) }}</span>
      <div class="item-style">
        <el-button type="danger" @click="addShopcartClick" v-if="data.type == 'goods'">加入购物车</el-button>
        <el-popover placement="right" width="320" trigger="hover">
          <div>
            <div class="item-sales">买家姓名：<span class="sales-text">{{data.ownName}}</span></div>
            <div class="item-sales">买家地址：<span class="sales-text">{{data.address}}</span></div>
            <div class="item-sales">更新时间：<span class="sales-text">{{ formatTimer(data.updateTime) }}</span></div>
          </div>
          <div style="color:#fc7373;margin-left:50px;cursor:pointer;display: flex;" slot="reference" @click="changeInfo" v-show="data.type == 'needs'">
            <div style="margin-right:10px;color:#fff;font-weight:bold;background: #fc7373;width: 20px;height: 20px;border-radius: 10px;line-height: 20px;text-align: center;">!</div>
            买家信息
          </div>
        </el-popover>
      </div>
    </div>
    <img :src="$store.state.imgShowRoad + '/file/' + data.picture" v-show="data.picture" alt="" />
    <div class="info">
      <div class="content" :title="data.content">{{ data.content }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import { ElMessage, ElPopover, ElButton } from 'element-plus'
import { addOrderToCart } from "../../api/cart";
import { selectOrderById } from "../../api/order";
import ChangeMessage from "../../components/ChangeMessage.vue";
import { selectUserByUsername } from "../../api/user";

// 响应式数据
const data = ref([])
const ownerInfo = ref({})
const userGoods = ref([])
const updateGoodInfo = ref({})
const updateUserData = ref({})

// 使用 store 和 route
const store = useStore()
const route = useRoute()

// 过滤器转换为方法
const formatChangeTime = (time) => {
  if (time) {
    return time.slice(0, 10);
  }
  return '';
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

// 定义 props
const props = defineProps({
  ctype: {
    type: String,
  },
  cdesciibe: {
    type: String,
  },
})

// 定义组件
defineExpose({
  ChangeMessage
})

// 方法定义
const addShopcartClick = () => {
  addOrderToCart({
    order_id: data.value.orderId,
  })
    .then((res) => {
      console.log(res);
      if (res.flag == true) {
        ElMessage.success(res.message);
      } else {
        ElMessage.error(res.message);
      }
    })
    .catch((err) => {
      ElMessage.error("添加失败,请先登录");
    });
}

const changeInfo = () => {
  store.commit("updateChangedOrderId", store.state.changedOrderId);
  selectOrderById({
    order_id: store.state.changedOrderId,
  })
    .then((res) => {
      updateGoodInfo.value = res.data;
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

// 生命周期钩子
onMounted(() => {
  store.commit("updateActiveIndex", "3");
  selectOrderById({
    order_id: route.query.orderId,
  }).then((res) => {
    if (res.flag == true) {
      data.value = res.data;
      getSalesInfo()
    }
  });
})
</script>

<style lang="less" scoped>
.details-box {
  width: 1100px;
  min-height: 100%;
  height: auto;
  margin: 20px auto;
  padding: 20px;
  background: #fff;
  display: flex;
  flex-direction: column;
  // align-items: center;
  img {
    width: 360px;
    height: 300px;
    margin: 50px auto 20px;
    border-radius: 6px;
  }
  .title {
    font-size: 24px;
    font-weight: bold;
    line-height: 35px;
    max-height: 70px;
    text-align: center;
    margin-bottom: 20px;
  }
  .time {
    margin-top: 5px;
    color: #999;
    display: flex;
    justify-content: flex-end;
  }
  .info {
    width: 100%;
    min-height: 300px;
    border-radius: 6px;
    padding: 10px 20px;
    margin: 0 auto;
    display: flex;
    align-content: center;
    justify-content: center;
    .content {
      min-height: 100px;
      // border: 1px dashed #f2f2f2;
      line-height: 25px;
      padding: 5px 10px;
      /*超出的部分隐藏*/
      overflow: hidden;
      /*文字用省略号替代超出的部分*/
      text-overflow: ellipsis;
      /*弹性伸缩盒子模型显示*/
      display: -webkit-box;
      /*限制在一个块元素显示文本的行数*/
      -webkit-line-clamp: 4;
      /*设置或检索伸缩盒对象的子元素排列方式*/
      -webkit-box-orient: vertical;
    }
    .item-style{
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      margin-top: 5px;
    }
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