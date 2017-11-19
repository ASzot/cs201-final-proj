<template>
    <div>
        <h2>Login Form</h2>

        <form>                  
          <div class="container">
            <label><b>Username</b></label>
		    <input type="text" placeholder="Enter Username" v-model="username" required>
		
		    <label><b>Password</b></label>
		    <input type="password" placeholder="Enter Password" v-model="pass" required>
                        
             <div class="clearfix">
		      <a href = "/"><button type="button" class="cancelbtn">Cancel</button></a>
		      <button type="submit" @click="onLogin" class="signupbtn">Login</button>
		    </div>
		
		    <div>
		      <p>{{ errorMsg }}</p>
		    </div>
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
        pass: "",
        errorMsg: ""
      }
    },
    methods: {
      onLogin: function () {
      	console.log("testing error");
        var _this = this;
        this.$http.post(GC_BACKEND + "/user/login", {
          params: {
            username: _this.username,
            password: _this.password
          }
        }, {}).then(response => {
          var res = response.body;
          console.log("Got response");
          if (!res.okay) {
            _this.errorMsg = "Could not log in!";
          }
          else {
            console.log("Loggin in...?");
            _this.saveUserData();
            console.log("Logged in!");
            // Go back to home page.
            router.push('/');
          }

        }, response => {
          console.log("Error!");
        });
      },
      saveUserData () {
        localStorage.setItem(GC_LOGGED_IN, true);
        localStorage.setItem(GC_USER_ID, 0);
        this.$root.$data.userId = localStorage.getItem(GC_USER_ID);
        //this.$root.$data.userId = -1;
      }
    }
  }
</script>
