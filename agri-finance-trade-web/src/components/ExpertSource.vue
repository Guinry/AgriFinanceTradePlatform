<template>
  <div class="goods-box4">
    <div class="search">
      <el-input placeholder="查找专家" v-model="searchValue" maxlength="100" clearable style="width:290px;">
        <template #prefix>
          <el-icon class="el-input__icon search-icon" @click="handleSearch">
            <Search />
          </el-icon>
        </template>
      </el-input>
    </div>

    <div class="goods" v-for="(item, index) in cgoods" :style="index===cgoods.length-1?'':'border-bottom:1px solid #f2f2f2;'" :key="index">
      <img :src="imgShowRoad + '/file/' + 'experta.png'" alt="" />
      <div class="info">
        <p class="content">
          <span class="info-item"><strong>姓名：</strong>{{item.realName}}</span>
          <span class="info-item"><strong>职称：</strong>{{item.position}}</span>
        </p>
        <p class="content">
          <span class="info-item"><strong>专业：</strong>{{item.profession}}</span>
          <span class="info-item"><strong>电话：</strong>{{item.phone}}</span>
        </p>
        <p class="content">
          <span class="info-item"><strong>单位：</strong>{{item.belong}}</span>
        </p>
      </div>
      <div class="info2">
        <span class="question-btn" @click="handleQuestion(item)">向我提问</span>
        <span class="question-btn appoint-btn" @click="handleAppoint(item)">线下预约</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { Search } from '@element-plus/icons-vue'

// 使用 Composition API
const store = useStore()
const router = useRouter()

// 定义 props
const props = defineProps({
  cgoods: {
    type: Array,
  },
})

// 定义 emits
const emit = defineEmits(['handleSearch'])

// 响应式数据
const searchValue = ref('')

// 计算属性替代 filters
const imgShowRoad = computed(() => store.state.imgShowRoad)

// 格式化时间函数
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

// 方法定义
const detailsClick = (item) => {
  store.commit("updateOrderId", item);
  router.push("/home/details").catch((err) => err);
}

const handleQuestion = (item) => {
  router.push(`/expertGuidance/askQuestion?id=${item.userName}`).catch((err) => err);
}

const handleAppoint = (item) => {
  router.push(`/expertGuidance/makeAppointment?id=${item.userName}`).catch((err) => err);
}

const handleSearch = () => {
  emit('handleSearch', searchValue.value)
}
</script>

<style lang="less" scoped>
.search {
  height: 60px;
  background-color: #ffffff;
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
  padding: 10px 20px;
  margin-top: 10px;
  display: flex;
  align-items: center;

  .search-icon {
    position: relative;
    bottom: 2px;
    left: -2px;
    cursor: pointer;
  }
}

.goods-box4 {
  border-radius: 15px;
  background-color: #ffffff;
  width: 1100px;
  margin: 0 auto;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);

  .goods {
    width: 1100px;
    padding: 20px;
    background-color: white;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    transition: all 0.3s ease;
    border-radius: 10px;
    margin: 15px 20px;

    &:hover {
      transform: translateY(-3px);
      box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
    }

    img {
      width: 100px;
      height: 100px;
      margin-right: 20px;
      border-radius: 50%;
      border: 3px solid #f0f0f0;
      object-fit: cover;
    }

    .info {
      flex: 1;
      margin-top: 0;
      font-size: 15px;

      .info-item {
        display: inline-block;
        margin-right: 30px;
        margin-bottom: 8px;
        color: #555;

        strong {
          color: #333;
        }
      }

      .content {
        height: auto;
        margin: 5px 0;
      }
    }
  }
}

.info2 {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-end;
  min-width: 160px;

  .question-btn {
    cursor: pointer;
    color: #fff;
    background-color: #67C23A;
    padding: 8px 16px;
    border-radius: 20px;
    font-size: 14px;
    transition: all 0.3s;
    display: block;
    text-align: center;
    margin-bottom: 10px;
    border: 1px solid #67C23A;

    &:hover {
      background-color: #5daf34;
      box-shadow: 0 4px 8px rgba(103, 194, 58, 0.3);
    }

    &.appoint-btn {
      background-color: #409EFF;
      border-color: #409EFF;

      &:hover {
        background-color: #337ecc;
        box-shadow: 0 4px 8px rgba(64, 158, 255, 0.3);
      }
    }
  }
}

.search :deep(.el-input--suffix .el-input__inner) {
  height: 40px;
  line-height: 40px;
  border-radius: 20px;
  border: 1px solid #ddd;
}
</style>