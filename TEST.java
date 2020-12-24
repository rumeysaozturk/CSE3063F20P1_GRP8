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
	      File file=new File("Secret/usermet.json"); 

		    for (int t=0;t<dataset1.size();t++) {
		    	File file1=new File(dataset1.get(t).getId()+".json");
		    }
		  
		     if(file.length()==0) { 
		    	 FileWriter file1 = new FileWriter("Secret/usermet.json");
		    	 Gson gson=new Gson();
		 
		       printusermet(file1,listOfUser);
		     
		       
		     
		 	       }else {
	           	
	        	    
		     JSONObject jsonUser1 = (JSONObject) jsonParser.parse(new FileReader("Secret/usermet.json"));
	         JSONArray jsonArrayUser1 = (JSONArray)(jsonUser1.get("users"));
	         for(int i=0; i<jsonArrayUser1.size(); i++) {
	        	 JSONObject a21=(JSONObject) jsonArrayUser1.get(i);
	        	 long TotalNumberofýnstance=(long)a21.get("totalNumberofýnstance");
	        	 long uniq=(long)a21.get("Number of unique instance");
	         	 long checkconsistency=(long)a21.get("consistency");
	        	 listOfUser.get(i).setTotalNumberofýnstance(TotalNumberofýnstance);
	        	 listOfUser.get(i).setNumofuniqueins(uniq);
	        	 listOfUser.get(i).setCheckconsistency(checkconsistency);
	        	 listOfUser.get(i).setConsistency();
	        	  JSONArray jsondataset = (JSONArray)a21.get("dataset id");
	         	    for(int c=0;c<jsondataset.size();c++) {
	         	    	long iddataset=(long)jsondataset.get(c);
                       System.out.println();
                         for(int h=0;h<dataset1.size();h++) {
  	               if(dataset1.get(h).getId()==iddataset) {
  	            	   listOfUser.get(i).getDataset().add(dataset1.get(h));
  	                        } }
	             
	         	    }
	         }}
		     
		     
		     File file2=new File(current.getId()+".json"); 
		     if(file2.length()==0) { 
		       FileWriter file1 = new FileWriter(file2);
		
		        }
		     else {
	         
		      	  JSONObject jsonUser1 = (JSONObject) jsonParser.parse(new FileReader(file2));
		          JSONArray jsonArrayUser1 = (JSONArray)(jsonUser1.get("users"));
		          for(int i=0; i<jsonArrayUser1.size(); i++) {
		         	 JSONObject a21=(JSONObject) jsonArrayUser1.get(i);
		      
		         	 long id=(long)a21.get("user ýd");
		         	 System.out.print(id+" ");
		         	    JSONArray jsonArrayUserlabeled = (JSONArray)a21.get("labeled");
		         	    for(int c=0;c<jsonArrayUserlabeled.size();c++) {
		         	   	 JSONObject a211=(JSONObject) jsonArrayUserlabeled.get(c);
		         	   long h1=(long)a211.get("instance id " );
		         	   INSTANCE a=new INSTANCE();
		         	   listOfUser.get(i).getLabeled().add(listOfInstance.get(a.find(listOfInstance, h1)));
		         	
		          }
		         	    JSONArray jsonArrayUserlabeled2 = (JSONArray)a21.get("labeled2");
		         	    for(int c=0;c<jsonArrayUserlabeled2.size();c++) {
		            	   	 JSONObject a211=(JSONObject) jsonArrayUserlabeled2.get(c);
		            	   long h1=(long)a211.get("instance id" );
		            	   long h2=(long)a211.get("label id");
		            	   LABEL label1=new LABEL();
		            	   LABEL_ASSIGNMENT a1=new LABEL_ASSIGNMENT(id,h1,listOfLabel.get(label1.find(listOfLabel, h2)));
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
		    	  listOfInstance.get(z).percentageOfLabels();
		      }
		      

		      //UNIQUE ARRAY 
		      JSONArray uniqueArray = (JSONArray)(jsonRead.get("UniqueArray"));
		      for (int z=0;z<uniqueArray.size();z++) {
		    	  JSONArray object=(JSONArray)uniqueArray.get(z);
		    	  for(int j=0; j<object.size(); j++) {
		    		  convert=String.valueOf(object.get(j));
		    		   for(int k=0; k<listOfUser.size(); k++) {
		    		   if(listOfUser.get(k).getUserId()==(long)Integer.valueOf(convert)) {
		    			   listOfInstance.get(z).checkUniqueUsers(listOfUser.get(k));
			    		   
			    		     USER cuser=listOfUser.get(k);
			        	     if(find(currentusers,cuser)) {
			        	    	 
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
		    		  System.out.print(convert);
		    	  }
		    	  listOfLabel.get(z).setUniqueInstance(labelArrayPrevious);	    	  
		      } 
      
	         }
	         
	         
	         else {//----------------------------------- IF THERE IS NO PREVIOUS FILE MAKE FIRST ASSIGNMENT
	        	 

	   	      
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
	        	   }}}
	        for(int k=0;k<currentusers.size();k++) {
	        	if(currentusers.get(k).getDataset().contains(current)) {
	        		continue;
	        	}else {
	        		currentusers.get(k).getDataset().add(current);
	        	}
	        }
	        	    
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
	        		 
	        		 listOfInstance.get(random).writingPercOfLabels();//writing percentage of all labels
	        		 listOfLabel.get(random1).checkUniqueInstance(listOfInstance.get(random));
	        		 
	        		 currentusers.get(l).addInstance((listOfInstance.get(random)));
	        		 currentusers.get(l).setNumofuniqueins((long)-1);
	        		 currentusers.get(l).setLabeled2(one);
	                 currentusers.get(l).setTotalNumberofýnstance((long)-1);
	                 currentusers.get(l).setConsistency();
				    //USER METRICS
					 FileWriter  file3 =new FileWriter("Secret/usermet.json");
			     	 FileWriter file1 =new FileWriter(file2);
				     file1.write(" ");	
				     printuserarray(file1,listOfUser);
				     file3.write(" ");	
				     printusermet(file3,listOfUser);	
	                 
	                 //INSTANCE METRICS
	                 print.backup(listOfInstance,listOfLabel,current.getId());
	        		 print.printInstanceMetrics(listOfInstance,listOfLabel, current.getId(),logger);
	        		 
	        		 //DATASET METRICS
	        		 print.printDatasetMetrics(listOfInstance,listOfLabel,current.getId(),current.getNumofuser(),logger,currentusers);
	                 
	        		 System.out.println("user id: " +one.getUserId()+" instance id: "+one.getInstanceId()+" label: "+one.getLabels().getText()+" date time: "+one.getDatetime());
	        		 System.out.println("InstanceId: "+ listOfInstance.get(random).getInstanceid()+ " LabelId: "+one.getLabels().getLabelid()+ " CurrenOlanLabelKaÃƒÂ§KezLabellendi: " + listOfInstance.get(random).getCountOfLabel().get((int)one.getLabels().getLabelid()-1)+
	        				 "\nnTotalCountOfLabelAssignment: " + listOfInstance.get(random).getTotalNumberOfLabelAssignment()+ " UniqueLabel:"+ numberOfUniqueLabel+
	        				 	" UniqueUser: "+ listOfInstance.get(random).getUniqueUsers().size() + " FrequentyLabelId: " + maxFrequentLabel.getLabelid() + " percentage: %"+ maxFrequentLabelPercentage +
	        				 	" Entropy: "+ entropy +"\n");
	        		 
	        		
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
		          currentu.setCheckconsistency((long)-1);
	
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
				  listOfLabel.get(random1).checkUniqueInstance(currentu.getLabeled().get(random));		 		
				   currentu.setTotalNumberofýnstance((long)-1);
			 	    currentu.setConsistency();
				  
				  
				    //WRITE USER METRICS
				    FileWriter  file3 =new FileWriter("Secret/usermet.json");
					FileWriter file1 =new FileWriter(file2);
					file1.write(" ");	
					printuserarray(file1,listOfUser);
			        file3.write(" ");	
			        printusermet(file3,listOfUser);	
			        
			        //INSTANCE METRICS
			        print.backup(listOfInstance,listOfLabel,current.getId());
					print.printInstanceMetrics(listOfInstance,listOfLabel,current.getId(),logger);
					
					//DATASET METRICS
	        		 print.printDatasetMetrics(listOfInstance,listOfLabel,current.getId(),current.getNumofuser(),logger,currentusers);
					  
		 		System.out.println("user id: "+two.getUserId()+ " instance id: "+two.getInstanceId()+" label: "+two.getLabels().getText()+" date time: "+two.getDatetime());
		 		System.out.println("InstanceId: "+ (currentu.getLabeled().get(random).getInstanceid()+ " LabelId: "+two.getLabels().getLabelid()+ " CurrenOlanLabelKaÃƒÂ§KezLabellendi: " + currentu.getLabeled().get(random).getCountOfLabel().get((int)two.getLabels().getLabelid()-1)+
						 "\nnTotalCountOfLabelAssignment: " + currentu.getLabeled().get(random).getTotalNumberOfLabelAssignment())+" UniqueLabel:"+ numberOfUniqueLabel+
		 					" UniqueUser: "+ currentu.getLabeled().get(random).getUniqueUsers().size()+ " FrequentyLabelId: " + maxFrequentLabel.getLabelid() + " percentage: %"+ maxFrequentLabelPercentage+
		 						" Entropy: "+ entropy +"\n");
				 
		 	  
		
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

					currentlabel.checkUniqueInstance(cInstance);
					
				
				 
					System.out.println("user id: "+two.getUserId()+" instance id: "+two.getInstanceId()+" label: "+two.getLabels().getText()+" date time: "+two.getDatetime());
					 System.out.println("InstanceId: "+ (cInstance.getInstanceid()+ " LabelId: "+two.getLabels().getLabelid()+ " CurrenOlanLabelKaÃƒÂ§KezLabellendi: " + cInstance.getCountOfLabel().get((int)two.getLabels().getLabelid()-1)+
							 "\nTotalCountOfLabelAssignment: " + cInstance.getTotalNumberOfLabelAssignment())+" UniqueLabel:"+ numberOfUniqueLabel+
							 	" UniqueUser: "+ cInstance.getUniqueUsers().size()+ " FrequentyLabelId: " + maxFrequentLabel.getLabelid() + " percentage: %"+ maxFrequentLabelPercentage +
							 	" Entropy: "+ entropy +"\n");
									
			 		//System.out.println("INSTANCE ID:" +cInstance.getInstanceid()+" " + cInstance.getLabels().get(cInstance.getLabels().size() -1).getLabelid());
					
					currentu.getLabeled().add(cInstance);
					 currentu.setNumofuniqueins((long)-1);
					currentu.setLabeled2(two);
				    currentu.setTotalNumberofýnstance((long)-1);
				    currentu.setConsistency();
				    System.out.println("user id: " +two.getUserId()+" instance id: "+two.getInstanceId()+" label: "+two.getLabels().getText()+" date time: "+two.getDatetime());
                    
				    
				    //WRITE USER METRICS
				    FileWriter  file3 =new FileWriter("Secret/usermet.json");
					FileWriter file1 =new FileWriter(file2);
					file1.write(" ");	
					printuserarray(file1,listOfUser);
			        file3.write(" ");	
			        printusermet(file3,listOfUser);	  
			        
			    	//INSTANCE METRICS
					print.backup(listOfInstance,listOfLabel,current.getId());
					print.printInstanceMetrics(listOfInstance,listOfLabel,current.getId(),logger);
			        
			      //DATASET METRICS
	        		 print.printDatasetMetrics(listOfInstance,listOfLabel, current.getId(),current.getNumofuser(),logger,currentusers);
				    break;  
				}

		
		     }//while
			
		    }//else
		    //Finish the timer
		    endTime = System.nanoTime();
		    currentu.setTime(endTime - startTime);
		    i++;
		     
		     }
	 
	   System.out.println(currentusers.get(0).getUserId()+" "+currentusers.get(0).getTotalNumberofýnstance()+" "+currentusers.get(0).getLabeled().size());
	   for( i=0;i<currentusers.get(0).getLabeled2().size();i++) {
		   System.out.println("user id: " +currentusers.get(0).getLabeled2().get(i).getUserId()+" instance id: "+currentusers.get(0).getLabeled2().get(i).getInstanceId()+" label: "+currentusers.get(0).getLabeled2().get(i).getLabels().getText()+" date time: "+currentusers.get(0).getLabeled2().get(i).getDatetime());
	    	 
	   }
	   
	   for(int k=0;k<currentusers.size();k++)
	System.out.print(currentusers.get(k).getUserId()+" "+currentusers .get(k).getLabeled().size()+" "+currentusers.get(k).getNumofuniqueins()+" "+currentusers.get(k).getCheckconsistency()+" "+currentusers.get(k).getConsistency()+"\n");

         
	    
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


