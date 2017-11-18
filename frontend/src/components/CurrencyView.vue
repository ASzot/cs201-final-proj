<template>
  <div>
    <v-checkbox label="Dynamically Update Chart?" @click="checkedUpdate"></v-checkbox>
    <candle-stick-chart v-bind:shouldUpdateGraph="shouldUpdate"></candle-stick-chart>
  </div>
</template>

<script>
  import CandleStickChart from '@/components/CandleStickChart.vue'
  export default {
    components: {
      CandleStickChart
    },
    data () {
      return {
        shouldUpdate: false
      }
    },
    methods: {
      checkedUpdate: function () {
        this.shouldUpdate = ~this.shouldUpdate;
      }
    }
  }
</script>

<style scoped>
</style>
