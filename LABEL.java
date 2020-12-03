
public class LABEL {
      private long labelid;
      private String text;
	public LABEL() {
		// TODO Auto-generated constructor stub
	}
   public LABEL(long labelid , String text) {
	   this.setLabelid(labelid);
	   this.setText(text);
   }

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
}
