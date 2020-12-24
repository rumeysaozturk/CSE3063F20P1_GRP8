import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PRINT {

	public PRINT(){		
	}

	
	//BACK UP THE PREVIOUS DATASET INFORMATIONS 
	public void backup(ArrayList<INSTANCE> instance,Long datasetId) {
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("dataset id", 1);
		int i=0; int j=0; int index=0;
		
		//COUNTOFLABEL
		JSONArray countArray = new JSONArray();
		for(i=0; i<instance.size(); i++) {
			
			countArray.add(instance.get(i).getCountOfLabel());
		}
		
		//UNIQUE USER
		JSONArray uniqueArray = new JSONArray();
		ArrayList<Integer> uniqueUserList=new ArrayList<>();
		String convert="";
		for(i=0; i<instance.size(); i++) {
			uniqueUserList=new ArrayList<>();
			for(j=0; j<instance.get(i).getUniqueUsers().size(); j++) {
				convert=String.valueOf(instance.get(i).getUniqueUsers().get(j).getUserId());
				uniqueUserList.add(j,Integer.valueOf(convert));
			}
			uniqueArray.add(uniqueUserList);	
		}
		
		//TOTAL LABEL ASSIGNMENT
		JSONArray totalArray= new JSONArray();
		for(i=0; i<instance.size(); i++) {
			totalArray.add(instance.get(i).getTotalNumberOfLabelAssignment());
		}
		
		jsonObject.put("CountOfLabel", countArray);
		jsonObject.put("UniqueArray", uniqueArray);
		jsonObject.put("TotalNumberOfAssignment", totalArray);
		
		try {
	        FileWriter file = new FileWriter("Secret/"+datasetId+".json");
	        file.write(jsonObject.toJSONString());
	        file.close();
	     } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	     }
	     System.out.println("JSON file created: "+jsonObject);
	}
}
