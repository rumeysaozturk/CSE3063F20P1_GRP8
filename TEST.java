import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.concurrent.TimeUnit;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.logging.*;
public class TEST {

	   public static void main(String args[]) throws FileNotFoundException, IOException, ParseException, SecurityException {
		    //LOGGER PART
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
	        }//********************
	     
		 
		   
		  ArrayList<LABEL>listOfLabel=new ArrayList<>();
		  ArrayList<INSTANCE>listOfInstance=new ArrayList<>();
	      JSONParser jsonParser = new JSONParser();
	     
	      JSONObject jsonUser = (JSONObject) jsonParser.parse(new FileReader("configuration.json"));
	   
	      JSONArray jsonArraydataset = (JSONArray)(jsonUser.get("datasets"));
	      ArrayList<DATASET>dataset1=new ArrayList<>();
	      createDatasets(jsonArraydataset,dataset1);
	    
	      //Parsing the contents of the JSON file
	      //Forming URL
	      //Read from file
	      //CurrentDatasetId determined  which dataset will run.
	      DATASET current=dataset1.get(((Integer.valueOf(String.valueOf(jsonUser.get("current dataset id"))))-1));
	         logger.info("Input file is read succesfully.\n");
	         logger.info("Dataset id: "+ current.getId()+ " is created");
	         logger.info("Dataset name: "+current.getName()+ " is created");
	         logger.info("maximum number of labels per instance: "+current.getMax_label()+ " is created\n");
                   
	         
	         //Take number of users in the dataset
	         Long nuser=current.getNumofuser();
	  
	         //Retrieving the array
	         JSONArray jsonArray = current.getLabels();
	         
	        
	         //Iterating the contents of the array
	      
	        
	         JSONArray jsonArray1 =current.getInstances();
	       
	        
	         //Iterating the contents of the array
	    
	         JSONArray jsonArrayUser = (JSONArray)(jsonUser.get("users"));
	     
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
	      
	      //GENERATE A USER************************************
		  ArrayList <USER> listOfUser =new ArrayList<USER>();
		 
	
	      USER user;
	      for(int i=0; i<jsonArrayUser.size(); i++) {
	    	  JSONObject a2=(JSONObject) jsonArrayUser.get(i);
		        user = new USER((long) a2.get("user id"),(String) a2.get("user name"),(String) a2.get("user type"),(double)a2.get("ConsistencyCheckProbability"));
	    	    listOfUser.add(user);
	    	    user.printUser(logger);
	    	   
	      }//*************************************************
	     
	     /*System.out.println();
	     File file=new File("usermet.json"); 
	     FileWriter file1 = new FileWriter("usermet.json");
	     Gson gson=new Gson();
	     String json2=gson.toJson(listOfUser);
	     if(file.length()==0) {
	       printusermet(file1,json2);
	     
	 	       }else {
	 	  JSONObject jsonUser1 = (JSONObject) jsonParser.parse(new FileReader("usermet.json"));
         JSONArray jsonArrayUser1 = (JSONArray)(jsonUser1.get("users"));
         for(int i=0; i<jsonArrayUser1.size(); i++) {
        	 JSONObject a21=(JSONObject) jsonArrayUser1.get(i);
        	 long TotalNumberofınstance=(long)a21.get("totalNumberofınstance");
        	 long checkconsistency=(long)a21.get("checkconsistency");
        	 listOfUser.get(i).setTotalNumberofınstance(TotalNumberofınstance);
        	 listOfUser.get(i).setCheckconsistency(checkconsistency);
         }
	        }*/
          
	     PRINT print=new PRINT();
     
	     //**********Find the dataset whether it exists previous iteration*******************   
         File fileControl = new File("Secret/"+current.getId()+".json");
  
