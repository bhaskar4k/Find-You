<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<script src="https://kit.fontawesome.com/5cd4730d1a.js" crossorigin="anonymous"></script>
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css">
	    <link rel="stylesheet" href="CSS/style_create_post.css">
	    <script src="JS/script.js"></script>
		<title>Create Post</title>	
	</head>
	
	<body>
		<div class="make_post" id="make_post">
			<form action="Profile" method="POST">
				<input type="hidden" id="command" name="command" value="EDIT_POST"/>					
				<input type="hidden" name="postid" value="${postid}"/>
				<textarea type="text" name="post" placeholder="Create your post" class="post_area" required>${post}</textarea>
				<input type="submit" value="Edit Post" class="upload_post">		
			</form>
		</div>
	</body>
</html>