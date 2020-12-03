
public class INSTANCE {
      private long instanceid;
      private String text;
      
	public INSTANCE() {
		// TODO Auto-generated constructor stub
	}
	
	public INSTANCE(long instanceid,String text) {
		this.setInstanceid(instanceid);
		this.text=text;
	}



	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(long instanceid) {
		this.instanceid = instanceid;
	}

}
