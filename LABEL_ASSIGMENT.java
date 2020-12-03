import java.time.LocalDateTime;
import java.util.ArrayList;

public class LABEL_ASSIGMENT {
       private  USER user;
       private INSTANCE ýnstance;
       private ArrayList<LABEL>labels=new ArrayList<>();
       LocalDateTime datetime ;
       
	public LABEL_ASSIGMENT() {
		// TODO Auto-generated constructor stub
	}
	
	public LABEL_ASSIGMENT(USER user,INSTANCE ýnst,LABEL label[]) {
		this.user=user;
		this.ýnstance=ýnst;
		datetime =LocalDateTime.now();
	
		
		for(int i=0;i<label.length;i++) {
			this.labels.add(label[i]);
		}
	}

	public USER getUser() {
		return user;
	}

	public void setUser(USER user) {
		this.user = user;
	}

	public INSTANCE getInstance() {
		return ýnstance;
	}

	public void setInstance(INSTANCE instance) {
		ýnstance = instance;
	}

	public ArrayList<LABEL> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<LABEL> labels) {
		this.labels = labels;
	}

}
