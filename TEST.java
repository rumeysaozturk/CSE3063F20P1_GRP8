import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PRINT {

	public PRINT(){		
	}
	
	public void printDatasetMetrics(ArrayList <INSTANCE> instance,ArrayList <LABEL> listOfLabel,Long datasetId,Long numOfUser,Logger logger) {
		int i=0; int j=0; int isLabeled=0; double percentage=0.0; int temp=0; String convert="";
	    int labelArray[]= new int[listOfLabel.size()];
	      try {
	         FileWriter file = new FileWriter("dataset"+datasetId+"Metrics"+".json");
	         file.write("{\n");
	         file.write("\"dataset id \":"+datasetId+"\n");
	         //STEP 1
	         file.write("{\"Completeness percentage\": %");
	         for(i=0; i<instance.size(); i++) {
	        	 if(instance.get(i).getTotalNumberOfLabelAssignment()!=0) {
	        		 isLabeled++;
	        	 }
	         }
	         file.write(""+((double)(isLabeled)/(double)instance.size())*100 +"}");
	         
	         //STEP 2
	         for(i=0; i<instance.size(); i++) {
	        	 convert=String.valueOf(instance.get(i).findMaxFrequentLabel(listOfLabel).getLabelid());
	        	 temp=Integer.valueOf(convert);
	        	 labelArray[temp-1]=labelArray[temp-1]+1;
	         }
	         file.write("\n{\"Class distribution based on final instance labels\": {");
	         for(i=0; i<listOfLabel.size(); i++) {
	        	 file.write("[%"+((double)labelArray[i]/(double)instance.size())*100+","+listOfLabel.get(i).getText()+"]");
	        	 if(i!=listOfLabel.size()-1) {
	        		 file.write(",");
	        	 }
	         }
	         file.write("}}");
	         
	         //STEP 3
	         file.write("\n{\"List number of unique instances for each class label \": {");
	         for(i=0; i<listOfLabel.size(); i++) {
	        	 file.write("["+listOfLabel.get(i).getText()+","+listOfLabel.get(i).getUniqueInstance().size()+"]");
	        	 if(i!=listOfLabel.size()-1) {
	        	 file.write(",");
	        	 }
	         }
	         file.write("}}");
	         
	         //STEP 4
	         file.write("\n{\"Number of users\": "+numOfUser + "}");
	         
	         //STEP 5
	         
	         
	         
	         file.write("\n}");
             file.flush();
	         file.close();
	         //logger.info("Instance metrics is written sucesfully.");
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
		
	}
	
	
	//**********************PRINT INSTANCE METRICS
    public void printInstanceMetrics(ArrayList <INSTANCE> instance,ArrayList <LABEL> listOfLabel,Long datasetId,Logger logger) {
	     
       	int i=0; int j=0;
    
	      try {
	         FileWriter file = new FileWriter("instanceMetricsForDataset"+datasetId+".json");
	         file.write("{\n");
	         file.write("Dataset id :"+1+"\n");
	         file.write("\"instances\":[");
	         for(i=0; i<instance.size(); i++){
	        	 file.write("\n{Instance id :"+ instance.get(i).getInstanceid()+" Total Number Of Label Assignment :" + instance.get(i).getTotalNumberOfLabelAssignment()
	        			 +", \"Number of unique label assignments\":" + instance.get(i).calculateTotalNumberOfUniqueLabel()+", \"Number of unique users\":"+instance.get(i).getUniqueUsers().size()
	        			 +", \"Most frequent class label and percentage\":[\""+instance.get(i).findMaxFrequentLabel(listOfLabel).getText()+"\",\"%"+instance.get(i).percentageOfMaxLabel()+"]"
	        			 +", \"List class labels and percentages\":[");
	        	 for(j=0; j<listOfLabel.size(); j++) {
	        	 file.write("[\""+listOfLabel.get(j).getText()+"\",\"%"+instance.get(i).getPercentageOfLabel().get(j)+"]");
	        	 if(j!=listOfLabel.size()-1) {
	        	 file.write(",");
	        	 }
	        	 }
	        	 file.write("], \"Entropy\":"+instance.get(i).entropy()+"}");
	        	 if(i!=instance.size()-1) {
	        		 file.write(",");
	        	 }
	         }
             file.flush();
	         file.close();
	         //logger.info("Instance metrics is written sucesfully.");
	      } catch (IOException e) {
	         e.printStackTrace();
	      }

    }

	//*****************BACK UP THE PREVIOUS DATASET INFORMATIONS 
	public void backup(ArrayList<INSTANCE> instance,ArrayList<LABEL> listOfLabel,Long datasetId) {
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
		
		//UNIQUE INSTANCE FOR EACH LABEL
		JSONArray labelArray = new JSONArray();
		for(i=0; i<listOfLabel.size(); i++) {
			labelArray.add(listOfLabel.get(i).getUniqueInstance());
		}
		
		jsonObject.put("CountOfLabel", countArray);
		jsonObject.put("UniqueArray", uniqueArray);
		jsonObject.put("TotalNumberOfAssignment", totalArray);
		jsonObject.put("LabelArray", labelArray);
		
		try {
	        FileWriter file = new FileWriter("Secret/"+datasetId+"instancemetrics.json");
	        file.write(jsonObject.toJSONString());
	        file.close();
	     } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	     }
	     System.out.println("JSON file created: "+jsonObject);
	}
}
