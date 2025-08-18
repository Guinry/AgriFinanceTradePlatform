<template>
  <NavigationBar />
  <div class="font-background">
    <div class="font">
      <div style="writing-mode: vertical-rl;color: #FCFCFC;font:17px 'SF Pro SC';">PRODUCTION</div>
      <div style="writing-mode: vertical-rl;color: #FCFCFC;font:23px 宋体;margin-left: 36px;margin-top:18px">健康养殖</div>
      <div style="writing-mode: vertical-rl;color: #FCFCFC;font:23px 宋体;margin-left: 12px;margin-top:18px">品质精选</div>
    </div>
  </div>
  <Subtitle subtitle="知识列表" description="买到就是赚到哦~"  style="margin-top:10px;"/>
  <div class="home-knowledge">
    <knowledge :cknowledge="knowledge"></knowledge>
    <pagination
        @item-click="pageClick"
        :cUrl="url"
        :cTotal="total"
        :cPageSize="pageSize"
        :current-page="knowledgeCount"
    ></pagination>
  </div>
  <Footer />
</template>

<script setup>
import {ref, onMounted} from 'vue'
import {useStore} from 'vuex'
import {selectKnowledgePage} from "../../api/knowledge";
import Knowledge from "./KnowledgeCards.vue";
import Pagination from "../../components/Pagination.vue";
import Subtitle from "../../components/Subtitle.vue";
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

// 响应式数据
const knowledge = ref([]);
const total = ref(0);
const pageSize = ref(10);
const url = ref("/knowledge/");
const knowledgeCount = ref(sessionStorage.getItem("/knowledge/pageCode")
    ? sessionStorage.getItem("/knowledge/pageCode")
    : 1)

// 使用 store
const store = useStore();

// 方法定义
const pageClick = (item) => {
  knowledge.value = item;
  console.log('Page clicked:', item); // 确认页码是否正确触发
};

// 生命周期钩子
onMounted(() => {
  loadKnowledge(knowledgeCount.value); // 加载数据
});

const loadKnowledge = (page) => {
  selectKnowledgePage({
    pageNum: page,
  }).then((res) => {
    if (res) {
      let tmp = res.list;
      tmp.forEach((e) => {
        const fileArr = e.picPath.split('.');
        e.type = fileArr[fileArr.length - 1];
      });
      knowledge.value = tmp;
      total.value = res.total;
    }
  });
}
</script>

<style lang="less" scoped>
.font-background {
  background-image: url("../../assets/page4/background4.jpg");
  height: 350px;
  width: 100%;
  max-width: 100%;
  padding-left: 350px;
  padding-right: 800px;
  padding-top: 30px;
  position: relative;
  background-repeat: no-repeat;
  background-size: cover;
  box-sizing: border-box;
  overflow: hidden; /* 超出部分隐藏 */

  .font {
    background-image: url('../../assets/page4/fontback4.png');
    background-size: 290px;
    background-repeat: no-repeat;
    height: 290px;
    padding-top: 175px;
    padding-left: 150px;
    display: flex;
  }
}
</style>
