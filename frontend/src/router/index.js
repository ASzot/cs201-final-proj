import Vue from 'vue'
import Router from 'vue-router'
import NavView from '@/components/NavView'
import Register from '@/components/Register.vue'
import Login from '@/components/Login.vue'
import UserSearch from '@/components/UserSearch.vue'
import UserWatchlistSocketTest from '@/components/UserWatchlistSocketTest.vue'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'NavView',
      component: NavView
    },
    {
        path: '/login',
        component: Login
    },
    {
    	 	path: '/register',
    	 	component: Register
    },
    {
    		path: '/UserSearch',
    		component: UserSearch
    },
    {
    		path:'/UserWatchlistSocketTest',
    		component: UserWatchlistSocketTest
    }
  ]
})
