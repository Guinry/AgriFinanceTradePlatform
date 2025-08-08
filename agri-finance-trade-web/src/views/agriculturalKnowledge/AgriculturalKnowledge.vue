<template>
  <div>
   <NavigationBar />
    <div class="font-background">
      <div class="font">
        <div style="writing-mode: vertical-rl;color: #FCFCFC;font:17px 'SF Pro SC';">PRODUCTION</div>
        <div style="writing-mode: vertical-rl;color: #FCFCFC;font:23px 宋体;margin-left: 36px;margin-top:18px">健康养殖</div>
        <div style="writing-mode: vertical-rl;color: #FCFCFC;font:23px 宋体;margin-left: 12px;margin-top:18px">品质精选</div>
      </div>
    </div>


    <Subtitle subtitle="知识列表" description="买到就是赚到哦~"  style="margin-top:100px;"/>
    <div class="home-konwledges">
      <knowledge :cknowledges="knowledges"></knowledge>
      <pagination
          @item-click="pageClick"
          :cUrl="url"
          :cTotal="total"
          :cPageSize="pageSize"
      ></pagination>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { selectKnowledgesPage } from "../../api/knowledge";
import Knowledge from "./KnowledgeCards.vue";
import Pagination from "../../components/Pagination.vue";
import Subtitle from "../../components/Subtitle.vue";
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

// 响应式数据
const knowledges = ref([])
const total = ref(0)
const pageSize = ref(10)
const url = ref("/knowledge/")
const knowledgesCount = ref(sessionStorage.getItem("/knowledge/pageCode")
  ? sessionStorage.getItem("/knowledge/pageCode")
  : 1)

// 使用 store
const store = useStore()

// 方法定义
const pageClick = (item) => {
  knowledges.value = item;
}

// 生命周期钩子
onMounted(() => {
  store.commit("updateActiveIndex", "4");
  selectKnowledgesPage({
    pageNum: knowledgesCount.value,
  }).then((res) => {
    if (res.flag === true) {
      let tmp = res.data.list;
      tmp.forEach(e => {
        const flieArr = e.picPath.split('.');
        e.type = flieArr[flieArr.length - 1]
      })
      knowledges.value = tmp;
      total.value = res.data.total;
    }
  });
})

// 组件注册
</script>

<style lang="less" scoped>

.font-background{
  background-image: url("../../assets/page4/background4.jpg");
  height: 350px;
  width: 1920px;
  padding-left: 650px;
  padding-top: 60px;
  .font{
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
