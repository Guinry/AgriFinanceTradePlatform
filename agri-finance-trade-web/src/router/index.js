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
        path: '/goodsSource',
        name: 'GoodsSource',
        component: () => import('../views/goodsSource/GoodsSource.vue')
    },
    {
        path: '/purchaseInfo',
        name: 'PurchaseInfo',
        component: () => import('../views/purchaseInfo/PurchaseInfo.vue')
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
