package AllServlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.sun.xml.internal.ws.wsdl.writer.document.Types;

@MultipartConfig
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource(name="jdbc/FindYou")
	private DataSource dataSource;
	private DbUtil dbUtil;
	
    public Profile() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String theCommand=request.getParameter("command");
			
			if (theCommand==null) {
				theCommand="PROFILE";
			}
			
			switch (theCommand) {	
				case "PROFILE":
					load_profile(request,response);
					break;
					
				default:
					load_profile(request,response);
			}			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		try{
			String theCommand=request.getParameter("command");
			
			if (theCommand==null) {
				theCommand="PROFILE";
			}
			
			switch (theCommand) {	
				case "PROFILE":
					load_profile(request,response);
					break;
					
				case "CHANGE_PROFILE_PHOTO":
					change_profile_photo(request,response);
					break;
				
				case "REDIRECT_TO_EDIT_PROFILE_PAGE":
					redirect_to_edit_profile_page(request,response);
					break;
					
				case "EDITING USER DETAILS":
					edit_user_details(request,response);
					break;
					
				case "REDIRECT_TO_MAKE_POST_PAGE":
					redirect_to_make_post_page(request,response);
					break;
					
				case "MAKE_POST":
					make_post(request,response);
					break;
					
				case "REDIRECT_TO_EDIT_POST_PAGE":
					redirect_to_edit_post_page(request,response);
					break;
					
				case "EDIT_POST":
					edit_post(request,response);
					break;
					
				case "DELETE_POST":
					delete_post(request,response);
					break;
				
				case "LOAD_ALL_COMMENTS":
					load_all_comments(request,response);
					break;
					
				case "LOAD_ALL_LIKES":
					load_all_likes(request,response);
					break;
					
				case "COMMENT_COUNT":
					comment_count(request, response);
					break;
					
				case "LIKE_COUNT":
					like_count(request, response);
					break;
					
				case "LOAD_ALL_PROFILE_SUGGESSION":
					load_all_profile_suggession(request,response);
					break;
					
				case "SHOW_OTHER_USER_PROFILE":
					String userid = (String)request.getParameter("userid");					
					HttpSession session = request.getSession();
					String session_userid = (String) session.getAttribute("session_userid");
					
					if(userid.equals(session_userid)){
						load_profile(request,response);
					}else{
						load_other_user_profile(request,response);
					}
					break;
					
				case "DO_COMMENT":
					do_comment(request,response);
					break;
					
				case "DO_LIKE":
					do_like(request,response);
					break;
					
				case "IS_REACTED":
					is_reacted(request,response);
					break;
					
				case "IS_FOLLOWED":
					is_followed(request,response);
					break;
					
				case "DO_FOLLOW":
					do_follow(request,response);
					break;
					
				case "GET_FOLLOWER_LIST":
					get_follower_list(request,response);
					break;
					
				case "GET_FOLLOWING_LIST":
					get_following_list(request,response);
					break;
					
				case "GET_FOLLOWER_COUNT":
					get_follower_count(request,response);
					break;
					
				case "GET_FOLLOWING_COUNT":
					get_following_count(request,response);
					break;
					
				default:
					load_profile(request,response);
			}			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void load_profile(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("session_userid");
		
		if(userid==null){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		}
		
		Encryption decryption = new Encryption();
        String userid1 = decryption.decrypt(userid);
		
		dbUtil=new DbUtil(dataSource);
		
		String profile_photo = dbUtil.showProfilePhoto(userid1);
		ProfileInfo profileInfo=dbUtil.getUserProfileInfo(userid1);
		User userInfo=dbUtil.getUserInfo(userid1);
		ArrayList<ArrayList<String>> allPosts = dbUtil.getAllPosts(userid1);
		
		for(int i=0; i<allPosts.size(); i++){
			ArrayList<String> temp= allPosts.get(i);
			for(int j=3; j<temp.size(); j+=3){
				String liker_user_id=temp.get(j);
				String encryted_liker_user_id=decryption.encrypt(liker_user_id);
				temp.set(j, encryted_liker_user_id);
			}
		}
		
		request.setAttribute("userid", userid);
		request.setAttribute("profile_photo", profile_photo);
		request.setAttribute("profileInfo", profileInfo);
		request.setAttribute("userInfo", userInfo);
		request.setAttribute("allPosts", allPosts);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/user_profile.jsp");
		dispatcher.forward(request, response);
	}
	
	private void load_other_user_profile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = (String)request.getParameter("userid");
		
		HttpSession session = request.getSession();
		String session_userid = (String) session.getAttribute("session_userid");
		
		Encryption decryption = new Encryption();
		
		if(session_userid==null || userid==null){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		}
		
		session_userid=decryption.decrypt(session_userid);
		String session_profile_photo = dbUtil.showProfilePhoto(session_userid);
		
        String userid1 = decryption.decrypt(userid);
		
		dbUtil=new DbUtil(dataSource);
		
		String profile_photo = dbUtil.showProfilePhoto(userid1);
		ProfileInfo profileInfo=dbUtil.getUserProfileInfo(userid1);
		User userInfo=dbUtil.getUserInfo(userid1);
		ArrayList<ArrayList<String>> allFollowerList = dbUtil.getAllFollowerList(userid1);
		ArrayList<ArrayList<String>> allFollowingList = dbUtil.getAllFollowingList(userid1);
		ArrayList<ArrayList<String>> allPosts = dbUtil.getAllPosts(userid1);
		
		for(int i=0; i<allPosts.size(); i++){
			ArrayList<String> temp= allPosts.get(i);
			for(int j=3; j<temp.size(); j+=3){
				String liker_user_id=temp.get(j);
				String encryted_liker_user_id=decryption.encrypt(liker_user_id);
				temp.set(j, encryted_liker_user_id);
			}
		}
		
		request.setAttribute("userid", userid);
		request.setAttribute("profile_photo", profile_photo);
		request.setAttribute("session_profile_photo", session_profile_photo);
		request.setAttribute("profileInfo", profileInfo);
		request.setAttribute("userInfo", userInfo);
		request.setAttribute("allFollowerList", allFollowerList);
		request.setAttribute("allFollowingList", allFollowingList);
		request.setAttribute("allPosts", allPosts);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/other_users_profile.jsp");
		dispatcher.forward(request, response);
	}
	
	private void change_profile_photo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = (String)request.getParameter("userid");
		Encryption decryption = new Encryption();
        userid = decryption.decrypt(userid);
		
		Part filePart = request.getPart("image"); 
        String imageFileName = null;
        if (filePart != null) {
            String contentDisp = filePart.getHeader("content-disposition");
            String[] tokens = contentDisp.split(";");
            for (String token : tokens) {
                if (token.trim().startsWith("filename")) {
                    imageFileName = token.substring(token.indexOf("=") + 2, token.length()-1);
                    break;
                }
            }
        }
        
        String extension="";
        for (int i = imageFileName.length()-1; i >=0 ; i--) {
            char c = imageFileName.charAt(i);
            if(c=='.') break;
            extension+=c;
        }
        
        StringBuilder sb = new StringBuilder(extension);
        sb.reverse();
        extension = sb.toString();
        extension = extension.toLowerCase();
        
        String[] validExtension = new String[3];
        validExtension[0] = "jpg";
        validExtension[1] = "jpeg";
        validExtension[2] = "png";
        
        if(extension.equals(validExtension[0]) || extension.equals(validExtension[1]) || extension.equals(validExtension[2])){
        	String uploadPath="D:/Eclipse Workspace/FindYou/WebContent/DB_image/"+imageFileName;
            
            try{
            	FileOutputStream output_stream=new FileOutputStream(uploadPath);
                InputStream input_stream=filePart.getInputStream();
                
                byte[] data=new byte[input_stream.available()];
                input_stream.read(data);
                output_stream.write(data);
                output_stream.close();
                
                dbUtil=new DbUtil(dataSource);
        		try {
        			dbUtil.changeProfilePhoto(userid,imageFileName);
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
            }catch (Exception e) {
    			e.printStackTrace();
    		}
        }else{
        	System.out.println("Wrong image format.");
        	request.setAttribute("userid", userid);
        	request.setAttribute("extension", extension);
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/user_profile.jsp");
    		dispatcher.forward(request, response);
        }
		
		load_profile(request,response);
	}
	
	private void redirect_to_edit_profile_page(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String userid = (String)request.getParameter("userid");
		String first_name = (String)request.getParameter("first_name");
		String last_name = (String)request.getParameter("last_name");
		String email = (String)request.getParameter("email");
		String phone = (String)request.getParameter("phone");
		String password = (String)request.getParameter("password");
		String bio = (String)request.getParameter("bio");
		
		Encryption decryption = new Encryption();
		
        first_name = decryption.decrypt(first_name);
        last_name = decryption.decrypt(last_name);
        email = decryption.decrypt(email);
        phone = decryption.decrypt(phone);
        password = decryption.decrypt(password);
        bio = decryption.decrypt(bio);
		
		request.setAttribute("userid", userid);
		request.setAttribute("first_name", first_name);
		request.setAttribute("last_name", last_name);
		request.setAttribute("email", email);
		request.setAttribute("phone", phone);
		request.setAttribute("password", password);
		request.setAttribute("bio", bio);
		
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/edit_profile.jsp");
		dispatcher.forward(request, response);
	}
	
	private void edit_user_details(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String userid = (String)request.getParameter("userid");
		String first_name = (String)request.getParameter("firstName");
		String last_name = (String)request.getParameter("lastName");
		String email = (String)request.getParameter("email");
		String phone = (String)request.getParameter("phone");
		String password = (String)request.getParameter("password");
		String bio = (String)request.getParameter("bio");
		
		Encryption decryption = new Encryption();		
        userid = decryption.decrypt(userid);
		
		dbUtil=new DbUtil(dataSource);
		User updateUser=new User(userid,first_name,last_name,email,phone,password,bio);
		
		dbUtil.edit_user_details(updateUser);
		
		load_homePage(request, response);
	}
	
	private void load_homePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dbUtil=new DbUtil(dataSource);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
	
	private void redirect_to_make_post_page(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String userid = (String)request.getParameter("userid");
		
		request.setAttribute("userid", userid);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/create_post.jsp");
		dispatcher.forward(request, response);
	}
	
	private void make_post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = (String)request.getParameter("userid");
		String post = (String)request.getParameter("post");
		
		if(post==null || post=="") return;
		
		Encryption decryption=new Encryption();
		userid=decryption.decrypt(userid);
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		String postid=userid+"|"+currentDateTime;
		
		String tempTime=""+currentDateTime;
		String upload_time="";
		
		for(int i=0; i<tempTime.length(); i++){
			if(tempTime.charAt(i)=='.') break;
			if(tempTime.charAt(i)=='-'){
				upload_time+=".";
				continue;
			}
			if(tempTime.charAt(i)=='T'){
				upload_time+=" - ";
				continue;
			}
			upload_time+=tempTime.charAt(i);
		}
        
		try {
			dbUtil=new DbUtil(dataSource);
			dbUtil.makePost(userid,postid,post,upload_time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void redirect_to_edit_post_page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String postid = (String)request.getParameter("postid");
		String post = (String)request.getParameter("post");
		request.setAttribute("postid", postid);
		request.setAttribute("post", post);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/edit_post.jsp");
		dispatcher.forward(request, response);
	}
	
	private void edit_post(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String postid = (String)request.getParameter("postid");
		String post = (String)request.getParameter("post");
		
		Encryption decryption=new Encryption();
		postid=decryption.decrypt(postid);
		
		dbUtil=new DbUtil(dataSource);
		dbUtil.editPost(postid,post);
		load_profile(request,response);
	}
	
	private void delete_post(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String postid = (String)request.getParameter("postid");
		
		Encryption decryption=new Encryption();
		postid=decryption.decrypt(postid);
		
		dbUtil=new DbUtil(dataSource);
		dbUtil.deletePost(postid);
		load_profile(request,response);
	}
	
	private void load_all_comments(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String postid=request.getParameter("postid");
		
		dbUtil=new DbUtil(dataSource);
		ArrayList<ArrayList<String>> allComments=dbUtil.loadAllComments(postid);
		
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		
        for(int i=0; i<allComments.size(); i++){
        	ArrayList<String> aComment=allComments.get(i);
        	
        	String outputString="";
        	if(i==0){
        		outputString+="<p style=\"color: #03adfc; font-size: 18px; font-weight: 600; padding: 10px;\">Commented by "+allComments.size()+"</p>";
        	}
        	
        	outputString+="<div style=\"display: flex; flex-direction: column; padding: 5px; border: 1px solid #03adfc; border-radius: 10px; width: 350px; margin: 3px; box-shadow: rgba(0, 0, 0, 0.15) 0px -50px 36px -28px inset;\">";

        	Encryption encryption=new Encryption();
    		String userid=encryption.encrypt(aComment.get(0));
    		
    		outputString+="<div style=\"width=100%;\">";
    		outputString+="<div style=\"float: left; width: 15%;\"><div style=\"height: 50px; width: 50px; overflow: hidden; border-radius: 50%; border: 2px solid #03adfc;\"><img src=\"./DB_image/"+aComment.get(4)+"\" style=\"object-fit: cover; width: 100%; height: 100%; border: 0;\"></div></div>";
        	outputString+="<div style=\"float: right; width: 85%;\"><form action=\"Profile\" method=\"POST\" target=\"_blank\"><input type=\"hidden\" name=\"command\" value=\"SHOW_OTHER_USER_PROFILE\"/><input type=\"hidden\" name=\"userid\" value=\""+userid+"\"/><input type=\"submit\" value=\""+aComment.get(1)+"\" style=\"font-size: 17px; font-weight: 600; padding-bottom: 4px; padding-left: 10px; padding-top: 5px; outline: none; background-color: white; border: 0px; cursor: pointer;\"></form>";
        	outputString+="<p style=\"font-size: 14px; font-weight: 500; padding-bottom: 4px; padding-left: 10px; letter-spacing: 1px;\">"+aComment.get(2)+"</p></div></div>";
        	outputString+="<p style=\"font-size: 17px; font-weight: 500; width=100%; padding-top: 10px; line-height: 24px;\">"+aComment.get(3)+"</p>";
        	
        	outputString+="</div>";
       
    		out.print(outputString);
        }
        
        if(allComments.size()==0){
        	String outputString="<p style=\"color: #03adfc; font-size: 18px; font-weight: 600; padding: 10px;\">Commented by 0</p>";
        	out.print(outputString);
        }
	}
	
	private void load_all_profile_suggession(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String typedUser=request.getParameter("typedUser");
		
		String firstName="", lastName="";
		boolean firstNameDone=false;
		for(int i=0; i<typedUser.length(); i++){
			if(typedUser.charAt(i)==' '){
				firstNameDone=true;
				continue;
			}
			if(!firstNameDone){
				firstName+=typedUser.charAt(i);
			}else{
				lastName+=typedUser.charAt(i);
			}
		}
		
		firstName = firstName.toLowerCase(); 
		lastName = lastName.toLowerCase();
		
		dbUtil=new DbUtil(dataSource);
		ArrayList<ArrayList<String>> allSuggession = new ArrayList<>();
		allSuggession=dbUtil.loadAllProfileSuggesion(firstName,lastName);
		
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		if(allSuggession.size()==0){
			out.print("<div style=\"display: flex; justify-content: space-between;\"><p style=\"color: #03adfc; font-size: 18px; font-weight: 600; padding: 10px;\">0 search results</p>"
    				+ "<p style=\"color: white; font-size: 25px; font-weight: 600; width: 30px; height: 30px; border-radius: 50%; background-color: red; padding-left: 6px; margin-top: 5px; cursor: pointer;\" onclick=\"close_search_list();\">X</p></div>");
		}
		
		for(int i=0; i<allSuggession.size(); i++){
			ArrayList<String> temp=allSuggession.get(i);
			
			String outputString="";
        	if(i==0){
        		outputString+="<div style=\"display: flex; justify-content: space-between;\"><p style=\"color: #03adfc; font-size: 18px; font-weight: 600; padding: 10px;\">"+allSuggession.size()+" search results</p>"
        				+ "<p style=\"color: white; font-size: 25px; font-weight: 600; width: 30px; height: 30px; border-radius: 50%; background-color: red; padding-left: 6px; margin-top: 5px; cursor: pointer;\" onclick=\"close_search_list();\">X</p></div>";
        	}
        	
        	outputString+="<div style=\"padding: 5px; display: flex; z-index: 101;\">";
        	
        	outputString+="<div style=\"height: 50px; width: 50px; overflow: hidden; border-radius: 50%; border: 2px solid #03adfc;\">";
    		outputString+="<img src=\"./DB_image/"+temp.get(2)+"\" style=\"object-fit: cover; width: 100%; height: 100%; border: 0;\"></div>";
        	outputString+="<form action=\"Profile\" method=\"POST\" target=\"_blank\"><input type=\"hidden\" name=\"command\" value=\"SHOW_OTHER_USER_PROFILE\"/><input type=\"hidden\" name=\"userid\" value=\""+temp.get(0)+"\"/><input type=\"submit\" value=\""+temp.get(1)+"\" style=\"font-size: 18px; font-weight: 600; padding-bottom: 4px; outline: none; background-color: white; border: 0px; cursor: pointer; padding-left: 10px; padding-top: 13px;\"></form>";
        	
        	outputString+="</div>";
       
    		out.print(outputString);
		}
	}
	
	private void do_comment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String typed_comment = (String)request.getParameter("typed_comment");
		if(typed_comment==null || typed_comment=="") return;
		String postid = (String)request.getParameter("postid");					
		HttpSession session = request.getSession();
		String session_userid = (String) session.getAttribute("session_userid");
		if(session_userid==null) return;
		
		dbUtil=new DbUtil(dataSource);
		dbUtil.doComment(postid,session_userid,typed_comment);
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		out.print("");

	}
	
	private void load_all_likes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String postid=request.getParameter("postid");
		
		dbUtil=new DbUtil(dataSource);
		ArrayList<ArrayList<String>> allLikes=dbUtil.loadAllLikes(postid);
		
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		if(allLikes.size()==0){
        	String outputString="<p style=\"color: #03adfc; font-size: 18px; font-weight: 600; padding: 10px;\">Liked by 0</p>";
        	out.print(outputString);
        }
		
		for(int i=0; i<allLikes.size(); i++){
			ArrayList<String> temp=allLikes.get(i);
			
			String outputString="";
			if(i==0){
        		outputString+="<p style=\"color: #03adfc; font-size: 18px; font-weight: 600; padding: 10px;\">Liked by "+allLikes.size()+"</p>";
        	}
        	
        	outputString+="<div style=\"display: flex; flex-direction: column; padding: 5px; border-radius: 10px; width: 350px; margin: 3px;\">";
        	
        	outputString+="<div style=\"display: flex;\"><div style=\"height: 50px; width: 50px; overflow: hidden; border-radius: 50%; border: 2px solid #03adfc;\">";
    		outputString+="<img src=\"./DB_image/"+temp.get(2)+"\" style=\"object-fit: cover; width: 100%; height: 100%; border: 0;\"></div>";
        	outputString+="<form action=\"Profile\" method=\"POST\" target=\"_blank\"><input type=\"hidden\" name=\"command\" value=\"SHOW_OTHER_USER_PROFILE\"/><input type=\"hidden\" name=\"userid\" value=\""+temp.get(0)+"\"/><input type=\"submit\" value=\""+temp.get(1)+"\" style=\"font-size: 18px; font-weight: 600; padding-bottom: 4px; outline: none; background-color: white; border: 0px; cursor: pointer; padding-left: 10px; padding-top: 13px;\"></form></div>";
        	
        	outputString+="</div>";
       
    		out.print(outputString);    		
		}
	}
	
	private void like_count(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String postid=request.getParameter("postid");
		
		dbUtil=new DbUtil(dataSource);
		String total_like=dbUtil.getLikeCount(postid);
		
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();		
		out.print(total_like+" Reaction");
	}
	
	private void comment_count(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String postid=request.getParameter("postid");
		
		dbUtil=new DbUtil(dataSource);
		String total_comment=dbUtil.getCommentCount(postid);
		
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();		
		out.print(total_comment+" Comment");
	}
	
	private void is_reacted(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		String postid = (String)request.getParameter("postid");					
		HttpSession session = request.getSession();
		String session_userid = (String) session.getAttribute("session_userid");
		if(session_userid==null) return;
		
		dbUtil=new DbUtil(dataSource);
		Boolean is_liked=dbUtil.isLiked(postid,session_userid);
		
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		
		if(is_liked==true){			
			out.print("<i class=\"fa fa-thumbs-up fa-4x\" style=\"color: #03adfc;\"></i>");
		}else{
			out.print("<i class=\"fa-regular fa-thumbs-up fa-4x\" style=\"color: grey;\"></i>");
		}
	}
	
	private void do_like(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String postid = (String)request.getParameter("postid");					
		HttpSession session = request.getSession();
		String session_userid = (String) session.getAttribute("session_userid");
		if(session_userid==null) return;

		dbUtil=new DbUtil(dataSource);
		dbUtil.doLike(postid,session_userid);
	}	
	
	private void is_followed(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = (String)request.getParameter("userid");					
		HttpSession session = request.getSession();
		String session_userid = (String) session.getAttribute("session_userid");
		if(session_userid==null) return;
		
		Encryption decryption=new Encryption();
		userid=decryption.decrypt(userid);
		session_userid=decryption.decrypt(session_userid);
		
		dbUtil=new DbUtil(dataSource);
		
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		
		if(dbUtil.isFollowed(userid,session_userid)==true){			
			out.print("<p style=\"background-color: #e8e6e6; padding: 10px 20px; box-shadow: rgba(0, 0, 0, 0.25) 0px 54px 55px, rgba(0, 0, 0, 0.12) 0px -12px 30px, rgba(0, 0, 0, 0.12) 0px 4px 6px, rgba(0, 0, 0, 0.17) 0px 12px 13px, rgba(0, 0, 0, 0.09) 0px -3px 5px; border-radius: 10px;\">Unfollow</p>");
		}else{
			out.print("<p style=\"background-color: #03adfc; color: white; padding: 10px 20px; box-shadow: rgba(0, 0, 0, 0.25) 0px 54px 55px, rgba(0, 0, 0, 0.12) 0px -12px 30px, rgba(0, 0, 0, 0.12) 0px 4px 6px, rgba(0, 0, 0, 0.17) 0px 12px 13px, rgba(0, 0, 0, 0.09) 0px -3px 5px; border-radius: 10px;\">Follow</p>");
		}
	}
	
	private void do_follow(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = (String)request.getParameter("userid");					
		HttpSession session = request.getSession();
		String session_userid = (String) session.getAttribute("session_userid");
		if(session_userid==null) return;
		
		dbUtil=new DbUtil(dataSource);
		dbUtil.doFollow(userid,session_userid);
	}
	
	private void get_follower_list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = (String)request.getParameter("userid");	
		Encryption decryption = new Encryption();
        userid = decryption.decrypt(userid);
        dbUtil=new DbUtil(dataSource);
        ArrayList<ArrayList<String>> allFollowerList = dbUtil.getAllFollowerList(userid);
        
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		
		if(allFollowerList.size()==0){
			out.print("<div style=\"display: flex; justify-content: space-between;\"><p style=\"color: #03adfc; font-size: 18px; font-weight: 600; padding: 10px;\">0 Follower</p>"
    				+ "<p style=\"color: white; font-size: 25px; font-weight: 600; width: 30px; height: 30px; border-radius: 50%; background-color: red; padding-left: 6px; margin-top: 5px; cursor: pointer;\" onclick=\"close_get_follower_list();\">X</p></div>");
		}
		
		for(int i=0; i<allFollowerList.size(); i++){
			ArrayList<String> temp=allFollowerList.get(i);
			
			String outputString="";
        	if(i==0){
        		outputString+="<div style=\"display: flex; justify-content: space-between;\"><p style=\"color: #03adfc; font-size: 18px; font-weight: 600; padding: 10px;\">"+allFollowerList.size()+" Follower</p>"
        				+ "<p style=\"color: white; font-size: 25px; font-weight: 600; width: 30px; height: 30px; border-radius: 50%; background-color: red; padding-left: 6px; margin-top: 5px; cursor: pointer;\" onclick=\"close_get_follower_list();\">X</p></div>";
        	}
        	
        	outputString+="<div style=\"padding: 5px; display: flex; z-index: 101;\">";
        	
        	outputString+="<div style=\"height: 50px; width: 50px; overflow: hidden; border-radius: 50%; border: 2px solid #03adfc;\">";
    		outputString+="<img src=\"./DB_image/"+temp.get(1)+"\" style=\"object-fit: cover; width: 100%; height: 100%; border: 0;\"></div>";
        	outputString+="<form action=\"Profile\" method=\"POST\" target=\"_blank\"><input type=\"hidden\" name=\"command\" value=\"SHOW_OTHER_USER_PROFILE\"/><input type=\"hidden\" name=\"userid\" value=\""+temp.get(2)+"\"/><input type=\"submit\" value=\""+temp.get(0)+"\" style=\"font-size: 18px; font-weight: 600; padding-bottom: 4px; outline: none; background-color: white; border: 0px; cursor: pointer; padding-left: 10px; padding-top: 13px;\"></form>";
        	
        	outputString+="</div>";
       
    		out.print(outputString);
		}
	}
	
	private void get_following_list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = (String)request.getParameter("userid");	
		Encryption decryption = new Encryption();
        userid = decryption.decrypt(userid);
        dbUtil=new DbUtil(dataSource);
        ArrayList<ArrayList<String>> allFollowingList = dbUtil.getAllFollowingList(userid);
        
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		
		if(allFollowingList.size()==0){
			out.print("<div style=\"display: flex; justify-content: space-between;\"><p style=\"color: #03adfc; font-size: 18px; font-weight: 600; padding: 10px;\">0 Following</p>"
    				+ "<p style=\"color: white; font-size: 25px; font-weight: 600; width: 30px; height: 30px; border-radius: 50%; background-color: red; padding-left: 6px; margin-top: 5px; cursor: pointer;\" onclick=\"close_get_following_list();\">X</p></div>");
		}
		
		for(int i=0; i<allFollowingList.size(); i++){
			ArrayList<String> temp=allFollowingList.get(i);
			
			String outputString="";
        	if(i==0){
        		outputString+="<div style=\"display: flex; justify-content: space-between;\"><p style=\"color: #03adfc; font-size: 18px; font-weight: 600; padding: 10px;\">"+allFollowingList.size()+" Following</p>"
        				+ "<p style=\"color: white; font-size: 25px; font-weight: 600; width: 30px; height: 30px; border-radius: 50%; background-color: red; padding-left: 6px; margin-top: 5px; cursor: pointer;\" onclick=\"close_get_following_list();\">X</p></div>";
        	}
        	
        	outputString+="<div style=\"padding: 5px; display: flex; z-index: 101;\">";
        	
        	outputString+="<div style=\"height: 50px; width: 50px; overflow: hidden; border-radius: 50%; border: 2px solid #03adfc;\">";
    		outputString+="<img src=\"./DB_image/"+temp.get(1)+"\" style=\"object-fit: cover; width: 100%; height: 100%; border: 0;\"></div>";
        	outputString+="<form action=\"Profile\" method=\"POST\" target=\"_blank\"><input type=\"hidden\" name=\"command\" value=\"SHOW_OTHER_USER_PROFILE\"/><input type=\"hidden\" name=\"userid\" value=\""+temp.get(2)+"\"/><input type=\"submit\" value=\""+temp.get(0)+"\" style=\"font-size: 18px; font-weight: 600; padding-bottom: 4px; outline: none; background-color: white; border: 0px; cursor: pointer; padding-left: 10px; padding-top: 13px;\"></form>";
        	
        	outputString+="</div>";
       
    		out.print(outputString);
		}
	}
	
	private void get_follower_count(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = (String)request.getParameter("userid");	
		Encryption decryption = new Encryption();
        userid = decryption.decrypt(userid);
        
        dbUtil=new DbUtil(dataSource);
        String count = dbUtil.getFollowerCount(userid);
        response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		out.print("Follower: "+count);
	}
	
	private void get_following_count(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = (String)request.getParameter("userid");	
		Encryption decryption = new Encryption();
        userid = decryption.decrypt(userid);
        
        dbUtil=new DbUtil(dataSource);
        String count = dbUtil.getFollowingCount(userid);
        response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		out.print("Following: "+count);
	}
}
