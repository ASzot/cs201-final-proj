<template>
<div>
<h2 style = "text-align: center; margin-top: 5%;">View WatchList</h2>

<p v-if="!userId">Please create an account or login to access the watchlist feature.</p>

<div class="container">
  <div>
    <p>{{ errorMsg }}</p>
  </div>
  
  <ul id="userSearchDisplay" v-if="userId">
    <div class="text-xs-center" v-if="loading">
      <p>Loading</p>
      <v-progress-circular indeterminate v-bind:size="70" v-bind:width="7" color="purple"></v-progress-circular></v-progress-circular>
    </div>
    <v-list>
      <v-list-tile v-for="name in userList" :key="name">
        <v-list-tile-content>
          <router-link :to="{ path: '/coin/' + name.toLowerCase() }" class="ml1 no-underline">{{ name }}</router-link>
        </v-list-tile-content>
      </v-list-tile>
    </v-list>
  </ul>
</div>

</div>
</template>

<script>
  import { GC_USER_ID, GC_LOGGED_IN, GC_BACKEND } from '@/constants/settings'
  export default {
    data () {
      return {
        username: "",
        errorMsg: "",
        userList: "",
        loading: false
      }
    },
    computed: {
      userId () {
        console.log("In userId function: " + this.$root.$data.userId);
        console.log("val of gcuserid in localstorage " + localStorage.getItem(GC_USER_ID));
        //return this.$root.$data.userId
        return localStorage.getItem(GC_USER_ID);
      }
    },
    beforeMount(){
    		if(localStorage.getItem(GC_USER_ID) != null){
    			this.onSignup();
    		}
    },
    methods: {
      onSignup: function () {
        var _this = this;

        this.loading = true;
        this.$http.post(GC_BACKEND + "/user/ViewWatchList", {
            username: localStorage.getItem(GC_USER_ID)
        }, {}).then(response => {
          var res = response["body"];
          console.log("Got response: " + res[0]);
          _this.loading = false;
          if (res) {
            _this.userList = res;
            console.log(_this.userList);
          }
          else {
            console.log("No users found");
            //_this.saveUserData();
            //console.log("Logged in!");
            // Go back to home page.
            //router.push('/');
          }

        }, response => {
          _this.loading = false;
          console.log("Error!");
        });
      },
      saveUserData () {
        localStorage.setItem(GC_LOGGED_IN, true);
        localStorage.setItem(GC_USER_ID, id)
        this.$root.$data.userId = localStorage.getItem(GC_USER_ID)
        //this.$root.$data.userId = -1;
      }
    }
  }
</script>
<style scoped>
  .ml1:link{
  	color: black !important; 
  	text-decoration: none; 
  	float: right; 
  	padding: 5px; 
  	font-size: 1.5em; 
  	
  }
  .ml1:visited{
  	color:black; 
  }
  .ml1:hover{
  	color: blue; 
  }
</style>
