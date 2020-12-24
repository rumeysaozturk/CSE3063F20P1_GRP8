import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.*;

public class USER {
      private Long userId;
      private String name;
      private String usertype;
      private Double ConsistencyCheckProbability;
      private ArrayList<INSTANCE>labeled=new ArrayList<INSTANCE>();
	public USER() {
		// TODO Auto-generated constructor stub
	}

	public USER(long userId,String name,String usertype,Double ConsistencyCheckProbability ) {
		this.setUserId(userId);
		this.setName(name);
		this.setUsertype(usertype);
		setConsistencyCheckProbability(ConsistencyCheckProbability);
	}
	
	//Print user
	public void printUser(Logger logger) {
		logger.info("user id: "+this.getUserId()+ " username: "+this.getName()+" user type: "+ this.getUsertype()+"user ConsistencyCheckProbability :" +this.getConsistencyCheckProbability()+ " is created.");
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

	public ArrayList<INSTANCE> getLabeled() {
		return labeled;
	}

	public void setLabeled(ArrayList<INSTANCE> labeled) {
		this.labeled = labeled;
	}
	public void addInstance(INSTANCE x) {
		this.labeled.add(x);
	}
	

	public Double getConsistencyCheckProbability() {
		return ConsistencyCheckProbability;
	}

	public void setConsistencyCheckProbability(Double consistencyCheckProbability) {
		ConsistencyCheckProbability = consistencyCheckProbability;
	}

}
