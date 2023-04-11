<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<script src="https://kit.fontawesome.com/5cd4730d1a.js" crossorigin="anonymous"></script>
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css">
	    <link rel="stylesheet" href="CSS/style_create_post.css">
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	    <script src="JS/script.js"></script>
	    <link rel="shortcut icon" type="image/x-icon" href="./DB_image/header.png">
		<title>Create Post</title>	
	</head>
	
	<body>
		<div class="make_post" id="make_post">
			<form>					
				<input type="hidden" id="userid" name="userid" value="${userid}"/>
				<textarea type="text" id="post" name="post" placeholder="Create your post" class="post_area" required></textarea>
				<p class="upload_post" onclick="make_post();" style="text-align: center; font-weight: 500; font-size: 22px;">Make Post</p>		
			</form>
		</div>
		
		<form action="Profile" method="POST" style="display: none;" id="myForm">					
			<input type="hidden" id="userid" name="command" value="PROFILE"/>
			<input type="submit" value="Submit">
		</form>
		
		<script>
			function make_post(){
				var theuserid=document.getElementById("userid").value;
				var thepost=document.getElementById("post").value;
				
				console.log(theuserid);
				console.log(thepost);
				$(document).ready(function(){
					$.ajax({
						type: 'POST',
						data: {command: "MAKE_POST", userid: theuserid, post: thepost},
						url: 'Profile',
						success: function(){
							document.getElementById("myForm").submit();
						}
					});
				});
			}
		</script>
	</body>
</html>