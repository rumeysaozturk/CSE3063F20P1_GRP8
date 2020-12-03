
public class USER {
      private int userId;
      private String name;
      private String usertype;
      
	public USER() {
		// TODO Auto-generated constructor stub
	}

	public USER(int userýd,String name,String usertype ) {
		this.setUserId(userýd);
		this.setName(name);
		this.setUsertype(usertype);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
	
}
