package AllServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/Feed")
public class Feed extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/FindYou")
	private DataSource dataSource;
	private DbUtil dbUtil;
	
    public Feed() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String theCommand=request.getParameter("command");			
			if (theCommand==null) {
				theCommand="FEED";
			}
			
			switch (theCommand) {	
				case "FEED":
					validate_login(request,response);
					break;
					
				default:
					validate_login(request,response);
			}			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String theCommand=request.getParameter("command");			
			if (theCommand==null) {
				theCommand="FEED";
			}
			
			switch (theCommand) {	
				case "FEED":
					validate_login(request,response);
					break;
				
				case "LOGIN":
					validate_login(request,response);
					break;
					
				case "REDIRECT_TO_NEWSFEED":
					load_feed(request,response);
					break;
					
				default:
					validate_login(request,response);
			}			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void validate_login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		String password=request.getParameter("password");	
		User loginUser=new User(email,phone,password);
		
		dbUtil=new DbUtil(dataSource);
		try {
			String userid=dbUtil.checkLoginAndValidate(loginUser);
			HttpSession session = request.getSession();
			String session_userid = (String) session.getAttribute("session_userid");
			
			Boolean session_is_active=false;
			if(userid=="WRONG"){
				userid=session_userid;
				
				if(userid!=null && userid!="WRONG"){
					session_is_active=true;
					Encryption decryption = new Encryption();
			        userid = decryption.decrypt(userid);
				}
			}
			
			if(userid==null || userid=="WRONG"){
				userid="WRONG";
				request.setAttribute("userid", userid);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
	    		dispatcher.forward(request, response);
			}else{
				Encryption encryption = new Encryption();
		        String encryptedString = encryption.encrypt(userid);
		        
		        if(session_is_active==false){
		        	session = request.getSession();
					session.setMaxInactiveInterval(86400000);
					session.setAttribute("session_userid", encryptedString);
		        }
				
		        load_feed(request,response);
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void load_feed(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String session_userid = (String) session.getAttribute("session_userid");
		if(session_userid==null){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
    		dispatcher.forward(request, response);
    		return;
		}
		
		Encryption decryption = new Encryption();
		String user_id=session_userid;
		session_userid = decryption.decrypt(session_userid);
		
		dbUtil=new DbUtil(dataSource);
		
		String profile_photo = dbUtil.showProfilePhoto(session_userid);
		ProfileInfo profileInfo=dbUtil.getUserProfileInfo(session_userid);
		User userInfo=dbUtil.getUserInfo(session_userid);
		ArrayList<ArrayList<String>> allPosts = dbUtil.getFeed(session_userid);
		
		request.setAttribute("userid", user_id);
		request.setAttribute("profile_photo", profile_photo);
		request.setAttribute("profileInfo", profileInfo);
		request.setAttribute("userInfo", userInfo);
		request.setAttribute("allPosts", allPosts);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/newsfeed.jsp");
		dispatcher.forward(request, response);
	}

}
