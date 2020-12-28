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
	      //READ INPUT FILE
	      JSONObject jsonUser = (JSONObject) jsonParser.parse(new FileReader("configuration.json"));
	      //READ DATASET
	      JSONArray jsonArraydataset = (JSONArray)(jsonUser.get("datasets"));
	      //DATASET ARRAYLIST
	      ArrayList<DATASET>dataset1=new ArrayList<>();
	      //SINGLETON
	      Singleton temp=Singleton.getInstance();
	      temp.createDatasets(jsonArraydataset, dataset1);

	      //Read from file
	      //CurrentDatasetId determined  which dataset will run.
	      DATASET current=dataset1.get(((Integer.valueOf(String.valueOf(jsonUser.get("current dataset id"))))-1));
     
	         //Take number of users in the dataset
	         Long nuser=current.getNumofuser();
	  
	         //Retrieving the array
	         JSONArray jsonArray = current.getLabels();

	         JSONArray jsonArrayInstance =current.getInstances();
	           
	         JSONArray jsonArrayUser = (JSONArray)(jsonUser.get("users"));
             
	          //USER ARRAYLIST
			  ArrayList <USER> listOfUser =new ArrayList<USER>();
			  
			  //READ INPUTS
	          Generate generate = new Generate();  
	          generate.readDataset(logger, current);
	          generate.readLabel(logger,jsonArray,listOfLabel);
	          generate.readInstance(logger, jsonArrayInstance,listOfLabel, listOfInstance);
		      generate.readUser(logger, jsonArrayUser, listOfUser);
		      //PRINT
              PRINT print=new PRINT();
		      
  
		 
          //**********************READ PREVIOUS INFORMATION*************
	        File file=new File("usermetrics.json"); 

		    for (int t=0;t<dataset1.size();t++) {
		    	File file1=new File("Secret"+dataset1.get(t).getId()+".json");
		    }
		  
		     if(file.length()==0) { 
		    	 FileWriter file1 = new FileWriter("usermetrics.json");
		    	
		     print.printusermetrics(file1,listOfUser,logger);
 
		 	       }
		     else {
	           	
	        	    
		     JSONObject jsonUser1 = (JSONObject) jsonParser.parse(new FileReader("usermetrics.json"));
	         JSONArray jsonArrayUser1 = (JSONArray)(jsonUser1.get("users"));
	         for(int i=0; i<jsonArrayUser1.size(); i++) {
	        	 JSONObject jsonArrayUser1Object=(JSONObject) jsonArrayUser1.get(i);
	        	 long TotalNumberofInstance=(long)jsonArrayUser1Object.get("totalNumberofInstance");
	        	 long uniq=(long)jsonArrayUser1Object.get("Number of unique instance");
	         	 long checkconsistency=(long)jsonArrayUser1Object.get("consistency");
	         	 double avr=(double)jsonArrayUser1Object.get("average time");
	        	 listOfUser.get(i).setTotalNumberofınstance(TotalNumberofInstance,logger);
	        	 listOfUser.get(i).setNumofuniqueins(uniq,logger);
	        	 listOfUser.get(i).setCheckconsistency(checkconsistency);
	        	 listOfUser.get(i).setConsistency();
	        	 listOfUser.get(i).setAverage((double)avr);
	        	  JSONArray jsondataset = (JSONArray)jsonArrayUser1Object.get("datasets");
	         	    for(int c=0;c<jsondataset.size();c++) {
	         	    	 JSONObject a=(JSONObject) jsondataset.get(c);
	         	    	long iddataset=(long)a.get("dataset id");
	         	    	double completeness=(double)a.get("completeness");
                    
                         for(int h=0;h<dataset1.size();h++) {
  	               if(dataset1.get(h).getId()==iddataset) {
  	            	   listOfUser.get(i).getDataset().add(dataset1.get(h));
  	            	   dataset1.get(h).setCompleteness(completeness);
  	                        } }
	             
	         	    }
	         }
	         }

		     File file2=new File("Secret"+current.getId()+".json"); 
		     if(file2.length()==0) { 
		       FileWriter file1 = new FileWriter(file2);
		
		        }
		      else {
	         
		      	  JSONObject jsonUser1 = (JSONObject) jsonParser.parse(new FileReader(file2));
		          JSONArray jsonArrayUser1 = (JSONArray)(jsonUser1.get("users"));
		          for(int i=0; i<jsonArrayUser1.size(); i++) {
		         	 JSONObject jsonArrayUser1_1=(JSONObject) jsonArrayUser1.get(i);
		      
		         	 long id=(long)jsonArrayUser1_1.get("user id");
		      
		         	    JSONArray jsonArrayUserlabeled = (JSONArray)jsonArrayUser1_1.get("labeled");
		         	    for(int c=0;c<jsonArrayUserlabeled.size();c++) {
		         	   	 JSONObject a211=(JSONObject) jsonArrayUserlabeled.get(c);
		         	   long h1=(long)a211.get("instance id " );
		         	   INSTANCE a=new INSTANCE();
		         	   listOfUser.get(i).getLabeled().add(listOfInstance.get(a.find(null,listOfInstance, h1)));
		         	
		          }
		         	    JSONArray jsonArrayUserlabeled2 = (JSONArray)jsonArrayUser1_1.get("labeled2");
		         	    for(int c=0;c<jsonArrayUserlabeled2.size();c++) {
		            	   	 JSONObject a211=(JSONObject) jsonArrayUserlabeled2.get(c);
		            	   long h1=(long)a211.get("instance id" );
		            	   long h2=(long)a211.get("label id");
		            	   LABEL label1=new LABEL();
		            	   LABEL_ASSIGNMENT a1=new LABEL_ASSIGNMENT(id,h1,listOfLabel.get(label1.find(listOfLabel,null, h2)));
		            	  listOfUser.get(i).getLabeled2().add(a1);

		             
		         	    }
 		         	    
		         	    }}
	 
       
   
       //**********************RANDOM ASSIGNMENT************************************************
	      ArrayList<USER> currentusers=new ArrayList<USER>();
	      int i=0;
	    	
	         
	      Integer numberOfUniqueLabel;
	      LABEL maxFrequentLabel= new LABEL();
	      Integer maxFrequentLabelPercentage;
	      double entropy;

	      
	      //-------------------------------------------------------------READING PREVIOUS VALUE---------------------------------
	      

	      USER bos=new USER();
	      
		     //**********Find the dataset whether it exists previous iteration*******************   
	         File fileControl = new File("Secret"+current.getId()+"instancemetrics.json");
	  
	         if (fileControl.exists()) {
	        	//Read previous file 
	          
		      JSONParser jsonParserRead= new JSONParser();
		      JSONObject jsonRead = (JSONObject) jsonParserRead.parse(new FileReader("Secret"+current.getId()+"instancemetrics.json"));
		      
		      //COUNT OF LABEL READ 
		      JSONArray array = (JSONArray)(jsonRead.get("CountOfLabel"));
		      ArrayList<Integer> countOfLabelPrevious=new ArrayList<>();
		      String convert="";
		      for (int z=0;z<array.size();z++) {
		    	  JSONArray object=(JSONArray)array.get(z);
		    	  countOfLabelPrevious=new ArrayList<>();
		    	  for(int j=0; j<object.size(); j++) {
		    		  convert=String.valueOf(object.get(j));
		    		  countOfLabelPrevious.add(Integer.valueOf(convert));
		    		 
		    	  }
		    	  listOfInstance.get(z).setCountOfLabel(countOfLabelPrevious);
		    	  
		      } 
		      
		      //TOTAL LABEL ASSIGNMENT READ
		      JSONArray totalArray = (JSONArray)(jsonRead.get("TotalNumberOfAssignment"));
		      for (int z=0;z<totalArray.size();z++) {
		    	  convert=String.valueOf(totalArray.get(z));
		    	  listOfInstance.get(z).setTotalNumberOfLabelAssignment(Integer.valueOf(convert));
		    	  listOfInstance.get(z).percentageOfLabels(logger);
		      }
		     

		      //UNIQUE ARRAY 
		      JSONArray uniqueArray = (JSONArray)(jsonRead.get("UniqueArray"));
		      for (int z=0;z<uniqueArray.size();z++) {
		    	  JSONArray object=(JSONArray)uniqueArray.get(z);
		    	  for(int j=0; j<object.size(); j++) {
		    		  convert=String.valueOf(object.get(j));
		    		   for(int k=0; k<listOfUser.size(); k++) {
		    		   if(listOfUser.get(k).getUserId()==(long)Integer.valueOf(convert)) {
		    			   listOfInstance.get(z).checkUniqueUsers(listOfUser.get(k),logger);
			    		   
			    		     USER cuser=listOfUser.get(k);
			    		    
			        	     if(bos.find(currentusers,cuser)) {
			        	    	 
			        	    	 continue;
			        	     }
			        	     else {
			        	   	  currentusers.add(cuser) ;
			        	     }
		    		   }
		    		   
		    		   
		    	       }    		  
		    	  } 
		      }
		      
		      //UNIQUE INSTANCE FOR EACH LABEL READ 
		      JSONArray labelArray = (JSONArray)(jsonRead.get("LabelArray"));
		      ArrayList<Integer> labelArrayPrevious=new ArrayList<>();
		      for (int z=0;z<listOfLabel.size();z++) {
		    	  JSONArray object=(JSONArray)labelArray.get(z);
		    	  labelArrayPrevious=new ArrayList<>();
		    	  for(int j=0; j<object.size(); j++) {
		    		  convert=String.valueOf(object.get(j));
		    		  labelArrayPrevious.add(Integer.valueOf(convert));
		    		
		    	  }
		    	  listOfLabel.get(z).setUniqueInstance(labelArrayPrevious);	    	  
		      } 
      
	         }
	         
	         
	         else {//----------------------------------- IF THERE IS NO PREVIOUS FILE MAKE FIRST ASSIGNMENT
	        	 

	       	
	        	 while(true) {
	        	   	 int random=(int)(Math.random()*(listOfUser.size()));
	        	     USER cuser=listOfUser.get(random);
	        	     if(bos.find(currentusers,cuser)) {
	        	    	 continue;
	        	     }
	        	     else {
	        	   	  currentusers.add(cuser) ;
	        	     }
	        	   if(currentusers.size()==nuser) {
	        		break;
	        	   }}}
	        for(int k=0;k<currentusers.size();k++) {
	        	if(currentusers.get(k).getDataset().contains(current)) {
	        		continue;
	        	}else {
	        		currentusers.get(k).getDataset().add(current);
	        	}
	        }
	          
	          long endTime=0;
	    	  long startTime=0;
	        	    
	        	   //--------------------------FIRST ASSIGNMENT-----------------------------------------------------
	        	   for(int l=0;l<currentusers.size();l++) {
	        		 startTime = System.nanoTime();
	        		 int random=(int)(Math.random()*(listOfInstance.size()));
	        		 int random1=(int)(Math.random()*(listOfLabel.size()));
	        		 LABEL_ASSIGNMENT one=new LABEL_ASSIGNMENT(currentusers.get(l).getUserId(),listOfInstance.get(random).getInstanceid(),listOfLabel.get(random1));
	        		 
	        		 //This functions are related to INSTANCE METRICS
	        		
	        		 Integer intObj = new Integer((int)(long)listOfLabel.get(random1).getLabelid());// casting label id to Integer
	        		 listOfInstance.get(random).setLabelCount(intObj); //counting label in the listOfInstance
	        		 listOfInstance.get(random).calculateTotalNumberOfLabelAssignment(logger); //INSTANCE METRICS STEP 1: calculating Total Number Of Label Assignment
	        		 numberOfUniqueLabel=listOfInstance.get(random).calculateTotalNumberOfUniqueLabel(logger);//INSTANCE METRICS STEP 2:calculating Total Number Of Unique Label
	        		 listOfInstance.get(random).checkUniqueUsers( currentusers.get(l),logger);//INSTANCE METRICS STEP 3:adding user into uniqueUsers list  
	        		 maxFrequentLabel = listOfInstance.get(random).findMaxFrequentLabel(listOfLabel,logger);// finding Max Frequent Label
	        		 maxFrequentLabelPercentage = listOfInstance.get(random).percentageOfMaxLabel(logger);//finding Max Frequent Label percentage
	        		 listOfInstance.get(random).percentageOfLabels(logger);//calculating percentage of each labels into percentageOfLabel array
	        		 entropy=listOfInstance.get(random).entropy(logger);//calculating entropy
	        		 
	        		
	        		 listOfLabel.get(random1).checkUniqueInstance(listOfInstance.get(random),logger);
	        		 endTime=System.nanoTime();
	        		 currentusers.get(l).setTime(endTime - startTime);
	        		 if(currentusers.get(l).getLabeled().contains(listOfInstance.get(random))) {
	        			 if(currentusers.get(l).getLabeled2().contains(one)) {
	        				 currentusers.get(l).setCheckconsistency((long)-1);
	        			 }else {
	        				 currentusers.get(l).setLabeled2(one);
	        			 }
	        		 }else {
	        		 currentusers.get(l).addInstance((listOfInstance.get(random)));
	        		 currentusers.get(l).setNumofuniqueins((long)-1,logger);
	        		 currentusers.get(l).setLabeled2(one);}
	                 currentusers.get(l).setTotalNumberofınstance((long)-1,logger);
	                 currentusers.get(l).setConsistency();
	                
	                 //DATASET METRICS
	        		 print.printDatasetMetrics(listOfInstance,listOfLabel,current.getId(),current.getNumofuser(),logger,currentusers,current);
				    
	        		 //USER METRICS
					 FileWriter  file3 =new FileWriter("usermetrics.json");
			     	 FileWriter file1 =new FileWriter(file2);
				     file1.write(" ");	
				     print.printuserarray(file1,listOfUser);
				     file3.write(" ");	
				     print.printusermetrics(file3,listOfUser,logger);	
	                 
	                 //INSTANCE METRICS
	                 print.backup(listOfInstance,listOfLabel,current.getId());
	        		 print.printInstanceMetrics(listOfInstance,listOfLabel, current.getId(),logger);
	        		 
	        		
	                 
	        		
	         }
	    
		

	  i=0;
	  endTime=0;
	  startTime=0;
	  while(true) {
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
		          currentu.setCheckconsistency((long)-1);
	
	              }
	              else {	
		 	 	  currentu.setLabeled2(two);
		 	 	  }
		 				
		 		 
		 		  Integer intObj = new Integer((int)(long)two.getLabels().getLabelid());
		 		  currentu.getLabeled().get(random).setLabelCount(intObj);
		 		  //This functions are related to USER METRICS
		 		  currentu.getLabeled().get(random).calculateTotalNumberOfLabelAssignment(logger);
		 		  numberOfUniqueLabel=currentu.getLabeled().get(random).calculateTotalNumberOfUniqueLabel(logger);
		 		  currentu.getLabeled().get(random).checkUniqueUsers( currentu,logger);
		 		  maxFrequentLabel = currentu.getLabeled().get(random).findMaxFrequentLabel(listOfLabel,logger);
				  maxFrequentLabelPercentage = currentu.getLabeled().get(random).percentageOfMaxLabel(logger);
				  currentu.getLabeled().get(random).percentageOfLabels(logger);
			    
				  entropy=currentu.getLabeled().get(random).entropy(logger);
				  listOfLabel.get(random1).checkUniqueInstance(currentu.getLabeled().get(random),logger);		 		
				   currentu.setTotalNumberofınstance((long)-1,logger);
			 	    currentu.setConsistency();
				  
			 	    //DATASET METRICS
	             	print.printDatasetMetrics(listOfInstance,listOfLabel,current.getId(),current.getNumofuser(),logger,currentusers,current);
				  
				    //WRITE USER METRICS
				    FileWriter  file3 =new FileWriter("usermetrics.json");
					FileWriter file1 =new FileWriter(file2);
					file1.write(" ");	
				print.printuserarray(file1,listOfUser);
			        file3.write(" ");	
			    print.printusermetrics(file3,listOfUser,logger);	
			        
			        //INSTANCE METRICS
			        print.backup(listOfInstance,listOfLabel,current.getId());
					print.printInstanceMetrics(listOfInstance,listOfLabel,current.getId(),logger);
					
					
					  
		 	
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
				
					Integer intObj = new Integer((int)(long)two.getLabels().getLabelid());
					cInstance.setLabelCount(intObj);
					//This functions are related to USER METRICS
					cInstance.calculateTotalNumberOfLabelAssignment(logger);
					numberOfUniqueLabel=cInstance.calculateTotalNumberOfUniqueLabel(logger);
					cInstance.checkUniqueUsers( currentu,logger);
					maxFrequentLabel = cInstance.findMaxFrequentLabel(listOfLabel,logger);
					maxFrequentLabelPercentage = cInstance.percentageOfMaxLabel(logger);
					cInstance.percentageOfLabels(logger);
				
					entropy=cInstance.entropy(logger);

					currentlabel.checkUniqueInstance(cInstance,logger);
					
				
				 
				
					currentu.getLabeled().add(cInstance);
					currentu.setNumofuniqueins((long)-1,logger);
					currentu.setLabeled2(two);
				    currentu.setTotalNumberofınstance((long)-1,logger);
				    currentu.setConsistency();
				   
				    //DATASET METRICS
	        		 print.printDatasetMetrics(listOfInstance,listOfLabel, current.getId(),current.getNumofuser(),logger,currentusers,current);
				    
				    //WRITE USER METRICS
				    FileWriter  file3 =new FileWriter("usermetrics.json");
					FileWriter file1 =new FileWriter(file2);
					file1.write(" ");	
					print.printuserarray(file1,listOfUser);
			        file3.write(" ");	
			        print.printusermetrics(file3,listOfUser,logger);	  
			        
			    	//INSTANCE METRICS
					print.backup(listOfInstance,listOfLabel,current.getId());
					print.printInstanceMetrics(listOfInstance,listOfLabel,current.getId(),logger);
			        
			     
				    break;  
				}

		
		     }//while
			
		    }//else
		    //Finish the timer
		    endTime = System.nanoTime();
		    currentu.setTime(endTime - startTime);
		    //i++;
		     
		     }
	 
	  		
	    
	   }//main
	   

	   
	   
	   
	   






}//class
	   
