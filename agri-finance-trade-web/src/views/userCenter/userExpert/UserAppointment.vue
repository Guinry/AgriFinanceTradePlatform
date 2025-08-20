<!--专家问答-->
<template>
  <div class="expert-appoint-container">
    <div class="header-section">
      <h2 class="page-title">专家预约</h2>
      <el-alert
        v-if="role==='expert'"
        title="您是专家，可以查看和回答用户的咨询问题"
        type="success"
        :closable="false"
        class="role-info"
      />
      <el-alert
        v-else
        title="您可以在这里查看专家对您问题的回复"
        type="info"
        :closable="false"
        class="role-info"
      />
    </div>
    
    <div v-if="appointArray.length === 0" class="empty-state">
      <el-empty description="暂无咨询记录">
      </el-empty>
    </div>
    
    <div v-for="(item,index) in appointArray" :key="index" class="appoint-item">
      <el-card class="appoint-card" shadow="hover">
        <div class="appoint-content">
          <div class="title-section">
            <h3 class="title">{{item.plantName}}</h3>
            <el-tag :type="item.status === 0 ? 'danger':'success'" size="small">{{item.status === 0 ? '未回答' :'已回答'}}</el-tag>
          </div>
          
          <div class="content-section">
            <p class="content">{{item.plantDetail}}</p>
          </div>
          
          <div class="info-grid">
            <div class="info-item" v-if="role==='expert'">
              <el-icon><User /></el-icon>
              <div class="info-text">
                <div class="info-label">咨询者</div>
                <div class="info-value">{{item.questioner}}</div>
              </div>
            </div>
            
            <div class="info-item" v-if="role==='expert'">
              <el-icon><Phone /></el-icon>
              <div class="info-text">
                <div class="info-label">联系方式</div>
                <div class="info-value">{{item.phone}}</div>
              </div>
            </div>
            
            <div class="info-item" v-if="role==='questioner'">
              <el-icon><User /></el-icon>
              <div class="info-text">
                <div class="info-label">专家姓名</div>
                <div class="info-value">{{item.expertName}}</div>
              </div>
            </div>
            
            <div class="info-item">
              <el-icon><Document /></el-icon>
              <div class="info-text">
                <div class="info-label">作物条件</div>
                <div class="info-value">{{item.plantCondition}}</div>
              </div>
            </div>
            
            <div class="info-item">
              <el-icon><Location /></el-icon>
              <div class="info-text">
                <div class="info-label">土壤条件</div>
                <div class="info-value" :title="item.soilCondition">{{item.soilCondition}}</div>
              </div>
            </div>
            
            <div class="info-item">
              <el-icon><ScaleToOriginal /></el-icon>
              <div class="info-text">
                <div class="info-label">面积</div>
                <div class="info-value">{{item.area}}亩</div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="appoint-actions">
          <el-button size="small" @click="handleDetail(item)">
            <el-icon><View /></el-icon>
            详情
          </el-button>
          <el-button v-if="role==='expert'" size="small" type="primary" @click="handleEdit(item)">
            <el-icon><Edit /></el-icon>
            回答
          </el-button>
          <el-button size="small" type="danger" @click="delAppoint(item)">
            <el-icon><Delete /></el-icon>
            删除
          </el-button>
        </div>
      </el-card>
    </div>
    
    <el-dialog 
      title="咨询详情" 
      v-model="showDetail" 
      width="600px" 
      :before-close="detailClose"
      class="detail-dialog"
    >
      <div class="detail-content">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="种植作物">{{detailObj.plantName}}</el-descriptions-item>
          <el-descriptions-item label="作物详细信息">{{detailObj.plantDetail}}</el-descriptions-item>
          <el-descriptions-item label="地址">{{detailObj.address}}</el-descriptions-item>
          <el-descriptions-item label="土壤条件">{{detailObj.soilCondition}}</el-descriptions-item>
          <el-descriptions-item label="面积">{{detailObj.area}}亩</el-descriptions-item>
          <el-descriptions-item label="作物条件">{{detailObj.plantCondition}}</el-descriptions-item>
          <el-descriptions-item v-if="role==='expert'" label="联系方式">{{detailObj.phone}}</el-descriptions-item>
          <el-descriptions-item v-if="role==='expert'" label="提问者">{{detailObj.questioner}}</el-descriptions-item>
          <el-descriptions-item label="问题状态">
            <el-tag :type="detailObj.status === 0 ? 'danger':'success'" size="small">
              {{detailObj.status === 0 ? '未回答' :'已回答'}}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item v-if="detailObj.answer" label="专家回答">{{detailObj.answer}}</el-descriptions-item>
        </el-descriptions>
      </div>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showDetail = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
    
    <el-dialog 
      title="回答问题" 
      v-model="dialogVisible" 
      width="650px" 
      :before-close="closeRevise"
      class="answer-dialog"
    >
      <div class="detail-content">
        <el-descriptions :column="1" border class="info-descriptions">
          <el-descriptions-item label="种植作物">{{detailObj.plantName}}</el-descriptions-item>
          <el-descriptions-item label="作物详细信息">{{detailObj.plantDetail}}</el-descriptions-item>
          <el-descriptions-item label="地址">{{detailObj.address}}</el-descriptions-item>
          <el-descriptions-item label="土壤条件">{{detailObj.soilCondition}}</el-descriptions-item>
          <el-descriptions-item label="面积">{{detailObj.area}}亩</el-descriptions-item>
          <el-descriptions-item label="作物条件">{{detailObj.plantCondition}}</el-descriptions-item>
          <el-descriptions-item v-if="role==='expert'" label="联系方式">{{detailObj.phone}}</el-descriptions-item>
          <el-descriptions-item v-if="role==='expert'" label="提问者">{{detailObj.questioner}}</el-descriptions-item>
        </el-descriptions>
        
        <el-form ref="formRef" :model="detailObj" label-width="80px" class="answer-form">
          <el-form-item label="问题状态">
            <el-tag :type="detailObj.status === 0 ? 'danger':'success'" size="small">
              {{detailObj.status === 0 ? '未回答' :'已回答'}}
            </el-tag>
          </el-form-item>
          
          <el-form-item label="专家回答" required>
            <el-input 
              type="textarea" 
              v-model="detailObj.answer" 
              :rows="5"
              placeholder="请输入您的回答..."
              maxlength="500"
              show-word-limit
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeRevise">取消</el-button>
          <el-button type="primary" @click="submitRevise">提交回答</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { ElMessage, ElMessageBox } from 'element-plus'
