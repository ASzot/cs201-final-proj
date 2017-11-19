<template>
  <div>
    <h3>{{ dispCur }}</h3>
    <v-checkbox label="Dynamically Update Chart?" @click="checkedUpdate"></v-checkbox>
    <candle-stick-chart v-bind:shouldUpdateGraph="shouldUpdate" v-bind:dispCur="dispCur"></candle-stick-chart>
  </div>
</template>

<script>
  import CandleStickChart from '@/components/CandleStickChart.vue'
  export default {
    components: {
      CandleStickChart
    },
    props: [
      'dispCur'
    ],
    data () {
      return {
        shouldUpdate: false
      }
    },
    methods: {
      checkedUpdate: function () {
        this.shouldUpdate = !this.shouldUpdate;
      }
    }
  }
</script>

<style scoped>
</style>
