<template>
  <div>
    <v-list dense>
      <v-list-tile v-for="item in items" :key="item.text">
        <v-list-tile-action>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-tile-action>
        <v-list-tile-content>
          <v-list-tile-title @click="onItemSelected">
            {{ item.text }}
          </v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-list>
  </div>
</template>

<script>
  import { GC_USER_ID, GC_LOGGED_IN, GC_BACKEND } from '@/constants/settings'

  export default {
    name: 'SideBarMenu',
    methods: {
      onItemSelected: function (event) {
        var innerText = event.target.innerHTML.trim();
        if (innerText == "Currencies") {
          this.$emit("navToCurrency");
        }
        else if(innerText == "Watchlist") {
        		this.$router.push("/watchlist");
        }
        else {
          this.snackbar = true;
          this.$emit("showError", "You need to be logged in to perform this action");
        }
      }
    },
    data: () => ({
      items: [
        { icon: 'trending_up', text: 'Currencies' },
        { icon: 'list' , text: 'Watchlist'}
      ]
    })
  }
</script>
