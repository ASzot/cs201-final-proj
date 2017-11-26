<template>
  <div>
    <h3>{{ dispCur }}</h3>
    <div v-if="userId" class="ml1 pointer" @click="addToWatchlist(dispCur)" style = "font-size:1.5em; cursor: pointer; width:20%;">Add to WatchList</div> <br/>
    <div>
        <p>{{ errorMsg }}</p>
    </div>
    <v-container grid-list-md text-xs-center>
      <v-layout row wrap>
        <v-flex xs4>
          <v-card>
            <v-card-text class="px-0">Volume</v-card-text>
            <v-card-text class="px-0">{{ volume }}</v-card-text>
          </v-card>
        </v-flex>
        <v-flex xs2>
          <v-card>
            <v-card-text class="px-0">High</v-card-text>
            <v-card-text class="px-0">{{ high }}</v-card-text>
          </v-card>
        </v-flex>
        <v-flex xs2>
          <v-card>
            <v-card-text class="px-0">Low</v-card-text>
            <v-card-text class="px-0">{{ low }}</v-card-text>
          </v-card>
        </v-flex>
        <v-flex xs2>
          <v-card>
            <v-card-text class="px-0">Last</v-card-text>
            <v-card-text class="px-0">{{ last }}</v-card-text>
          </v-card>
        </v-flex>
        <v-flex xs2>
          <v-card>
            <v-card-text class="px-0">Change</v-card-text>
            <v-card-text v-bind:class="percentStyle">{{ Math.abs(percentChange) }}</v-card-text>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>

    <v-checkbox v-model="shouldUpdate" label="Dynamically Update Chart?"></v-checkbox>

    <v-container fluid>
      <v-layout row wrap>
        <v-flex xs6>
          <v-card class='card-pad'>
            <v-subheader>Candle Interval</v-subheader>
            <v-select
              v-bind:items="possDataPeriods"
              v-model="selectedDataPeriod"
              :placeholder="selectedDataPeriod.text"
              single-line
              bottom>
            </v-select>
          </v-card>
        </v-flex>
        <v-flex xs6>
          <v-card class='card-pad'>
            <v-subheader>Data Start</v-subheader>
            <v-select
              v-bind:items="possDataStarts"
              v-model="selectedDataStart"
              :placeholder="selectedDataStart.text"
              single-line
              bottom>
            </v-select>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>

    <period-picker :mvas="mvas"></period-picker>

    <candle-stick-chart 
      v-bind:shouldUpdateGraph="shouldUpdate" 
      v-bind:dispCur="dispCur"
      v-bind:toCur="toCur"
      v-bind:market="useMarket"
      v-bind:dataPeriod="selectedDataPeriod.val"
      v-bind:dataStart="selectedDataStart.val"
      v-bind:mvas="mvas"
      >
    </candle-stick-chart>
  </div>
</template>

<script>
  import CandleStickChart from '@/components/CandleStickChart.vue'
  import PeriodPicker from '@/components/PeriodPicker.vue'
  import { GC_BACKEND } from '@/constants/settings.js'
  import { GC_USER_ID, GC_AUTH_TOKEN } from '@/constants/settings'

  export default {
    components: {
      CandleStickChart, PeriodPicker
    },
    props: [
      'dispCur'
    ],
    data () {
      return {
        // Configure data start
        selectedDataStart: { text: '1 month', val: "2629743" },
        errorMsg: "",
        // Configure starting moving averages
        mvas: [4, 10],
        possDataStarts: [
          { text: '30 mins', val:'1800' },
          { text: '1 hr', val:'3600' },
          { text: '1 day', val:'86400' },
          { text: '1 week', val:'604800' },
          { text: '1 month', val:'2629743' },
          { text: '6 months', val:'15778458' },
          { text: '1 year', val:'31556926' },
        ],
        // Configure starting day period
        selectedDataPeriod: { text: '1 day', val: '86400' },
        possDataPeriods: [
          { text: '1 min', val: '60' },
          { text: '5 mins', val: '300' },
          { text: '10 mins', val:'600' },
          { text: '15 mins', val:'900' },
          { text: '30 mins', val:'1800' },
          { text: '1 hr', val:'3600' },
          { text: '1 day', val:'86400' },
          { text: '1 week', val:'604800' },
        ],
        shouldUpdate: false,
        useMarket: 'gdax',
        toCur: 'usd',
        volume: 0,
        high: 0,
        low: 0,
        last: 0,
        percentChange: 0
      }
    },
    methods: {
      displaySummary: function () {
        var _this = this;
        this.$http.get(GC_BACKEND + "/exchange/summary", {
          params: {
            fromCur: this.dispCur,
            toCur: this.toCur,
            exchange: this.useMarket
          }
        }, {}).then(response => {
          var res = response.body;
          _this.volume = res.volume;
          _this.high = res.price.high;
          _this.low = res.price.low;
          _this.last = res.price.last;
          _this.percentChange = res.price.change.percentage.toFixed(2);
        }, response => {
          console.log("Error");
        });
      },
      updateView: function (afterUpdate) {
        var _this = this;
        this.$http.get(GC_BACKEND + "/exchange/markets", {
          params: {
            allowedTos: "usd,usdt"
          }
        }, {}).then(response => {
          var res = response.body;
          // Get where the from is dispCur.

          var matchingCurs = _.filter(res, function (m) {
            return m.fromCur.ticker == _this.dispCur;
          });

          // Just get the first one 
          var useMarket = matchingCurs[0];

          console.log("Got matching market");
          console.log(useMarket);

          _this.useMarket = useMarket.exchange;
          console.log("Changed market to " + _this.useMarket);
          _this.toCur = useMarket.toCur.ticker;

          this.$emit("updateChart", {
            dataStart: this.selectedDataStart.val, 
            dataPeriod: this.selectedDataPeriod.val
          });

          if (afterUpdate != null) {
            afterUpdate();
          }
        }, response => {
          console.log("Error");
        });
      },
      addToWatchlist: function (dispCur) {
        console.log(dispCur);
        console.log("User id: " + localStorage.getItem(GC_USER_ID));
        var _this = this;
        this.$http.post(GC_BACKEND + "/user/addTicker", {
            ticker: dispCur,
            userID: localStorage.getItem(GC_USER_ID)
        }, {}).then(response => {
          var res = response.body;
          console.log("Got response");
          if (!res.okay) {
            _this.errorMsg = "You have already added the ticker";
            alert("You have already added the ticker " + dispCur);
          }
          else {
            console.log(dispCur + " Added");
            alert(dispCur + " has been added to your watch list");
          }

        }, response => {
          console.log("Error!");
        });
        
      }
    },
    computed: {
      percentStyle: function () {
        return "px0 " + (this.percentChange < 0 ? "neg-percent" : "pos-percent");
      },
       userId () {
          return localStorage.getItem(GC_USER_ID);
        }
    },
    watch: {
      dispCur: function(val) {
        console.log("Display currency changed!");
        this.updateView(this.displaySummary);
      },
      selectedDataPeriod: function (val) {
        this.$emit("updateChart", {
          dataStart: this.selectedDataStart.val, 
          dataPeriod: this.selectedDataPeriod.val
        });
      },
      selectedDataStart: function (val) {
        this.$emit("updateChart", {
          dataStart: this.selectedDataStart.val, 
          dataPeriod: this.selectedDataPeriod.val
        });
      }
    },
    mounted: function () {
      this.displaySummary();
    }
  }
</script>

<style>
  .neg-percent {
    color: #e74c3c;
  }
  .pos-percent {
    color: #2ecc71;
  }
  .card-pad {
    padding: 5px;
  }
</style>
