import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import navbar from '../components/navbar'
import TodoList from '../components/TodoList'
import register from '../components/register'
import login from '../components/login'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: HelloWorld
    },
    {
        path: '/login',
        component: login
     },
     {
    	 	path: '/register',
    	 	component: register
     }
  ]
})
