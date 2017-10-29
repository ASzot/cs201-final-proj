<template>
  <v-container>{{ msg }}</v-container>
</template>

<script>
import config from '../config.js'
export default {
  name: 'HelloWorld',
  mounted: function () {
    // Keep reference to this to update this.msg
    var _this = this;

    this.$http.get(config.backendEndpoint + '/test', {
      params: {
        name: 'andrew'
      }
    }, {
    }).then(response => {
      var res = response.body;
      _this.msg = res.testMsg + ", " + res.name;
    }, response => {
      console.log('There was an error');
    });
  },
  data () {
    return {
      msg: 'Loading'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
