import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TEST {

	   public static void main(String args[]) throws FileNotFoundException, IOException, ParseException, SecurityException {
		    Logger logger = Logger.getLogger("MyLog");
	        FileHandler fileHandler;
		   try {
	             
	            // This block configure the logger with handler and formatter
			    fileHandler = new FileHandler("tracing.log");
	            logger.addHandler(fileHandler);
	            //logger.setLevel(Level.ALL);
	            SimpleFormatter formatter = new SimpleFormatter();
	            fileHandler.setFormatter(formatter);
	       
	             
	        } catch (SecurityException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	     
		 
		   
		  ArrayList<LABEL>listOfLabel=new ArrayList<>();
		  ArrayList<INSTANCE>listOfInstance=new ArrayList<>();
	      JSONParser jsonParser = new JSONParser();
	      long maxLabel=0;
	      
	      JSONObject jsonUser = (JSONObject) jsonParser.parse(new FileReader("configuration.json"));
	      JSONArray jsonArraydataset = (JSONArray)(jsonUser.get("datasets"));
	      ArrayList<DATASET>dataset1=new ArrayList<>();
	      createDatasets(jsonArraydataset,dataset1);
	    
	         //Parsing the contents of the JSON file
	         //Forming URL
	      /*burası filedan okuncak*/
	    DATASET current=dataset1.get(0);
	         logger.info("Input file is read succesfully.\n");
	         logger.info("Dataset id: "+ current.getId()+ " is created");
	         logger.info("Dataset name: "+current.getName()+ " is created");
	         logger.info("maximum number of labels per instance: "+current.getMax_label()+ " is created\n");
	         //Take the maximum labels for each instances
	         maxLabel =current.getMax_label();
	        Long nuser=current.getNumofuser();
	  
	         //Retrieving the array
	         JSONArray jsonArray = current.getLabels();
	         
	        
	         //Iterating the contents of the array
	      
	        
	         JSONArray jsonArray1 =current.getInstances();
	       
	        
	         //Iterating the contents of the array
	    
	         JSONArray jsonArrayUser = (JSONArray)(jsonUser.get("users"));
	        	      
	      for (int i=0;i<jsonArray.size();i++) {
	    	  JSONObject a=(JSONObject) jsonArray.get(i);
		        long id =(long) a.get("label id");
		        String text=(String) a.get("label text");
		        LABEL a1=new LABEL(id,text);
		        listOfLabel.add(a1);
		        
	      }
    
	      for (int i=0;i<jsonArray1.size();i++) {
	    	  JSONObject a2=(JSONObject) jsonArray1.get(i);
		        long id1 =(long) a2.get("id");
		        String text2=(String) a2.get("instance");
		       INSTANCE a3=new INSTANCE(id1,text2);
		        listOfInstance.add(a3);
		        
	      }    
	      
	      //GENERATE A USER****************************************************************
		  ArrayList <USER> listOfUser =new ArrayList<USER>();
		 
	
	      int lengthUser=jsonArrayUser.size();
	      USER user;
	      String text2="";
	      String text3="";
	      for(int i=0; i<jsonArrayUser.size(); i++) {
	    	  JSONObject a2=(JSONObject) jsonArrayUser.get(i);
		        long id1 =(long) a2.get("user id");
		        text2=(String) a2.get("user name");
		        text3=(String) a2.get("user type");
		        Double ConsistencyCheckProbability=(double)a2.get("ConsistencyCheckProbability");
		        user = new USER(id1,text2,text3,ConsistencyCheckProbability);
	    	    listOfUser.add(user);
	    	    user.printUser(logger);
	    	   
	      }
	      System.out.println();
          
	        //**********************RANDOM ASSIGNMENT************************************************
	      ArrayList<USER>currentusers=new ArrayList<USER>();
	      int i=0;
	    	 int rand=(int)((Math.random())*(listOfUser.size()));
	    	
	         USER cuser1=listOfUser.get(rand);
	         currentusers.add(cuser1) ;
	    while(true) {
	   	 int random=(int)(Math.random()*(listOfUser.size()));
	     USER cuser=listOfUser.get(random);
	     if(find(currentusers,cuser)) {
	    	 continue;
	     }
	     else {
	   	  currentusers.add(cuser) ;
	     }
	if(currentusers.size()==nuser) {
		break;
	}}
	 for(int l=0;l<currentusers.size();l++)  	{
	int  random=(int)(Math.random()*(listOfInstance.size()));
	int random1=(int)(Math.random()*(listOfLabel.size()));
	System.out.println(random+" ndndndnd");
	LABEL_ASSIGNMENT one=new LABEL_ASSIGNMENT(currentusers.get(l).getUserId(),listOfInstance.get(random).getInstanceid(),listOfLabel.get(random1));
	 System.out.println(one.getUserId()+" "+one.getInstanceId()+" "+one.getLabels().getText()+" "+one.getDatetime());
	(currentusers.get(l)).addInstance((listOfInstance.get(random)));
	 }
	  i=0;
	 while(i<150) {
		    int randomuser=(int)(Math.random()*(currentusers.size()));
		     USER currentu=currentusers.get(randomuser);
		     int checkprob=(int)(Math.random()*(100));
		     if(checkprob<currentu.getConsistencyCheckProbability()*100) {
		    	 int  random=(int)(Math.random()*(currentu.getLabeled().size()));
		    	
		 		int random1=(int)(Math.random()*(listOfLabel.size()));
		 	
		 		LABEL_ASSIGNMENT two=new LABEL_ASSIGNMENT(currentu.getUserId(),currentu.getLabeled().get(random).getInstanceid(),listOfLabel.get(random1));
		 		 System.out.println(two.getUserId()+" "+two.getInstanceId()+" "+two.getLabels().getText()+" "+two.getDatetime());
		 		
		    	 
		     }
		     else {
		
			
			int random1=(int)(Math.random()*(listOfLabel.size()));
			LABEL currentlabel=listOfLabel.get(random1);
			while(true) {
			
				int  random=(int)(Math.random()*(listOfInstance.size()));
				INSTANCE cınstance=listOfInstance.get(random);
			 if(currentu.getLabeled().contains(cınstance)) {
				 if(currentu.getLabeled().size()==listOfInstance.size()){
			    	break;
			     } 
				
				 continue;}
			     else {
			    	 LABEL_ASSIGNMENT two=new LABEL_ASSIGNMENT(currentu.getUserId(),cınstance.getInstanceid(),currentlabel);
			 		 System.out.println(two.getUserId()+" "+two.getInstanceId()+" "+two.getLabels().getText()+" "+two.getDatetime());
			 		 currentu.getLabeled().add(cınstance);
			   break;  }
			
				
			
		
		     }
			
		     }
		     System.out.println(i);i++;}
	 
	   
	  
	   /*	 
	     
	     Gson gson=new Gson();
	     String json2="";
	 
	    
	      try {
	         FileWriter file = new FileWriter("output.json");
	         //file.write(gsonInput.toJson(jsonObject));
	         file.write("{\n");
	         file.write("\"dataset id\":"+current.getId()+",\n"+"\"dataset name\":"
	    	         +current.getName()+",\n" + "\"maximum number of labels per instance\":"
	        		 +current.getMax_label()+",\n");
	    	  
	         //Print labels to json file
	         file.write("\"class labels\":"+(current.getLabels().toString())+" \n");
	         
	         
	         file.write("\"instances\":[\n");
	         //Print Instances to json file
	        /* for(int c=0;c<listOfInstance.size();c++) {
	    	     json2=gson.toJson(listOfInstance.get(c));
	    	     file.write(json2+",\n");
	         }*/
	         
	     /*  file.write(gson.toJson(listOfInstance));
	         file.write("],\n");
	         
	         file.write("\"class label assignments\":[\n");
	         //Print Assignment to json file
	         for(int c=0;c<listOfLabelAssignment.size();c++) {

	    	     json2=gson.toJson(listOfLabelAssignment.get(c));
	    	     file.write(json2+",\n");
	         }
	         file.write("],\n");
	         
	         file.write("\"users" +":[\n");
	         
	         
	         //Print Users to json file
	         for(int c=0;c<listOfUser.size();c++) {

	    	     json2=gson.toJson(listOfUser.get(c));
	    	     file.write(json2+",\n");
	         }
	         file.write("]\n");
	         file.write("{\n");
	        
	         file.flush();
	         file.close();
	         logger.info("Output is written sucesfully.");
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }*/
	    
}
	   

	   
public static void createDatasets(JSONArray jsonArraydataset,ArrayList<DATASET>datasets) throws FileNotFoundException, IOException, ParseException{
	JSONParser jsonParser = new JSONParser();
	 for (int i=0;i<jsonArraydataset.size();i++) {
   	  JSONObject a=(JSONObject) jsonArraydataset.get(i);
   
	JSONObject dataset= (JSONObject)jsonParser.parse(new FileReader(a.get("filepath").toString()));
    JSONArray jsonArray = (JSONArray)(dataset.get("class labels"));
    JSONArray jsonArray1 = (JSONArray)(dataset.get("instances"));

    DATASET a1=new DATASET((long)dataset.get("dataset id"),(long)dataset.get("maximum number of labels per instance"),(String)dataset.get("dataset name"),jsonArray,jsonArray1,(Long)a.get("numberofuser"));
    datasets.add(a1);
	}
}
public static boolean find(ArrayList<USER>array,USER user) {
if(array.contains(user)) {
	return true;
}
else {
	return false;
}
}



	
}
	   

