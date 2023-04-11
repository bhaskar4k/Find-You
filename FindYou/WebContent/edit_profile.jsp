<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<script src="https://kit.fontawesome.com/5cd4730d1a.js" crossorigin="anonymous"></script>
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css">
		<link rel="stylesheet" href="CSS/style_edit_profile.css">
		<script src="JS/script.js"></script>
		<title>EDIT PROFILE</title>
	</head>
	<body>
		<%
		String userid = (String) request.getAttribute("userid");
		String first_name = (String) request.getAttribute("first_name");
		String last_name = (String) request.getAttribute("last_name");
		String email = (String) request.getAttribute("email");
		String phone = (String) request.getAttribute("phone");
		String password = (String) request.getAttribute("password");
		String bio = (String) request.getAttribute("bio");
		%>
	
		<form action="Profile" method="POST" class="edit-profile">
			<h2 class="form_name">EDIT YOUR DETAILS</h2>
				
			<input type="hidden" id="command" name="userid" value="${userid}"/>
			<input type="hidden" id="command" name="command" value="EDITING USER DETAILS"/>
						
			<label for="firstName" class="label_of_field">First Name:</label><br>
			<input type="text" id="firstName" name="firstName" class="input_field" value="${first_name}" required><br><br>
						
			<label for="lastName" class="label_of_field">Last Name:</label><br>
			<input type="text" id="lastName" name="lastName" class="input_field" value="${last_name}" required><br><br>
						
			<label for="email" class="label_of_field">Email:</label><br>
			<input type="email" id="email" name="email" class="input_field" value="${email}" required><br><br>
						
			<label for="phone" class="label_of_field">Phone:</label><br>
			<input type="tel" id="phone" name="phone" class="input_field" value="${phone}" required><br><br>
						
			<label for="password" class="label_of_field">New Password:</label><br>
			<input type="password" id="password" name="password" class="input_field" value="${password}" required><br><br>
				
			<label for="confirmPassword" class="label_of_field">Confirm Password:</label><br>
			<input type="password" id="confirmPassword" name="confirmPassword" class="input_field" value="${password}" required><br><br>
				
			<label for="bio" class="label_of_field">Bio:</label><br>
			<input type="text" id="bio" name="bio" class="input_field" value="${bio}" required><br><br>
						
			<input type="submit" value="CHANGE DETAILS" class="submit_btn">
		</form>
		
		<script>
			const passwordInput = document.querySelector('#password');
			const confirmPasswordInput = document.querySelector('#confirmPassword');
			const form = document.querySelector('.edit-profile');
			
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