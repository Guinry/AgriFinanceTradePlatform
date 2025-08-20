<template>
  <div class="knowledge-box">
    <div class="knowledge" v-for="(item, index) in cknowledge" :key="index">
      <div class="flipper" >
        <div class="front">
          <video class="knowledge-img" width="200px" height="160px" v-if="item.type==='mp4'||item.type==='MP4'" accept="MP4,mp4" :src="$store.state.imgShowRoad + '/file/upload/order/' + item.picPath" controls autoplay/>
          <img v-else class="knowledge-img" :src="$store.state.imgShowRoad + '/file/upload/order/' + item.picPath" alt="" />
          <div class="front-title">{{ item.title }}</div>
        </div>
        <div class="back">
          <h3 class="back-title" @click="handleDetail(item)">{{ item.title }}</h3>
          <p class="content">{{ item.content }}</p>
          <span class="initiator">
          <img src="../../assets/img/up-user.png" @click="handleDetail(item)" alt=""/>
          {{item.ownName}}
        </span>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>
// 定义 props
const props = defineProps({
  cknowledge: {
    type: Array,
  },
})

// 引入 Composition API 相关功能
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

// 使用 store 和 router
const store = useStore()
const router = useRouter()

// 方法定义
const handleDetail = (item) => {
  router.push(`/agriculturalKnowledge/knowledgeDetails/${item.knowledgeId}`).catch((err) => err)
}
</script>

<style lang="less" scoped>
.knowledge-box {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  background-color: #f6f6f6;
  display: flex;
  flex-direction: row;
  justify-content: center;
  flex-wrap: wrap;
  gap: 30px;
  padding: 20px 0;

  .knowledge {
    width: 48%;
    max-width: 550px;
    height: 280px;
    padding: 0;
    border-radius: 15px;
    background-color: white;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    perspective: 1000px;
    transition: transform 0.3s ease;

    &:hover {
      transform: translateY(-5px);
    }

    .flipper {
      transition: 0.8s;
      transform-style: preserve-3d;
      position: relative;
      width: 100%;
      height: 100%;
      border-radius: 15px;

      .front{
        z-index: 2;
        backface-visibility: hidden;
        position: absolute;
        width: 100%;
        height: 100%;

        video{
          border: none;
          backface-visibility: hidden;
          position: absolute;
          width: 100%;
          height: 100%;
          object-fit: cover;
        }

        .knowledge-img {
          width: 100%;
          height: 100%;
          object-fit: cover;
          filter: brightness(70%);
        }

        .front-title{
          width: 100%;
          height: 100%;
          display: flex;
          align-items: center;
          justify-content: center;
          position: absolute;
          top: 0;
          left: 0;
          color: white;
          font-weight: bold;
          font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
          font-size: 22px;
          text-align: center;
          padding: 20px;
          box-sizing: border-box;
          text-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
        }
      }

      .back{
        width: 100%;
        height: 100%;
        padding: 25px;
        box-sizing: border-box;
        backface-visibility: hidden;
        position: absolute;
        top: 0;
        left: 0;
        transform: rotateY(180deg);
        background: linear-gradient(135deg, #f5f7fa 0%, #e4edf5 100%);
        display: flex;
        flex-direction: column;

        .back-title {
          font-weight: bold;
          font-size: 20px;
          color: #333;
          margin: 0 0 15px 0;
          cursor: pointer;
          transition: all 0.3s;

          &:hover{
            color: #035D1C;
            text-decoration: underline;
          }
        }

        .content {
          font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
          font-size: 15px;
          color: #555;
          line-height: 1.6;
          flex-grow: 1;
          overflow: hidden;
          display: -webkit-box;
          -webkit-line-clamp: 6;
          -webkit-box-orient: vertical;
          text-align: justify;
        }

        .initiator {
          color: #888;
          font-size: 14px;
          display: flex;
          align-items: center;
          align-self: flex-end;
          margin-top: 15px;

          img {
            width: 24px;
            height: 24px;
            margin-right: 8px;
            border-radius: 50%;
            cursor: pointer;
          }
        }
      }
    }
  }
}

/* flip the pane when hovered */
.knowledge:hover .flipper, .knowledge.hover .flipper {
  transform: rotateY(180deg);
}

@media (max-width: 1200px) {
  .knowledge-box {
    .knowledge {
      width: 45%;
    }
  }
}

@media (max-width: 768px) {
  .knowledge-box {
    gap: 20px;

    .knowledge {
      width: 100%;
      height: 250px;
    }
  }
}
</style>