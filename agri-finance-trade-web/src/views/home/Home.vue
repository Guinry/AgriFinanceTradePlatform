<template>
  <div class="home-content">
    <NavigationBar />
    <Carousel />
    <Search />
    <OurService />
    <HotSales />
    <AboutUs />
    <Footer />
  </div>
</template>

<script setup>
import NavigationBar from "../../components/NavigationBar.vue";
import Carousel from "./Carousel.vue";
import Search from "./Search.vue";
import OurService from "./OurService.vue";
import HotSales from "./HotSales.vue";
import AboutUs from "./AboutUs.vue";
import Footer from "../../components/Footer.vue";
</script>

<style lang="less" scoped> 
.home-content{
  width: 100%;
  height: 100%;
  overflow-x: hidden;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

</style>