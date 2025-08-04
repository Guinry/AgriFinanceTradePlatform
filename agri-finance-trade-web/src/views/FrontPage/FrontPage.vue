<template>
  <div class="front-container" m="t-4">
    <Carousel/>
    <Search/>
    <OurService/>
    <HotSales/>
<!--    <AboutUs/>-->
  </div>
  <footer/>
</template>


<script setup>
import OurService from "./OurService.vue";
import HotSales from "./HotSales.vue";
import Search from "./Search.vue";
import Carousel from "./Carousel.vue";
import AboutUs from "./AboutUs.vue";
import Footer from "../../components/Footer.vue";
</script>

<style lang="less" scoped>

.bottom-menu-container{
  background-color: #545C64;
  height: 200px;
  display: flex;
  justify-content: center;
  align-items: center;
  .bottom-menu-box{
    width: 280px;
    color: #ffbc49;
    font-size:20px;
    margin: 0 80px;
    text-align: center;
  }
}
</style>
