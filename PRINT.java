import java.io.FileWriter;


import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;



import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
public class PRINT {

	public PRINT(){		
	}
	
	public void printDatasetMetrics(ArrayList <INSTANCE> instance,ArrayList <LABEL> listOfLabel,Long datasetId,Long numOfUser,Logger logger,ArrayList<USER> currentUser,DATASET dataset) {
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
	         dataset.setCompleteness(((double)(isLabeled)/(double)instance.size())*100);
	         logger.info("dataset id: " +datasetId + " Completeness percentage is calculated : " + ((double)(isLabeled)/(double)instance.size())*100 );
	         
	         //STEP 2
	         for(i=0; i<instance.size(); i++) {
	        	 convert=String.valueOf(instance.get(i).findMaxFrequentLabel(listOfLabel,logger).getLabelid());
	        	 temp=Integer.valueOf(convert);
	        	 labelArray[temp-1]=labelArray[temp-1]+1;
	         }
	         file.write("\n{\"Class distribution based on final instance labels\": {");
	         for(i=0; i<listOfLabel.size(); i++) {
	        	 
	        	 if(i==0) {
	        		String strDouble = String.format("%.2f", (((double)(labelArray[i]-(instance.size()-isLabeled))/(double)isLabeled))*100);
	        		file.write("[%"+strDouble+","+listOfLabel.get(i).getText()+"]");
	        	 }
	        	 else {
	        		 String strDouble = String.format("%.2f", ((double)labelArray[i]/(double)isLabeled)*100);
	        		 file.write("[%"+strDouble+","+listOfLabel.get(i).getText()+"]");
	        	 }
	        	 
	        	 if(i!=listOfLabel.size()-1) {
	        		 file.write(",");
	        	 }
	        	 
	        	 logger.info("dataset id: " +datasetId + " Class distribution is calculated" );
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
	         logger.info("dataset id: " +datasetId + " Unique instance for each label is calculated" );
	         file.write("}}");
	         
	         //STEP 4
	         file.write("\n{\"Number of users\": "+numOfUser + "}");
	         logger.info("dataset id: " +datasetId + " Number of user is calculated : "+ numOfUser );
	         
	         //STEP 5
	         file.write("\n{\"users\":[");
	         for(int k=0;k<currentUser.size();k++) {
	        	 String strDouble = String.format("%.2f", currentUser.get(k).getConsistency());
	        	 file.write("{\"user id\":"+currentUser.get(k).getUserId()+",\"completeness percantage\":"+((double)currentUser.get(k).getLabeled().size()/instance.size())*100+
	        			 ",\"consistency\":"+strDouble+"}\n");
	         }
	         logger.info("dataset id: " +datasetId + " Users assigned and their completeness percentage is calculated " );
	         logger.info("dataset id: " +datasetId + " Users assigned and their completeness consistency is calculated " );
	         file.write("]}\n");
	         
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
	        			 +", \"Number of unique label assignments\":" + instance.get(i).calculateTotalNumberOfUniqueLabel(logger)+", \"Number of unique users\":"+instance.get(i).getUniqueUsers().size()
	        			 +", \"Most frequent class label and percentage\":[\""+instance.get(i).findMaxFrequentLabel(listOfLabel,logger).getText()+"\",\"%"+instance.get(i).percentageOfMaxLabel(logger)+"]"
	        			 +", \"List class labels and percentages\":[");
	        	 for(j=0; j<listOfLabel.size(); j++) {
	        	 file.write("[\""+listOfLabel.get(j).getText()+"\",\"%"+instance.get(i).getPercentageOfLabel().get(j)+"]");
	        	 if(j!=listOfLabel.size()-1) {
	        	 file.write(",");
	        	 }
	        	 }
	        	 file.write("], \"Entropy\":"+instance.get(i).entropy(logger)+"}");
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
	
	public void printusermetrics(FileWriter file1,ArrayList<USER>listofuser,Logger logger) throws IOException {
		
		
		  file1.write("{");
	      file1.write("\"users\":[");
		  for(int c=0;c<listofuser.size();c++) {
			 file1.write("{\"userId\":"+listofuser.get(c).getUserId()+","+"\"totalNumberofInstance\":"+listofuser.get(c).getTotalNumberofınstance()+", \"Number of unique instance\":"+
		     listofuser.get(c).getNumofuniqueins()+",\"consistency\":"+ listofuser.get(c).getCheckconsistency()+","+
		     "\"consistency probality\":"+ listofuser.get(c).getConsistency()+",");
			 
			 
			 file1.write("\"number of dataset\":"+listofuser.get(c).getDataset().size()+",");
			 logger.info("user id: "+listofuser.get(c).getUserId()+" Number of dataset is calculated : "+listofuser.get(c).getDataset().size());
			  file1.write("\"datasets\":[");
			 	 for(int x=0;x<listofuser.get(c).getDataset().size();x++) {
			 	      file1.write("{\"dataset id\":"+listofuser.get(c).getDataset().get(x).getId()+","+"\"completeness\":"+listofuser.get(c).getDataset().get(x).getCompleteness()+"}");
			 	      logger.info("user id: "+listofuser.get(c).getUserId()+" Dataset id:"+listofuser.get(c).getDataset().get(x).getId()+"dataset completeness is calculated : "+listofuser.get(c).getDataset().get(x).getCompleteness());
			 	 }
			 
			 	   	file1.write("],");
			 	   file1.write("\"average time\":"+(listofuser.get(c).average()+listofuser.get(c).getAverage())+",\"standart deviation\":"+listofuser.get(c).stdeviation()+"}\n");
			  
			logger.info("user id: "+listofuser.get(c).getUserId()+" Number of dataset is calculated : "+listofuser.get(c).getDataset().size());
			logger.info("user id: "+listofuser.get(c).getUserId()+" Total number of Instance is calculated: "+listofuser.get(c).getTotalNumberofınstance());
			logger.info("user id: "+listofuser.get(c).getUserId()+" Number of unique instance is calculated: "+ listofuser.get(c).getNumofuniqueins());
			
			logger.info("user id: "+listofuser.get(c).getUserId()+" Consistency percentage is calculated: "+listofuser.get(c).getCheckconsistency());
			logger.info("user id: "+listofuser.get(c).getUserId()+" Average time is calculated : "+(listofuser.get(c).average()+listofuser.get(c).getAverage()));
			logger.info("user id: "+listofuser.get(c).getUserId()+" Standart deviation is calculated : "+listofuser.get(c).stdeviation());
		  }
	    
	    //Print Users to json file
	 
	   // file1.write("]\n");
	    file1.write("]\n}\n");
	   
	    file1.flush();
	    file1.close();
	}

public   void printuserarray(FileWriter file2,ArrayList<USER>listofuser) throws IOException {
	 file2.write("{");
    file2.write("\"users\":[");
 
    for (int c=0;c<listofuser.size();c++) {
   	file2.write("{\"user id\":"+listofuser.get(c).getUserId()+",");
   	file2.write("\"labeled\":[");
   	 for(int i=0;i<listofuser.get(c).getLabeled().size();i++) {
   	           file2.write("{ \"instance id \" :"+listofuser.get(c).getLabeled().get(i).getInstanceid()+"},");
   	 }
   	 file2.write("],");
   	 file2.write("\"labeled2\":[");
   	 for(int x=0;x<listofuser.get(c).getLabeled2().size();x++) {
      file2.write("{\"instance id\":"+listofuser.get(c).getLabeled2().get(x).getInstanceId()+",\"label id\":"+listofuser.get(c).getLabeled2().get(x).getLabels().getLabelid()+"},");
   	 }
   	file2.write("]}\n");
  
    }
    file2.write("] \n}");
    
    file2.flush();
    file2.close();
}
	
}
