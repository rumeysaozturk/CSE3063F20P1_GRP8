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
	          
	      INSTANCE addInstance;	         
	      for (int i=0;i<jsonArray1.size();i++) {
	    	    JSONObject a2=(JSONObject) jsonArray1.get(i);
	    	    addInstance =new INSTANCE((long) a2.get("id"),(String) a2.get("instance"));
	    	    addInstance.createListForLabelCount(listOfLabel.size());// creating countOfLabel list and assign 0 to countOfLabel list in the beginning
	    	    addInstance.createListForLabelPercentage(listOfLabel.size());//// creating percentageOfLabel list and assign 0 to percentageOfLabel list in the beginning
		        listOfInstance.add(addInstance);
		        
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
	      File file=new File("usermetrics.json"); 

		    for (int t=0;t<dataset1.size();t++) {
		    	File file1=new File("Secret/"+dataset1.get(t).getId()+".json");
		    }
		  
		     if(file.length()==0) { 
		    	 FileWriter file1 = new FileWriter("usermetrics.json");
		    	
		 PRINT a=new PRINT();
		      a. printusermetrics(file1,listOfUser,logger);
		     
		       
		     
		 	       }else {
	           	
	        	    
		     JSONObject jsonUser1 = (JSONObject) jsonParser.parse(new FileReader("usermetrics.json"));
	         JSONArray jsonArrayUser1 = (JSONArray)(jsonUser1.get("users"));
	         for(int i=0; i<jsonArrayUser1.size(); i++) {
	        	 JSONObject a21=(JSONObject) jsonArrayUser1.get(i);
	        	 long TotalNumberofInstance=(long)a21.get("totalNumberofInstance");
	        	 long uniq=(long)a21.get("Number of unique instance");
	         	 long checkconsistency=(long)a21.get("consistency");
	         	 double avr=(double)a21.get("average time");
	        	 listOfUser.get(i).setTotalNumberofÄ±nstance(TotalNumberofInstance,logger);
	        	 listOfUser.get(i).setNumofuniqueins(uniq,logger);
	        	 listOfUser.get(i).setCheckconsistency(checkconsistency);
	        	 listOfUser.get(i).setConsistency();
	        	 listOfUser.get(i).setAverage((double)avr);
	        	  JSONArray jsondataset = (JSONArray)a21.get("datasets");
	         	    for(int c=0;c<jsondataset.size();c++) {
	         	    	 JSONObject a=(JSONObject) jsondataset.get(c);
	         	    	long iddataset=(long)a.get("dataset id");
	         	    	double completeness=(double)a.get("completeness");
                    //   System.out.println();
                         for(int h=0;h<dataset1.size();h++) {
  	               if(dataset1.get(h).getId()==iddataset) {
  	            	   listOfUser.get(i).getDataset().add(dataset1.get(h));
  	            	   dataset1.get(h).setCompleteness(completeness);
  	                        } }
	             
	         	    }
	         }
	         }
		     
		     
		     File file2=new File("Secret/"+current.getId()+".json"); 
		     if(file2.length()==0) { 
		       FileWriter file1 = new FileWriter(file2);
		
		        }
		     else {
	         
		      	  JSONObject jsonUser1 = (JSONObject) jsonParser.parse(new FileReader(file2));
		          JSONArray jsonArrayUser1 = (JSONArray)(jsonUser1.get("users"));
		          for(int i=0; i<jsonArrayUser1.size(); i++) {
		         	 JSONObject a21=(JSONObject) jsonArrayUser1.get(i);
		      
		         	 long id=(long)a21.get("user id");
		         // System.out.print(id+" ");
		         	    JSONArray jsonArrayUserlabeled = (JSONArray)a21.get("labeled");
		         	    for(int c=0;c<jsonArrayUserlabeled.size();c++) {
		         	   	 JSONObject a211=(JSONObject) jsonArrayUserlabeled.get(c);
		         	   long h1=(long)a211.get("instance id " );
		         	   INSTANCE a=new INSTANCE();
		         	   listOfUser.get(i).getLabeled().add(listOfInstance.get(a.find(null,listOfInstance, h1)));
		         	
		          }
		         	    JSONArray jsonArrayUserlabeled2 = (JSONArray)a21.get("labeled2");
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
	      
	      PRINT print=new PRINT();
	      USER bos=new USER();
	      
		     //**********Find the dataset whether it exists previous iteration*******************   
	         File fileControl = new File("Secret/"+current.getId()+"instancemetrics.json");
	  
	         if (fileControl.exists()) {
	        	//Read previous file 
	          
		      JSONParser jsonParserRead= new JSONParser();
		      JSONObject jsonRead = (JSONObject) jsonParserRead.parse(new FileReader("Secret/"+current.getId()+"instancemetrics.json"));
		      
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
		    		  //System.out.print(convert);
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
		    		//  System.out.print(convert);
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
	        		 listOfInstance.get(random).addLabel(listOfLabel.get(random1));//adding label to labelInInstance
	        		 Integer intObj = new Integer((int)(long)listOfLabel.get(random1).getLabelid());// casting label id to Integer
	        		 listOfInstance.get(random).setLabelCount(intObj); //counting label in the listOfInstance
	        		 listOfInstance.get(random).calculateTotalNumberOfLabelAssignment(logger); //INSTANCE METRICS STEP 1: calculating Total Number Of Label Assignment
	        		 numberOfUniqueLabel=listOfInstance.get(random).calculateTotalNumberOfUniqueLabel(logger);//INSTANCE METRICS STEP 2:calculating Total Number Of Unique Label
	        		 listOfInstance.get(random).checkUniqueUsers( currentusers.get(l),logger);//INSTANCE METRICS STEP 3:adding user into uniqueUsers list  
	        		 maxFrequentLabel = listOfInstance.get(random).findMaxFrequentLabel(listOfLabel,logger);// finding Max Frequent Label
	        		 maxFrequentLabelPercentage = listOfInstance.get(random).percentageOfMaxLabel(logger);//finding Max Frequent Label percentage
	        		 listOfInstance.get(random).percentageOfLabels(logger);//calculating percentage of each labels into percentageOfLabel array
	        		 entropy=listOfInstance.get(random).entropy(logger);//calculating entropy
	        		 
	        		 listOfInstance.get(random).writingPercOfLabels();//writing percentage of all labels
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
	                 currentusers.get(l).setTotalNumberofÄ±nstance((long)-1,logger);
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
	        		 
	        		
	                 
	        		/* System.out.println("user id: " +one.getUserId()+" instance id: "+one.getInstanceId()+" label: "+one.getLabels().getText()+" date time: "+one.getDatetime());
	        		 System.out.println("InstanceId: "+ listOfInstance.get(random).getInstanceid()+ " LabelId: "+one.getLabels().getLabelid()+ " CurrenOlanLabelKaÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§KezLabellendi: " + listOfInstance.get(random).getCountOfLabel().get((int)one.getLabels().getLabelid()-1)+
	        				 "\nnTotalCountOfLabelAssignment: " + listOfInstance.get(random).getTotalNumberOfLabelAssignment()+ " UniqueLabel:"+ numberOfUniqueLabel+
	        				 	" UniqueUser: "+ listOfInstance.get(random).getUniqueUsers().size() + " FrequentyLabelId: " + maxFrequentLabel.getLabelid() + " percentage: %"+ maxFrequentLabelPercentage +
	        				 	" Entropy: "+ entropy +"\n");*/
	        		 
	        		
	                // System.out.println("user id: " +one.getUserId()+" instance id: "+one.getInstanceId()+" label: "+one.getLabels().getText()+" date time: "+one.getDatetime());
	        		 
	         }
	    
		

	  i=0;
	  endTime=0;
	  startTime=0;
	  while(i<5) {
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
		 				
		 		  currentu.getLabeled().get(random).addLabel(two.getLabels());
		 		  Integer intObj = new Integer((int)(long)two.getLabels().getLabelid());
		 		  currentu.getLabeled().get(random).setLabelCount(intObj);
		 		  //This functions are related to USER METRICS
		 		  currentu.getLabeled().get(random).calculateTotalNumberOfLabelAssignment(logger);
		 		  numberOfUniqueLabel=currentu.getLabeled().get(random).calculateTotalNumberOfUniqueLabel(logger);
		 		  currentu.getLabeled().get(random).checkUniqueUsers( currentu,logger);
		 		  maxFrequentLabel = currentu.getLabeled().get(random).findMaxFrequentLabel(listOfLabel,logger);
				  maxFrequentLabelPercentage = currentu.getLabeled().get(random).percentageOfMaxLabel(logger);
				  currentu.getLabeled().get(random).percentageOfLabels(logger);
			      //currentu.getLabeled().get(random).writingPercOfLabels();
				  entropy=currentu.getLabeled().get(random).entropy(logger);
				  listOfLabel.get(random1).checkUniqueInstance(currentu.getLabeled().get(random),logger);		 		
				   currentu.setTotalNumberofÄ±nstance((long)-1,logger);
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
					
					
					  
		 	/*	System.out.println("user id: "+two.getUserId()+ " instance id: "+two.getInstanceId()+" label: "+two.getLabels().getText()+" date time: "+two.getDatetime());
		 		System.out.println("InstanceId: "+ (currentu.getLabeled().get(random).getInstanceid()+ " LabelId: "+two.getLabels().getLabelid()+ " CurrenOlanLabelKaÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§KezLabellendi: " + currentu.getLabeled().get(random).getCountOfLabel().get((int)two.getLabels().getLabelid()-1)+
						 "\nnTotalCountOfLabelAssignment: " + currentu.getLabeled().get(random).getTotalNumberOfLabelAssignment())+" UniqueLabel:"+ numberOfUniqueLabel+
		 					" UniqueUser: "+ currentu.getLabeled().get(random).getUniqueUsers().size()+ " FrequentyLabelId: " + maxFrequentLabel.getLabelid() + " percentage: %"+ maxFrequentLabelPercentage+
		 						" Entropy: "+ entropy +"\n"); */
				 
		 	  
		
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
					cInstance.calculateTotalNumberOfLabelAssignment(logger);
					numberOfUniqueLabel=cInstance.calculateTotalNumberOfUniqueLabel(logger);
					cInstance.checkUniqueUsers( currentu,logger);
					maxFrequentLabel = cInstance.findMaxFrequentLabel(listOfLabel,logger);
					maxFrequentLabelPercentage = cInstance.percentageOfMaxLabel(logger);
					cInstance.percentageOfLabels(logger);
					//cInstance.writingPercOfLabels();
					entropy=cInstance.entropy(logger);

					currentlabel.checkUniqueInstance(cInstance,logger);
					
				
				 
				/*	System.out.println("user id: "+two.getUserId()+" instance id: "+two.getInstanceId()+" label: "+two.getLabels().getText()+" date time: "+two.getDatetime());
					 System.out.println("InstanceId: "+ (cInstance.getInstanceid()+ " LabelId: "+two.getLabels().getLabelid()+ " CurrenOlanLabelKaÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§KezLabellendi: " + cInstance.getCountOfLabel().get((int)two.getLabels().getLabelid()-1)+
							 "\nTotalCountOfLabelAssignment: " + cInstance.getTotalNumberOfLabelAssignment())+" UniqueLabel:"+ numberOfUniqueLabel+
							 	" UniqueUser: "+ cInstance.getUniqueUsers().size()+ " FrequentyLabelId: " + maxFrequentLabel.getLabelid() + " percentage: %"+ maxFrequentLabelPercentage +
							 	" Entropy: "+ entropy +"\n"); */
									
			 		//System.out.println("INSTANCE ID:" +cInstance.getInstanceid()+" " + cInstance.getLabels().get(cInstance.getLabels().size() -1).getLabelid());
					
					currentu.getLabeled().add(cInstance);
					currentu.setNumofuniqueins((long)-1,logger);
					currentu.setLabeled2(two);
				    currentu.setTotalNumberofÄ±nstance((long)-1,logger);
				    currentu.setConsistency();
				    //System.out.println("user id: " +two.getUserId()+" instance id: "+two.getInstanceId()+" label: "+two.getLabels().getText()+" date time: "+two.getDatetime());
                    
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
		    i++;
		     
		     }
	 
	  		System.out.println(currentusers.get(0).getUserId()+" "+currentusers.get(0).getTotalNumberofÄ±nstance()+" "+currentusers.get(0).getLabeled().size());
	  		for( i=0;i<currentusers.get(0).getLabeled2().size();i++) {
	  			System.out.println("user id: " +currentusers.get(0).getLabeled2().get(i).getUserId()+" instance id: "+currentusers.get(0).getLabeled2().get(i).getInstanceId()+" label: "+currentusers.get(0).getLabeled2().get(i).getLabels().getText()+" date time: "+currentusers.get(0).getLabeled2().get(i).getDatetime());
	    	 
	  		}
	   
	  		for(int k=0;k<currentusers.size();k++)
	  			System.out.print(currentusers.get(k).getUserId()+"TOTAL"+currentusers.get(k).getTotalNumberofÄ±nstance()+" "+currentusers .get(k).getLabeled().size()+" "+currentusers.get(k).getNumofuniqueins()+" "+currentusers.get(k).getCheckconsistency()+" "+currentusers.get(k).getConsistency()+"\n");
			
         
	    
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
	   
	   
	   






}//class
	   