         if (fileControl.exists()) {
        	//Read previous file 
          
	      JSONParser jsonParserRead= new JSONParser();
	      JSONObject jsonRead = (JSONObject) jsonParserRead.parse(new FileReader("Secret/1.json"));
	      
	      //COUNT OF LABEL READ 
	      JSONArray array = (JSONArray)(jsonRead.get("CountOfLabel"));
	      ArrayList<Integer> countOfLabelPrevious=new ArrayList<>();
	      String convert="";
	      for (int i=0;i<array.size();i++) {
	    	  JSONArray object=(JSONArray)array.get(i);
	    	  countOfLabelPrevious=new ArrayList<>();
	    	  for(int j=0; j<object.size(); j++) {
	    		  convert=String.valueOf(object.get(j));
	    		  countOfLabelPrevious.add(Integer.valueOf(convert));
	    		  //System.out.print(convert);
	    	  }
	    	  listOfInstance.get(i).setCountOfLabel(countOfLabelPrevious);
	    	  
	      } 
	      //TOTAL LABEL ASSIGNMENT READ
	      JSONArray totalArray = (JSONArray)(jsonRead.get("TotalNumberOfAssignment"));
	      for (int i=0;i<totalArray.size();i++) {
	    	  convert=String.valueOf(totalArray.get(i));
	    	  listOfInstance.get(i).setTotalNumberOfLabelAssignment(Integer.valueOf(convert));
	    	  listOfInstance.get(i).percentageOfLabels();
	      }
         }
         
   
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
	    
	   //--------------------------FIRST ASSIGNMENT-----------------------------------------------------
	   for(int l=0;l<currentusers.size();l++) {
	     
		 int random=(int)(Math.random()*(listOfInstance.size()));
		 int random1=(int)(Math.random()*(listOfLabel.size()));
		 LABEL_ASSIGNMENT one=new LABEL_ASSIGNMENT(currentusers.get(l).getUserId(),listOfInstance.get(random).getInstanceid(),listOfLabel.get(random1));
		
		 //This functions are related to INSTANCE METRICS
		 listOfInstance.get(random).addLabel(listOfLabel.get(random1));//adding label to labelInInstance
		 Integer intObj = new Integer((int)(long)listOfLabel.get(random1).getLabelid());// casting label id to Integer
		 listOfInstance.get(random).setLabelCount(intObj); //counting label in the listOfInstance
		 listOfInstance.get(random).calculateTotalNumberOfLabelAssignment(); //INSTANCE METRICS STEP 1: calculating Total Number Of Label Assignment
		 numberOfUniqueLabel=listOfInstance.get(random).calculateTotalNumberOfUniqueLabel();//INSTANCE METRICS STEP 2:calculating Total Number Of Unique Label
		 listOfInstance.get(random).checkUniqueUsers( currentusers.get(l));//INSTANCE METRICS STEP 3:adding user into uniqueUsers list  
		 maxFrequentLabel = listOfInstance.get(random).findMaxFrequentLabel(listOfLabel);// finding Max Frequent Label
		 maxFrequentLabelPercentage = listOfInstance.get(random).percentageOfMaxLabel();//finding Max Frequent Label percentage
		 listOfInstance.get(random).percentageOfLabels();//calculating percentage of each labels into percentageOfLabel array
		 entropy=listOfInstance.get(random).entropy();//calculating entropy
		 print.backup(listOfInstance,current.getId());

		 
		 listOfInstance.get(random).writingPercOfLabels();//writing percentage of all labels
		 System.out.println("user id: " +one.getUserId()+" instance id: "+one.getInstanceId()+" label: "+one.getLabels().getText()+" date time: "+one.getDatetime());
		 System.out.println("InstanceId: "+ listOfInstance.get(random).getInstanceid()+ " LabelId: "+one.getLabels().getLabelid()+ " CurrenOlanLabelKaÃ§KezLabellendi: " + listOfInstance.get(random).getCountOfLabel().get((int)one.getLabels().getLabelid()-1)+
				 "\nnTotalCountOfLabelAssignment: " + listOfInstance.get(random).getTotalNumberOfLabelAssignment()+ " UniqueLabel:"+ numberOfUniqueLabel+
				 	" UniqueUser: "+ listOfInstance.get(random).getUniqueUsers().size() + " FrequentyLabelId: " + maxFrequentLabel.getLabelid() + " percentage: %"+ maxFrequentLabelPercentage +
				 	" Entropy: "+ entropy +"\n");
		 
		 currentusers.get(l).addInstance((listOfInstance.get(random)));

		 currentusers.get(l).setLabeled2(one);
         currentusers.get(l).setTotalNumberofınstance((long)0);
        // System.out.println("user id: " +one.getUserId()+" instance id: "+one.getInstanceId()+" label: "+one.getLabels().getText()+" date time: "+one.getDatetime());
	  }
		
