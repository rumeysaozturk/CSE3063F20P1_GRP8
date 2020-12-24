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
	      /*burasÃ½ filedan okuncak*/
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
	         /////////////////////////////////////////////////////////////////////////////
	         

	        	      
	      for (int i=0;i<jsonArray.size();i++) {
	    	  JSONObject a=(JSONObject) jsonArray.get(i);
		        LABEL a1=new LABEL((long) a.get("label id"),(String) a.get("label text"));
		        listOfLabel.add(a1);
		        
	      }
	      
	      INSTANCE a3;	         
	      for (int i=0;i<jsonArray1.size();i++) {
	    	  JSONObject a2=(JSONObject) jsonArray1.get(i);
		         a3 =new INSTANCE((long) a2.get("id"),(String) a2.get("instance"));
		         a3.createListForLabelCount(listOfLabel.size());// creating countOfLabel list and assign 0 to countOfLabel list in the beginning
		         a3.createListForLabelPercentage(listOfLabel.size());//// creating percentageOfLabel list and assign 0 to percentageOfLabel list in the beginning
		        listOfInstance.add(a3);
		        
	      }    
	      
	      //GENERATE A USER****************************************************************
		  ArrayList <USER> listOfUser =new ArrayList<USER>();
		 
	
	      USER user;
	      for(int i=0; i<jsonArrayUser.size(); i++) {
	    	  JSONObject a2=(JSONObject) jsonArrayUser.get(i);
		        user = new USER((long) a2.get("user id"),(String) a2.get("user name"),(String) a2.get("user type"),(double)a2.get("ConsistencyCheckProbability"));
	    	    listOfUser.add(user);
	    	    user.printUser(logger);
	    	   
	      }
	      System.out.println();
          
	        //**********************RANDOM ASSIGNMENT************************************************
	      ArrayList<USER> currentusers=new ArrayList<USER>();
	      int i=0;
	    	 int rand=(int)((Math.random())*(listOfUser.size()));
	    	
	         USER cuser1=listOfUser.get(rand);
	         currentusers.add(cuser1) ;
	         
	         Integer numberOfUniqueLabel;
	         LABEL maxFrequentLabel= new LABEL();
	         Integer maxFrequentLabelPercentage;
	         double entropy;
	         
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
	    ///////////////////////////////////////////////////////////////////////////////////////////  ILK ATAMA 
	 for(int l=0;l<currentusers.size();l++) {
		 
		 
		 
		 int  random=(int)(Math.random()*(listOfInstance.size()));
		 int random1=(int)(Math.random()*(listOfLabel.size()));
		 LABEL_ASSIGNMENT one=new LABEL_ASSIGNMENT(currentusers.get(l).getUserId(),listOfInstance.get(random).getInstanceid(),listOfLabel.get(random1));
		
		 listOfInstance.get(random).addLabel(listOfLabel.get(random1));//adding label to labelInInstance
		 
		 Integer intObj = new Integer((int)(long)listOfLabel.get(random1).getLabelid());// casting label id to Integer
		 listOfInstance.get(random).setLabelCount(intObj); //counting label in the listOfInstance
		 listOfInstance.get(random).calculateTotalNumberOfLabelAssignment(); //calculating Total Number Of Label Assignment
		 numberOfUniqueLabel=listOfInstance.get(random).calculateTotalNumberOfUniqueLabel();//calculating Total Number Of Unique Label
		 listOfInstance.get(random).checkUniqueUsers( currentusers.get(l));//adding user into uniqueUsers list  
		 maxFrequentLabel = listOfInstance.get(random).findMaxFrequentLabel(listOfLabel);// finding Max Frequent Label
		 maxFrequentLabelPercentage = listOfInstance.get(random).percentageOfMaxLabel();//finding Max Frequent Label percentage
		 listOfInstance.get(random).percentageOfLabels();//calculating percentage of each labels into percentageOfLabel array
		 entropy=listOfInstance.get(random).entropy();//calculating entropy
		 
		 listOfInstance.get(random).writingPercOfLabels();//writing percentage of all labels
		 System.out.println("user id: " +one.getUserId()+" instance id: "+one.getInstanceId()+" label: "+one.getLabels().getText()+" date time: "+one.getDatetime());
		 System.out.println("InstanceId: "+ listOfInstance.get(random).getInstanceid()+ " LabelId: "+one.getLabels().getLabelid()+ " CurrenOlanLabelKaÃ§KezLabellendi: " + listOfInstance.get(random).getCountOfLabel().get((int)one.getLabels().getLabelid()-1)+
				 "\nnTotalCountOfLabelAssignment: " + listOfInstance.get(random).getTotalNumberOfLabelAssignment()+ " UniqueLabel:"+ numberOfUniqueLabel+
				 	" UniqueUser: "+ listOfInstance.get(random).getUniqueUsers().size() + " FrequentyLabelId: " + maxFrequentLabel.getLabelid() + " percentage: %"+ maxFrequentLabelPercentage +
				 	" Entropy: "+ entropy +"\n");
		 
		 currentusers.get(l).addInstance((listOfInstance.get(random)));

	
	 }
	 
	 
	 
	  i=0;
	 while(i<20) {
		 
		 
		     int randomuser=(int)(Math.random()*(currentusers.size()));
		     USER currentu=currentusers.get(randomuser);
		     int checkprob=(int)(Math.random()*(100));
		     
		      /////////////////////////////////////////////////////////////////////////////////  CCP KÃœÃ‡ÃœKSE ATAMA
		     
		     if(checkprob<currentu.getConsistencyCheckProbability()*100) {
		    	int  random=(int)(Math.random()*(currentu.getLabeled().size()));
		    	
		 		int random1=(int)(Math.random()*(listOfLabel.size()));
		 	
		 		LABEL_ASSIGNMENT two=new LABEL_ASSIGNMENT(currentu.getUserId(),currentu.getLabeled().get(random).getInstanceid(),listOfLabel.get(random1));
		 		
		 		
		 		
		 		currentu.getLabeled().get(random).addLabel(two.getLabels());
		 		Integer intObj = new Integer((int)(long)two.getLabels().getLabelid());
		 		currentu.getLabeled().get(random).setLabelCount(intObj);
		 		
		 		currentu.getLabeled().get(random).calculateTotalNumberOfLabelAssignment();
		 		numberOfUniqueLabel=currentu.getLabeled().get(random).calculateTotalNumberOfUniqueLabel();
		 		currentu.getLabeled().get(random).checkUniqueUsers( currentu);
		 		maxFrequentLabel = currentu.getLabeled().get(random).findMaxFrequentLabel(listOfLabel);
				maxFrequentLabelPercentage = currentu.getLabeled().get(random).percentageOfMaxLabel();
				currentu.getLabeled().get(random).percentageOfLabels();
				currentu.getLabeled().get(random).writingPercOfLabels();
				entropy=currentu.getLabeled().get(random).entropy();
		 		
		 		System.out.println("user id: "+two.getUserId()+ " instance id: "+two.getInstanceId()+" label: "+two.getLabels().getText()+" date time: "+two.getDatetime());
		 		System.out.println("InstanceId: "+ (currentu.getLabeled().get(random).getInstanceid()+ " LabelId: "+two.getLabels().getLabelid()+ " CurrenOlanLabelKaÃ§KezLabellendi: " + currentu.getLabeled().get(random).getCountOfLabel().get((int)two.getLabels().getLabelid()-1)+
						 "\nnTotalCountOfLabelAssignment: " + currentu.getLabeled().get(random).getTotalNumberOfLabelAssignment())+" UniqueLabel:"+ numberOfUniqueLabel+
		 					" UniqueUser: "+ currentu.getLabeled().get(random).getUniqueUsers().size()+ " FrequentyLabelId: " + maxFrequentLabel.getLabelid() + " percentage: %"+ maxFrequentLabelPercentage+
		 						" Entropy: "+ entropy +"\n");
				 
		 		//System.out.println("INSTANCE ID:" +currentu.getLabeled().get(random).getInstanceid()+" " + currentu.getLabeled().get(random).getLabels().get(currentu.getLabeled().get(random).getLabels().size() -1).getLabelid());
		 		
		    	 
		     }
		     
		     
		    else {
		    	
		    	///////////////////////////////////////////////////////////////////////////////////  CCP BÃœYÃœKSE ATAMA
		
			
			int random1=(int)(Math.random()*(listOfLabel.size()));
			LABEL currentlabel=listOfLabel.get(random1);
			
			while(true) {
				int  random=(int)(Math.random()*(listOfInstance.size()));
				INSTANCE cInstance=listOfInstance.get(random);
				
				if(currentu.getLabeled().contains(cInstance)) {
			    	 continue;
			     }
				else {
					LABEL_ASSIGNMENT two=new LABEL_ASSIGNMENT(currentu.getUserId(),cInstance.getInstanceid(),currentlabel);
					cInstance.addLabel(two.getLabels());
					Integer intObj = new Integer((int)(long)two.getLabels().getLabelid());
					cInstance.setLabelCount(intObj);
					
					cInstance.calculateTotalNumberOfLabelAssignment();
					numberOfUniqueLabel=cInstance.calculateTotalNumberOfUniqueLabel();
					cInstance.checkUniqueUsers( currentu);
					maxFrequentLabel = cInstance.findMaxFrequentLabel(listOfLabel);
					maxFrequentLabelPercentage = cInstance.percentageOfMaxLabel();
					cInstance.percentageOfLabels();
					cInstance.writingPercOfLabels();
					entropy=cInstance.entropy();
					 
					System.out.println("user id: "+two.getUserId()+" instance id: "+two.getInstanceId()+" label: "+two.getLabels().getText()+" date time: "+two.getDatetime());
					 System.out.println("InstanceId: "+ (cInstance.getInstanceid()+ " LabelId: "+two.getLabels().getLabelid()+ " CurrenOlanLabelKaÃ§KezLabellendi: " + cInstance.getCountOfLabel().get((int)two.getLabels().getLabelid()-1)+
							 "\nTotalCountOfLabelAssignment: " + cInstance.getTotalNumberOfLabelAssignment())+" UniqueLabel:"+ numberOfUniqueLabel+
							 	" UniqueUser: "+ cInstance.getUniqueUsers().size()+ " FrequentyLabelId: " + maxFrequentLabel.getLabelid() + " percentage: %"+ maxFrequentLabelPercentage +
							 	" Entropy: "+ entropy +"\n");
									
			 		//System.out.println("INSTANCE ID:" +cInstance.getInstanceid()+" " + cInstance.getLabels().get(cInstance.getLabels().size() -1).getLabelid());
					
					currentu.getLabeled().add(cInstance);
					break;  
				}

		
		     }//while
			
		    }//else
		     
		     i++;}
	 
	   
	  
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
	    
	   }//main
	   

	   
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
	   }//createDatasets
	   
	   
	   
	   public static boolean find(ArrayList<USER>array,USER user) {
		   if(array.contains(user)) {
			   return true;
		   }
		   else {
			   return false;
		   }
	   }//find



	
}//class
	   
