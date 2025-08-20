<template>
  <div>
    <KnowledgeList />
  </div>
</template>

<script setup>
import KnowledgeList from "../../../components/KnowledgeList.vue";
import { onMounted } from 'vue'
import { useStore } from 'vuex'

const store = useStore()

onMounted(() => {
  store.commit("updateUserActiveIndex", "2-3");
})
</script>

<style>
</style>