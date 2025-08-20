<template>
  <div class="published-message">
    <div class="publish-content">
      <h2 class="page-title">农业知识库</h2>
      <div class="message" v-for="(item, index) in userKnowledge" :key="index" :style="(index+1)%2===0?'margin-right:0':'margin-right:20px'">
        <div class="knowledge-card">
          <div class="image-container">
            <img v-if="item.picPath" class="knowleage-icon" :src="imgShowRoad + '/file/' + item.picPath" alt="" />
            <img class="knowleage-icon" v-else src="../assets/img/wutu.gif" alt="默认图片">
            <div class="image-overlay">
              <el-icon class="view-icon"><View /></el-icon>
            </div>
          </div>

          <div class="info">
            <h4 class="title">{{ item.title }}</h4>
            <div class="meta-info">
              <span class="initiator">
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
              <div @click.once="changeKnowledgeInfo(item.knowledgeId)" class="action-btn">
                <change-knowledge :cupdate-knowledge-info="updateInfo"></change-knowledge>
              </div>
              <div @click.stop="deleteKnowledgeInfo(item.knowledgeId)" class="action-btn">
                <delete-knowledge></delete-knowledge>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-if="userKnowledge.length === 0" class="empty-state">
      <el-empty description="暂无知识内容，点击下方按钮发布您的第一条知识">
        <el-button type="success" @click="publishKnowledgeClick">立即发布</el-button>
      </el-empty>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
import { Plus, User, Calendar, View } from '@element-plus/icons-vue'
import {
  selectKnowledgeById,
  selectKnowledgeByUsername,
} from "../api/knowledge";
import ChangeKnowledge from "./ChangeKnowledge.vue";
import DeleteKnowledge from "./DeleteKnowledge.vue";

// 使用 Composition API
const router = useRouter()
const store = useStore()

// 响应式数据
const userKnowledge = ref([])
const updateInfo = ref({})

// 计算属性
const imgShowRoad = computed(() => store.state.imgShowRoad)

// 方法定义
const publishKnowledgeClick = () => {
  router.push("/publishKnowledge").catch((err) => err);
}

const changeKnowledgeInfo = (knowledgeId) => {
  store.commit("updateChangedKnowledgeId", knowledgeId);
  selectKnowledgeById({
    knowledgeId: store.state.changedKnowledgeId,
  })
      .then((res) => {
        updateInfo.value = res.data;
      })
      .catch((err) => {
        console.log(err);
        ElMessage.error("获取知识信息失败");
      });
}

const deleteKnowledgeInfo = (knowledgeId) => {
  store.commit("updateChangedKnowledgeId", knowledgeId);
}

// 格式化日期
const formatDate = (dateString) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
}

// 生命周期钩子
onMounted(() => {
  store.commit("updateUserActiveIndex", "4-3");
  selectKnowledgeByUsername({})
      .then((res) => {
        userKnowledge.value = res.data;
      })
      .catch((err) => {
        console.log(err);
        ElMessage.error("获取知识列表失败");
      });
})
</script>

<style lang="less" scoped>
.expert-appoint-container {
  .header-section {
    margin-bottom: 20px;

    .page-title {
      font-size: 24px;
      font-weight: 600;
      color: #333;
      margin-bottom: 15px;
    }

    .role-info {
      border-radius: 8px;
    }
  }

  .empty-state {
    padding: 50px 0;
  }

  .appoint-item {
    margin-bottom: 20px;

    .appoint-card {
      border-radius: 12px;
      border: 1px solid #ebeef5;
      transition: all 0.3s ease;

      &:hover {
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        transform: translateY(-2px);
      }

      .appoint-content {
        padding: 20px;

        .title-section {
          display: flex;
          justify-content: space-between;
          align-items: center;
          margin-bottom: 15px;

          .title {
            margin: 0;
            font-size: 18px;
            font-weight: 600;
            color: #333;
          }
        }

        .content-section {
          margin-bottom: 20px;

          .content {
            margin: 0;
            color: #666;
            line-height: 1.6;
            font-size: 14px;
          }
        }

        .info-grid {
          display: grid;
          grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
          gap: 15px;
          margin-bottom: 20px;

          .info-item {
            display: flex;
            align-items: flex-start;
            gap: 10px;

            i {
              font-size: 16px;
              color: #409eff;
              margin-top: 2px;
            }

            .info-text {
              .info-label {
                font-size: 12px;
                color: #999;
                margin-bottom: 2px;
              }

              .info-value {
                font-size: 14px;
                color: #333;
                word-break: break-word;
              }
            }
          }
        }
      }

      .appoint-actions {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
        padding-top: 15px;
        border-top: 1px solid #eee;
      }
    }
  }

  .detail-dialog, .answer-dialog {
    :deep(.el-dialog__header) {
      border-bottom: 1px solid #eee;
      padding: 15px 20px;
    }

    :deep(.el-dialog__body) {
      padding: 20px;
    }

    .detail-content {
      .info-descriptions {
        margin-bottom: 20px;
      }

      .answer-form {
        .el-form-item {
          margin-bottom: 20px;
        }
      }
    }

    .dialog-footer {
      text-align: right;
    }
  }
}

/* Responsive Styles */
@media (max-width: 1150px) {
  .expert-appoint-container {
    padding: 15px;
    .appoint-item {
      margin-bottom: 15px;
    }

    .appoint-card {
      padding: 15px;
    }

    .appoint-content {
      padding: 15px;
    }

    .info-grid {
      grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    }
  }
}

@media (max-width: 768px) {
  .expert-appoint-container {
    padding: 10px;
    .header-section .page-title {
      font-size: 20px;
    }

    .appoint-item {
      margin-bottom: 15px;
    }

    .appoint-card {
      padding: 10px;
    }

    .appoint-content {
      padding: 10px;
    }

    .info-grid {
      grid-template-columns: 1fr;
    }
  }
}
</style>