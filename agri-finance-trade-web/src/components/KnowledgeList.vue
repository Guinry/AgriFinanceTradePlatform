<template>
  <div class="knowledge-library">
    <div class="header">
      <h2 class="page-title">农业知识库</h2>
      <el-button type="success" class="publish-btn" @click="publishKnowledgeClick">立即发布</el-button>
    </div>

    <!-- 知识列表 -->
    <div class="knowledge-list">
      <div
          v-for="(item, index) in userKnowledge"
          :key="item.knowledgeId"
          class="knowledge-card"
          :style="index % 2 === 0 ? 'margin-right: 20px' : 'margin-right: 0'"
      >
        <div class="card-header">
          <div class="image-container">
            <img v-if="item.picPath" :src="`${imgShowRoad}/file/${item.picPath}`" alt="知识图片" />
            <img v-else src="../assets/img/default-image.png" alt="默认图片" />
            <div class="overlay">
              <el-icon class="view-icon"><View /></el-icon>
            </div>
          </div>
          <div class="info">
            <h4 class="title">{{ item.title }}</h4>
            <div class="meta-info">
              <span class="author">
                <el-icon><User /></el-icon>
                {{ item.ownName }}
              </span>
              <span class="date">
                <el-icon><Calendar /></el-icon>
                {{ formatDate(item.createTime) }}
              </span>
            </div>
            <p class="content">{{ item.content }}</p>
            <div class="btns-style">
              <el-button type="primary" @click="changeKnowledgeInfo(item.knowledgeId)">修改</el-button>
              <el-button type="danger" @click="deleteKnowledgeInfo(item.knowledgeId)">删除</el-button>
            </div>
          </div>
        </div>
      </div>

      <!-- 空状态 -->
      <div v-if="userKnowledge.length === 0" class="empty-state">
        <el-empty description="暂无知识内容，点击下方按钮发布您的第一条知识">
          <el-button type="success" @click="publishKnowledgeClick">立即发布</el-button>
        </el-empty>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage, ElMessageBox } from 'element-plus'
import { selectKnowledgeById, selectKnowledgeByUsername, deleteKnowledgeById } from '../api/knowledge'
import { Plus, User, Calendar, View } from '@element-plus/icons-vue'

const router = useRouter()
const store = useStore()

// 响应式数据
const userKnowledge = ref([])
const updateInfo = ref({})

// 计算属性
const imgShowRoad = computed(() => store.state.imgShowRoad)

// 点击发布按钮
const publishKnowledgeClick = () => {
  router.push("/publishKnowledge").catch((err) => console.log(err))
}

// 获取知识详情
const changeKnowledgeInfo = (knowledgeId) => {
  ElMessageBox.confirm('确定要修改这条知识吗？', '确认修改', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    store.commit("updateChangedKnowledgeId", knowledgeId)
    router.push("/publishKnowledge")
  }).catch(() => {
    ElMessage.info('已取消修改')
  })
}

// 删除知识
const deleteKnowledgeInfo = (knowledgeId) => {
  ElMessageBox.confirm('确定要删除这条知识吗？删除后无法恢复', '确认删除', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deleteKnowledgeById({ knowledgeId })
      .then((res) => {
        if (res.code === 200) {
          ElMessage.success('删除成功')
          // 从列表中移除已删除的项
          userKnowledge.value = userKnowledge.value.filter(item => item.knowledgeId !== knowledgeId)
        } else {
          ElMessage.error('删除失败')
        }
      })
      .catch((err) => {
        console.log(err)
        ElMessage.error("删除知识失败")
      })
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 格式化日期
const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
}

// 生命周期钩子，初始化用户数据
onMounted(() => {
  store.commit("updateUserActiveIndex", "2-3")
  selectKnowledgeByUsername({})
      .then((res) => {
        userKnowledge.value = res.data
      })
      .catch((err) => {
        console.log(err)
        ElMessage.error("获取知识列表失败")
      })
})
</script>

<style scoped lang="less">
.knowledge-library {
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;

    .page-title {
      font-size: 24px;
      font-weight: 600;
      color: #333;
    }

    .publish-btn {
      font-size: 14px;
      padding: 10px 20px;
    }
  }

  .knowledge-list {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;

    .knowledge-card {
      width: calc(50% - 10px);
      border: 1px solid #ebeef5;
      border-radius: 12px;
      overflow: hidden;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      transition: transform 0.3s ease, box-shadow 0.3s ease;

      &:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
      }

      .card-header {
        display: flex;
        flex-direction: column;

        .image-container {
          position: relative;
          height: 200px;
          overflow: hidden;
          background-color: #f5f5f5;

          img {
            width: 100%;
            height: 100%;
            object-fit: cover;
          }

          .overlay {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(0, 0, 0, 0.3);
            display: flex;
            justify-content: center;
            align-items: center;
            opacity: 0;
            transition: opacity 0.3s ease;

            .view-icon {
              color: #fff;
              font-size: 30px;
            }
          }

          &:hover .overlay {
            opacity: 1;
          }
        }

        .info {
          padding: 20px;

          .title {
            font-size: 18px;
            font-weight: 600;
            color: #333;
            margin-bottom: 10px;
          }

          .meta-info {
            display: flex;
            justify-content: space-between;
            font-size: 14px;
            color: #999;

            .author,
            .date {
              display: flex;
              align-items: center;

              el-icon {
                font-size: 16px;
                margin-right: 5px;
              }
            }
          }

          .content {
            font-size: 14px;
            color: #666;
            margin: 10px 0;
            line-height: 1.6;
            text-overflow: ellipsis;
            overflow: hidden;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
          }

          .btns-style {
            display: flex;
            justify-content: space-between;
            margin-top: 15px;

            el-button {
              font-size: 14px;
            }
          }
        }
      }
    }

    .empty-state {
      padding: 50px 0;
      text-align: center;
    }
  }

  /* 响应式设计 */
  @media (max-width: 768px) {
    .knowledge-list {
      .knowledge-card {
        width: 100%;
      }
    }
  }
}
</style>