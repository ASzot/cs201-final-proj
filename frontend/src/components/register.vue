<template>
<div>
<h2>Signup Form</h2>

<form action="" style="border:1px solid #ccc">
  <div class="container">
    <label><b>Email</b></label>
    <input type="text" placeholder="Enter Email" name="email" required>

    <label><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="psw" required>

    <label><b>Repeat Password</b></label>
    <input type="password" placeholder="Repeat Password" name="psw-repeat" required>
    <input type="checkbox" checked="checked"> Remember me
    <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>

    <div class="clearfix">
      <a href = "/"><button type="button" class="cancelbtn">Cancel</button></a>
      <button type="submit" class="signupbtn">Sign Up</button>
    </div>
  </div>
</form>
</div>
</template>

<script>
  import { GC_USER_ID, GC_AUTH_TOKEN } from '../constants/settings'

  export default {
    name: 'register',
    data () {
      return {
        email: '',
        login: true,
        name: '',
        password: ''
      }
    },
    methods: {
      confirm () {
        //implement; once registration is confirmed, save user tokens
      },
      saveUserData (id, token) {
        localStorage.setItem(GC_USER_ID, id)
        localStorage.setItem(GC_AUTH_TOKEN, token)
        this.$root.$data.userId = localStorage.getItem(GC_USER_ID)
      }
    }
  }
</script>