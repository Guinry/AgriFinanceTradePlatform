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
        path: '/agriculturalKnowledge/knowledgeDetails/:id',
        name: 'KnowledgeDetails',
        component: () => import('../views/agriculturalKnowledge/KnowledgeDetails.vue')
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
        path: '/expertGuidance/guideDetails/:id',
        name: 'ExpertDetails',
        component: () => import('../views/expertGuidance/GuidanceDetails.vue')
    },
    {
        path: '/expertGuidance/askQuestion',
        name: 'AskQuestion',
        component: () => import('../views/expertGuidance/AskQuestion.vue')
    },
    {
        path: '/expertGuidance/makeAppointment',
        name: 'MakeAppointment',
        component: () => import('../views/expertGuidance/MakeAppointment.vue')
    },
    {
        path: '/financeApplication',
        name: 'FinanceApplication',
        component: () => import('../views/financeApplication/FinanceApplication.vue')
    },
    {
        path: '/smartMatch',
        name: 'SmartMatch',
        component: () => import('../views/financeApplication/SmartMatch.vue')
    },
    {
        path: '/financeDetails',
        name: 'FinanceDetails',
        component: () => import('../views/financeApplication/FinanceDetails.vue')
    },
    {
        path: '/userCenter',
        component: () => import('../views/userCenter/UserCenter.vue'),
        children: [
            {
                path: '',
                redirect: '/userInformation'
            },
            {
                path: '/userInformation',
                name: 'UserInformation',
                component: () => import('../views/userCenter/userInformation/UserInformation.vue')
            },
            {
                path: '/userAddress',
                name: 'UserAddress',
                component: () => import('../views/userCenter/userInformation/UserAddress.vue')
            },
            {
                path: '/userPassword',
                name: 'UserPassword',
                component: () => import('../views/userCenter/userInformation/UserPassword.vue')
            },
            {
                path: '/userGoods',
                name: 'UserGoods',
                component: () => import('../views/userCenter/userPublish/UserGoods.vue')
            },
            {
                path: '/publishGoods',
                name: 'PublishGoods',
                component: () => import('../views/userCenter/userPublish/PublishGoods.vue')
            },
            {
                path: '/userNeeds',
                name: 'UserNeeds',
                component: () => import('../views/userCenter/userPublish/UserNeeds.vue')
            },
            {
                path: '/publishNeeds',
                name: 'PublishNeeds',
                component: () => import('../views/userCenter/userPublish/PublishNeeds.vue')
            },
            {
                path: '/userQuestion',
                name: 'UserQuestion',
                component: () => import('../views/userCenter/userExpert/UserQuestion.vue')
            },
            {
                path: '/userAppointment',
                name: 'UserAppointment',
                component: () => import('../views/userCenter/userExpert/UserAppointment.vue')
            },
            {
                path: '/userBuy',
                name: 'UserBuy',
                component: () => import('../views/userCenter/userOrder/UserBuy.vue')
            },
            {
                path: '/userSell',
                name: 'UserSell',
                component: () => import('../views/userCenter/userOrder/UserSell.vue')
            },
            {
                path: '/userFinance',
                name: 'UserFinance',
                component: () => import('../views/userCenter/userFinance/UserFinance.vue')
            }
        ]
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router