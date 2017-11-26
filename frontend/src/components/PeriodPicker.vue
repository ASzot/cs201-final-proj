<template>
  <div>
    <v-container fluid>
      <v-layout row wrap>
        <v-flex xs6>
          <v-card class='card-pad' style='text-align: center'>
            <v-btn color="success" @click="addMVA">Add Moving Average</v-btn>
            <v-btn color="info" @click="applyMVA">Apply</v-btn>
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
    }
  },
  props: ['mvas'],
  methods: {
    addMVA: function () {
      console.log("Adding new MVA");
      this.mvas.push(5);
    },
    removeMVA: function (index) {
      this.mvas.splice(index, 1);
    },
    applyMVA: function () {
    }
  }
}
</script>
<style scoped>
.title-span {
  font-size: 1.1em;
}
</style>
