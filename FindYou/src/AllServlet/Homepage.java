package AllServlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


@WebServlet("/Homepage")
public class Homepage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/FindYou")
	private DataSource dataSource;
	private DbUtil dbUtil;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String theCommand=request.getParameter("command");			
			if (theCommand==null) {
				theCommand="HOMEPAGE";
			}
			
			switch (theCommand) {	
				case "HOMEPAGE":
					load_homePage(request,response);
					break;
								
				default:
					load_homePage(request,response);
			}			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String theCommand=request.getParameter("command");			
			if (theCommand==null) {
				theCommand="HOMEPAGE";
			}
			
			switch (theCommand) {	
				case "HOMEPAGE":
					load_homePage(request,response);
					break;
					
				case "CLOSE SESSION":
					close_session(request,response);
					break;
							
				case "SIGNUP":
					register_user(request,response);
					break;
					
				default:
					load_homePage(request,response);
			}			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void load_homePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dbUtil=new DbUtil(dataSource);
		
		HttpSession session = request.getSession();
		String session_userid = (String) session.getAttribute("session_userid");
		if(session_userid!=null){
			request.setAttribute("userid", session_userid);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/newsfeed.jsp");
			dispatcher.forward(request, response);
		}else{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	private void register_user(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String first_name=request.getParameter("firstName");
		String last_name=request.getParameter("lastName");
		String dob=request.getParameter("dob");
		String gender=request.getParameter("gender");
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		String password=request.getParameter("password");
		String userid=email+phone;
		
		User newUser=new User(first_name,last_name,dob,gender,email,phone,password,userid);
		
		dbUtil=new DbUtil(dataSource);
		try {
			dbUtil.addNewUser(newUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		load_homePage(request,response);
	}
	
	private void close_session(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);    
	    if (session != null) {
	        session.invalidate();
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
	    }
	}
}
