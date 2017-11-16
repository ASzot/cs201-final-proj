import Vue from 'vue'
import Router from 'vue-router'
import NavView from '@/components/NavView'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'NavView',
      component: NavView
    }
  ]
})
