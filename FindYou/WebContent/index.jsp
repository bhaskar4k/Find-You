<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<script src="https://kit.fontawesome.com/5cd4730d1a.js" crossorigin="anonymous"></script>
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css">
	    <link rel="stylesheet" href="CSS/style.css">
	    <script src="JS/script.js"></script>
		<title>FIND YOU</title>
	</head>
	<body>
		<% String failure = (String) request.getAttribute("userid");
		if (failure=="WRONG") {%>
			<script>
				alert("Invalid Credentials.");
			</script>
		<%}%>
		
		<div class="homescreen">
			<h1>Find You</h1>
			
			<button class="login_signup_btn" onclick="open_signup_form();">SIGNUP</button>
			<button class="login_signup_btn" onclick="open_login_form();">LOGIN</button>
					
			<!-- --------------------------------- SIGNUP FORM ----------------------------------->
			<form action="Homepage" method="POST" class="signup">
				<h2 class="form_name">RESIGTER</h2>
			
				<input type="hidden" id="command" name="command" value="SIGNUP"/>
					
				<label for="firstName" class="label_of_field">First Name:</label><br>
				<input type="text" id="firstName" name="firstName" class="input_field" required><br><br>
					
				<label for="lastName" class="label_of_field">Last Name:</label><br>
				<input type="text" id="lastName" name="lastName" class="input_field" required><br><br>
					
				<label for="dob" class="label_of_field">Date of Birth:</label><br>
				<input type="date" id="dob" name="dob" class="input_field" required><br><br>
					
				<label for="gender" class="label_of_field" style="margin-right: 15px;">Gender:</label>
					
				<input type="radio" id="male" name="gender" value="male" required>
				<label for="male" class="label_of_field" style="margin-left: 20px;margin-right: 30px;">Male</label>
				<input type="radio" id="female" name="gender" value="female" required>
				<label for="female" class="label_of_field" style="margin-left: 20px;margin-right: 30px;">Female</label>
				<input type="radio" id="other" name="gender" value="other" required>
				<label for="other" class="label_of_field" style="margin-left: 20px;">Other</label><br><br>
					
				<label for="email" class="label_of_field">Email:</label><br>
				<input type="email" id="email" name="email" class="input_field" required><br><br>
					
				<label for="phone" class="label_of_field">Phone:</label><br>
				<input type="tel" id="phone" name="phone" class="input_field" required><br><br>
					
				<label for="password" class="label_of_field">Password:</label><br>
				<input type="password" id="password" name="password" class="input_field" required><br><br>
					
				<label for="confirmPassword" class="label_of_field">Confirm Password:</label><br>
				<input type="password" id="confirmPassword" name="confirmPassword" class="input_field" required><br><br>
					
				<input type="submit" value="Submit" class="submit_btn">
				<button onclick="close_signup_form()" class="close_btn">Close</button>
			</form>
			<!---------------------------------------------------------------------------------------->
			
			
			<!-- --------------------------------- LOGIN FORM ----------------------------------->
			<form action="Feed" method="POST" class="login">
				<h2 class="form_name">LOGIN</h2>
			
				<input type="hidden" id="command" name="command" value="LOGIN"/>
					
				<label for="email" class="label_of_field">Email:</label><br>
				<input type="email" id="email" name="email" class="input_field" required><br><br>
					
				<label for="phone" class="label_of_field">Phone:</label><br>
				<input type="tel" id="phone" name="phone" class="input_field" required><br><br>
					
				<label for="password" class="label_of_field">Password:</label><br>
				<input type="password" id="password" name="password" class="input_field" required><br><br>
					
				<input type="submit" value="Submit" class="submit_btn">
				<button onclick="close_login_form()" class="close_btn">Close</button>
			</form>
			<!---------------------------------------------------------------------------------------->
			
		</div>
		
		<script>
			function open_signup_form(){
				const element = document.querySelector('.signup');
				element.style.display="block";
			}
			function close_signup_form(){
				const element = document.querySelector('.signup');
				element.style.display="none";
			}
			
			function open_login_form(){
				const element = document.querySelector('.login');
				element.style.display="block";
			}
			function close_login_form(){
				const element = document.querySelector('.login');
				element.style.display="none";
			}
			
			const passwordInput = document.querySelector('#password');
			const confirmPasswordInput = document.querySelector('#confirmPassword');
			const form = document.querySelector('.signup');
			
			form.addEventListener('submit', function (event) {
				const password = passwordInput.value;
			    const confirmPassword = confirmPasswordInput.value;
			  
			  	if (password !== confirmPassword) {
			    	alert('Passwords do not match!');
			    	event.preventDefault();
			  	}
			});
		</script>
	</body>
</html>