import { selectAppointByUser,reviseAppointByUserId,delAppointByUserId } from '../../../api/question.js'
import { User, Phone, Document, Location, ScaleToOriginal, View, Edit, Delete } from '@element-plus/icons-vue'

// 使用 Composition API
const store = useStore()

// 响应式数据
const appointArray = ref([])
const role = ref("")
const showDetail = ref(false)
const dialogVisible = ref(false)
const detailObj = ref({
  title:'',
  expertName:'',
  questioner:'',
  status:''
})

const formRef = ref(null)

// 方法定义
const getData = () => {
  role.value = store.getters.isExpert?'expert':'questioner'

  // kind：普通用户：questioner；专家：expert
  selectAppointByUser({type:role.value}).then(res => {
    console.log('rererer',res)
    appointArray.value = res.data
  }).catch(err=>{
    console.log(err)
  })
}

const delAppoint = (item) => {
  ElMessageBox.confirm('确认删除该行信息？', '删除确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    delAppointByUserId({id:item.id}).then(res=>{
      ElMessage({
        type: 'success',
        message: '删除成功!'
      });
      getData()
    }).catch(err=>{
      console.log(err)
      ElMessage.error('删除失败')
    })
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '已取消删除'
    });
  });
}

const handleDetail = (item) => {
  showDetail.value = true
  detailObj.value = Object.assign({},{...item})
}

const detailClose = () => {
  showDetail.value = false
}

const handleEdit = (item) => {
  dialogVisible.value = true
  detailObj.value = Object.assign({},{...item})
}

const closeRevise = () => {
  dialogVisible.value = false
}

const submitRevise = () => {
  detailObj.value.status = 1
  reviseAppointByUserId(detailObj.value).then(res => {
    ElMessage.success('回答成功！')
    dialogVisible.value = false
    getData()
  }).catch(err=>{
    console.log(err)
    ElMessage.error('提交失败')
  })
}

// 生命周期钩子
onMounted(() => {
  store.commit("updateUserActiveIndex", "4-2");
  getData()
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
</style>
