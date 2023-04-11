<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<script src="https://kit.fontawesome.com/5cd4730d1a.js" crossorigin="anonymous"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css">
	    <link rel="stylesheet" href="CSS/style_search_profile.css">
		<title>Insert title here</title>
	</head>
	
	<body>
		<form class="searchbar">
			<input type="text" name="nameOfUser" id="nameOfUser" placeholder="Search profile" oninput="myFunction()" autocomplete="off"/>
		</form>
		
		<div id="all_profile_suggession" style="display: none;">
			<div id="load_all_profile_suggession" style="display: none; z-index: 1000"></div> 
		</div>
		
		<script>
			function myFunction(){
				var typedUser = document.getElementById("nameOfUser").value;
				console.log("The user typed: " + typedUser);
				
				$(document).ready(function(){
					$.ajax({
						type: 'POST',
						data: {command: "LOAD_ALL_PROFILE_SUGGESSION", typedUser: typedUser},
						url: 'Profile',
						success: function(result){
							$('#load_all_profile_suggession').html(result);
							$('#load_all_profile_suggession').css('display', 'block');
							$('#all_profile_suggession').css('display', 'block');
						}
					});
				});
			}
			
			function close_search_list(){
				document.getElementById("load_all_profile_suggession").style.display="none";
				document.getElementById("all_profile_suggession").style.display="none";
			}
		</script>
	</body>
</html>