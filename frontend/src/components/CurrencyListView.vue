<template>
  <div class='curr-list-container'>
    <v-text-field v-model="searchText" v-on:keyup="onSearchChange" label="Search"></v-text-field>
    <v-list dense>
      <v-list-tile-content v-for="ticker in filteredTickers" :key="ticker.ticker" >
        <v-list-tile-title class='center-currency' @click="onOptionSelected">
          {{ ticker.ticker }}
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
        var searchText = this.searchText.toUpperCase();

        if (searchText != "") {
          this.filteredTickers = this._.filter(this.tickers, function (t) {
            return t.ticker.indexOf(searchText) !== -1;
          });
        }
        else {
          this.filteredTickers = this.tickers;
        }
      }
    },
    mounted: function () {
      var _this = this;
      this.$http.get(GC_BACKEND + "/exchange/all", {
      }, {}).then(response => {
        var res = response.body;

        _this.tickers = res;
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
