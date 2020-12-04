


import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;



public class LABEL_ASSIGNMENT {
      private long instanceıd;
	  private long  userıd;
     
      private ArrayList<LABEL>labels=new ArrayList<>();
      private String datetime ;
      
      
      
	public LABEL_ASSIGNMENT() {
	}
		// TODO Auto-generated constructor stub
	
	
	public LABEL_ASSIGNMENT (long userıd, long inst,ArrayList<LABEL> labels) {
		this.setUserıd(userıd);
		this.setInstanceıd(inst);
		this.labels=labels;
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


	public ArrayList<LABEL> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<LABEL> labels) {
		this.labels = labels;
	}


	public long getUserıd() {
		return userıd;
	}


	public void setUserıd(long userıd) {
		this.userıd = userıd;
	}


	public long getInstanceıd() {
		return instanceıd;
	}


	public void setInstanceıd(long instanceıd) {
		this.instanceıd = instanceıd;
	}
	
}




