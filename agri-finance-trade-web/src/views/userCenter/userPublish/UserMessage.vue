<!--我的发布-->
<template>
  <div class="published-message">
    <div style="display:flex;">
      <el-button type="success" plain round @click="publishClick" style="margin: 10px 20px"
      >发布{{ cdesciibe }}</el-button
      >
      <div class="search" style="text-align: right">

        <el-input  placeholder="请输入商品内容进行搜索" v-model="searchValue" maxlength="100" clearable style="width:250px;margin-right: 5px">
          <template #prefix>
            <i class="el-input__icon el-icon-search search-icon" @click="handleSearch"></i>
          </template>
        </el-input>

      </div>
    </div>

    <div
      style="
        display: flex;
        flex-wrap: wrap;
        background: #fff;
        width: 900px;
        padding: 10px 20px;
      "
    >
      <div class="message" v-for="(item, index) in userGoods" :key="index">
        <img
          v-if="item.picture"
          :src="$store.state.imgShowRoad + '/file/' + item.picture"
        />
        <img v-else src="../../../assets/img/wutu.gif" style="height: 177px" />
        <div class="info">
          <div class="title">{{ item.title }}</div>
          <div class="item-style">
            发起人：<span class="initiator">{{ item.ownName }}</span>
          </div>
          <div class="content initiator">{{ item.content }}</div>
          <div class="price">￥ {{ item.price }}</div>
          <div class="published-message-operation">
            <span @click.once="changeInfo(item.orderId)">
              <change-message
                :cupdateGoodInfo="updateGoodInfo"
              ></change-message>
            </span>
            <span @click.stop="deleteInfo(item.orderId)">
              <delete-message></delete-message>
            </span>
          </div>
        </div>
      </div>
      <Pagination
        @item-click="pageClick"
        :cUrl="url + ctype + '/'"
        :cTotal="total"
        :cPageSize="pageSize"
      ></Pagination>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { selectOrderById, selectOrderByUsernameAndType } from "../../../api/order";
import ChangeMessage from "../../../components/ChangeMessage.vue";
import DeleteMessage from "./DeleteMessage.vue";
import Pagination from "../../../components/Pagination.vue";

// 定义 props
const props = defineProps({
  ctype: {
    type: String,
  },
  cdesciibe: {
    type: String,
  },
});

// 使用 store 和 router
const store = useStore()
const router = useRouter()

// 响应式数据
const userGoods = ref([])
const updateGoodInfo = ref({})
const pageNum = ref(1)
const searchValue = ref("")
const total = ref(0)
const pageSize = ref(30)
const url = ref("/order/search/")

// 方法定义
const pageClick = (item) => {
  console.log("item", item);
  userGoods.value = item;
}

const publishClick = () => {
  router.push("/publish" + props.ctype).catch((err) => err);
}

const changeInfo = (item) => {
  store.commit("updateChangedOrderId", item);
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

const deleteInfo = (item) => {
  store.commit("updateChangedOrderId", item);
}

const handleSearch = () => {
  getData();
}

const getData = () => {
  selectOrderByUsernameAndType({
    ctype: props.ctype,
    pageNum: pageNum.value,
    keys: searchValue.value,
  })
    .then((res) => {
      userGoods.value = res.data.list;
      total.value = res.data.total;
    })
    .catch((err) => {
      console.log(err);
    });
}

// 生命周期钩子
onMounted(() => {
  getData();
});
</script>

<style lang="less" scoped>
.search {
  height: 60px;
  background-color: white;
  padding: 10px 20px;
  width: 70%;
  margin-top: 10px;
  .search-icon {
    position: relative;
    bottom: 2px;
    left: -2px;
    cursor: pointer;
  }
}
.published-message {
  width: 900px;
  margin: 0 auto;
  height: 100%;
  background: #fff;

  .message {
    box-sizing: border-box;
    width: 410px;
    height: 180px;
    border: 1px solid #f2f2f2;
    background-color: white;
    border-radius: 6px;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    margin: 10px 20px 10px 0;

    // padding: 10px 20px;
    img {
      width: 200px;
      height: 180px;
      margin-right: 10px;
      border-radius: 6px;
    }
    .info {
      margin-top: 10px;
      .item-style {
        height: 20px;
        line-height: 20px;
      }
      .initiator {
        color: #666;
      }
      .title {
        font-size: 16px;
        height: 30px;
        /*超出的部分隐藏*/
        overflow: hidden;
        /*文字用省略号替代超出的部分*/
        text-overflow: ellipsis;
        /*弹性伸缩盒子模型显示*/
        display: -webkit-box;
        /*限制在一个块元素显示文本的行数*/
        -webkit-line-clamp: 3;
        /*设置或检索伸缩盒对象的子元素排列方式*/
        -webkit-box-orient: vertical;
      }
      .content {
        width: 170px;
        line-height: 20px;
        max-height: 80px !important;
        margin-top: 10px;
        word-break: break-all;
        /*超出的部分隐藏*/
        overflow: hidden;
        /*文字用省略号替代超出的部分*/
        text-overflow: ellipsis;
        /*弹性伸缩盒子模型显示*/
        display: -webkit-box;
        /*限制在一个块元素显示文本的行数*/
        -webkit-line-clamp: 3;
        /*设置或检索伸缩盒对象的子元素排列方式*/
        -webkit-box-orient: vertical;
      }
      .price {
        font-size: 16px;
        font-weight: bold;
        display: block;
        color: red;
      }
    }
  }
  .published-message-operation {
    margin-bottom: 5px;
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    align-items: center;
  }
}
.search :deep(.el-input--suffix .el-input__inner) {
  height: 35px;
  line-height: 35px;
}
</style>