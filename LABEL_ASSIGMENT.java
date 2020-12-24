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
