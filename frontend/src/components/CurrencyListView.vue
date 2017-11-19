<template>
  <v-list dense>
    <v-list-tile-content v-for="ticker in tickers" :key="ticker.ticker">
      <v-list-tile-title>
        {{ ticker.ticker }}
      </v-list-tile-title>
    </v-list-tile-content>
  </v-list>
</template>
<script>
  import { GC_BACKEND } from '@/constants/settings.js'

  export default {
    data () {
      return {
        tickers: []
      }
    },
    mounted: function () {
      var _this = this;
      this.$http.get(GC_BACKEND + "/exchange/all", {
      }, {}).then(response => {
        var res = response.body;

        _this.tickers = res;
      }, response => {
        console.log("Error!");
      });
    }
  }
</script>

<style>
.center-currency {
}
</style>
