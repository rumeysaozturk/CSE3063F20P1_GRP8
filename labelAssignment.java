import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.Calendar;
import java.util.ArrayList;

public class labelAssignment {
	  private long instanceid
	  private long labelid
      private Instance ınstance;
	  private User user;
      private Label labels;
      private String datetime ;
      
	public labelAssignment () {
	}
 
		// TODO Auto-generated constructor stub
	
	public labelAssignment (long instanceid, long labelid, User user, Instance instance,Label labels) {
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
	
 
	//PRINT FONKSYİONU YAZ, logger ile bildirim yapsın.Instance id ve label id bastırsın.
	
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

	public Label getLabels() {
		return labels;
	}

	public void setLabels(Label labels) {
		this.labels = labels;
	}



	public Instance getInstance() {
		return ınstance;
	}



	public void setInstance(Instance instance) {
		ınstance = instance;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}
	
}
