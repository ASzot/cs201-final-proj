<template>
  <div id="app">
    <v-app>
      <main>
        <router-view/>
        <header></header>
	    <div class="flex flex-fixed">
	      <div v-if="userId" class="ml1 pointer" @click="logout()">logout</div>
	      <div v-else>
		      <router-link v-if= "$route.path != '/login'"  to="/login" class="ml1 no-underline">login</router-link>
		      <router-link v-if= "$route.path != '/register'"  to="/register" class="ml1 no-underline">register</router-link>
	      </div>
	    </div>
      </main>
    </v-app>
  </div>
</template>

<script>
import register from './components/register.vue'
import login from './components/login.vue'

export default {
  name: 'app',
  components: {
  	login, register
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
