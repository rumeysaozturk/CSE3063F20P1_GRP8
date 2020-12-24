import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.Calendar;
import java.util.ArrayList;

public class LABEL_ASSIGNMENT {
      private long instanceId;
	  private long  userId;
      private LABEL labels;
      private String datetime ;
      
	public LABEL_ASSIGNMENT() {
	}
    
     

		// TODO Auto-generated constructor stub
	
	public LABEL_ASSIGNMENT (long userId, long inst,LABEL labels) {
		this.setUserId(userId);
		this.setInstanceId(inst);
		this.setLabels(labels);
		Date date = new Date();
		SimpleDateFormat dateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String stringDate = dateFor.format(date);
	    this.datetime = stringDate;
	}
	
    /*  //Sort Labels
  public void sortLabels() {
		   int l=0; int m=0; LABEL temp;
		   for(l=0;l<this.getLabels().size();l++) {
	    		for(m=0; m <this.getLabels().size();m++) {
	    		 if(this.getLabels().get(l).getLabelid() < this.getLabels().get(m).getLabelid()) {      //swap elements if not in order
	                 temp = this.getLabels().get(l);   
	                 this.getLabels().set(l,this.getLabels().get(m));
	                 this.getLabels().set(m,temp);    
	               } 
	    		}    		
	    	  }
	   }
	*/
	 //Prints actions
	 /* public void print(Logger logger) {
		   String message="";
		   message="instance id:" + this.instanceId + ", class labels ids:[" ;
		      
	          int n=0;
	          for(n=0; n<this.labels.size(); n++) {
	        	  if(n==this.labels.size()-1) {
	        		  message+=this.labels.get(n).getLabelid()+"],";
	        	  }
	        	  else {
	        		  message+=this.labels.get(n).getLabelid()+",";
	        	  }
	          }
	          message+=" user:" +this.userId+",";
	          message+=" datetime:" +this.getDatetime()+" is created.";
	          logger.info(message);
	   }*/
	

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	



	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public long getInstanceId() {
		return instanceId;
	}


	public void setInstanceId(long instanceId) {
		this.instanceId = instanceId;
	}



	public LABEL getLabels() {
		return labels;
	}



	public void setLabels(LABEL labels) {
		this.labels = labels;
	}
	
	
}
