import java.util.*;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class INSTANCE implements finder {
      private long id;
      private String instance;
      private ArrayList<LABEL> labelInInstance = new ArrayList<>();// store of all labels for each instance
      private ArrayList<Integer> countOfLabel = new ArrayList<>();// store of each label counts for each instance
      private Integer totalNumberOfLabelAssignment = 0;
      private ArrayList<USER> uniqueUsers = new ArrayList<>(); // store of unique users who labeled for each instance
      private ArrayList<Integer> percentageOfLabel = new ArrayList<>();// store of label percantage for each instance
     
      
      

	public INSTANCE() {
  		// TODO Auto-generated constructor stub
  	}
  	
  	public INSTANCE(long id,String text ) {
  		this.setInstanceid(id);
  		this.instance=text;
  		
  	}
  	//calculating entropy
  	public double entropy( Logger logger) {
  		double result=0.0;
  		double divide;
  		double countOfLabel;
  		double totalNumberOfLabelAssignment;
  		
  		double uniqueLabel= calculateTotalNumberOfUniqueLabel(logger);
  		if(uniqueLabel!=0&&uniqueLabel!=1&& this.totalNumberOfLabelAssignment!=0) {
  		  for(int i=0; i<this.countOfLabel.size(); i++) {
  			
  			if(this.countOfLabel.get(i)!=0) {
  			countOfLabel=this.countOfLabel.get(i);
  		  	totalNumberOfLabelAssignment=this.totalNumberOfLabelAssignment;
  			divide=countOfLabel/totalNumberOfLabelAssignment;
  			result-= divide* (Math.log10(divide) / Math.log10(uniqueLabel)) ;
  			}
  		}
  		
  		}
  		logger.info("instance id: "+this.getInstanceid()+ " entropy is calculated : "+result);
  		return result;
  			
  	}
  	
  	
  	
  
  	
  	
  	// assign 0 to percentageOfLabel list in the beginning
  	public void createListForLabelPercentage( Integer numberOfLabel)
  	{
  		for(int i=0; i<numberOfLabel ;i++) {
  			this.percentageOfLabel.add(0);}
  	}
  	// calculating percentage of each labels into percentageOfLabel array
  	public void percentageOfLabels(Logger logger) {
  		if(this.totalNumberOfLabelAssignment!=0) {
  		Integer numberOfLabelled;
  		for(int i=0; i<this.percentageOfLabel.size() ; i++) {
  			numberOfLabelled = this.countOfLabel.get(i);
  	  		Integer percentage = (numberOfLabelled*100) / this.totalNumberOfLabelAssignment;
  	  		this.percentageOfLabel.set(i, percentage);
  		}	
  		}	
  		logger.info("instance id: "+this.getInstanceid()+ " class labels and percentages are calculated " );
  	}	
  	
  	//finding Max Frequent Label
  	public LABEL findMaxFrequentLabel(ArrayList<LABEL> allLabels,Logger logger) {
  		Integer index = this.countOfLabel.indexOf(Collections.max(this.countOfLabel));
  		logger.info("instance id: "+this.getInstanceid()+ "  most frequent class label  is calculated : " +allLabels.get(index) );
  		return allLabels.get(index);
  		
  		
  	}
  	
  	
    //finding Max Frequent Label percentage
  	public Integer percentageOfMaxLabel(Logger logger) {
  		if(this.totalNumberOfLabelAssignment!=0) {
  		   Integer numberOfLabelled=Collections.max(this.countOfLabel);
  		   Integer percentage = (numberOfLabelled*100) / this.totalNumberOfLabelAssignment;
  		   logger.info("instance id: "+this.getInstanceid()+ "  most frequent class label percentage is calculated " );
  		   return percentage;	
  		}
  		else {
  		
  			return 0;
  		}	
  	}
  	// adding unique user into uniqueUsers list 
  	public void checkUniqueUsers( USER user , Logger logger) {
  		if(this.uniqueUsers.size()==0) {
  			this.uniqueUsers.add(user);
  		}
  		else if(!this.uniqueUsers.contains(user)) {
  				this.uniqueUsers.add(user);
  		}
  		logger.info("instance id: "+this.getInstanceid()+ "  number of unique users is calculated " );
  	}
  	// calculating Total Number Of Unique Label
	public Integer calculateTotalNumberOfUniqueLabel( Logger logger) {
		
  		Integer count=0;
  		for(int i=0; i<this.countOfLabel.size(); i++)
  			if(this.countOfLabel.get(i)!=0)
  				count++;
  		logger.info("instance id: "+this.getInstanceid()+ " total number of unique label is calculated " );
  		return count;
  	}
	
	
  	//calculating Total Number Of Label Assignment
  	public void calculateTotalNumberOfLabelAssignment( Logger logger) {
  		logger.info("instance id: "+this.getInstanceid()+ " total number of label assigmnet is calculated : " + this.totalNumberOfLabelAssignment);
  		this.totalNumberOfLabelAssignment++;
  		
  	}
 // assign 0 to countOfLabel list in the beginning
  	public void createListForLabelCount( Integer numberOfLabel)
  	{
  		for(int i=0; i<numberOfLabel ;i++) {
     		countOfLabel.add(0);}
  	}
  	
  	// increase count of label
	public void setLabelCount( Integer id) {
		this.countOfLabel.set(id-1, this.countOfLabel.get(id-1)+1);
		
	}

	//adding label to labelInInstance
   public void addLabel(LABEL label) {
	   this.labelInInstance.add(label);
   }
   //find instances according to their ids
	public int find(ArrayList<LABEL>list,ArrayList<INSTANCE>listofinstance,Long instanceid) {
		for(int c=0;c<listofinstance.size();c++) {
			if(instanceid==listofinstance.get(c).id) {
				return c;
			}
		}
		return -1;
	}
  	//////////////////////////////////////////////////////////// GETTERS & SETTERS
   
	public ArrayList<USER> getUniqueUsers() {
		return uniqueUsers;
	}

	public void setUniqueUsers(ArrayList<USER> uniqueUsers) {
		this.uniqueUsers = uniqueUsers;
	}
   
   public Integer getTotalNumberOfLabelAssignment() {
		return totalNumberOfLabelAssignment;
	}

	public void setTotalNumberOfLabelAssignment(Integer totalNumberOfLabelAssignment) {
		this.totalNumberOfLabelAssignment = totalNumberOfLabelAssignment;
	}
    
	public ArrayList<LABEL> getLabelInInstance() {
		return labelInInstance;
	}

	public void setLabelInInstance(ArrayList<LABEL> labelInInstance) {
		this.labelInInstance = labelInInstance;
	}

	public ArrayList<Integer> getCountOfLabel() {
		return countOfLabel;
	}

	public void setCountOfLabel(ArrayList<Integer> countOfLabel) {
		this.countOfLabel = countOfLabel;
	}
	

	public String getText() {
		return instance;
	}

	public void setText(String text) {
		this.instance = text;
	}

	public long getInstanceid() {
		return id;
	}

	public void setInstanceid(long id) {
		this.id = id;
	}


	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public ArrayList<LABEL> getLabels() {
		return labelInInstance;
	}

	public void setLabels(ArrayList<LABEL> labelInInstance) {
		this.labelInInstance = labelInInstance;
	}

	public ArrayList<Integer> getPercentageOfLabel() {
		return percentageOfLabel;
	}

	public void setPercentageOfLabel(ArrayList<Integer> percentageOfLabel) {
		this.percentageOfLabel = percentageOfLabel;
	}
    
	

}
