<template>
  <div class='curr-list-container'>
    <v-text-field v-model="searchText" v-on:keyup="onSearchChange" label="Search"></v-text-field>
    <v-list dense>
      <v-list-tile-content v-for="ticker in filteredTickers" :key="ticker" >
        <v-list-tile-title class='center-currency' @click="onOptionSelected">
          {{ ticker }} 
        </v-list-tile-title>
      </v-list-tile-content>
    </v-list>
  </div>
</template>
<script>
  import { GC_BACKEND } from '@/constants/settings.js'

  export default {
    data () {
      return {
        filteredTickers: [],
        tickers: [],
        searchText: ""
      }
    },
    methods: {
      onOptionSelected: function (event) {
        var selectedTicker = event.target.innerHTML;
        selectedTicker = selectedTicker.trim().toLowerCase();
        this.$emit("changeDispCur", selectedTicker);
      },
      onSearchChange: function () {
        var searchText = this.searchText.toLowerCase();

        if (searchText != "") {
          this.filteredTickers = this._.filter(this.tickers, function (t) {
            return t.indexOf(searchText) !== -1;
          });
        }
        else {
          this.filteredTickers = this.tickers;
        }
      }
    },
    mounted: function () {
      var _this = this;
      var _ = this._;
      this.$http.get(GC_BACKEND + "/exchange/markets", {
        params: {
          allowedTos: "usd,usdt"
        }
      }, {}).then(response => {
        var res = response.body;
        // Get the from tickers
        var fromTickers = _.map(res, function (m) {
          return m.fromCur.ticker;
        });

        var uniqTickers = _.uniq(fromTickers);

        _this.tickers = uniqTickers;
        // To set the filtered tickers list
        _this.onSearchChange();
      }, response => {
        console.log("Error!");
      });
    }
  }
</script>

<style>
.center-currency {
  text-align: center;
  height: 40px;
}

.center-currency:hover {
  background-color: #3498db;
  cursor: pointer;
}

.curr-list-container {
  margin-left: 20px;
  margin-right: 20px;
}
</style>
