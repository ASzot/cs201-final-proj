<template>
  <v-app
    height="415px"
    id="e3"
    light
    standalone>
    <v-navigation-drawer
      class="pb-0"
      fixed
      height="100%"
      clipped
      enable-resize-watcher
      v-model="drawer" >
      <v-list dense>
        <v-list-tile v-for="item in items" :key="item.text">
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>
              {{ item.text }}
            </v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
        <v-subheader class="mt-3 grey--text text--darken-1">SUBSCRIPTIONS</v-subheader>
        <v-list-tile class="mt-3">
          <v-list-tile-action>
            <v-icon class="grey--text text--darken-1">add_circle_outline</v-icon>
          </v-list-tile-action>
          <v-list-tile-title class="grey--text text--darken-1">Browse Channels</v-list-tile-title>
        </v-list-tile>
        <v-list-tile>
          <v-list-tile-action>
            <v-icon class="grey--text text--darken-1">settings</v-icon>
          </v-list-tile-action>
          <v-list-tile-title class="grey--text text--darken-1">Manage Subscriptions</v-list-tile-title>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>
    <v-toolbar class="blue">
      <v-toolbar-title>
        <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
        <span>Cryptowatch</span>
	      </v-toolbar-title>
	       <div class="flex flex-fixed">
	 	      <div v-if="userId" class="ml1 pointer" @click="logout()">logout</div>
	 	      <div v-else>
	 		      <router-link v-if= "$route.path != '/login'"  to="/login" class="ml1 no-underline">login</router-link>
	 		      <router-link v-if= "$route.path != '/register'"  to="/register" class="ml1 no-underline">register</router-link>
	 	      </div>
	 	   </div>
    </v-toolbar>
    <main>
      <v-container>
        <v-layout>
          <v-flex xs12 id="content-space">
            <currency-view></currency-view>
          </v-flex>
        </v-layout>
      </v-container>
    </main>
  </v-app>
</template>

<script>
  import CurrencyView from '@/components/CurrencyView.vue'
  export default {
    components: {
      CurrencyView
    },
    methods: {
    	  logout () {
          localStorage.removeItem(GC_USER_ID)
          localStorage.removeItem(GC_AUTH_TOKEN)
          this.$root.$data.userId = localStorage.getItem(GC_USER_ID)
      }
    },
    computed: {
        userId () {
          return this.$root.$data.userId
        }
    },
    data: () => ({
      drawer: true,
      items: [
        { icon: 'trending_up', text: 'Most Popular' },
        { icon: 'subscriptions', text: 'Subscriptions' },
        { icon: 'history', text: 'History' },
        { icon: 'featured_play_list', text: 'Playlists' },
        { icon: 'watch_later', text: 'Watch Later' }
      ]
    }),
    watch: {
      drawer: function(val) {
        console.log("Drawer changed!");
        if (val) {
          document.getElementById("content-space").style.marginLeft = "300px";
        }
        else {
          document.getElementById("content-space").style.marginLeft = "0px";
        }
      }
    }
  }
</script>

<style>
  .pb-0 {
    margin-top: 65px !important;
    height: calc(100% - 65px) !important;
  }
  #content-space {
    margin-left: 300px;
  }
</style>
