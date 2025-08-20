<template>
  <div class="user-buy">
    <div
      class="buy"
      v-for="(item, index) in userBuyList"
      :key="index"
      :style="(index + 1) % 2 === 0 ? 'margin-right:0' : 'margin-right:20px'"
    >
      <img
        v-if="item.picture"
        :src="$store.state.imgShowRoad + '/file/upload/order/' + item.picture"
        alt=""
      />
      <img
        v-else
        src=""
        style="border: 1px solid #f2f2f2"
      />
      <div class="right-content">
        <div class="right-item">
          姓名：<span class="item-text">{{ item.ownName }}</span>
        </div>
        <div class="right-item">
          创建时间：<span class="item-text">{{
            formatTimer(item.createTime)
          }}</span>
        </div>
        <div class="right-item">
          更新时间：<span class="item-text">{{
            formatTimer(item.updateTime)
          }}</span>
        </div>
        <div class="right-item" :title="item.address">
          <div class="item-text">{{ item.address }}</div>
        </div>
        <div class="item-content">
          <span class="price">￥ {{ item.totalPrice }}</span>
          <el-tag :type="item.type === 0 ? 'danger' : 'success'" size="small">{{
            item.type === 0 ? "未发货" : "已发货"
          }}</el-tag>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { selectBuyByUserName } from "../../../api/order";

// 使用 Composition API
const store = useStore()

// 响应式数据
const userBuyList = ref([])

// 过滤器转换为方法
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

// 生命周期钩子
onMounted(() => {
  store.commit("updateUserActiveIndex", "3-1");
  selectBuyByUserName({}).then((res) => {
    userBuyList.value = res.data;
  });
})
</script>

<style lang="less" scoped>
.user-buy {
  margin: 0 auto;
  width: 900px;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  flex-wrap: wrap;
  background: #fff;
  padding: 0px 20px;
  .buy {
    box-sizing: border-box;
    width: 420px;
    margin: 20px 0 10px;
    height: 180px;
    border: 1px solid #f2f2f2;
    border-radius: 6px;
    display: flex;
    flex-direction: row;
    padding-right: 10px;
    justify-content: space-between;
    img {
      width: 200px;
      height: 180px;
      margin-right: 10px;
      border-radius: 6px;
    }
    .right-content {
      .right-item {
        line-height: 30px;
        height: 30px;
        .item-text {
          color: #666;
          width: 190px;
          height: 50px;
          /*超出的部分隐藏*/
          overflow: hidden;
          /*文字用省略号替代超出的部分*/
          text-overflow: ellipsis;
          word-break: break-all;
          white-space: nowrap;
        }
      }
      .price {
        font-size: 16px;
        color: red;
      }
      .item-content {
        display: flex;
        justify-content: space-between;
        align-content: center;
        margin-top: 5px;
      }
    }
  }
}
</style>
