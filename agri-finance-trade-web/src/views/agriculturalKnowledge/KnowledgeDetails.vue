<!-- 农业知识详情 -->
<template>
  <NavigationBar />
  <div class="knowledge-detail-container">
    <div class="header-section">
      <h1 class="title">{{ updateInfo.title }}</h1>
      <div class="meta-info">
        <span class="author">作者：{{ updateInfo.ownName }}</span>
        <span class="date">发布日期：{{ formatTimer(updateInfo.updateTime) }}</span>
      </div>
    </div>

    <div class="media-section">
      <video
        v-if="updateInfo.type === 'mp4' || updateInfo.type === 'MP4'"
        class="media-content"
        :src="imgShowRoad + '/file/' + updateInfo.picPath"
        controls
      ></video>
      <img
        v-else
        class="media-content"
        :src="imgShowRoad + '/file/' + updateInfo.picPath"
        alt=""
      />
    </div>

    <div class="content-section">
      <div class="content-text">{{ updateInfo.content }}</div>
    </div>

    <div class="comments-section">
      <div class="comments-header">
        <h2 class="comments-title">评论 ({{ commentArray.length || 0 }})</h2>
      </div>

      <div class="comments-list">
        <div
          class="comment-item"
          v-for="(item, index) in commentArray"
          :key="index"
        >
          <div class="comment-content">{{ item.content }}</div>
          <div class="comment-meta">
            <span class="comment-author">{{ item.ownName }}</span>
            <span class="comment-date">{{ formatTimer2(item.createTime) }}</span>
          </div>
        </div>
        
        <div
          v-if="commentArray.length === 0"
          class="no-comments"
        >
          <img src="../../assets/page4/noComments.png" alt="暂无评论" />
          <p>暂无评论，快来抢沙发吧！</p>
        </div>
      </div>

      <div class="comment-form">
        <h3 class="form-title">发表评论</h3>
        <el-input
          type="textarea"
          v-model="content"
          :rows="4"
          placeholder="请输入您的评论..."
          class="comment-textarea"
        ></el-input>
        <div class="form-actions">
          <el-button 
            type="success" 
            @click="handleComment"
            :disabled="!content.trim()"
          >
            发布评论
          </el-button>
        </div>
      </div>
    </div>
  </div>
  <Footer />
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import {
  selectKnowledgeById,
  selectComment,
  addComment
} from '../../api/knowledge'
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

// 响应式数据
const updateInfo = ref({})
const content = ref('')
const commentArray = ref([])
const store = useStore()
const route = useRoute()

// 计算属性
const imgShowRoad = computed(() => store.state.imgShowRoad)

// 过滤器转换为方法
const formatTimer = (value) => {
  if (!value) return ''
  let date = new Date(value)
  let y = date.getFullYear()
  let MM = date.getMonth() + 1
  MM = MM < 10 ? '0' + MM : MM
  let d = date.getDate()
  d = d < 10 ? '0' + d : d
  let h = date.getHours()
  h = h < 10 ? '0' + h : h
  let m = date.getMinutes()
  m = m < 10 ? '0' + m : m
  let s = date.getSeconds()
  s = s < 10 ? '0' + s : s
  return y + '-' + MM + '-' + d + ' ' + h + ':' + m
}

const formatTimer2 = (value) => {
  if (!value) return ''
  let date = new Date(value)
  let y = date.getFullYear()
  let MM = date.getMonth() + 1
  MM = MM < 10 ? '0' + MM : MM
  let d = date.getDate()
  d = d < 10 ? '0' + d : d
  let h = date.getHours()
  h = h < 10 ? '0' + h : h
  let m = date.getMinutes()
  m = m < 10 ? '0' + m : m
  let s = date.getSeconds()
  s = s < 10 ? '0' + s : s
  return y + '-' + MM + '-' + d + ' ' + h + ':' + m
}

// 方法定义
const getData = () => {
  selectKnowledgeById({
    knowledgeId: route.params.id
  }).then((res) => {
      let tmp = res.data
      const flieArr = tmp.picPath.split('.')
      tmp.type = flieArr[flieArr.length - 1]
      updateInfo.value = tmp
    }).catch((err) => {
      console.log(err)
    })
}

// 查询评论
const getCommentData = () => {
  selectComment({
    knowledgeId: route.params.id
  }).then((res) => {
      commentArray.value = res.data
    }).catch((err) => {
      console.log(err)
    })
}

