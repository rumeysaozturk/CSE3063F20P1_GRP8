public class USER {
      private long userId;
      private String name;
      private String usertype;
      
	public USER() {
		// TODO Auto-generated constructor stub
	}

	public USER(long userId,String name,String usertype ) {
		this.setUserId(userId);
		this.setName(name);
		this.setUsertype(usertype);
	}
	
	//Print user
	public void printUser() {
		System.out.println("user id: "+this.getUserId()+ " username: "+this.getName()+" user type: "+ this.getUsertype() + " is created.");
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
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
