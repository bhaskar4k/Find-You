package AllServlet;

public class ProfileInfo {
	private String user_id;
	private String full_name;
	private String bio;
	private int following;
	private int follower;
	
	public ProfileInfo(String user_id, String full_name, String bio, int following, int follower) {
		super();
		this.user_id = user_id;
		this.full_name = full_name;
		this.bio = bio;
		this.following = following;
		this.follower = follower;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public int getFollowing() {
		return following;
	}

	public void setFollowing(int following) {
		this.following = following;
	}

	public int getFollower() {
		return follower;
	}

	public void setFollower(int follower) {
		this.follower = follower;
	}

	@Override
	public String toString() {
		return "ProfileInfo [user_id=" + user_id + ", full_name=" + full_name
				+ ", bio=" + bio + ", following=" + following + ", follower="
				+ follower + "]";
	}
}
