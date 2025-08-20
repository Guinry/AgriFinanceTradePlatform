<template>
  <div class="published-message">
    <div class="header-section">
      <h2 class="page-title">农业知识库</h2>
      <p class="page-description">分享您的专业知识，帮助更多农户解决问题</p>
      <div class="action-bar">
        <el-button type="success" plain round @click="publishKnowledgeClick" class="publish-btn">
          <el-icon><Plus /></el-icon>
          发布知识
        </el-button>
      </div>
    </div>
    
    <div class="publish-content">
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
      <el-empty description="暂无知识内容，点击上方按钮发布您的第一条知识">
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
  router.push("/home/addmessage/publishknowledge").catch((err) => err);
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
.published-message {
  margin: 0 auto;
  width: 1100px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  padding: 20px;
  
  .header-section {
    text-align: center;
    padding: 20px 0;
    border-bottom: 1px solid #eee;
    margin-bottom: 30px;
    
    .page-title {
      font-size: 28px;
      color: #333;
      margin-bottom: 10px;
      font-weight: 600;
    }
    
    .page-description {
      font-size: 16px;
      color: #666;
      margin-bottom: 20px;
    }
    
    .action-bar {
      .publish-btn {
        font-size: 16px;
        padding: 12px 24px;
        background: linear-gradient(135deg, #67C23A, #5daf34);
        border: none;
        transition: all 0.3s ease;
        
        &:hover {
          transform: translateY(-2px);
          box-shadow: 0 6px 16px rgba(103, 194, 58, 0.3);
        }
        
        i {
          margin-right: 8px;
        }
      }
    }
  }
  
  .publish-content {
    background: #fff;
    display: flex;
    flex-wrap: wrap;
    padding: 10px 20px;
  }
  
  .message {
    width: 530px;
    margin: 0 0 25px;
    
    .knowledge-card {
      border: 1px solid #ebeef5;
      border-radius: 12px;
      overflow: hidden;
      transition: all 0.3s ease;
      background: #fff;
      height: 100%;
      
      &:hover {
        transform: translateY(-5px);
        box-shadow: 0 12px 20px rgba(0, 0, 0, 0.1);
        
        .image-overlay {
          opacity: 1;
        }
      }
      
      .image-container {
        position: relative;
        height: 200px;
        overflow: hidden;
        
        .knowleage-icon {
          width: 100%;
          height: 100%;
          object-fit: cover;
          transition: transform 0.3s ease;
        }
        
        .image-overlay {
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          background: rgba(0, 0, 0, 0.5);
          display: flex;
          align-items: center;
          justify-content: center;
          opacity: 0;
          transition: opacity 0.3s ease;
          
          .view-icon {
            font-size: 36px;
            color: #fff;
          }
        }
      }
      
      .info {
        padding: 20px;
        
        .title {
          height: auto;
          line-height: 1.4;
          font-size: 20px;
          margin: 0 0 15px;
          color: #333;
          font-weight: 600;
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
          word-break: break-all;
        }
        
        .meta-info {
          display: flex;
          margin-bottom: 15px;
          font-size: 14px;
          
          .initiator, .date {
            display: flex;
            align-items: center;
            color: #999;
            margin-right: 20px;
            
            i {
              margin-right: 5px;
              font-size: 16px;
            }
          }
        }
        
        .content {
          height: 75px;
          line-height: 25px;
          color: #666;
          margin-bottom: 20px;
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
          word-break: break-all;
        }
        
        .btns-style {
          display: flex;
          justify-content: flex-end;
          gap: 15px;
          
          .action-btn {
            cursor: pointer;
            
            :deep(.modify), :deep(.delete) {
              padding: 8px 16px;
              border-radius: 20px;
              font-size: 14px;
              transition: all 0.3s ease;
              
              &:hover {
                background-color: #f0f9eb;
                color: #5daf34;
              }
            }
          }
        }
      }
    }
  }
  
  .empty-state {
    padding: 50px 0;
    
    :deep(.el-empty) {
      padding: 0;
    }
  }
}

// 响应式设计
@media (max-width: 1150px) {
  .published-message {
    width: 100%;
    padding: 15px;
  }
  
  .message {
    width: 100%;
    margin-right: 0 !important;
  }
}

@media (max-width: 768px) {
  .published-message {
    .header-section {
      .page-title {
        font-size: 24px;
      }
      
      .page-description {
        font-size: 14px;
      }
    }
    
    .message {
      .knowledge-card {
        .info {
          padding: 15px;
          
          .title {
            font-size: 18px;
          }
          
          .meta-info {
            flex-direction: column;
            gap: 5px;
          }
        }
      }
    }
  }
}
</style>