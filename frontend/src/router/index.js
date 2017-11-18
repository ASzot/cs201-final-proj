import Vue from 'vue'
import Router from 'vue-router'
import NavView from '@/components/NavView'
import register from '@/components/register.vue'
import login from '@/components/login.vue'
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
        component: login
    },
    {
    	 	path: '/register',
    	 	component: register
    }
  ]
})
