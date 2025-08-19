<!--专家问答-->
<template>
  <div class="expert-question-container">
    <div class="header-section">
      <h2 class="page-title">专家问答</h2>
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

    <div v-if="questionArray.length === 0" class="empty-state">
      <el-empty description="暂无咨询记录">
        <el-button type="success" @click="$router.push('/expert/question')">发起咨询</el-button>
      </el-empty>
    </div>

    <div v-for="(item,index) in questionArray" :key="index" class="question-item">
      <el-card class="question-card" shadow="hover">
        <div class="question-content">
          <div class="title-section">
            <h3 class="title">{{item.title}}</h3>
            <el-tag :type="item.status === 0 ? 'danger':'success'" size="small">{{item.status === 0 ? '未回答' :'已回答'}}</el-tag>
          </div>

          <div class="content-section">
            <p class="content">{{item.question}}</p>
          </div>

          <div class="info-grid">
            <div class="info-item" v-if="role==='expert'">
              <el-icon><User /></el-icon>
              <div class="info-text">
                <div class="info-label">提问者</div>
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
          </div>
        </div>

        <div class="question-actions">
          <el-button size="small" @click="handleDetail(item)">
            <el-icon><View /></el-icon>
            详情
          </el-button>
          <el-button v-if="role==='questioner'" size="small" type="primary" @click="handleEdit(item)">
            <el-icon><Edit /></el-icon>
            修改
          </el-button>
          <el-button v-if="role==='expert'" size="small" type="primary" @click="handleEdit(item)">
            <el-icon><Edit /></el-icon>
            回答
          </el-button>
          <el-button size="small" type="danger" @click="delQuestion(item)">
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
          <el-descriptions-item label="问题标题">{{detailObj.title}}</el-descriptions-item>
          <el-descriptions-item label="问题内容">{{detailObj.question}}</el-descriptions-item>
          <el-descriptions-item v-if="role==='questioner'" label="专家姓名">{{detailObj.expertName}}</el-descriptions-item>
          <el-descriptions-item v-if="role==='expert'" label="提问者">{{detailObj.questioner}}</el-descriptions-item>
          <el-descriptions-item v-if="role==='expert'" label="联系方式">{{detailObj.phone}}</el-descriptions-item>
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
          <el-button type="primary" @click="showDetail = false">确 定</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog
        :title="role === 'questioner' ? '修改问题' : '回答问题'"
        v-model="dialogVisible"
        width="650px"
        :before-close="closeRevise"
        class="answer-dialog"
    >
      <div class="detail-content">
        <el-descriptions :column="1" border class="info-descriptions">
          <el-descriptions-item label="问题标题">{{detailObj.title}}</el-descriptions-item>
          <el-descriptions-item label="问题内容">{{detailObj.question}}</el-descriptions-item>
          <el-descriptions-item v-if="role==='questioner'" label="专家姓名">{{detailObj.expertName}}</el-descriptions-item>
          <el-descriptions-item v-if="role==='expert'" label="提问者">{{detailObj.questioner}}</el-descriptions-item>
          <el-descriptions-item v-if="role==='expert'" label="联系方式">{{detailObj.phone}}</el-descriptions-item>
        </el-descriptions>

        <el-form ref="form" :model="detailObj" label-width="80px" class="answer-form">
          <el-form-item label="问题状态">
            <el-tag :type="detailObj.status === 0 ? 'danger':'success'" size="small">
              {{detailObj.status === 0 ? '未回答' :'已回答'}}
            </el-tag>
          </el-form-item>

          <!-- 提问者可以改问题内容 -->
          <el-form-item label="问题内容" v-if="role==='questioner'&&detailObj.status === 0" required>
            <el-input
                type="textarea"
                v-model="detailObj.question"
                :rows="4"
                placeholder="请输入问题内容..."
                maxlength="500"
                show-word-limit
            ></el-input>
          </el-form-item>

          <!-- 专家回答问题 -->
          <el-form-item label="专家回答" v-if="role==='expert'" required>
            <el-input
                type="textarea"
                v-model="detailObj.answer"
                :rows="5"
                placeholder="请输入您的回答..."
                maxlength="500"
                show-word-limit
            ></el-input>
          </el-form-item>

          <!-- 提问者查看回答 -->
          <el-form-item label="专家回答" v-if="role==='questioner' && detailObj.answer">
            <div class="answer-display">{{detailObj.answer}}</div>
          </el-form-item>
        </el-form>
      </div>

      <template #footer>
        <span slot="footer" class="dialog-footer">
          <el-button @click="closeRevise">取 消</el-button>
          <el-button type="primary" @click="submitRevise">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { selectQuestionByUser,reviseQuestionByUserId,delQuestionByUserId } from '../../../api/question.js'
import { User, Phone, View, Edit, Delete } from '@element-plus/icons-vue'

export default {
  components: {
    User,
    Phone,
    View,
    Edit,
    Delete
  },
  data(){
    return{
      questionArray:[],
      showDetail: false,
      dialogVisible: false,
      role:"",
      detailObj:{
        title:'',
        question:'',
        answer:'',
        address:'',
        area:'',
        expertName:'',
        plantCondition:'',
        plantDetail:'',
        plantName:'',
        questioner:'',
        soilCondition:'',
        status:1
      }
    }
  },
  methods:{
    getData(){
      this.role = this.$store.getters.isExpert?'expert':'questioner'
      // kind：普通用户：questioner；专家：expert
      selectQuestionByUser({role:this.role}).then(res => {
        this.questionArray = res.data
      }).catch(err=>{
        console.log(err)
      })
    },
    delQuestion(item){
      this.$confirm('确认删除该行信息？', '删除确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delQuestionByUserId({id:item.id}).then(res=>{
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.getData()
        }).catch(err=>{
          console.log(err)
          this.$message.error('删除失败')
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleDetail(item){
      this.showDetail = true
      this.detailObj = Object.assign({},{...item})
    },
    detailClose(){
      this.showDetail = false
    },
    handleEdit(item){
      this.dialogVisible = true
      this.detailObj = Object.assign({},{...item})
    },
    closeRevise(){
      this.dialogVisible = false
    },
    submitRevise(){
      if(this.role === 'expert'){
        this.detailObj.status = 1
      } else {
        this.detailObj.status = 0
      }

      reviseQuestionByUserId(this.detailObj).then(res => {
        this.$message.success('操作成功！')
        this.dialogVisible = false
        this.getData()
      }).catch(err=>{
        console.log(err)
        this.$message.error('操作失败')
      })
    }
  },
  mounted(){
    this.$store.commit("updateUserActiveIndex", "4-1");
    this.getData()
  }
}
</script>

<style lang="less" scoped>
.expert-question-container {
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

  .question-item {
    margin-bottom: 20px;

    .question-card {
      border-radius: 12px;
      border: 1px solid #ebeef5;
      transition: all 0.3s ease;

      &:hover {
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        transform: translateY(-2px);
      }

      .question-content {
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
          grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
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

      .question-actions {
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

        .answer-display {
          background-color: #f5f7fa;
          border-radius: 4px;
          padding: 15px;
          min-height: 100px;
          line-height: 1.6;
        }
      }
    }

    .dialog-footer {
      text-align: right;
    }
  }
}
</style>