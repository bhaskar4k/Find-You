<%@page import="java.io.OutputStream"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="javax.xml.bind.DatatypeConverter" %>
<%@ page import="AllServlet.ProfileInfo" %>
<%@ page import="AllServlet.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="AllServlet.Encryption" %>
 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<script src="https://kit.fontawesome.com/5cd4730d1a.js" crossorigin="anonymous"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css">
	    <link rel="stylesheet" href="CSS/style_user_profile.css">
	    <script src="JS/script.js"></script>	   
	  	
	  	<%
		Encryption encryption = new Encryption();
        
		String userid = (String) request.getAttribute("userid");
		String extension = (String) request.getAttribute("extension");
		String profile_photo = (String) request.getAttribute("profile_photo");
		User userInfo = (User) request.getAttribute("userInfo");		
		
		ArrayList<ArrayList<String>> allPosts = (ArrayList<ArrayList<String>>) request.getAttribute("allPosts");
		
		String[] validExtension = new String[3];
        validExtension[0] = "jpg";
        validExtension[1] = "jpeg";
        validExtension[2] = "png";
        
        if(extension!=null && (extension!=validExtension[0] || extension!=validExtension[1] || extension!=validExtension[2])){%>
        	<script>
				alert("Invalid Image Format. Supported formats are 'jpeg' or 'jpg' or 'png'");
			</script>
        <%}%>	
          		  	
	  	<link rel="shortcut icon" type="image/x-icon" href="./DB_image/header.png">
		<title>${profileInfo.full_name}</title>
	</head>
	<body>		
		<div class="profile_header" id="profile_header">
			<div class="all_heading">
				<form action="Feed" method="POST">
					<input type="hidden" name="command" value="REDIRECT_TO_NEWSFEED"/>
					<input type="hidden" name="userid" value="${userid}"/>
					<button style="font-size: 30px; padding-left: 10px; font-family: 'Pacifico', sans-serif; color: #03adfc; background-color: white; border: none; border-radius: 20px; cursor: pointer;">Find You</button>
				</form>				
				
				<div class="search_box">
					<jsp:include page="search_profile.jsp" />
				</div>
				
				<div style="position: relative; overflow: hidden; width: 45px; height: 45px; margin-top: 6px; margin-right: 6px; cursor: pointer;" onclick="profile_controller();">
					<img src="./DB_image/<%=profile_photo%>" alt="Profile Photo" style="width: 100%; height: 100%; object-fit: cover; border-radius: 50%; border: 2px solid #03adfc;">
					<div style="position: absolute; z-index: 1; bottom: 0; right: 0; width: 16px; height: 16px; border-radius: 50%; background-color: #24e002; border: 3px solid white;"></div>
				</div>				
			</div>
			
			<div id="profile_controller">
				<form action="Profile" method="POST" enctype="multipart/form-data" style="margin-top: 15px;">
					<input type="hidden" name="command" value="CHANGE_PROFILE_PHOTO"/>
					<input type="hidden" name="userid" value="${userid}"/>
						
					<input type="file" name="image" id="files" style="display: none;">
					<label for="files" class="custom-file-input">Select file</label>
				    <input type="submit" value="Change Profile Photo" class="upload-button">
				</form>
				
				<form action="Profile" method="POST">
					<input type="hidden" id="command" name="command" value="REDIRECT_TO_EDIT_PROFILE_PAGE"/>
					
					<input type="hidden" name="userid" value="${userid}"/>
					<input type="hidden" name="first_name" value="${userInfo.first_name}"/>
					<input type="hidden" name="last_name" value="${userInfo.last_name}"/>
					<input type="hidden" name="email" value="${userInfo.email}"/>
					<input type="hidden" name="phone" value="${userInfo.phone}"/>
					<input type="hidden" name="password" value="${userInfo.password}"/>
					<input type="hidden" name="bio" value="${userInfo.bio}"/>
					
					<input type="submit" value="Edit Profile Info" class="edit_profile_btn">	
				</form>
				
				<form action="Homepage" method="POST">
					<input type="hidden" id="command" name="command" value="CLOSE SESSION"/>
				  	<input type="submit" value="Logout" class="logout_btn">
				</form>
			</div>
			
			<script>
				function profile_controller(){
					if(document.getElementById("profile_controller").style.height=="0px"){
						document.getElementById("profile_controller").style.display="block";
						document.getElementById("profile_controller").style.transition="0.3s all ease-in-out";
						setTimeout(function() {
							document.getElementById("profile_controller").style.opacity="1";
							document.getElementById("profile_controller").style.height="243px";
						}, 300);
					}else{
						document.getElementById("profile_controller").style.height="0px";
						document.getElementById("profile_controller").style.opacity="0";
						document.getElementById("profile_controller").style.transition="0.3s all ease-in-out";
						setTimeout(function() {
							document.getElementById("profile_controller").style.display="none";
						}, 300);
					}
				}
			</script>
		
				
			<div class="left">
				<div class="profile_photo">
					<img src="./DB_image/<%=profile_photo%>" alt="Upload Profile Photo">
					<%-- <img src="<%=request.getContextPath()%>/DB_image/<%=profile_photo%>" alt="Upload Profile Photo"> --%>
				</div>			
				<h1>${profileInfo.full_name}</h1>
				
				<!-- CREATE POST -->			

				<form action="Profile" method="POST" class="redirect_to_make_post">
					<input type="hidden" name="userid" value="${userid}"/>
					<input type="hidden" id="command" name="command" value="REDIRECT_TO_MAKE_POST_PAGE"/>
					<i class="fa fa-upload fa-3x" aria-hidden="true"></i>
					<input type="submit" value="Create Post" class="create_post">
				</form>		
			</div>
			
			<div class="right">
				<div class="user_info">
					<div class="follow">
						<p class="follower" id="follower_count" onclick="get_follower_list();"></p>
						<p class="following" id="following_count" onclick="get_following_list()">Following : ${profileInfo.following}</p>
					</div>
				
					<p class="bio">${profileInfo.bio}</p>
				</div>
				<div class="all_post_count">
					<p class="post_count">Post count : <%=allPosts.size()%></p>
				</div>
			</div>	
			
			<div id="get_all_follower_list" style="display: none;">
				<div id="all_follower_list" style="display: none; z-index: 1000"></div>
			</div>
			
			<div id="get_all_following_list" style="display: none;">
				<div id="all_following_list" style="display: none; z-index: 1000"></div>
			</div>
			
			<script>
				get_follower_count();
				get_following_count();
				
				function get_follower_count(){
					setInterval(get_follower_count, 10000);
					$(document).ready(function(){
						$.ajax({
							type: 'POST',
							data: {command: "GET_FOLLOWER_COUNT", userid: "<%=userid%>"},
							url: 'Profile',
							success: function(result){
								$('#follower_count').html(result);
							}
						});
					});
				}
				
				function get_following_count(){
					setInterval(get_following_count, 10000);
					$(document).ready(function(){
						$.ajax({
							type: 'POST',
							data: {command: "GET_FOLLOWING_COUNT", userid: "<%=userid%>"},
							url: 'Profile',
							success: function(result){
								$('#following_count').html(result);
							}
						});
					});
				}
			
			
				function get_follower_list(){
					$(document).ready(function(){
						$.ajax({
							type: 'POST',
							data: {command: "GET_FOLLOWER_LIST", userid: "<%=userid%>"},
							url: 'Profile',
							success: function(result){
								if(document.getElementById("get_all_following_list").style.display==="block"){
									close_get_following_list();
								}
								$('#all_follower_list').html(result);
								$('#get_all_follower_list').css('display', 'block');
								$('#all_follower_list').css('display', 'block');
								setTimeout(function() {
									$('#get_all_follower_list').css('opacity', '1');
									$('#get_all_follower_list').css('height', '300px');
								}, 150);
							}
						});
					});
				}
				
				function close_get_follower_list(){
					document.getElementById("get_all_follower_list").style.height="0";
					document.getElementById("get_all_follower_list").style.opacity="0";
					document.getElementById("all_follower_list").style.display="none";
					setTimeout(function() {
						document.getElementById("get_all_follower_list").style.display="none";
					}, 150);
				}
				
				function get_following_list(){
					$(document).ready(function(){
						$.ajax({
							type: 'POST',
							data: {command: "GET_FOLLOWING_LIST", userid: "<%=userid%>"},
							url: 'Profile',
							success: function(result){
								if(document.getElementById("get_all_follower_list").style.display==="block"){
									close_get_follower_list();
								}
								$('#all_following_list').html(result);
								$('#get_all_following_list').css('display', 'block');
								$('#all_following_list').css('display', 'block');
								setTimeout(function() {
									$('#get_all_following_list').css('opacity', '1');
									$('#get_all_following_list').css('height', '300px');
								}, 150);
							}
						});
					});
				}
				
				function close_get_following_list(){
					document.getElementById("get_all_following_list").style.height="0";
					document.getElementById("get_all_following_list").style.opacity="0";
					document.getElementById("all_following_list").style.display="none";
					setTimeout(function() {
						document.getElementById("get_all_following_list").style.display="none";
					}, 150);
				}
			</script>
			
			<div class="all_posts">					
				<%for(int i=0; i<allPosts.size(); i++) {
					ArrayList<String> temp=allPosts.get(i);%>
					
					<%String edit_id="f", reaction_id="r", comment_id="c", curtime=temp.get(2);
					for(int j=0; j<curtime.length(); j++){
						if(curtime.charAt(j)==' ' || curtime.charAt(j)==':' || curtime.charAt(j)=='.' || curtime.charAt(j)=='-') continue;
						edit_id+=curtime.charAt(j);
						reaction_id+=curtime.charAt(j);
						comment_id+=curtime.charAt(j);
					}%>
					
					<div class="each_post">
						<div style="display: flex;">
							<div style="width: 52px; height: 52px;">
								<img src="./DB_image/<%=profile_photo%>" alt="Profile Photo" style="width: 100%; height: 100%; object-fit: cover; border-radius: 50%; border: 2px solid #03adfc;">
							</div>
							<div style="padding-left: 15px;">
								<p class="full_name">${profileInfo.full_name}</p>
								<p class="upload_time"><%=temp.get(2)%></p>							
							</div>
						</div>
						<p class="post"><%=temp.get(1)%></p>
						
						<%String timeStamp=temp.get(2), newTimeStamp="";
						for(int j=0; j<timeStamp.length(); j++){
							if(timeStamp.charAt(j)==' ' || timeStamp.charAt(j)=='-' || timeStamp.charAt(j)=='.' || timeStamp.charAt(j)==':') continue;
							newTimeStamp+=timeStamp.charAt(j);
						}
						String commentator_command="commentator_command"+newTimeStamp;
						String commentator_postid="commentator_postid"+newTimeStamp;
						String typed_comment="typed_comment"+newTimeStamp;
						String function_do_comment="function_do_comment"+newTimeStamp;
						String do_like="do_like"+newTimeStamp;
						String function_do_like="function_do_like"+newTimeStamp;
						String reaction_checker_refresher="reaction_checker"+newTimeStamp;
						String get_like_count=reaction_id+"glc";
						String get_comment_count=comment_id+"gacc";
						String reaction_count_refresher="rcrf"+newTimeStamp;
						String comment_count_refresher="ccrf"+newTimeStamp;
						String load_reaction_id="rr"+reaction_id;
						String reaction_all_refresher="rar"+newTimeStamp;
						String load_comment_id="cc"+comment_id;
						String comment_all_refresher="car"+newTimeStamp;%>
						
						<div class="doLikeComment">
							<div id="<%=do_like%>" onclick="<%=function_do_like%>();"></div>													
							
							<form style="display: flex; width: 80%;">
								<input type="hidden" id="<%=commentator_command%>" name="command" value="DO_COMMENT"/>
								<input type="hidden" id="<%=commentator_postid%>" name="postid" value="<%=temp.get(0)%>"/>					
								<textarea class="typed_comment" id="<%=typed_comment%>" name="typed_comment" placeholder="Make comment..."></textarea>
								<p class="make_comment_btn" onclick="<%=function_do_comment%>();">+</p>
							</form>
						</div>
								
						<% if(i==0 || i==1 || i==2 || i==3 || i==4){%>
							<script>
								<%=reaction_checker_refresher%>();
								<%=reaction_count_refresher%>();
								<%=comment_count_refresher%>();
							
								function <%=reaction_checker_refresher%>(){
									$(document).ready(function(){
										$.ajax({
											type: 'POST',
											data: {command: "IS_REACTED", postid: "<%=temp.get(0)%>"},
											url: 'Profile',
											success: function(result){
												$('#<%=do_like%>').html(result);
											}
										});
									});
								}
							    
							    function <%=reaction_count_refresher%>(){
									$(document).ready(function(){
										$.ajax({
											type: 'POST',
											data: {command: "LIKE_COUNT", postid: "<%=temp.get(0)%>"},
											url: 'Profile',
											success: function(result){
												$('#<%=get_like_count%>').html(result);
											}
										});
									});
								}
							    
							    function <%=comment_count_refresher%>(){
									$(document).ready(function(){
										$.ajax({
											type: 'POST',
											data: {command: "COMMENT_COUNT", postid: "<%=temp.get(0)%>"},
											url: 'Profile',
											success: function(result){
												$('#<%=get_comment_count%>').html(result);
											}
										});
									});
								}
							</script>
						<%}%>
									
						<script>																					
							function <%=function_do_like%>() {	
								var theCommand1 = "DO_LIKE";
								var thepostid = "<%=temp.get(0)%>";
								
								$(document).ready(function() {
						            $.ajax({
						                type: 'POST',
						                data: {command: theCommand1, postid: thepostid},
						                url: 'Profile',
						                success: function() {
						                	<%=reaction_checker_refresher%>();
						                	<%=reaction_count_refresher%>();
						                	<%=reaction_all_refresher%>();
						                }
						            });						          						            
						        });									
							}
							
						    function <%=reaction_checker_refresher%>(){
								$(document).ready(function(){
									$.ajax({
										type: 'POST',
										data: {command: "IS_REACTED", postid: "<%=temp.get(0)%>"},
										url: 'Profile',
										success: function(result){
											$('#<%=do_like%>').html(result);
										}
									});
								});
							}
						    
						    function <%=reaction_count_refresher%>(){
								$(document).ready(function(){
									$.ajax({
										type: 'POST',
										data: {command: "LIKE_COUNT", postid: "<%=temp.get(0)%>"},
										url: 'Profile',
										success: function(result){
											$('#<%=get_like_count%>').html(result);
										}
									});
								});
							}
						    
						    function <%=reaction_all_refresher%>(){
								$(document).ready(function(){
									$.ajax({
										type: 'POST',
										data: {command: "LOAD_ALL_LIKES", postid: "<%=temp.get(0)%>"},
										url: 'Profile',
										success: function(result){
											$('#<%=load_reaction_id%>').html(result);
										}
									});
								});
							}
							
						    function <%=function_do_comment%>() {
						        var theCommand=document.getElementById("<%=commentator_command%>").value;
						        var thepostid=document.getElementById("<%=commentator_postid%>").value;
						        var thecmnt=document.getElementById("<%=typed_comment%>").value;
						
						        $(document).ready(function() {
						            $.ajax({
						                type: 'POST',
						                data: {command: theCommand, postid: thepostid, typed_comment: thecmnt},
						                url: 'Profile',
						                success: function() {
						                	<%=comment_count_refresher%>();
						                	<%=comment_all_refresher%>();
						                }
						            });
						        });
						        document.getElementById("<%=typed_comment%>").value="";
						    }				
						    
						    function <%=comment_count_refresher%>(){
								$(document).ready(function(){
									$.ajax({
										type: 'POST',
										data: {command: "COMMENT_COUNT", postid: "<%=temp.get(0)%>"},
										url: 'Profile',
										success: function(result){
											$('#<%=get_comment_count%>').html(result);
										}
									});
								});
							}
						    
						    function <%=comment_all_refresher%>(){
								$(document).ready(function(){
									$.ajax({
										type: 'POST',
										data: {command: "LOAD_ALL_COMMENTS", postid: "<%=temp.get(0)%>"},
										url: 'Profile',
										success: function(result){
											$('#<%=load_comment_id%>').html(result);
										}
									});
								});
							}
						</script>

						
						<div class="like_cmnt_details">							
							<p class="like_count" style="float: left;" onclick="<%=reaction_id%>();" id="<%=get_like_count%>"></p>
							<p class="like_count" style="float: left; margin-left: 10px;" onclick="<%=comment_id%>();" id="<%=get_comment_count%>"></p>
						</div>	
						
						<div class="comment" id="<%=reaction_id%>">
							<div id="<%=load_reaction_id%>"></div> 
						</div>
						
						<% String reaction_count_live_load="rcll"+reaction_id;						
						String interval_reaction_count_loader_id="ircli"+reaction_id;
						String is_reaction_active="irrra"+reaction_id;						
						String interval_is_reaction_active_id="iirai"+reaction_id;
						String buttonRectReaction="butrr"+reaction_id;%>
						
						<script>
							var <%=interval_reaction_count_loader_id%> = undefined;
							var <%=interval_is_reaction_active_id%> = undefined;
							const <%=get_like_count%> = document.getElementById("<%=get_like_count%>");
							
							window.addEventListener("scroll", function() {
								const <%=buttonRectReaction%> = <%=get_like_count%>.getBoundingClientRect();
								
								if (<%=buttonRectReaction%>.top >= 0 && <%=buttonRectReaction%>.bottom <= window.innerHeight) {				
									if(typeof <%=interval_reaction_count_loader_id%> === 'undefined'){
										<%=reaction_count_live_load%>();
									}
									if(typeof <%=interval_is_reaction_active_id%> === 'undefined'){
										<%=is_reaction_active%>();
									}
								} else {
									if(typeof <%=interval_reaction_count_loader_id%> != 'undefined'){
										clearInterval(<%=interval_reaction_count_loader_id%>);
										<%=interval_reaction_count_loader_id%> = undefined;	
										
										clearInterval(<%=interval_is_reaction_active_id%>);
										<%=interval_is_reaction_active_id%> = undefined;
									}				
								}
							});							
																			
							function <%=reaction_count_live_load%>(){
								if(typeof <%=interval_reaction_count_loader_id%> === 'undefined'){
									<%=interval_reaction_count_loader_id%> = setInterval(<%=reaction_count_live_load%>, 10000);
								}
								$(document).ready(function(){
									$.ajax({
										type: 'POST',
										data: {command: "LIKE_COUNT", postid: "<%=temp.get(0)%>"},
										url: 'Profile',
										success: function(result){
											$('#<%=get_like_count%>').html(result);
										}
									});
								});
							}
							
							function <%=is_reaction_active%>(){
								if(typeof <%=interval_is_reaction_active_id%> === 'undefined'){
									<%=interval_is_reaction_active_id%> = setInterval(<%=is_reaction_active%>, 10000);
								}
								$(document).ready(function(){
									$.ajax({
										type: 'POST',
										data: {command: "IS_REACTED", postid: "<%=temp.get(0)%>"},
										url: 'Profile',
										success: function(result){
											$('#<%=do_like%>').html(result);
										}
									});
								});
							}
						</script>											
						
						<div class="comment" id="<%=comment_id%>">
							<div id="<%=load_comment_id%>"></div> 
						</div>
						
						<% String comment_count_live_loader="accll"+comment_id;
						String interval_comment_count_loader_id="iccli"+comment_id;
						String buttonRectComment="butrc"+comment_id;%>
						
						<script>
							var <%=interval_comment_count_loader_id%> = undefined;
							const <%=get_comment_count%> = document.getElementById("<%=get_comment_count%>");
							
							window.addEventListener("scroll", function() {
								const <%=buttonRectComment%> = <%=get_comment_count%>.getBoundingClientRect();
								
								if (<%=buttonRectComment%>.top >= 0 && <%=buttonRectComment%>.bottom <= window.innerHeight) {				
									if(typeof <%=interval_comment_count_loader_id%> === 'undefined'){
										<%=comment_count_live_loader%>();
									}			
								} else {
									if(typeof <%=interval_comment_count_loader_id%> != 'undefined'){
										clearInterval(<%=interval_comment_count_loader_id%>);
										<%-- console.log("<%=temp.get(1)%> comment closed"); --%>
										<%=interval_comment_count_loader_id%> = undefined;					
									}				
								}
							});							
																			
							function <%=comment_count_live_loader%>(){
								<%-- console.log("<%=temp.get(1)%> comment getting called"); --%>
								if(typeof <%=interval_comment_count_loader_id%> === 'undefined'){
									<%=interval_comment_count_loader_id%> = setInterval(<%=comment_count_live_loader%>, 10000);
								}
								$(document).ready(function(){
									$.ajax({
										type: 'POST',
										data: {command: "COMMENT_COUNT", postid: "<%=temp.get(0)%>"},
										url: 'Profile',
										success: function(result){
											$('#<%=get_comment_count%>').html(result);
										}
									});
								});
							}
						</script>
						
						<style>
							#<%=reaction_id%>{
								display: none;
							}
							
							#<%=load_reaction_id%>{
								overflow-y: auto;
								min-height: 0px;
								max-height: 300px;
								width: 372px;
							}
									
							#<%=load_reaction_id%>::-webkit-scrollbar {
								width: 12px;
								height: 12px;
							}
									
							#<%=load_reaction_id%>::-webkit-scrollbar-track {
								background-color: white;
							}
									
							#<%=load_reaction_id%>::-webkit-scrollbar-thumb {
								background-color: #b0aeae;
								border-radius: 10px;
								border: 3px solid white;
							}
							
														
							#<%=comment_id%>{
								display: none;
							}
							
							#<%=load_comment_id%>{
								overflow-y: auto;
								min-height: 0px;
								max-height: 300px;
								width: 372px;
							}
									
							#<%=load_comment_id%>::-webkit-scrollbar {
								width: 12px;
								height: 12px;
							}
									
							#<%=load_comment_id%>::-webkit-scrollbar-track {
								background-color: white;
							}
									
							#<%=load_comment_id%>::-webkit-scrollbar-thumb {
								background-color: #b0aeae;
								border-radius: 10px;
								border: 3px solid white;
							}
						</style>
						
						<%String reaction_live_loader="rll"+reaction_id;
						String interval_reaction_loader_id="irli"+reaction_id; %>
						
						<%String comment_live_loader="cll"+comment_id;						
						String interval_comment_loader_id="icli"+comment_id;%>
						
						<script>
							var <%=interval_reaction_loader_id%>;														
							function <%=reaction_id%>(){
								if(document.getElementById("<%=reaction_id%>").style.display=="none"){
									
									if(document.getElementById("<%=comment_id%>").style.display=="block"){
										document.getElementById("<%=comment_id%>").style.display="none";
										if (typeof <%=interval_comment_loader_id%> != 'undefined') {
											clearInterval(<%=interval_comment_loader_id%>);
										}
									}
									document.getElementById("<%=reaction_id%>").style.display="block";
									
									<%=reaction_live_loader%>();
									<%=interval_reaction_loader_id%> = setInterval(<%=reaction_live_loader%>, 10000);
								}else{
									document.getElementById("<%=reaction_id%>").style.display="none";
									clearInterval(<%=interval_reaction_loader_id%>);
								}
							}
							
							var <%=interval_comment_loader_id%>;
							function <%=comment_id%>(){								
								if(document.getElementById("<%=comment_id%>").style.display=="none"){
									
									if(document.getElementById("<%=reaction_id%>").style.display=="block"){
										document.getElementById("<%=reaction_id%>").style.display="none";
										if (typeof <%=interval_reaction_loader_id%> != 'undefined') {
											clearInterval(<%=interval_reaction_loader_id%>);
										}
									}
									document.getElementById("<%=comment_id%>").style.display="block";
									
									<%=comment_live_loader%>();
									<%=interval_comment_loader_id%> = setInterval(<%=comment_live_loader%>, 10000);
								}else{
									document.getElementById("<%=comment_id%>").style.display="none";
									clearInterval(<%=interval_comment_loader_id%>);
								}																
							}
							
							function <%=reaction_live_loader%>(){
								$(document).ready(function(){
									$.ajax({
										type: 'POST',
										data: {command: "LOAD_ALL_LIKES", postid: "<%=temp.get(0)%>"},
										url: 'Profile',
										success: function(result){
											$('#<%=load_reaction_id%>').html(result);
										}
									});
								});
							}
							
							function <%=comment_live_loader%>(){
								$(document).ready(function(){
									$.ajax({
										type: 'POST',
										data: {command: "LOAD_ALL_COMMENTS", postid: "<%=temp.get(0)%>"},
										url: 'Profile',
										success: function(result){
											$('#<%=load_comment_id%>').html(result);
										}
									});
								});
							}
						</script>
						
						<p class="edit_post_dot" onclick="<%=edit_id%>();">&#8942;</p>
						
						<div id="<%=edit_id%>" style="display: none;">
							<form action="Profile" method="POST">
								<input type="hidden" id="command" name="command" value="REDIRECT_TO_EDIT_POST_PAGE"/>					
								<input type="hidden" name="postid" value="<%=temp.get(0)%>"/>
								<input type="hidden" name="post" value="<%=temp.get(1)%>"/>
								<input type="submit" value="Edit Post" class="post_edit_btn" style="background-color: green;">
							</form>
							
							<form action="Profile" method="POST">
								<input type="hidden" id="command" name="command" value="DELETE_POST"/>					
								<input type="hidden" name="postid" value="<%=temp.get(0)%>"/>
								<input type="submit" value="Delete Post" class="post_edit_btn" style="background-color: red;">
							</form>
						</div>
						
						<style>
							#<%=edit_id%>{
								display: none;
								cursor: pointer;
								display: flex;
								flex-direction: column;
								align-items: center;
								justify-content: center;
								width: 120px;
								position: absolute;
								top: 6px;
								right: 16px;
							}
						</style>
						
						<script>
							function <%=edit_id%>(){
								console.log("Call aarha h");
								if(document.getElementById("<%=edit_id%>").style.display=="none"){
									document.getElementById("<%=edit_id%>").style.display="block";
								}else{
									document.getElementById("<%=edit_id%>").style.display="none";
								}
							}
						</script>			
					</div>
				<%}%>		
			</div>
			
		</div>
			
	</body>
</html>