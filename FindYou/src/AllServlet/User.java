package AllServlet;

public class User {
	private String first_name;
	private String last_name;
	private String dob;
	private String gender;
	private String email;
	private String phone;
	private String password;
	private String userid;
	private String bio;
	
	public User(String first_name, String last_name, String dob, String gender, String email, String phone, String password, String userid) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.dob = dob;
		this.gender = gender;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.userid = userid;
	}

	public User(String email, String phone, String password) {
		this.email = email;
		this.phone = phone;
		this.password = password;
	}
	
	public User(String userid, String first_name, String last_name, String email, String phone, String password, String bio) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.userid = userid;
		this.bio = bio;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "User [first_name=" + first_name + ", last_name=" + last_name
				+ ", dob=" + dob + ", gender=" + gender + ", email=" + email
				+ ", phone=" + phone + ", password=" + password + ", userid="
				+ userid + ", bio=" + bio + "]";
	}

	
}
