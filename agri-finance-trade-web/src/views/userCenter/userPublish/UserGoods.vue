<template>
  <div style="height:100%">
    <UserMessage :ctype="type" :cdesciibe="describe"></UserMessage>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useStore } from 'vuex'
import UserMessage from "./UserMessage.vue";

const store = useStore();

const type = ref("goods");
const describe = ref("商品");

store.commit("updateUserActiveIndex", "2-1");
</script>

<style>
</style>
