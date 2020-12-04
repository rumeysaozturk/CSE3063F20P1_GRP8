

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;

public class LABEL_ASSIGNMENT {
      private long instanceID;
	  private long  userID;
      private ArrayList<LABEL> labelID=new ArrayList<>();
      private String datetime ;
      
	public LABEL_ASSIGNMENT() {
	}
		// TODO Auto-generated constructor stub
	
	
	public LABEL_ASSIGNMENT (long userID, long inst,ArrayList<LABEL> labelID) {
		this.setUserıd(userID);
		this.setInstanceıd(inst);
		this.setLabelIndex(labelID);
		Date date = new Date();
		SimpleDateFormat dateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String stringDate = dateFor.format(date);
	    this.datetime = stringDate;
	}
	
      //Sort Labels
	  public void sortLabels() {
		   int l=0; int m=0; LABEL temp;
		   for(l=0;l<this.getLabelIndex().size();l++) {
	    		for(m=0; m <this.getLabelIndex().size();m++) {
	    		 if(this.getLabelIndex().get(l).getLabelid() < this.getLabelIndex().get(m).getLabelid()) {      //swap elements if not in order
	                 temp = this.getLabelIndex().get(l);   
	                 this.getLabelIndex().set(l,this.getLabelIndex().get(m));
	                 this.getLabelIndex().set(m,temp);    
	               } 
	    		}    		
	    	  }
	   }
	
	 //Prints actions
	  public void print() {
		   
		   System.out.print("instance id:" + this.instanceID + ", class labels ids:[" );
		      

	          int n=0;
	          for(n=0; n<this.labelID.size(); n++) {
	        	  if(n==this.labelID.size()-1) {
	        		  System.out.print(this.labelID.get(n).getLabelid()+"],");
	        	  }
	        	  else {
	        		  System.out.print(this.labelID.get(n).getLabelid()+",");
	        	  }
	          }
	          System.out.print(" user :" +this.userID+", ");
	          System.out.println(" datetime :" +this.getDatetime()+" is created.");

		   
	   }
	

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public long getInstanceID() {
		return instanceID;
	}


	public void setInstanceID(long instanceID) {
		this.instanceID = instanceID;
	}


	public ArrayList<LABEL> getLabelIndex() {
		return labelID;
	}


	public void setLabelIndex(ArrayList<LABEL> labelID) {
		this.labelID = labelID;
	}


	public long getUserıd() {
		return userID;
	}


	public void setUserıd(long userID) {
		this.userID = userID;
	}


	public long getInstanceıd() {
		return instanceID;
	}


	public void setInstanceıd(long instanceID) {
		this.instanceID = instanceID;
	}
	
}
