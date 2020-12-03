

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;


public class LABEL_ASSIGNMENT {
       private USER user;
       private INSTANCE instance;
       private ArrayList<LABEL>labels=new ArrayList<>();
       private String datetime ;
       
  
	public LABEL_ASSIGNMENT() {
	}
	
	public LABEL_ASSIGNMENT(USER user, INSTANCE inst,ArrayList<LABEL> labels) {
		this.user = user;
		this.instance=inst;
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

	public USER getUser() {
		return user;
	}

	public void setUser(USER user) {
		this.user = user;
	}

	public INSTANCE getInstance() {
		return instance;
	}

	public void setInstance(INSTANCE instance) {
		this.instance = instance;
	}

	public ArrayList<LABEL> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<LABEL> labels) {
		this.labels = labels;
	}
	
}
