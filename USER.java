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
      private Long totalNumberofýnstance=(long)0;
      private ArrayList<INSTANCE>labeled=new ArrayList<INSTANCE>();
      private ArrayList<LABEL_ASSIGNMENT>labeled2=new ArrayList<LABEL_ASSIGNMENT>();
      private Long checkconsistency=(long)0;
      private ArrayList<Long>time=new ArrayList<Long>();
      private Long Numofuniqueins=(long)0;
      private double consistency=0.0;
      private ArrayList<DATASET>dataset=new ArrayList<DATASET>();
      
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

	

	public ArrayList<LABEL_ASSIGNMENT> getLabeled2() {
		return labeled2;
	}

	public void setLabeled2(LABEL_ASSIGNMENT labeled2) {
		this.labeled2.add(labeled2);
	}
	
	public boolean 	existbefore(LABEL_ASSIGNMENT two) {
	for(int c=0;c<this.getLabeled2().size();c++) {
		if(this.getLabeled2().get(c).getInstanceId()==two.getInstanceId()&&this.getLabeled2().get(c).getLabels().equals(two.getLabels())) {
			System.out.println("berra");
			
			return true;
		}
	
	}
	return false;

}



	public ArrayList<Long> getTime() {
		return time;
	}

	public void setTime(Long  time) {
		this.time.add(time);
	}
	
public long average() {
	if(this.totalNumberofýnstance==0) {
		return 0;
	}else {
		long sum=0;
	for(int i=0;i<this.time.size();i++) {
		sum=time.get(i);
	}
return sum/this.totalNumberofýnstance;
	}
}

public double stdeviation() {
	if(this.totalNumberofýnstance==0) {
		return 0;
	}else {
	long mean =this.average();
	double sum=0;
	for(int i=0;i<this.time.size();i++) {
		sum=Math.pow((time.get(i)-mean), 2);
	}
	double std=Math.sqrt((sum/(this.totalNumberofýnstance-1)));
	return std;}
}

public Long getCheckconsistency() {
	return checkconsistency;
}

public void setCheckconsistency(Long checkconsistency) {
	if(checkconsistency==-1) {
		this.checkconsistency++;
	}else {
	this.checkconsistency = checkconsistency;
}
	}

public Long getTotalNumberofýnstance() {
	return totalNumberofýnstance;
}

public void setTotalNumberofýnstance(Long totalNumberofýnstance) {
	if(totalNumberofýnstance==-1) {
		this.totalNumberofýnstance++;
	}
	else {
	this.totalNumberofýnstance = totalNumberofýnstance;
    }
	}

public Long getNumofuniqueins() {
	return Numofuniqueins;
}

public void setNumofuniqueins(Long numofuniqueins) {
	if(numofuniqueins==-1) {
		this.Numofuniqueins++;
	}
	else {
	this.Numofuniqueins=numofuniqueins;
    }
}

public double getConsistency() {
	return consistency;
}

public void setConsistency() {
	if(this.totalNumberofýnstance==0) {
		this.consistency=0.0;
	}else {
	this.consistency=((double)this.checkconsistency/this.totalNumberofýnstance)*100;
}}

public ArrayList<DATASET> getDataset() {
	return dataset;
}

public void setDataset(ArrayList<DATASET> dataset) {
	this.dataset = dataset;
}
}
