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
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
