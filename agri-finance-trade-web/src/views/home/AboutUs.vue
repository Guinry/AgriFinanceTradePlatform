<template>
  <div class="about-us-container">
    <Subtitle subtitle="关于我们" description="超越期望，满足你的每一个需求"/>

    <div class="about-us-content">
      <el-row :gutter="20">
        <el-col :span="8" class="left-column">
          <div class="title">
            <div>关于我们</div>
            <div class="sub-title">ABOUT US</div>
            <div class="divider">——</div>
          </div>
        </el-col>
        <el-col :span="14" class="right-column">
          <div class="content">
            <h3>项目介绍：</h3>
            <p>融销通是一款基于数字经济的农产品融销一体平台</p>
            <p>从用户的角度出发，能够很好的支持农户进行产销购入等活动：针对性强，使用简便，农户进行买卖商品，联系客户时，操作简单易于理解。将农产品求购与货源同时展出，提高了成功率。长此以往将形成一个良性循环。</p>
            <p>融销通支持农业专家进行知识分享，解惑答疑等工作，农户不仅在网站平台上进行商品的买卖，如果遇到有关问题，可以线上与专家讨论，搜索相关知识，也可以与专家线下预约，解决农业问题。</p>
            <p>同时，平台也提供了一个良好的银行与用户进行融资的环境，农户用户作为贷款很大的群体，实际中却不尽人意，银行方面，难以获取实际需求用户，难以了解用户需求，农民方面，文化水平有限或单方面排斥，不能很好的相关获取信息，利用银行资源。平台瞄准需求，给银行推智能匹配客户，给农户开通融资申请接口，方便双方沟通，达到双赢。</p>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import Subtitle from "../../components/Subtitle.vue";
</script>

<style lang="less" scoped>
.about-us-container {
  text-align: center;
  margin-top: -40px;
}

.about-us-content {
  display: flex;
  justify-content: center;
  padding: 0 40px; /* 降低左右内边距 */
  align-items: stretch;
}

.row {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.left-column,
.right-column {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}

.title {
  background-color: #4ab344;
  width: 100%;
  padding: 20px 0;
  margin-bottom: 10px;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.title div {
  font-family: 'PingFang SC', sans-serif;
  font-weight: 600;
  font-size: 28px;
  color: white;
}

.sub-title {
  font-size: 18px;
  font-weight: 400;
  margin-top: 5px;
  color: #ffffff;
}

.divider {
  font-size: 20px;
  margin-top: 10px;
  color: #ffffff;
}

.content {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  text-align: left;
  line-height: 1.8;
  letter-spacing: 0.5px;
  margin-top: 20px;

  p {
    margin-bottom: 20px;
    text-indent: 2em;
  }

  h3 {
    font-size: 20px;
    color: #4ab344;
    margin-bottom: 10px;
    text-align: left;
  }
}

@media (max-width: 1200px) {
  .about-us-content {
    padding: 0 15px; /* 调整为更小的内边距 */
  }
}

@media (max-width: 768px) {
  .about-us-content {
    padding: 0 10px;
    flex-direction: column;
    text-align: left;
  }

  .title {
    width: 100%;
  }
}
</style>
