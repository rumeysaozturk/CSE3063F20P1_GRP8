import java.lang.Integer;
import java.util.ArrayList;

import org.json.simple.JSONArray;
public class DATASET {
  private Long id;
  private Long max_label;
  private String name;
  private  JSONArray labels;
  private JSONArray ınstances;
  
	public DATASET() {
		// TODO Auto-generated constructor stub
	}
public DATASET(long id,long max_label,String name ,JSONArray label,JSONArray Instance) {
	setId(id);
	setMax_label(max_label);
	setName(name);
	setLabels(label);
	setInstances(Instance);
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
		return ınstances;
	}
	public void setInstances(JSONArray ınstance) {
		this.ınstances = ınstance;
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


	
	
}
