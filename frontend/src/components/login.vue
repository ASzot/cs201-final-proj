<template>
	<div>
		<h2>Login Form</h2>

		<form>		  		
		  <div class="container">
		    <label><b>Username</b></label>
		    <input type="text" placeholder="Enter Username" name="uname" required>
		
		    <label><b>Password</b></label>
		    <input type="password" placeholder="Enter Password" name="psw" required>
		        
		    <button type="submit">Login</button>
		    <input type="checkbox" checked="checked"> Remember me
		  </div>
		
		  <div class="container" style="background-color:#f1f1f1">
		    <a href = "/"><button type="button" class="cancelbtn">Cancel</button></a>
		    <span class="psw">Forgot <a href="#">password?</a></span>
		  </div>
		</form>
		
	</div>
</template>