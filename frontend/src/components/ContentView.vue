<template>
  <div>
    <div id="main" style="width: 800px;height:600px;"></div>
  </div>
</template>

<script>
  import { GC_BACKEND } from '@/constants/settings.js'

  export default {
    methods: {
      setChartOptions: function(data, dates) {
        console.log("Setting chart options");
        var myChart = echarts.init(document.getElementById('main'));
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
            data: dates,
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
            data: data,
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

        console.log("Setting chart");
        myChart.setOption(option);
      },
      setChart: function() {
        console.log("Fetching data");
        var _ = this._;
        var _this = this;

        console.log("Calling endpoint");
        var period = "60";
        this.$http.get(GC_BACKEND + "/exchange/candle", {
          params: {
            fromCur: 'btc',
            toCur: 'usd',
            period: period
          }
        }, {}).then(response => {
          console.log("Preparing to transform");
          console.log(response);
          var res = response.body;
          var periods = res.periods;
          var points = periods[period].timeSeriesPoints;
          //console.log(res);
          var data = _.map(points, function (trade) {
            return [+trade.open, +trade.high, +trade.low, +trade.close];
          });
          var dates = _.map(points, function (trade) {
            return trade.dateStr;
          });

          console.log("Data transformed");
          _this.setChartOptions(data, dates);

        }, response => {
          console.log("failure");
          console.log(response);
        });
      }
    },
    mounted: function () {
      // Request chart data.
      //setChart();
    }
  }
</script>
