<template>
  <div>
    <div class="text-xs-center">
      <v-btn round color="primary" dark @click="onShow">{{ showMsg }}</v-btn>
    </div>
    <v-container v-if="shouldShow" fluid>
      <v-layout row wrap>
        <v-flex xs12>
          <v-card class='card-pad' style='text-align: center'>
            <v-btn color="success" @click="addMVA">Add Moving Average</v-btn>
          </v-card>
        </v-flex>
        <v-flex xs6 v-for="(mva, index) in mvas" :key="index">
          <v-card class='card-pad'>
            <span class='title-span'>Moving Average</span>
            <v-btn flat color="orange" @click="removeMVA(index)">Remove</v-btn> 
            <v-text-field label="# days" v-model="mvas[index]"></v-text-field>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>
<script>
export default {
  data () {
    return {
      showMsg: "Show Moving Average Options",
      shouldShow: false,
    }
  },
  props: ['mvas'],
  methods: {
    onShow: function () {
      this.shouldShow = !this.shouldShow;
      if (this.shouldShow) {
        this.showMsg = "Hide Moving Average Options";
      }
      else {
        this.showMsg = "Show Moving Average Options";
      }
    },
    addMVA: function () {
      this.mvas.push("");
    },
    removeMVA: function (index) {
      this.mvas.splice(index, 1);
    }
  }
}
</script>
<style scoped>
.title-span {
  font-size: 1.1em;
}
</style>
