package AllServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.annotation.Resource;
import javax.sql.DataSource;

import sun.misc.GC.LatencyRequest;

public class DbUtil {
	@Resource(name="jdbc/FindYou")
	private static DataSource dataSource;
	
	public DbUtil(DataSource dataSource){
		DbUtil.dataSource=dataSource;
	}

	public void addNewUser(User newUser) throws Exception {
		Connection myCon=null;
		PreparedStatement statement=null;
		
		try{
			myCon=dataSource.getConnection();
			String sql="INSERT INTO `find_you`.`user_info` (`first_name`,"
					+ "`last_name`,`dob`,`gender`,`email`,`phone`,`password`) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
			statement = myCon.prepareStatement(sql);

			statement.setString(1, newUser.getFirst_name());
			statement.setString(2, newUser.getLast_name());
			statement.setString(3, newUser.getDob());
			statement.setString(4, newUser.getGender());
			statement.setString(5, newUser.getEmail());
			statement.setString(6, newUser.getPhone());
			statement.setString(7, newUser.getPassword());

			statement.executeUpdate();						
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(myCon!=null) myCon.close();
			if(statement!=null) statement.close();
		}
	}

	public String checkLoginAndValidate(User loginUser) throws Exception {
		Connection myCon=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String userid = "WRONG";
		try{
			myCon=dataSource.getConnection();
			
			String sql = "SELECT user_id FROM find_you.user_info WHERE email=? AND password=? AND phone=?;";
	        stmt = myCon.prepareStatement(sql);
	        stmt.setString(1, loginUser.getEmail());
	        stmt.setString(2, loginUser.getPassword());
	        stmt.setString(3, loginUser.getPhone());
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	        	userid = rs.getString("user_id");
	        } 	       	        
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(myCon!=null) myCon.close();
	        if(stmt!=null) stmt.close();
	        if(rs!=null) rs.close();
		}
		
