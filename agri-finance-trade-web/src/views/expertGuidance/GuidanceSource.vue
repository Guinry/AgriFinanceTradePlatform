<template>
  <div class="goods-box3">
    <!-- 搜索框和筛选按钮 -->
    <div class="search-container">
      <el-input placeholder="有问题直接搜" v-model="searchValue" maxlength="100" clearable class="search-input">
        <template #prefix>
          <el-icon class="el-input__icon search-icon" @click="handleSearch">
            <Search />
          </el-icon>
        </template>
      </el-input>
      <el-button-group class="button-group">
        <el-button round @click="handleTopicDetail('')">全部</el-button>
        <el-button round @click="handleTopicDetail('苹果果树种植方法')">苹果</el-button>
        <el-button round @click="handleTopicDetail('玉米苗灌溉')">玉米</el-button>
        <el-button round @click="handleTopicDetail('北方草莓种植品种')">草莓</el-button>
      </el-button-group>
    </div>

    <!-- 问题列表 -->
    <div class="goods">
      <div class="info" v-for="(item, index) in cgoods" :key="index">
        <div class="content">
          <div class="question-header">
            <div class="question-text">问</div>
            <div class="question-content" @click="handleDetail(item)">{{ item.title }}</div>
          </div>
          <div class="answer-container" v-if="item.status == 1">
            <div class="answer-text">答</div>
            <div class="answer-content">{{ item.answer }}</div>
          </div>
          <div class="no-answer" v-else>
            <div class="answer-text">答</div>
            <div class="answer-content pending">专家尚未回复...</div>
          </div>
        </div>
        <div class="person-contents">
          <span class="person-item">提问者：<strong>{{ item.questioner }}</strong></span>
          <span class="person-item">专家：<strong>{{ item.expertName }}</strong></span>
        </div>
      </div>
    </div>

    <!-- 分页 -->
    <Pagination @item-click="pageClick" :cUrl="url" :cTotal="total" :cPageSize="pageSize"></Pagination>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { Search } from '@element-plus/icons-vue'
import Pagination from "../../components/Pagination.vue"

const store = useStore()
const router = useRouter()

const searchValue = ref('')

const props = defineProps({
  cgoods: {
    type: Array,
  },
  total: {
    type: Number
  },
  pageSize: {
    type: Number
  },
  url: {
    type: String
  }
})

const emit = defineEmits(['pageClick', 'handleSearch'])

const detailsClick = (item) => {
  store.commit("updateOrderId", item)
  router.push(`/home/details?id=${item.id}`).catch((err) => err)
}

const pageClick = (val) => {
  emit('pageClick', val)
}

const handleSearch = () => {
  emit('handleSearch', searchValue.value)
}

const handleDetail = (item) => {
  router.push(`/expertGuidance/guideDetails/${item.id}`)
}

const handleTopicDetail = (val) => {
  searchValue.value = val
  handleSearch()
}
</script>

<style lang="less" scoped>
/* 整体搜索和筛选容器 */
.search-container {
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-bottom: 25px;
  align-items: center;
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.search-input {
  width: 700px;
  padding: 0;
  border-radius: 25px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);

  :deep(.el-input__inner) {
    border-radius: 25px;
    height: 45px;
    line-height: 45px;
  }
}

.search-icon {
  cursor: pointer;
  font-size: 18px;
  color: #999;
}

.button-group {
  display: flex;
  align-items: center;
  justify-content: flex-start;

  :deep(.el-button) {
    border-radius: 20px;
    margin: 0 5px;
    padding: 10px 20px;
    border: 1px solid #e6e6e6;
    color: #666;

    &:hover {
      background-color: #f0f9eb;
      border-color: #c2e7b0;
      color: #67C23A;
    }

    &:focus {
      background-color: #f0f9eb;
      border-color: #c2e7b0;
      color: #67C23A;
    }
  }
}

/* 商品展示列表 */
.goods {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.info {
  background-color: #fff;
  border-radius: 12px;
  padding: 25px;
  margin-bottom: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
    transform: translateY(-2px);
  }
}

.content {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

.question-header {
  display: flex;
  align-items: flex-start;
  margin-bottom: 15px;
}

.question-text {
  background-color: #ff4f16;
  color: white;
  font-weight: bold;
  border-radius: 50%;
  padding: 10px;
  width: 36px;
  height: 36px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 15px;
  flex-shrink: 0;
}

.question-content {
  font-weight: bold;
  cursor: pointer;
  color: #333;
  font-size: 17px;
  line-height: 1.4;
  flex: 1;

  &:hover {
    color: #035D1C;
    text-decoration: underline;
  }
}

.answer-container,
.no-answer {
  display: flex;
  margin-top: 10px;
}

.answer-text {
  background-color: #23c6c6;
  color: white;
  font-weight: bold;
  border-radius: 50%;
  padding: 10px;
  width: 36px;
  height: 36px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 15px;
  flex-shrink: 0;
}

.answer-content {
  color: #555;
  font-size: 15px;
  line-height: 1.6;
  width: 100%;
  text-align: justify;

  &.pending {
    color: #999;
    font-style: italic;
  }
}

.person-contents {
  font-size: 14px;
  color: #666;
  display: flex;
  justify-content: flex-end;
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px dashed #eee;

  .person-item {
    margin-left: 20px;

    strong {
      color: #333;
    }
  }
}

/* 分页样式 */
.pagination {
  margin-top: 30px;
  display: flex;
  justify-content: center;
  padding: 20px;
}

.goods-box3 {
  margin-top: 20px;
  width: 100%;
  padding: 0 20px;
}
</style>
