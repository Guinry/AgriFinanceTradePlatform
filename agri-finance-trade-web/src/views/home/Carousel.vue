<template>
  <el-carousel class="demonstration" trigger="click" :interval="5000" arrow="always">
    <el-carousel-item>
      <img src="../../assets/FrontPage/CarouselImages/carousel1.jpg" class="carousel-image" alt=""/>
      <div class="carousel-title1">
        <span class="big-font">裕</span><span class="small-font">则济天下</span>
        <span class="big-font">润</span><span class="small-font">则四方</span>
        <span class="big-font">丰</span><span class="small-font">之收获</span>
        <div class="subtitle">Give the economy moist quartet bumper harvest</div>
      </div>
    </el-carousel-item>
    <el-carousel-item>
      <img src="../../assets/FrontPage/CarouselImages/carousel2.jpg" class="carousel-image" alt=""/>
      <div class="carousel-title2">
        <div class="slogan">以质量求生存 以信誉求发展</div>
        <div class="main-text">为您提供优质的产品和您满意的服务</div>
        <div class="secondary-text">TO PROVIDE YOU WITH QUALITY PRODUCTS AND</div>
      </div>
    </el-carousel-item>
  </el-carousel>
</template>

<script setup>
// 脚本部分可以按需要进行扩展
</script>

<style lang="less" scoped>
.demonstration {
  margin: 0;
  padding: 0;
}

:deep(.el-carousel__container) {
  position: relative;
  height: 350px;
}

:deep(.el-carousel__indicators--horizontal) {
  position: absolute;
  left: 50%;
  bottom: 10px;
  transform: translateX(-50%);
  text-align: center;
}

:deep(.el-carousel__indicator--horizontal button) {
  width: 60px;
  height: 6px;
  background: rgba(255, 255, 255, 0.7);
  border-radius: 3px;
}

:deep(.el-carousel__indicator--horizontal.is-active button) {
  background: #ffffff;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 确保图片铺满容器 */
}

.carousel-title1, .carousel-title2 {
  position: absolute;
  color: white;
  z-index: 2;
  width: 100%;
  padding-left: 20px;
  padding-right: 20px;
}

.carousel-title1 {
  top: 25%; /* 调整文字位置避免重叠 */
  left: 0;
  right: 0;
  text-align: center;
}

.big-font {
  font-family: '思源黑体 Heavy';
  font-size: 80px;
  color: rgb(78, 143, 55);
}

.small-font {
  font-family: '思源黑体 Heavy';
  font-size: 40px;
  color: #2E2921;
}

.subtitle {
  font-size: 20px;
  font-family: '思源黑体 Heavy';
  color: rgb(145, 145, 133);
  margin-top: 20px;
}

.carousel-title2 {
  top: 20%;
  left: 15%;
  height: 300px;
  width: 70%;
  background: url("../../assets/FrontPage/fontback2.png") no-repeat center center;
  background-size: cover;
  padding-top: 30px;
  color: white;
  text-align: left;
}

.slogan {
  background-color: #F07C0A;
  color: white;
  height: 39px;
  width: 275px;
  font-size: 18px;
  font-family: '思源黑体 Heavy';
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 78px;
  border-radius: 20px;
}

.main-text {
  font-size: 53px;
  font-family: '思源黑体 Heavy';
  width: 494px;
  height: 116px;
  margin-left: 100px;
  color: #0E9821;
  margin-top: 10px; /* 增加上边距来分隔 */
}

.secondary-text {
  font-size: 15px;
  font-family: 'Microsoft YaHei';
  margin-left: 100px;
  color: #666666;
  margin-top: 30px; /* 增加上边距来分隔 */
}

@media (max-width: 768px) {
  .carousel-title1 .big-font {
    font-size: 60px;
  }

  .carousel-title1 .small-font {
    font-size: 30px;
  }

  .carousel-title2 {
    width: 90%;
    padding-top: 10px;
  }

  .main-text {
    font-size: 40px;
    margin-left: 20px;
  }

  .secondary-text {
    font-size: 14px;
    margin-left: 20px;
  }
}

@media (max-width: 1200px) {
  .carousel-title1 .big-font {
    font-size: 70px;
  }

  .carousel-title1 .small-font {
    font-size: 35px;
  }
}
</style>
