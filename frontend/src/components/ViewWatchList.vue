<template>
<div>
<h5 style = "text-align: center; margin-top: 5%;">WatchList</h5>

<p v-if="!userId">Please create an account or login to access the watchlist feature.</p>

<form>
  <div class="container">
    <div>
      <p>{{ errorMsg }}</p>
    </div>
    
    <ul id="userSearchDisplay" v-if="userId" style = "margin-top:-10%; margin-left: 5%;">
		<li v-for="name in userList" style = "font-size: 1.5em;">
		  {{ name }}
		</li>
		<!--<a href = "/" style = "font-size: 1.5em">Home</a>-->
		
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
    beforeMount(){
    		if(localStorage.getItem(GC_USER_ID) != null){
    			this.onSignup();
    		}
    },
    methods: {
      onSignup: function () {
        var _this = this;
        this.$http.post(GC_BACKEND + "/user/ViewWatchList", {
            username: localStorage.getItem(GC_USER_ID)
        }, {}).then(response => {
          var res = response["body"];
          console.log("Got response: " + res[0]);
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
