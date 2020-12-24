import java.lang.Integer;
import java.util.ArrayList;

import org.json.simple.JSONArray;
public class DATASET {
  private Long id;
  private Long max_label;
  private String name;
  private  JSONArray labels;
  private JSONArray instances;
  private Long numofuser;
  private double completeness;
	public DATASET() {
		// TODO Auto-generated constructor stub
	}
public DATASET(long id,long max_label,String name ,JSONArray label,JSONArray Instance,Long numberofuser) {
	setId(id);
	setMax_label(max_label);
	setName(name);
	setLabels(label);
	setInstances(Instance);
	setNumofuser(numberofuser);
}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public JSONArray getLabels() {
		return labels;
	}
	public void setLabels(JSONArray labels) {
		this.labels = labels;
	}
	public JSONArray getInstances() {
		return instances;
	}
	public void setInstances(JSONArray instance) {
		this.instances = instance;
	}
	public long getMax_label() {
		return max_label;
	}
	public void setMax_label(long max_label) {
		this.max_label = max_label;
	}
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNumofuser() {
		return numofuser;
	}
	public void setNumofuser(Long numofuser) {
		this.numofuser = numofuser;
	}
	public double getCompleteness() {
		return completeness;
	}
	public void setCompleteness(double completeness) {
		this.completeness = completeness;
	}
	
}
