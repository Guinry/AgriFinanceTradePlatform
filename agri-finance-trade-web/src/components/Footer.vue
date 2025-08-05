<template>
  <footer class="footer">
    <div class="container">
      <div class="row">
        <div class="col-md-3">
          <h4>关于我们</h4>
          <ul>
            <li><a href="#">平台简介</a></li>
            <li><a href="#">服务协议</a></li>
            <li><a href="#">隐私政策</a></li>
          </ul>
        </div>
        <div class="col-md-3">
          <h4>帮助中心</h4>
          <ul>
            <li><a href="#">常见问题</a></li>
            <li><a href="#">操作指南</a></li>
            <li><a href="#">安全保障</a></li>
          </ul>
        </div>
        <div class="col-md-3">
          <h4>联系我们</h4>
          <p>客服电话：19511687612</p>
          <p>工作时间：周一至周五 9:00-18:00</p>
          <p>邮箱：2386999483@qq.com</p>
        </div>
        <div class="col-md-3">
          <h4>关注我们</h4>
          <div class="social-icons">
            <a href="#"><i class="fab fa-weibo"></i></a>
            <a href="#"><i class="fab fa-weixin"></i></a>
            <a href="#"><i class="fab fa-qq"></i></a>
          </div>
        </div>
      </div>
      <hr />
      <p class="copyright">Copyright © 2025 融销通. 版权所有</p>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  background-color: #333;
  color: #fff;
  padding: 20px 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 15px;
}

.row {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: wrap;
}

.col-md-3 {
  flex: 0 0 25%;
  max-width: 25%;
  margin-bottom: 20px;
}

h4 {
  font-size: 16px;
  margin-bottom: 10px;
}

ul {
  list-style: none;
  padding: 0;
}

ul li {
  margin-bottom: 5px;
}

ul li a {
  text-decoration: none; /* 移除下划线 */
  color: #aaa; /* 设置初始颜色 */
  transition: color 0.3s ease;
}

ul li a:hover {
  color: #fff; /* 链接悬停时的颜色 */
  text-decoration: none; /* 悬停时也移除下划线 */
}

.social-icons a {
  text-decoration: none; /* 移除下划线 */
  color: #aaa;
  font-size: 20px;
  margin-right: 10px;
  transition: color 0.3s ease;
}

.social-icons a:hover {
  color: #fff;
  text-decoration: none; /* 悬停时也移除下划线 */
}

/* 添加：使版权信息居中 */
.copyright {
  text-align: center; /* 居中对齐 */
}

.footer {
  margin-top: auto;
}
</style>
<script setup lang="ts">
</script>