	  long endTime=0;
	  long startTime=0;
	  i=0;
	  while(i<20) {
		 //Time starts 
		 startTime = System.nanoTime();

		     int randomuser=(int)(Math.random()*(currentusers.size()));
		     USER currentu=currentusers.get(randomuser);
		     int checkprob=(int)(Math.random()*(100));
		     
	     //----------------------------IF CPP is lower----------------
		     if(checkprob<currentu.getConsistencyCheckProbability()*100) {
		    	int  random=(int)(Math.random()*(currentu.getLabeled().size()));
		    	
		 		int random1=(int)(Math.random()*(listOfLabel.size()));
		 	
		 		LABEL_ASSIGNMENT two=new LABEL_ASSIGNMENT(currentu.getUserId(),currentu.getLabeled().get(random).getInstanceid(),listOfLabel.get(random1));
		 		
	              if(currentu.existbefore(two)) {
		          currentu.setCheckconsistency((long)0);
	              }
	              else {	
		 	 	  currentu.setLabeled2(two);
		 	 	  }
		 				
		 		  currentu.getLabeled().get(random).addLabel(two.getLabels());
		 		  Integer intObj = new Integer((int)(long)two.getLabels().getLabelid());
		 		  currentu.getLabeled().get(random).setLabelCount(intObj);
		 		  //This functions are related to USER METRICS
		 		  currentu.getLabeled().get(random).calculateTotalNumberOfLabelAssignment();
		 		  numberOfUniqueLabel=currentu.getLabeled().get(random).calculateTotalNumberOfUniqueLabel();
		 		  currentu.getLabeled().get(random).checkUniqueUsers( currentu);
		 		  maxFrequentLabel = currentu.getLabeled().get(random).findMaxFrequentLabel(listOfLabel);
				  maxFrequentLabelPercentage = currentu.getLabeled().get(random).percentageOfMaxLabel();
				  currentu.getLabeled().get(random).percentageOfLabels();
			      //currentu.getLabeled().get(random).writingPercOfLabels();
				  entropy=currentu.getLabeled().get(random).entropy();
				  print.backup(listOfInstance,current.getId());
		 		
		 		System.out.println("user id: "+two.getUserId()+ " instance id: "+two.getInstanceId()+" label: "+two.getLabels().getText()+" date time: "+two.getDatetime());
		 		System.out.println("InstanceId: "+ (currentu.getLabeled().get(random).getInstanceid()+ " LabelId: "+two.getLabels().getLabelid()+ " CurrenOlanLabelKaÃ§KezLabellendi: " + currentu.getLabeled().get(random).getCountOfLabel().get((int)two.getLabels().getLabelid()-1)+
						 "\nnTotalCountOfLabelAssignment: " + currentu.getLabeled().get(random).getTotalNumberOfLabelAssignment())+" UniqueLabel:"+ numberOfUniqueLabel+
		 					" UniqueUser: "+ currentu.getLabeled().get(random).getUniqueUsers().size()+ " FrequentyLabelId: " + maxFrequentLabel.getLabelid() + " percentage: %"+ maxFrequentLabelPercentage+
		 						" Entropy: "+ entropy +"\n");
				 
		 	     currentu.setTotalNumberofınstance((long)0);
		
		 		//System.out.println("INSTANCE ID:" +currentu.getLabeled().get(random).getInstanceid()+" " + currentu.getLabeled().get(random).getLabels().get(currentu.getLabeled().get(random).getLabels().size() -1).getLabelid());
		 	   // System.out.println("user id: " +two.getUserId()+" instance id: "+two.getInstanceId()+" label: "+two.getLabels().getText()+" date time: "+two.getDatetime());
		    	 
		     }
		     
		     
		    else {  //----------------------------IF CPP is higher----------------

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
					//This functions are related to USER METRICS
					cInstance.calculateTotalNumberOfLabelAssignment();
					numberOfUniqueLabel=cInstance.calculateTotalNumberOfUniqueLabel();
					cInstance.checkUniqueUsers( currentu);
					maxFrequentLabel = cInstance.findMaxFrequentLabel(listOfLabel);
					maxFrequentLabelPercentage = cInstance.percentageOfMaxLabel();
					cInstance.percentageOfLabels();
					//cInstance.writingPercOfLabels();
					entropy=cInstance.entropy();
					print.backup(listOfInstance,current.getId());

					 
					System.out.println("user id: "+two.getUserId()+" instance id: "+two.getInstanceId()+" label: "+two.getLabels().getText()+" date time: "+two.getDatetime());
					 System.out.println("InstanceId: "+ (cInstance.getInstanceid()+ " LabelId: "+two.getLabels().getLabelid()+ " CurrenOlanLabelKaÃ§KezLabellendi: " + cInstance.getCountOfLabel().get((int)two.getLabels().getLabelid()-1)+
							 "\nTotalCountOfLabelAssignment: " + cInstance.getTotalNumberOfLabelAssignment())+" UniqueLabel:"+ numberOfUniqueLabel+
							 	" UniqueUser: "+ cInstance.getUniqueUsers().size()+ " FrequentyLabelId: " + maxFrequentLabel.getLabelid() + " percentage: %"+ maxFrequentLabelPercentage +
							 	" Entropy: "+ entropy +"\n");
									
			 		//System.out.println("INSTANCE ID:" +cInstance.getInstanceid()+" " + cInstance.getLabels().get(cInstance.getLabels().size() -1).getLabelid());
					
					currentu.getLabeled().add(cInstance);
					currentu.setLabeled2(two);
				    currentu.setTotalNumberofınstance((long)0);
				    System.out.println("user id: " +two.getUserId()+" instance id: "+two.getInstanceId()+" label: "+two.getLabels().getText()+" date time: "+two.getDatetime());

				    ArrayList<INSTANCE>tlabeled=new ArrayList<INSTANCE>();
				    ArrayList<LABEL_ASSIGNMENT>tlabeled2=new ArrayList<LABEL_ASSIGNMENT>();
				     ArrayList<Long>time=new ArrayList<Long>();
				    FileWriter  file2 =new FileWriter("usermet.json");
		
			    file2.write(" ");	
			
				 
			//	  printusermet(file2,json1);
				  
				    break;  
				}

		
		     }//while
			
		    }//else
		    //Finish the timer
		    endTime = System.nanoTime();
		    currentu.setTime(endTime - startTime);
		     i++;
		     
		     }
	 
	         /*System.out.println(currentusers.get(0).getUserId()+" "+currentusers.get(0).getTotalNumberofınstance()+" "+currentusers.get(0).getLabeled().size());
	          for( i=0;i<currentusers.get(0).getLabeled2().size();i++) {
		         System.out.println("user id: " +currentusers.get(0).getLabeled2().get(i).getUserId()+" instance id: "+currentusers.get(0).getLabeled2().get(i).getInstanceId()+" label: "+currentusers.get(0).getLabeled2().get(i).getLabels().getText()+" date time: "+currentusers.get(0).getLabeled2().get(i).getDatetime()); 	 
	          }*/
		     //System.out.println(currentusers.get(0).average()+" "+currentusers.get(0).stdeviation());
		
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



public static void printusermet(FileWriter file1,String json2) throws IOException {
   
	  file1.write("{\"users\":[\n");
      
      
      //Print Users to json file
      file1.write(json2+",\n");
      
      file1.write("]\n");
      file1.write("}\n");
     
      file1.flush();
      file1.close();
}


}//class
	   
