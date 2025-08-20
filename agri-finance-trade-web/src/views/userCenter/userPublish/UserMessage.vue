<template>
  <div class="published-message">
    <div class="header-section">
      <el-button type="success" plain round @click="publishClick" class="publish-btn">
        <el-icon><Plus /></el-icon>
        发布{{ cdesciibe }}
      </el-button>
      <div class="search-wrapper">
        <el-input
            placeholder="请输入商品内容进行搜索"
            v-model="searchValue"
            maxlength="100"
            clearable
            class="search-input"
        >
          <template #prefix>
            <el-icon class="search-icon" @click="handleSearch"><Search /></el-icon>
          </template>
        </el-input>
        <el-button type="primary" plain @click="handleSearch" class="search-btn">
          <el-icon><Search /></el-icon>
          搜索
        </el-button>
      </div>
    </div>

    <div class="content-section">
      <div class="message-grid" v-if="userGoods.length > 0">
        <div class="message-card" v-for="(item, index) in userGoods" :key="index">
          <div class="image-container">
            <img
                v-if="item.picture"
                :src="$store.state.imgShowRoad + '/file/upload/order/' + item.picture"
                class="product-image"
            />
            <img v-else src="../../../assets/img/wutu.gif" class="product-image" />
          </div>
          <div class="info">
            <div class="title">{{ item.title }}</div>
            <div class="item-style">
              发起人：<span class="initiator">{{ item.ownName }}</span>
            </div>
            <div class="content initiator">{{ item.content }}</div>
            <div class="price">￥ {{ item.price }}</div>
          </div>

          <div class="card-footer">
            <div class="published-message-operation">
              <span @click.once="changeInfo(item.orderId)" class="operation-btn edit-btn">
                <el-icon><Edit /></el-icon>
                编辑
              </span>
              <span @click.stop="deleteInfo(item.orderId)" class="operation-btn delete-btn">
                <el-icon><Delete /></el-icon>
                删除
              </span>
            </div>
          </div>
        </div>
      </div>

      <el-empty v-else description="暂无发布内容" class="empty-state"></el-empty>

      <div class="pagination-wrapper" v-if="total > pageSize">
        <Pagination
            @item-click="pageClick"
            :cUrl="url + ctype + '/'"
            :cTotal="total"
            :cPageSize="pageSize"
        ></Pagination>
      </div>
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
import { Plus, Search, Edit, Delete } from '@element-plus/icons-vue'

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
      // 跳转到编辑页面
      router.push("/publish" + props.ctype).catch((err) => console.log(err));
    })
    .catch((err) => {
      console.log(err);
    });
}

const deleteInfo = (item) => {
  store.commit("updateChangedOrderId", item);
  // 显示删除确认对话框
  ElMessageBox.confirm(
    '确定要删除这条信息吗？',
    '删除确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(() => {
    // 调用API删除订单
    deleteOrderById({order_id: item})
      .then((res) => {
        if (res.flag) {
          ElMessage.success('删除成功');
          // 重新加载数据
          getData();
        } else {
          ElMessage.error(res.message || '删除失败');
        }
      })
      .catch(() => {
        ElMessage.error('删除失败，请稍后重试');
      });
  }).catch(() => {
    ElMessage.info('已取消删除');
  });
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
.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 8px;

  .publish-btn {
    padding: 12px 24px;
    font-size: 14px;
  }

  .search-wrapper {
    display: flex;
    align-items: center;
    gap: 10px;

    .search-input {
      width: 300px;

      :deep(.el-input__wrapper) {
        border-radius: 20px;
      }
    }

    .search-btn {
      border-radius: 20px;
    }
  }
}

.content-section {
  .message-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
    gap: 20px;
    margin-bottom: 30px;
  }

  .message-card {
    border: 1px solid #ebeef5;
    border-radius: 8px;
    overflow: hidden;
    transition: all 0.3s ease;
    background: #fff;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.15);
    }

    .image-container {
      height: 200px;
      overflow: hidden;

      .product-image {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
    }

    .info {
      padding: 15px;

      .title {
        font-size: 16px;
        font-weight: 600;
        margin-bottom: 10px;
        color: #333;
        height: 45px;
        /*超出的部分隐藏*/
        overflow: hidden;
        /*文字用省略号替代超出的部分*/
        text-overflow: ellipsis;
        /*弹性伸缩盒子模型显示*/
        display: -webkit-box;
        /*限制在一个块元素显示文本的行数*/
        -webkit-line-clamp: 2;
        /*设置或检索伸缩盒对象的子元素排列方式*/
        -webkit-box-orient: vertical;
      }

      .item-style {
        height: 20px;
        line-height: 20px;
        margin-bottom: 8px;
      }

      .initiator {
        color: #666;
        font-size: 14px;
      }

      .content {
        line-height: 20px;
        max-height: 60px !important;
        margin: 10px 0;
        word-break: break-all;
        /*超出的部分隐藏*/
        overflow: hidden;
        /*文字用省略号替代超出的部分*/
        text-overflow: ellipsis;
        /*弹性伸缩盒子模型显示*/
        display: -webkit-box;
        /*限制在一个块元素显示文本的行数*/
        -webkit-line-clamp: 2;
        /*设置或检索伸缩盒对象的子元素排列方式*/
        -webkit-box-orient: vertical;
      }

      .price {
        font-size: 18px;
        font-weight: bold;
        color: #fa541c;
        margin-top: 10px;
      }
    }

    .card-footer {
      border-top: 1px solid #ebeef5;
      padding: 15px;

      .published-message-operation {
        display: flex;
        justify-content: flex-end;
        gap: 15px;

        .operation-btn {
          display: flex;
          align-items: center;
          gap: 5px;
          cursor: pointer;
          padding: 5px 10px;
          border-radius: 4px;
          transition: all 0.3s;

          &.edit-btn {
            color: #409eff;

            &:hover {
              background-color: #ecf5ff;
              color: #409eff;
            }
          }

          &.delete-btn {
            color: #f56c6c;

            &:hover {
              background-color: #fef0f0;
              color: #f56c6c;
            }
          }
        }
      }
    }
  }

  .empty-state {
    padding: 50px 0;
  }

  .pagination-wrapper {
    display: flex;
    justify-content: center;
    padding: 20px 0;
  }
}

.search-icon {
  cursor: pointer;
  color: #999;
}
</style>