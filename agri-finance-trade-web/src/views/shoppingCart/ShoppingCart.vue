<template>
  <NavigationBar />
  <div class="cart">
    <receiving-address ref="address"></receiving-address>
    <el-table
      ref="multipleTableRef"
      :data="dataArray"
      tooltip-effect="dark"
      style="width: 100%;margin-top: 20px;"
      :reserve-selection="true"
      :row-key="(row) => { return row.id }"
      @selection-change="handleSelectionChange"
      :row-class-name="rowClassName">
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column label="商品">
        <template #default="scope">
          <div class="goods">
            <img :src="$store.state.imgShowRoad + '/file/' + scope.row.picture" alt=""/>
            <div class="info">
              <h4 class="title">{{ scope.row.title }}</h4>
              <p class="content">{{ scope.row.content }}</p>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="单价" width="120">
        <template #default="scope">
          <p class="price">￥{{ scope.row.price }}</p>
        </template>
      </el-table-column>
      <el-table-column label="数量" width="180">
        <template #default="scope">
          <el-input-number v-model="scope.row.count" @change="(val) => handleChange(val, scope.row)" :min="1" :max="1000000000" style="width:120px;"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column label="操作" show-overflow-tooltip width="100">
        <template #default="scope">
          <el-button type="info" class="delete-bar" @click="deleteClick(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 20px" class="cancle">
      <el-button @click="toggleSelection()">取消选择</el-button>
    </div>
    <div class="submit">
      <div class="total-price">
        <span>总价:￥{{ totalprice }}</span>
      </div>
      <el-button type="danger" @click="payment" class="place-order">提交订单</el-button>
    </div>
  </div>
  <Footer />
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { alipayRoad } from "../../api/alipay";
import { cartDeleteOrder, cartShow, updateGoodsCount } from "../../api/cart";
import ReceivingAddress from "../../components/ReceivingAddress.vue";
import { selectDefaultByOwnName } from "../../api/address";
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

// 使用 Composition API
const store = useStore()
const router = useRouter()

// 响应式数据
const dataArray = ref([])
const totalprice = ref("")
const addressData = ref({})
const multipleSelection = ref([])
const multipleTableRef = ref(null)
const address = ref(null)

// 定义 reload 方法
const reload = () => {
  router.go(0)
}

// 提供 reload 方法给子组件使用
defineExpose({
  reload
})

// 监听器
watch(multipleSelection, (val1, val2) => {
  console.log('11111val1', val1)
  console.log('1111val2', val2)
})

watch(dataArray, (val) => {

})

// 方法定义
// 获取当前的行数
const rowClassName = ({ row, rowIndex }) => {
  // 把每一行的索引放进row
  row.index = rowIndex;
}

const payment = () => {
  console.log('this.multipleSelection', multipleSelection.value)
  if (multipleSelection.value && multipleSelection.value.length > 0) {
    alipayRoad({
      addId: addressData.value.id,
      tMoney: totalprice.value,
      shoppingModelList: multipleSelection.value
    })
      .then((res) => {
        store.commit("updatePaymentInfo", res);
        // 打开新页面
        window.open(res.data);
        reload();
      })
      .catch((err) => {
        console.log(err);
      });
  } else {
    ElMessage.error('请选择商品')
  }
}

const deleteClick = (row) => {
  ElMessageBox.confirm('确认删除该商品?', '删除', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    cartDeleteOrder({
      order_id: row.shoppingId,
    }).then((res) => {
      ElMessage.success("删除成功");
      getOrderList()
    }).catch((err) => {
      ElMessage.error("删除失败");
    });
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '已取消删除'
    });
  });
}

const toggleSelection = (rows) => {
  if (rows) {
    rows.forEach((row) => {
      multipleTableRef.value.toggleRowSelection(row);
    });
  } else {
    multipleTableRef.value.clearSelection();
  }
}

const handleSelectionChange = (val) => {
  multipleSelection.value = val;
  localStorage.setItem('multipleSelection', JSON.stringify(multipleSelection.value))
  let sum = 0
  val.forEach(e => {
    sum = sum + (Number(e.price) * e.count)
    totalprice.value = sum
    console.log('this.totalprice', totalprice.value)
  })
}

const calPrice = () => {
  let sum = 0
  multipleSelection.value = JSON.parse(localStorage.getItem('multipleSelection'))
  if (multipleSelection.value && multipleSelection.value.length > 0) {
    dataArray.value.forEach(e => {
      multipleSelection.value.forEach(e1 => {
        if (e.shoppingId === e1.shoppingId) {
          console.log('e1.shoppingId', e.count, e1.shoppingId)
          sum = sum + (Number(e1.price) * e.count)
          totalprice.value = sum
          console.log('---this.totalprice', totalprice.value)
        }
      })
    })
  }
}

// 更新商品数量  /cart/update/{id}/{count}
const handleChange = (val, row) => {
  if (multipleSelection.value && multipleSelection.value.length > 0) {
    multipleSelection.value.forEach(e => {
      if (e.shoppingId === row.shoppingId) {
        e.count = val
      }
    })
    localStorage.setItem('multipleSelection', JSON.stringify(multipleSelection.value))
  }

  updateGoodsCount({
    count: val,
    id: row.shoppingId
  }).then(res => {
    multipleSelection.value = JSON.parse(localStorage.getItem('multipleSelection'))
    calPrice()
  }).catch(err => {
    console.log(err)
  })
}

const getOrderList = () => {
  cartShow({}).then((res) => {
    if (res.flag == true) {
      dataArray.value = res.data;
      totalprice.value = 0;
    } else {
      ElMessage.error('您未登录，请先登录')
      router.push("/login")
    }
  })
    .catch((err) => {
      console.log(err);
    });
}

// 生命周期钩子
onMounted(() => {
  if (store.state.token == "") {
    alert;
  }
  getOrderList()
  store.commit("updateActiveIndex", "6");
  selectDefaultByOwnName({})
    .then((res) => {
      addressData.value = res.data;
    })
    .catch((err) => {
      console.log(err);
    });
})
</script>

<style lang="less" scoped>
.cart {
  width: 1100px;
  margin: 10px auto;
  background: #fff;
  padding: 10px 20px;
  .goods {
    width: 500px;
    img {
      float: left;
      width: 100px;
      height: 100px;
      margin-right: 20px;
      border-radius: 6px;
    }
    .info {
      // float: left;
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
      align-items: flex-start;
      .title {
        margin: 0;
      }
      .content {
        height: 65px;
        // width: 200px;
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
    }
  }
  .price,
  .subtotal {
    color: red;
  }
  .count {
    .itxt {
      width: 40px;
      height: 30px;
      margin: 0 10px;
    }
    button {
      display: inline-block;
      width: 20px;
      height: 20px;
      padding: 0;
      text-align: center;
      line-height: 20px;
      border: 1px solid darkgray;
      font-size: 20px;
    }
  }
  .cancle {
    float: left;
  }
  .submit {
    margin-top: 20px;
    float: right;
    width: 300px;
    height: 40px;
    .total-price {
      font-size: 20px;
      float: left;
      color: red;
      // margin-right: 50px;
      line-height: 40px;
    }
    .place-order {
      float: right;
    }
  }
}
.delete-bar {
  width: 60px;
  height: 30px;
  padding: 0;
  span {
    margin: 0;
  }
}
</style>