const handleComment = () => {
  if (content.value === '') {
    ElMessage.error('评论内容不能为空！')
    return
  }
  if (localStorage.getItem('token')) {
    addComment({
      knowledgeId: route.params.id,
      content: content.value
    })
      .then((res) => {
        content.value = ''
        ElMessage.success('评论成功！')
        getCommentData()
      })
      .catch((err) => {
        console.log(err)
      })
  } else {
    ElMessage.error('请先登录')
  }
}

// 生命周期钩子
onMounted(() => {
  getData()
  getCommentData()
})
</script>

<style lang="less" scoped>
.knowledge-detail-container {
  width: 100%;
  max-width: 900px;
  background: #fff;
  min-height: 800px;
  margin: 30px auto 50px;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
  font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
}

.header-section {
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #eee;
}

.title {
  font-size: 28px;
  font-weight: 600;
  color: #333;
  margin: 0 0 15px 0;
  line-height: 1.4;
}

.meta-info {
  display: flex;
  gap: 20px;
  font-size: 14px;
  color: #666;
  
  .author, .date {
    display: flex;
    align-items: center;
    
    &::before {
      content: "";
      display: inline-block;
      width: 3px;
      height: 3px;
      background-color: #999;
      border-radius: 50%;
      margin-right: 8px;
    }
  }
}

.media-section {
  display: flex;
  justify-content: center;
  margin: 30px 0;
  
  .media-content {
    max-width: 100%;
    height: auto;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    
    &[controls] {
      width: 100%;
      max-width: 800px;
      height: auto;
    }
  }
}

.content-section {
  margin: 30px 0;
  
  .content-text {
    font-size: 16px;
    line-height: 1.8;
    color: #444;
    white-space: pre-wrap;
    text-align: justify;
  }
}

.comments-section {
  margin-top: 40px;
  padding-top: 30px;
  border-top: 1px solid #eee;
}

.comments-header {
  margin-bottom: 20px;
  
  .comments-title {
    font-size: 22px;
    font-weight: 600;
    color: #333;
    margin: 0;
  }
}

.comments-list {
  margin-bottom: 40px;
}

.comment-item {
  background: #fafafa;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 15px;
  transition: all 0.3s ease;
  
  &:hover {
    background: #f5f5f5;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  }
  
  .comment-content {
    font-size: 15px;
    color: #555;
    line-height: 1.6;
    margin-bottom: 12px;
  }
  
  .comment-meta {
    display: flex;
    justify-content: space-between;
    font-size: 13px;
    color: #888;
    
    .comment-author {
      font-weight: 500;
      color: #035D1C;
    }
  }
}

.no-comments {
  text-align: center;
  padding: 40px 0;
  
  img {
    width: 120px;
    height: auto;
    margin-bottom: 15px;
  }
  
  p {
    color: #999;
    font-size: 15px;
  }
}

.comment-form {
  .form-title {
    font-size: 20px;
    font-weight: 600;
    color: #333;
    margin: 0 0 20px 0;
  }
  
  .comment-textarea {
    margin-bottom: 20px;
    
    :deep(.el-textarea__inner) {
      border-radius: 8px;
      padding: 15px;
      font-size: 15px;
      border: 1px solid #ddd;
      
      &:focus {
        border-color: #035D1C;
      }
    }
  }
  
  .form-actions {
    display: flex;
    justify-content: flex-end;
    
    :deep(.el-button) {
      padding: 12px 30px;
      border-radius: 6px;
      font-size: 15px;
      background-color: #035D1C;
      border-color: #035D1C;
      
      &:hover {
        background-color: #028a2c;
        border-color: #028a2c;
      }
      
      &:disabled {
        background-color: #f5f5f5;
        border-color: #ddd;
        color: #ccc;
      }
    }
  }
}

// 响应式设计
@media (max-width: 768px) {
  .knowledge-detail-container {
    padding: 20px 15px;
    margin: 15px auto;
  }
  
  .title {
    font-size: 24px;
  }
  
  .meta-info {
    flex-direction: column;
    gap: 8px;
  }
  
  .content-text {
    font-size: 15px;
  }
  
  .comment-meta {
    flex-direction: column;
    gap: 5px;
  }
}
</style>