		return userid;
	}

	public void changeProfilePhoto(String userid, String imageFileName) throws Exception {
		Connection con=null;
		PreparedStatement statement=null;
		try{
			con=dataSource.getConnection();
			String sql = "DELETE FROM find_you.profile_photo WHERE user_id=(?)";

			statement = con.prepareStatement(sql);
			
			statement.setString(1, userid);
			
			statement.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			con=dataSource.getConnection();
			String sql = "INSERT INTO `find_you`.`profile_photo` (`user_id`,`imageFileName`) VALUES (?, ?);";

			statement = con.prepareStatement(sql);
			
			statement.setString(1, userid);
			statement.setString(2, imageFileName);
			
			statement.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
			if(statement!=null) statement.close();
		}
	}

	public String showProfilePhoto(String userid) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
	    try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	        String sql="SELECT imageFileName FROM find_you.profile_photo WHERE user_id='"+userid+"'";
	        rs = stmt.executeQuery(sql);
	        
	        if(rs.next()) {
	        	String profile_photo = (String) rs.getString("imageFileName");
	            return profile_photo;
	        }else{
	        	System.out.println("No profile pic");
	        }	        	        
	    }catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
	        if(stmt!=null) stmt.close();
	        if(rs!=null) rs.close();
		}
	    return "";
	}

	public ProfileInfo getUserProfileInfo(String userid) throws Exception{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
	    try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	    	
	    	String user_id="",first_name="",last_name="",bio="",full_name="";
	    	int following_count=0,follower_count=0;
	        
	    	String sql="SELECT user_id, first_name, last_name, bio FROM find_you.user_info WHERE user_id='"+userid+"';";
	        rs = stmt.executeQuery(sql);
	        if(rs.next()) {
	        	user_id = (String) rs.getString("user_id");
	        	first_name = (String) rs.getString("first_name");
	        	last_name = (String) rs.getString("last_name");
	        	bio = (String) rs.getString("bio");
	        	full_name=first_name+" "+last_name;
	        }
	        
	        String sql1="SELECT COUNT(following_user_id) as following FROM find_you.following_info WHERE user_id='"+userid+"';";
	        rs = stmt.executeQuery(sql1);	        
	        if(rs.next()) {
	        	following_count = (int) rs.getInt("following");
	        }
	        
	        String sql2="SELECT COUNT(follower_user_id) as follower FROM find_you.follower_info WHERE user_id='"+userid+"';";
	        rs = stmt.executeQuery(sql2);	        
	        if(rs.next()) {
	        	follower_count = (int) rs.getInt("follower");
	        }
	        
	        ProfileInfo profileInfo=new ProfileInfo(user_id,full_name,bio,following_count,follower_count);            	        
	        
	        return profileInfo;
	    }catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
	        if(stmt!=null) stmt.close();
	        if(rs!=null) rs.close();
		}
		
		return null;
	}

	public User getUserInfo(String userid) throws Exception {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
	    try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	        String sql="SELECT * FROM find_you.user_info WHERE user_id='"+userid+"';";
	        rs = stmt.executeQuery(sql);
	        
	        if(rs.next()) {
	        	String user_id = (String) rs.getString("user_id");
	        	String first_name = (String) rs.getString("first_name");
	        	String last_name = (String) rs.getString("last_name");
	        	String email = (String) rs.getString("email");
	        	String phone = (String) rs.getString("phone");
	        	String password = (String) rs.getString("password");
	        	String bio = (String) rs.getString("bio");
	        	
	        	Encryption encryption = new Encryption();
	        	first_name=encryption.encrypt(first_name);
				last_name=encryption.encrypt(last_name);
				email=encryption.encrypt(email);
				phone=encryption.encrypt(phone);
				password=encryption.encrypt(password);
				bio=encryption.encrypt(bio);

	        	
	        	User userInfo=new User(user_id,first_name,last_name,email,phone,password,bio);	            	        	
	        	
	        	return userInfo;
	        }else{
	        	System.out.println("No User info");
	        }
	    }catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
        	if(stmt!=null) stmt.close();
        	if(rs!=null) rs.close();
		}
		
		return null;
	}

	public void edit_user_details(User updateUser) throws Exception {
		Connection con=null;
		PreparedStatement statement=null;
		try{
			con=dataSource.getConnection();
			String sql = "UPDATE find_you.user_info SET first_name=?,last_name=?,email=?,phone=?,password=?,bio=? WHERE user_id=?;";
			
			statement = con.prepareStatement(sql);
			
			statement.setString(1, updateUser.getFirst_name());
			statement.setString(2, updateUser.getLast_name());
			statement.setString(3, updateUser.getEmail());
			statement.setString(4, updateUser.getPhone());
			statement.setString(5, updateUser.getPassword());
			statement.setString(6, updateUser.getBio());
			statement.setString(7, updateUser.getUserid());
			
			statement.executeUpdate();					
		}catch(Exception ex){
			System.out.println("ERROR IN UPDATING USER");
			ex.printStackTrace();
		}finally{
			if(statement!=null) statement.close();
			if(con!=null) con.close();
		}
	}
	
	public ArrayList<ArrayList<String>> getAllFollowerList(String userid) throws Exception{
		ArrayList<ArrayList<String>> allFollowerList = new ArrayList<>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
	    try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	        String sql="SELECT follower_user_id FROM find_you.follower_info WHERE user_id='"+userid+"';";
	        rs = stmt.executeQuery(sql);
	        
	        ArrayList<String> followers=new ArrayList<>();
	        while(rs.next()) {
	        	String user_id = (String) rs.getString("follower_user_id");
	        	followers.add(user_id);
	        }
	        
	        Encryption encryption = new Encryption();
	        for (int i = 0; i < followers.size(); i++) {
	        	String current_follower_user_id=followers.get(i);
	        	sql="SELECT ui.first_name,ui.last_name,pp.imageFileName FROM "
	        			+ "find_you.user_info ui JOIN find_you.profile_photo pp ON ui.user_id=pp.user_id "
	        			+ "WHERE ui.user_id='"+current_follower_user_id+"';";
	        	
	        	rs = stmt.executeQuery(sql);
	        	if(rs.next()) {
		        	String first_name = (String) rs.getString("first_name");
		        	String last_name = (String) rs.getString("last_name");
		        	String full_name=first_name+" "+last_name;
		        	String imageFileName = (String) rs.getString("imageFileName");
		        	
		        	ArrayList<String> temp = new ArrayList<>();
		        	current_follower_user_id=encryption.encrypt(current_follower_user_id);
		        	temp.add(full_name);
		        	temp.add(imageFileName);
		        	temp.add(current_follower_user_id);
		        	allFollowerList.add(temp);
		        }
	        }	        	        	        
	    }catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
	        if(stmt!=null) stmt.close();
	        if(rs!=null) rs.close();
		}
		
	    Comparator<ArrayList<String>> comparator = new Comparator<ArrayList<String>>() {
            public int compare(ArrayList<String> list1, ArrayList<String> list2) {
                return list1.get(0).compareTo(list2.get(0));
            }
        };
        Collections.sort(allFollowerList, comparator);
		return allFollowerList;
	}
	
	public ArrayList<ArrayList<String>> getAllFollowingList(String userid) throws Exception{
		ArrayList<ArrayList<String>> allFollowingList = new ArrayList<>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
	    try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	        String sql="SELECT following_user_id FROM find_you.following_info WHERE user_id='"+userid+"';";
	        rs = stmt.executeQuery(sql);
	        
	        ArrayList<String> following=new ArrayList<>();
	        while(rs.next()) {
	        	String user_id = (String) rs.getString("following_user_id");
	        	following.add(user_id);
	        }
	        
	        Encryption encryption = new Encryption();
	        for (int i = 0; i < following.size(); i++) {
	        	String current_following_user_id=following.get(i);
	        	sql="SELECT ui.first_name,ui.last_name,pp.imageFileName FROM "
	        			+ "find_you.user_info ui JOIN find_you.profile_photo pp ON ui.user_id=pp.user_id "
	        			+ "WHERE ui.user_id='"+current_following_user_id+"';";
	        	
	        	rs = stmt.executeQuery(sql);
	        	if(rs.next()) {
		        	String first_name = (String) rs.getString("first_name");
		        	String last_name = (String) rs.getString("last_name");
		        	String full_name=first_name+" "+last_name;
		        	String imageFileName = (String) rs.getString("imageFileName");
		        	
		        	ArrayList<String> temp = new ArrayList<>();
		        	current_following_user_id=encryption.encrypt(current_following_user_id);
		        	temp.add(full_name);
		        	temp.add(imageFileName);
		        	temp.add(current_following_user_id);
		        	allFollowingList.add(temp);
		        }
	        }
	    }catch(Exception ex){
			ex.printStackTrace();
		}finally{
        	if(con!=null) con.close();
	        if(stmt!=null) stmt.close();
	        if(rs!=null) rs.close();
        }
		
	    Comparator<ArrayList<String>> comparator = new Comparator<ArrayList<String>>() {
            public int compare(ArrayList<String> list1, ArrayList<String> list2) {
                return list1.get(0).compareTo(list2.get(0));
            }
        };
        Collections.sort(allFollowingList, comparator);
		return allFollowingList;
	}
	
	public void makePost(String userid, String postid, String post, String upload_time) throws Exception {
		Connection myCon=null;
		PreparedStatement statement=null;
		
		try{
			myCon=dataSource.getConnection();
			String sql="insert into find_you.posts (user_id,post_id,post,upload_time) values(?,?,?,?);";
			statement = myCon.prepareStatement(sql);

			statement.setString(1, userid);
			statement.setString(2, postid);
			statement.setString(3, post);
			statement.setString(4, upload_time);

			statement.executeUpdate();						
		}catch(Exception ex){
			System.out.println("Error in make post");
			ex.printStackTrace();
		}finally{
			if(myCon!=null) myCon.close();
			if(statement!=null) statement.close();
		}
	}
	
	public ArrayList<ArrayList<String>> getAllPosts(String userid) throws Exception {
		ArrayList<ArrayList<String>> allPosts = new ArrayList<>();		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
	    try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	        String sql="select post_id,post,upload_time from find_you.posts where user_id='"+userid+"';";
	        rs = stmt.executeQuery(sql);
	        
	        while(rs.next()) {
	        	ArrayList<String> temp = new ArrayList<>();
	        	String post_id = (String) rs.getString("post_id");
	        	String post = (String) rs.getString("post");
	        	String upload_time = (String) rs.getString("upload_time");
	        	
	        	Encryption encryption = new Encryption();
	        	String encrypted_post_id=encryption.encrypt(post_id);
	        	
	        	temp.add(encrypted_post_id);
	        	temp.add(post);
	        	temp.add(upload_time);
		        
		        allPosts.add(temp);
	        }
	    }catch(Exception ex){
	    	System.out.println("Error NHK");
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
	        if(stmt!=null) stmt.close();
	        if(rs!=null) rs.close();
		}
	    		
        Collections.reverse(allPosts);
		return allPosts;
	}
	
	public void editPost(String postid, String post) throws Exception{
		Connection con=null;
		PreparedStatement statement=null;
		try{
			con=dataSource.getConnection();
			
			String sql="update find_you.posts set post='"+post+"' where post_id='"+postid+"';";		
			statement = con.prepareStatement(sql);			
			statement.executeUpdate();
		}catch(Exception ex){
			System.out.println("ERROR IN EDITING POST");
			ex.printStackTrace();
		}finally{
			if(statement!=null) statement.close();
			if(con!=null) con.close();
		}
	}
	
	public void deletePost(String postid) throws Exception{
		Connection con=null;
		PreparedStatement statement = null;
		try{
			con=dataSource.getConnection();
			
			String sql="delete from find_you.posts where post_id='"+postid+"';";		
			statement = con.prepareStatement(sql);			
			statement.executeUpdate();
			
			sql="delete from find_you.like where post_id='"+postid+"';";		
			statement = con.prepareStatement(sql);			
			statement.executeUpdate();
			
			sql="delete from find_you.comment where post_id='"+postid+"';";		
			statement = con.prepareStatement(sql);			
			statement.executeUpdate();
		}catch(Exception ex){
			System.out.println("ERROR IN DELETING POST");
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
			if(statement!=null) statement.close();
		}
	}
	
	public ArrayList<ArrayList<String>> loadAllComments(String postid) throws Exception {
		Encryption decryption = new Encryption();
    	postid=decryption.decrypt(postid);
    	
		ArrayList<ArrayList<String>> allComments = new ArrayList<>();	
		
		Connection con = null;
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
	    try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	        String sql="select user_id,comment_id,comment,comment_time from find_you.comment where post_id='"+postid+"';";
	        rs = stmt.executeQuery(sql);
	        
	        while(rs.next()) {
	        	ArrayList<String> temp = new ArrayList<>();
	        	
	        	String user_id = (String) rs.getString("user_id");
	        	String comment_id = (String) rs.getString("comment_id");
	        	String comment = (String) rs.getString("comment");
	        	String comment_time = (String) rs.getString("comment_time");
	        	
		        stmt2 = con.createStatement();
		        String sql2="SELECT first_name,last_name FROM find_you.user_info WHERE user_id='"+user_id+"'";
		        rs2 = stmt2.executeQuery(sql2);
		        
		        String full_name="";
		        if(rs2.next()) {
		        	String first_name = (String) rs2.getString("first_name");
		        	String last_name = (String) rs2.getString("last_name");
		        	full_name=first_name+" "+last_name;
		        }
		        
		        sql2="SELECT imageFileName FROM find_you.profile_photo WHERE user_id='"+user_id+"'";
		        rs2 = stmt2.executeQuery(sql2);
		        
		        String imageFileName="";
		        if(rs2.next()) {
		        	imageFileName = (String) rs2.getString("imageFileName");
		        }
		        
		        temp.add(user_id);
		        temp.add(full_name);
		        temp.add(comment_time);
		        temp.add(comment);
		        temp.add(imageFileName);
		        temp.add(comment_id);
		        
		        allComments.add(temp);
	        }
	    }catch(Exception ex){
	    	System.out.println("Error asche");
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
	        if(stmt!=null) stmt.close();
	        if(rs!=null) rs.close();
	        if(stmt2!=null) stmt2.close();
	        if(rs2!=null) rs2.close();
		}
		
        Collections.reverse(allComments);
		
		return allComments;
	}
	
	public ArrayList<ArrayList<String>> loadAllProfileSuggesion(String firstName, String lastName) throws Exception{
		ArrayList<ArrayList<String>> allSuggession = new ArrayList<>();
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	    	String sql="";
	    	
	    	if(firstName!="" && lastName!=""){
	    		sql+="SELECT ui.user_id, ui.first_name, ui.last_name, pp.imageFileName FROM find_you.user_info ui JOIN find_you.profile_photo pp ON ui.user_id = pp.user_id WHERE ui.first_name LIKE '"+firstName+"%' AND ui.last_name LIKE '"+lastName+"%';";		        
	    	}else if(firstName!="" && lastName==""){
	    		sql+="SELECT ui.user_id, ui.first_name, ui.last_name, pp.imageFileName FROM find_you.user_info ui JOIN find_you.profile_photo pp ON ui.user_id = pp.user_id WHERE ui.first_name LIKE '"+firstName+"%';";
	    	}
	    	
	    	if(sql!=""){
	    		rs = stmt.executeQuery(sql);
		        
		        while(rs.next()) {
		        	ArrayList<String> temp = new ArrayList<>();
		        	
		        	String user_id = (String) rs.getString("user_id");
		        	String first_name = (String) rs.getString("first_name");
		        	String last_name = (String) rs.getString("last_name");
		        	String imageFileName = (String) rs.getString("imageFileName");
		        	
		        	Encryption encryption = new Encryption();
		        	String encrypted_user_id=encryption.encrypt(user_id);
		        	
		        	String full_name = first_name+" "+last_name;
		        	
		        	temp.add(encrypted_user_id);
		        	temp.add(full_name);
		        	temp.add(imageFileName);
		        				        
		        	allSuggession.add(temp);
		        }
	    	}
	    }catch(Exception ex){
	    	System.out.println("Error Asche bhai");
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
			if(stmt!=null) stmt.close();
			if(rs!=null) rs.close();
		}
		
		return allSuggession;
	}
	
	public void doComment(String postid, String userid, String typed_comment) throws Exception {
		Encryption decryption=new Encryption();
		postid=decryption.decrypt(postid);
		userid=decryption.decrypt(userid);
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		String tempTime=""+currentDateTime;
		String comment_time="";
		
		for(int i=0; i<tempTime.length(); i++){
			if(tempTime.charAt(i)=='.') break;
			if(tempTime.charAt(i)=='-'){
				comment_time+=".";
				continue;
			}
			if(tempTime.charAt(i)=='T'){
				comment_time+=" - ";
				continue;
			}
			comment_time+=tempTime.charAt(i);
		}
		
		
		String commentid=userid+"||"+postid+"||"+comment_time;
		
		Connection myCon=null;
		PreparedStatement statement = null;
		
		try{
			myCon=dataSource.getConnection();
			String sql="insert into find_you.comment (post_id,user_id,comment_id,comment,comment_time) values(?,?,?,?,?);";
			statement = myCon.prepareStatement(sql);

			statement.setString(1, postid);
			statement.setString(2, userid);
			statement.setString(3, commentid);
			statement.setString(4, typed_comment);
			statement.setString(5, comment_time);

			statement.executeUpdate();						
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(myCon!=null) myCon.close();
			if(statement!=null) statement.close();
		}
	}
	
	public ArrayList<ArrayList<String>> loadAllLikes(String postid) throws Exception{
		ArrayList<ArrayList<String>> allLikes = new ArrayList<>();
		
		Encryption decryption = new Encryption();
    	postid=decryption.decrypt(postid);
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Statement stmt1 = null;
		ResultSet rs1 = null;
		
		try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	    	String sql="select user_id from find_you.like where post_id='"+postid+"';";
	    	
	    	rs = stmt.executeQuery(sql);
	        
	        while(rs.next()) {
	        	ArrayList<String> temp = new ArrayList<>();
	        	
	        	String user_id = (String) rs.getString("user_id");
	        	
	        	String sql1="SELECT ui.first_name, ui.last_name, pp.imageFileName FROM find_you.user_info ui JOIN find_you.profile_photo pp ON ui.user_id = pp.user_id WHERE ui.user_id='"+user_id+"';";
	        	
	        	stmt1 = con.createStatement();
	    		rs1 = stmt1.executeQuery(sql1);
	    		
	    		String full_name="", imageFileName="";
	    		if(rs1.next()){
	    			String first_name = (String) rs1.getString("first_name");
	    			String last_name = (String) rs1.getString("last_name");
	    			full_name = first_name+" "+last_name;
	    			imageFileName = (String) rs1.getString("imageFileName");
	    		}
	        	
	        	Encryption encryption = new Encryption();
	        	String encrypted_user_id=encryption.encrypt(user_id);
	        	
	        	temp.add(encrypted_user_id);
	        	temp.add(full_name);
	        	temp.add(imageFileName);
	        				        
	        	allLikes.add(temp);	        		        	
	        }
	    }catch(Exception ex){
	    	System.out.println("Like gulo aschena");
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
			if(stmt!=null) stmt.close();
			if(rs!=null) rs.close();
			if(stmt1!=null) stmt1.close();
			if(rs1!=null) rs1.close();
		}
		
		Collections.reverse(allLikes);
		
		return allLikes;
	}
	
	public String getLikeCount(String postid) throws Exception {
		Encryption decryption = new Encryption();
    	postid=decryption.decrypt(postid);
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String like_count="0";
		
		try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	    	String sql="SELECT count(user_id) as like_count FROM find_you.like where post_id='"+postid+"';";
	    	
	    	rs = stmt.executeQuery(sql);
	        
	        if(rs.next()) {	        	
	        	like_count = (String) rs.getString("like_count");	        	
	        }
	    }catch(Exception ex){
	    	System.out.println("Like Count aschena");
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
			if(stmt!=null) stmt.close();
			if(rs!=null) rs.close();
		}
		
		return like_count;
	}
	
	public String getCommentCount(String postid) throws Exception {
		Encryption decryption = new Encryption();
    	postid=decryption.decrypt(postid);
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String comment_count="0";
		
		try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	    	String sql="SELECT count(user_id) as comment_count FROM find_you.comment where post_id='"+postid+"';";
	    	
	    	rs = stmt.executeQuery(sql);
	        
	        if(rs.next()) {	        	
	        	comment_count = (String) rs.getString("comment_count");	        	
	        }
	    }catch(Exception ex){
	    	System.out.println("Comment Count aschena");
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
			if(stmt!=null) stmt.close();
			if(rs!=null) rs.close();
		}
		
		return comment_count;
	}
	
	public Boolean isLiked(String postid, String userid) throws Exception {
		Encryption decryption=new Encryption();
		postid=decryption.decrypt(postid);
		userid=decryption.decrypt(userid);
		
		Boolean is_liked=false;
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	    	String sql="select count(user_id) as user_id from find_you.like where post_id='"+postid+"' and user_id='"+userid+"';";
	    	
	    	rs = stmt.executeQuery(sql);
	        
	        if(rs.next()) {	        	
	        	String users_count = (String) rs.getString("user_id");
	        	int i=Integer.parseInt(users_count); 
	        	if(i>0){
	        		is_liked=true;
	        	}
	        }
	    }catch(Exception ex){
	    	System.out.println("Like koreche kina seta aschena");
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
			if(stmt!=null) stmt.close();
			if(rs!=null) rs.close();
		}
		
		return is_liked;
	}
	
	public void doLike(String postid, String userid) throws Exception {
		String sql="";
		if(isLiked(postid,userid)==true){
			Encryption decryption=new Encryption();
			postid=decryption.decrypt(postid);
			userid=decryption.decrypt(userid);
			
			sql+="delete from find_you.like where post_id='"+postid+"' and user_id='"+userid+"';";			
		}else{
			Encryption decryption=new Encryption();
			postid=decryption.decrypt(postid);
			userid=decryption.decrypt(userid);
			
			sql+="insert into find_you.like (post_id, user_id) value('"+postid+"','"+userid+"');";
		}

		Connection myCon=null;
		PreparedStatement statement = null;		
		
		try{
			myCon=dataSource.getConnection();
			statement = myCon.prepareStatement(sql);
			statement.executeUpdate();						
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(myCon!=null) myCon.close();
			if(statement!=null) statement.close();
		}
	}
	
	public Boolean isFollowed(String userid, String follower_userid) throws Exception {		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
			
		Boolean is_followed=false;
		try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	    	String sql="select count(user_id) as follow from find_you.follower_info where user_id='"+userid+"' and follower_user_id='"+follower_userid+"';";
	    	
	    	rs = stmt.executeQuery(sql);
	        
	        if(rs.next()) {	        	
	        	String follow_count = (String) rs.getString("follow");
	        	int i=Integer.parseInt(follow_count); 
	        	if(i>0){
	        		is_followed=true;
	        	}
	        }
	    }catch(Exception ex){
	    	System.out.println("Follow koreche kina seta aschena");
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
			if(stmt!=null) stmt.close();
			if(rs!=null) rs.close();
		}
		
		return is_followed;
	}
	
	public void doFollow(String userid, String follower_userid) throws Exception {
		Encryption decryption=new Encryption();
		userid=decryption.decrypt(userid);
		follower_userid=decryption.decrypt(follower_userid);
		
		Connection myCon=dataSource.getConnection();
		PreparedStatement statement = null;	
		String sql="";
		
		if(isFollowed(userid,follower_userid)==true){			
			sql="delete from find_you.follower_info where user_id='"+userid+"' and follower_user_id='"+follower_userid+"';";
			try{
				statement = myCon.prepareStatement(sql);
				statement.executeUpdate();	
				sql="delete from find_you.following_info where user_id='"+follower_userid+"' and following_user_id='"+userid+"';";
				statement = myCon.prepareStatement(sql);
				statement.executeUpdate();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				if(myCon!=null) myCon.close();
				if(statement!=null) statement.close();
			}
		}else{			
			sql="insert into find_you.follower_info (user_id, follower_user_id) value('"+userid+"','"+follower_userid+"');";
			statement = myCon.prepareStatement(sql);
			statement.executeUpdate();	
			sql="insert into find_you.following_info (user_id, following_user_id) value('"+follower_userid+"','"+userid+"');";
			statement = myCon.prepareStatement(sql);
			statement.executeUpdate();
		}	
	}
	
	public String getFollowerCount(String userid) throws Exception {
		String count="0";		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
	    try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	    	String sql="SELECT count(follower_user_id) as count FROM find_you.follower_info WHERE user_id='"+userid+"';";
	        rs = stmt.executeQuery(sql);
	        
	        if(rs.next()) {
	        	count = (String) rs.getString("count");
	        }
	    }catch(Exception ex){
	    	System.out.println("Error NHK");
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
	        if(stmt!=null) stmt.close();
	        if(rs!=null) rs.close();
		}

		return count;
	}
	
	public String getFollowingCount(String userid) throws Exception {
		String count="0";		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
	    try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	    	String sql="SELECT count(following_user_id) as count FROM find_you.following_info WHERE user_id='"+userid+"';";
	        rs = stmt.executeQuery(sql);
	        
	        if(rs.next()) {
	        	count = (String) rs.getString("count");
	        }
	    }catch(Exception ex){
	    	System.out.println("Error NHK");
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
	        if(stmt!=null) stmt.close();
	        if(rs!=null) rs.close();
		}

		return count;
	}
	
	public ArrayList<ArrayList<String>> getFeed(String userid) throws Exception {
		ArrayList<ArrayList<String>> allPosts1 = new ArrayList<>();
		ArrayList<ArrayList<String>> allPosts2 = new ArrayList<>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Statement stmt1 = null;
		ResultSet rs1 = null;
		
	    try {
	    	con=dataSource.getConnection();
	    	stmt = con.createStatement();
	        String sql="SELECT posts.* FROM (SELECT posts.*, 1 as sort_order FROM find_you.posts WHERE posts.user_id IN (SELECT following_user_id FROM find_you.following_info WHERE user_id = "+userid+") UNION SELECT posts.*, 2 as sort_order FROM find_you.posts WHERE find_you.posts.user_id NOT IN (SELECT following_user_id FROM find_you.following_info WHERE user_id = "+userid+") ) as posts ORDER BY sort_order ASC, upload_time DESC;";
	        rs = stmt.executeQuery(sql);
	        
	        while(rs.next()) {
	        	ArrayList<String> temp = new ArrayList<>();
	        	String posted_user_id = (String) rs.getString("user_id");
	        	String post_id = (String) rs.getString("post_id");
	        	String post = (String) rs.getString("post");
	        	String upload_time = (String) rs.getString("upload_time");
	        	String sort_order = (String) rs.getString("sort_order");
	        	
	        	stmt1 = con.createStatement();
		        String sql1="SELECT imageFileName FROM find_you.profile_photo where user_id='"+posted_user_id+"';";
		        rs1 = stmt1.executeQuery(sql1);
		        String photo="";
		        if(rs1.next()){
		        	photo=(String) rs1.getString("imageFileName");
		        }
		        
		        stmt1 = con.createStatement();
		        sql1="SELECT first_name,last_name FROM find_you.user_info where user_id='"+posted_user_id+"';";
		        rs1 = stmt1.executeQuery(sql1);
		        String name="";
		        if(rs1.next()){
		        	name=(String) rs1.getString("first_name");
		        	name+=" ";
		        	name+=(String) rs1.getString("last_name");
		        }
	        	
	        	Encryption encryption = new Encryption();
	        	post_id=encryption.encrypt(post_id);
	        	posted_user_id=encryption.encrypt(posted_user_id);
	        	
	        	temp.add(post_id);
	        	temp.add(post);
	        	temp.add(upload_time);
	        	temp.add(posted_user_id);
	        	temp.add(name);
	        	temp.add(photo);
	        	
	        	if(sort_order=="1"){
	        		allPosts1.add(temp);
	        		Collections.shuffle(allPosts1);
	        	}else{
	        		allPosts2.add(temp);
	        		Collections.shuffle(allPosts2);
	        	}
	        }
	    }catch(Exception ex){
	    	System.out.println("Error in feed NHK");
			ex.printStackTrace();
		}finally{
			if(con!=null) con.close();
	        if(stmt!=null) stmt.close();
	        if(rs!=null) rs.close();
	        if(stmt1!=null) stmt1.close();
	        if(rs1!=null) rs1.close();
		}
	    
	    ArrayList<ArrayList<String>> mergedList = new ArrayList<>();
	    mergedList.addAll(allPosts1);
	    mergedList.addAll(allPosts2);
		return mergedList;
	}
}








