<template>
  <div>
    <h2 style = "text-align:center; margin-top: 3%;">Signup Form</h2>

    <div class="container" style = "text-align:center; margin-top: 3%;">
      <label><b>Username</b></label>
      <input type="text" placeholder="Enter Username" v-model="username" required>

      <label><b>Password</b></label>
      <input type="password" placeholder="Enter Password" v-model="pass" required>

      <label><b>Repeat Password</b></label>
      <input type="password" placeholder="Repeat Password" v-model="passRepeat" required>

      <div class="clearfix" style = "padding: 30px;">
        <a href = "/"><button type="button" class="cancelbtn" style = "padding: 10px;">Cancel</button></a>
        <button type="submit" @click="onSignup" class="signupbtn" style = "padding: 10px;">Sign Up</button>
      </div>

      <v-alert v-if="errorMsg != ''" color="error" icon="warning" value="true">
        {{ errorMsg }}
      </v-alert>
    </div>
  </div>
</template>

<script>
  import { GC_USER_ID, GC_LOGGED_IN, GC_BACKEND } from '@/constants/settings'
  export default {
    data () {
      return {
        username: "",
        pass: "",
        passRepeat: "",
        errorMsg: ""
      }
    },
    methods: {
      onSignup: function () {
        if (this.pass != this.passRepeat) {
          this.errorMsg = "Passwords do not match";
          return;
        }
        var _this = this;
        console.log("username: " + _this.username + " password: " + _this.pass);
        this.$http.post(GC_BACKEND + "/user/create", {
          //params: {
            username: _this.username,
            password: _this.pass
          //}
        }, {}).then(response => {
          var res = response.body;
          console.log("Got response");
          if (!res.okay) {
            _this.errorMsg = "Could not register account";
          }
          else {
            console.log("Loggin in...?");
            _this.saveUserData();
            console.log("Logged in!");
            // Go back to home page.
            this.$router.push({ path: '/' });
          }

        }, response => {
          console.log("Error!");
        });
      },
      saveUserData () {
        localStorage.setItem(GC_LOGGED_IN, true);
        localStorage.setItem(GC_USER_ID, this.username);
        this.$root.$data.userId = localStorage.getItem(GC_USER_ID);
        //this.$root.$data.userId = -1;
      }
    }
  }
</script>
