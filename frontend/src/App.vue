<template>
  <div id="app">
    <v-app>
      <router-view></router-view>
    </v-app>
  </div>
</template>

<script>
import register from '@/components/Register.vue'
import login from '@/components/Login.vue'
import { GC_USER_ID, GC_AUTH_TOKEN } from '@/constants/settings.js'

export default {
  name: 'app',
  components: {
      login, register
  },
  computed: {
      userId () {
        return this.$root.$data.userId
      }
  },
  methods: {
      logout () {
        localStorage.removeItem(GC_USER_ID)
        localStorage.removeItem(GC_AUTH_TOKEN)
        this.$root.$data.userId = localStorage.getItem(GC_USER_ID)
      }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