public static void printusermet(FileWriter file1,ArrayList<USER>listofuser) throws IOException {
	
	
    
	  file1.write("{");
      file1.write("\"users\":[");
	  for(int c=0;c<listofuser.size();c++) {
		 file1.write("{\"userId\":"+listofuser.get(c).getUserId()+","+"\"totalNumberofýnstance\":"+listofuser.get(c).getTotalNumberofýnstance()+", \"Number of unique instance\":"+
	     listofuser.get(c).getNumofuniqueins()+",\"consistency\":"+ listofuser.get(c).getCheckconsistency()+","+
	     "\"consistency probality\":"+ listofuser.get(c).getConsistency()+",");
	  
		 file1.write("\"number of dataset\":"+listofuser.get(c).getDataset().size()+",");
		  file1.write("\"dataset id\":[");
		 	 for(int x=0;x<listofuser.get(c).getDataset().size();x++) {
		 	      file1.write(listofuser.get(c).getDataset().get(x).getId()+",");
		 	   	 }
		 
		 	   	file1.write("],");
		 	   file1.write("\"average time\":"+listofuser.get(c).average()+",\"standart deviation\":"+listofuser.get(c).stdeviation()+"}\n");
	  
	  }
    
    //Print Users to json file
 
   // file1.write("]\n");
    file1.write("]\n}\n");
   
    file1.flush();
    file1.close();
}

public static  void printuserarray(FileWriter file2,ArrayList<USER>listofuser) throws IOException {
	 file2.write("{");
    file2.write("\"users\":[");
 
    for (int c=0;c<listofuser.size();c++) {
   	file2.write("{\"user ýd\":"+listofuser.get(c).getUserId()+",");
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


}//class
	   
