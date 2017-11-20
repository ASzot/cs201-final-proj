<template>
<div>
<h2>View WatchList</h2>

<form>
  <div class="container">
    <div v-if="userId" class="ml1 pointer" @click="onSignup" style = "float:right; font-size:1.5em; cursor: pointer">View currencies</div>
    <div>
      <p>{{ errorMsg }}</p>
    </div>
    
    <ul id="userSearchDisplay">
		<li v-for="name in userList">
		  {{ name }}
		</li>
	</ul>
  </div>
</form>
</div>
</template>

<script>
  import { GC_USER_ID, GC_LOGGED_IN, GC_BACKEND } from '@/constants/settings'
  export default {
    data () {
      return {
        username: "",
        errorMsg: "",
        userList: ""
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
    methods: {
      onSignup: function () {
        var _this = this;
        this.$http.post(GC_BACKEND + "/user/ViewWatchList", {
            username: localStorage.getItem(GC_USER_ID)
        }, {}).then(response => {
          var res = response.body;
          console.log("Got response: " + response);
          if (res.allCurrencies) {
            _this.userList = res.allCurrencies;
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
