<template>
<div>
<h2>Search For Users</h2>

<form style="border:1px solid #ccc">
  <div class="container">
    <label><b>Username</b></label>
    <input type="text" placeholder="Search For Username" v-model="username" required>

    <div class="clearfix">
      <a href = "/"><button type="button" class="cancelbtn">Cancel</button></a>
      <button type="submit" @click="onSignup" class="signupbtn">Search</button>
    </div>

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
  import { GC_LOGGED_IN, GC_BACKEND } from '@/constants/settings'
  export default {
    data () {
      return {
        username: "",
        errorMsg: "",
        userList: ""
      }
    },
    methods: {
      onSignup: function () {
        var _this = this;
        this.$http.post(GC_BACKEND + "/user/search", {
          params: {
            username: _this.username
          }
        }, {}).then(response => {
          var res = response.body;
          console.log("Got response");
          if (res.allUsers) {
            _this.userList = res.allUsers;
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
