import { createRouter, createWebHashHistory } from 'vue-router'

import Home from '../views/Home.vue'
import FrontPage from '../views/FrontPage/FrontPage.vue'

const routes = [
    {
        path: '/',
        redirect: '/home'
    },
    {
        path: '/home',
        name: 'Home',
        component: Home,
        children: [
            {
                path: '',
                redirect: 'front'
            },
            {
                path: 'front',
                name: 'FrontPage',
                component: FrontPage
            }
        ]
    },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
