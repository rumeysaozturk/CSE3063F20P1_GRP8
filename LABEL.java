import java.util.ArrayList;

public class LABEL {
      private long labelid;
      private String text;
      ArrayList<Integer> uniqueInstance = new ArrayList<>();
	public LABEL() {
		// TODO Auto-generated constructor stub
	}
   public LABEL(long labelid , String text) {
	   this.setLabelid(labelid);
	   this.setText(text);
   }
   
//check the unique instance
public void checkUniqueInstance(INSTANCE instance) {
	if(!this.uniqueInstance.contains(Integer.valueOf(String.valueOf(instance.getInstanceid())))) {
		this.uniqueInstance.add(Integer.valueOf(String.valueOf(instance.getInstanceid())));
	}
}

public int find(ArrayList<LABEL>listoflabel,long labelid) {
	for(int c=0;c<listoflabel.size();c++) {
		if(labelid==listoflabel.get(c).labelid) {
			return c;
		}
	}
	return -1;
}

//****************************GETTER SETTERS************************
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public long getLabelid() {
	return labelid;
}
public void setLabelid(long labelid) {
	this.labelid = labelid;
}
public ArrayList<Integer> getUniqueInstance() {
	return uniqueInstance;
}
public void setUniqueInstance(ArrayList<Integer> uniqueInstance) {
	this.uniqueInstance = uniqueInstance;
}


}
