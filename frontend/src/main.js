// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuetify from 'vuetify'
import VueResource from 'vue-resource'
import App from './App'
import router from './router'
import('../node_modules/vuetify/dist/vuetify.min.css')
import lodash from 'lodash'
import VueLodash from 'vue-lodash'

Vue.use(Vuetify);
Vue.use(VueResource);
Vue.use(VueLodash, lodash);

Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
});
