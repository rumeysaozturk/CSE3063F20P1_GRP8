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
	  private long instanceid
	  private long labelid
      private INSTANCE �nstance;
	  private USER user;
      private LABEL labels;
      private String datetime ;
      
	public LABEL_ASSIGNMENT() {
	}
 
		// TODO Auto-generated constructor stub
	
	public LABEL_ASSIGNMENT (long instanceid, long labelid, USER user, INSTANCE instance,LABEL labels) {
		this.setInstanceid(instanceid);
		this.setLabelid(labelid);
		this.setUser(user);
		this.setInstance(instance);
		this.setLabels(labels);
		Date date = new Date();
		SimpleDateFormat dateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String stringDate = dateFor.format(date);
	    this.datetime = stringDate;
	}
	
 
	//PRINT FONKSY�ONU YAZ, logger ile bildirim yaps�n.Instance id ve label id bast�rs�n.
	
	public void printIds(Logger logger) {
		logger.info("instance id: "+this.getInstanceid()+ " label id: "+this.getLabelid());
	}
	
	public long getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(long instanceid) {
		this.instanceid = instanceid;
	}
	
	public long getLabelid() {
		return labelid;
	}
	public void setLabelid(long labelid) {
		this.labelid = labelid;
	}
	
	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public LABEL getLabels() {
		return labels;
	}

	public void setLabels(LABEL labels) {
		this.labels = labels;
	}



	public INSTANCE getInstance() {
		return �nstance;
	}



	public void setInstance(INSTANCE instance) {
		�nstance = instance;
	}



	public USER getUser() {
		return user;
	}



	public void setUser(USER user) {
		this.user = user;
	}
	
}
