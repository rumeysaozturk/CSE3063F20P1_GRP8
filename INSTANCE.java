
public class INSTANCE {
      private long id;
      private String instance;
      
	public INSTANCE() {
		// TODO Auto-generated constructor stub
	}
	
	public INSTANCE(long id,String text) {
		this.setInstanceid(id);
		this.instance=text;
	}

	public String getText() {
		return instance;
	}

	public void setText(String text) {
		this.instance = text;
	}

	public long getInstanceid() {
		return id;
	}

	public void setInstanceid(long id) {
		this.id = id;
	}

}
