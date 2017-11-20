<template>
  <div>
    <div id="main" style="width: 800px;height:600px;"></div>
  </div>
</template>

<script>
  import { GC_BACKEND, GC_UPDATE_TIMES } from '@/constants/settings.js'

  export default {
    data () {
      return {
        lastTimestamp: -1,
        dispData: null,
        dispDates: null,
        chartUpdateInterval: null,
        waitingForUpdate: false,
        useChart: null,
        currentlySetting: false
      }
    },
    props: [
      'shouldUpdateGraph', 
      'dispCur',
      'toCur',
      'market',
      'dataPeriod'
    ],
    methods: {
      setChartOptions: function() {
        console.log("Setting chart options");
        if (this.useChart == null) {
          this.useChart = echarts.init(document.getElementById('main'));
        }
        var option = {
          title: {
            text: 'Price chart',
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          xAxis: {
            type: 'category',
            data: this.dispDates,
            scale: true,
            boundaryGap : false,
            axisLine: {onZero: false},
            splitLine: {show: false},
            splitNumber: 20,
            min: 'dataMin',
            max: 'dataMax'
          },
          yAxis: {
            scale: true,
            splitArea: {
              show: true
            }
          },
          dataZoom: [
              {
                  type: 'inside',
                  start: 50,
                  end: 100
              },
              {
                  show: true,
                  type: 'slider',
                  y: '90%',
                  start: 50,
                  end: 100
              }
          ],
          grid: {
            left: '10%',
            right: '10%',
            bottom: '15%'
          },
          animation: false,
          series: [{
            type: 'candlestick',
            name: 'Prices',
            data: this.dispData,
            itemStyle: {
                normal: {
                    color: '#FD1050',
                    color0: '#0CF49B',
                    borderColor: '#FD1050',
                    borderColor0: '#0CF49B'
                }
            }
          }]
        };

        this.useChart.setOption(option);
      },
      updateChart: function() {
        if (this.waitingForUpdate || !this.shouldUpdateGraph || this.currentlySetting) {
          return;
        }

        // Get the data from last data point until now. 
        console.log("Calling update!!!");

        var _this = this;
        this.waitingForUpdate = true;
        this.$http.get(GC_BACKEND + "/exchange/candle", {
          params: {
            fromCur: this.dispCur,
            toCur: this.toCur,
            exchange: this.market,
            period: this.dataPeriod,
            begin: _this.lastTimestamp
          }
        }, {}).then(response => {
          var res = response.body;

          var periods = res.periods;
          var points = periods[_this.dataPeriod].timeSeriesPoints;

          var pointData = _this.getPointData(points);
          var dateData = _this.getDateData(points);

          _this.lastTimestamp = pointData[points.length - 1].timestamp;
          _this.dispData = _this.dispData.concat(pointData);
          _this.dispDates = _this.dispDates.concat(dateData);

          // Last point before chart is set.
          if (_this.currentlySetting) {
            return;
          }

          _this.setChartOptions();
          _this.waitingForUpdate = false;
        }, response => {
          console.log("Error!");
        });
      },
      getDateData: function (points) {
          return _.map(points, function (trade) {
            return trade.dateStr;
          });
      },
      getPointData: function (points) {
          return _.map(points, function (trade) {
            return [+trade.open, +trade.high, +trade.low, +trade.close];
          });
      },
      setUpdateInterval: function () {
        if (this.dataPeriod in GC_UPDATE_TIMES) {
          var updateInterval = GC_UPDATE_TIMES[this.dataPeriod];
          this.chartUpdateInterval = setInterval(this.updateChart, updateInterval);
        }
      },
      setChart: function(dataPeriod) {
        console.log("Fetching data");
        console.log("Got data period " + dataPeriod);
        var _ = this._;
        var _this = this;

        // We don't want the chart updating part of the way through this.
        if (this.chartUpdateInterval != null) {
          clearInterval(this.chartUpdateInterval);
          this.chartUpdateInterval = null;
          this.currentlySetting = true;
        }

        console.log("Calling endpoint");
        this.$http.get(GC_BACKEND + "/exchange/candle", {
          params: {
            fromCur: this.dispCur,
            toCur: this.toCur,
            exchange: this.market,
            period: dataPeriod
          }
        }, {}).then(response => {
          var res = response.body;

          var periods = res.periods;
          console.log("Fetching period " + _this.dataPeriod);
          var points = periods[dataPeriod].timeSeriesPoints;

          this.currentlySetting = false;
          _this.lastTimestamp = points[points.length - 1].timestamp;
          _this.dispData = _this.getPointData(points)
          _this.dispDates = _this.getDateData(points);

          _this.setChartOptions();

          _this.setUpdateInterval();
        }, response => {
          console.log("failure");
          console.log(response);
        });
      }
    },
    watch: {
    },
    destroyed: function() {
      clearInterval(this.chartUpdateInterval);
    },
    created: function () {
      this.$parent.$on('updateChart', this.setChart);
    },
    mounted: function () {
      // Request chart data.
      this.setChart(this.dataPeriod);
    }
  }
</script>
