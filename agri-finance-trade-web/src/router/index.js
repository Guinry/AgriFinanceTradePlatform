import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        redirect: '/home'
    },
    {
        path: '/home',
        name: 'Home',
        component: () => import('../views/home/Home.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/login/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/register/Register.vue')
    },
    {
        path: '/goodsSource',
        name: 'GoodsSource',
        component: () => import('../views/goodsSource/GoodsSource.vue')
    },
    // 添加: 商品详情页路由
    {
        path: '/goodsSource/goodsDetails',
        name: 'GoodsDetails',
        component: () => import('../views/goodsSource/GoodsDetails.vue')
    },
    {
        path: '/purchaseInfo',
        name: 'PurchaseInfo',
        component: () => import('../views/purchaseInfo/PurchaseInfo.vue')
    },
    {
        path: '/purchaseInfo/PurchaseDetails',
        name: 'PurchaseDetails',
        component: () => import('../views/purchaseInfo/PurchaseDetails.vue')
    },
    {
        path: '/agriculturalKnowledge',
        name: 'AgriculturalKnowledge',
        component: () => import('../views/agriculturalKnowledge/AgriculturalKnowledge.vue')
    },
    {
        path: '/shoppingCart',
        name: 'ShoppingCart',
        component: () => import('../views/shoppingCart/ShoppingCart.vue')
    },
    {
        path: '/expertGuidance',
        name: 'ExpertGuidance',
        component: () => import('../views/expertGuidance/ExpertGuidance.vue')
    },
    {
        path: '/financingApplication',
        name: 'FinancingApplication',
        component: () => import('../views/financingApplication/FinancingApplication.vue')
    },
    {
        path: '/smartMatch',
        name: 'SmartMatch',
        component: () => import('../views/financingApplication/SmartMatch.vue')
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router