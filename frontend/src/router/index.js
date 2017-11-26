import Vue from 'vue'
import Router from 'vue-router'
import NavView from '@/components/NavView'
import Register from '@/components/Register.vue'
import Login from '@/components/Login.vue'
import UserSearch from '@/components/UserSearch.vue'
import UserWatchlistSocketTest from '@/components/UserWatchlistSocketTest.vue'
import ViewWatchList from '@/components/ViewWatchList.vue'
import OrderBook from '@/components/OrderBook.vue'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'LandingPage',
      component: NavView
    },
    {
      path: '/coin/:cur',
      name: 'CoinView',
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
    },
    {
    		path:'/WatchList',
    		component: ViewWatchList
    }
  ]
})
