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

public class TEST {

	   public static void main(String args[]) throws FileNotFoundException, IOException, ParseException {
	      //Creating a JSONParser object
		   
		  ArrayList<LABEL>listOfLabel=new ArrayList<>();
		  ArrayList<INSTANCE>listOfInstance=new ArrayList<>();
	      JSONParser jsonParser = new JSONParser();
	      int maxLabel=0;
	      JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("CES3063F20_LabelingProject_Input-2.json"));

	         //Parsing the contents of the JSON file
	         //Forming URL
	         //System.out.println("Contents of the JSON are: ");
	         System.out.println("Dataset id: "+jsonObject.get("dataset id") + " is created");
	         System.out.println("Dataset name: "+jsonObject.get("dataset name")+ " is created");
	         System.out.println("maximum number of labels per instance: "+jsonObject.get("maximum number of labels per instance")+ " is created");
	         //Take the maximum labels for each instances
	         maxLabel = Integer.parseInt(jsonObject.get("maximum number of labels per instance").toString());
	      
	         
	         //Retrieving the array
	         JSONArray jsonArray = (JSONArray)(jsonObject.get("class labels"));
	         System.out.println("");
	        
	         //Iterating the contents of the array
	      
	        
	      JSONArray jsonArray1 = (JSONArray)(jsonObject.get("instances"));
	         System.out.println("");
	        
	         //Iterating the contents of the array
	    
	        
	        	      
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
	      
	      for (int i=0;i<listOfLabel.size();i++) {
	
		     System.out.println(listOfLabel.get(i).getLabelid()+"  "+listOfLabel.get(i).getText());
		        
	      }
	      
	      
	      for (int i=0;i<listOfInstance.size();i++) {
	
		     System.out.println(listOfInstance.get(i).getInstanceid()+"  "+listOfInstance.get(i).getText());
		        
	      }
	    
	     
	      
	      
	      //GENERATE A USER****************************************************************
		  ArrayList <USER> listOfUser =new ArrayList<USER>();
		  USER user;
		  int userID=1;
	      int i=0;
	      int j=0;
	      int n=0;
	      String userName="";
	      int lengthUser=4;
	      for(i=1; i<=lengthUser; i++) {
	    	  userName="RandomLearningMechanism"+userID;
	    	  user=new USER(userID,userName,"RandomBoot");
	    	  listOfUser.add(user);
	    	  userID++;
	      }
          
	      //For loop for instance***********************************************************
		  ArrayList <LABEL_ASSIGNMENT> listOfLabelAssignment =new ArrayList<LABEL_ASSIGNMENT>();
		  LABEL_ASSIGNMENT element ; 
		  LABEL temp;
  	      ArrayList<Integer> numberOfLabelIndex= new ArrayList<Integer>(); 
	  	  ArrayList<LABEL> storeOfLabel= new ArrayList<LABEL>(); 
	  	  ArrayList<Integer> numberOfUserIndex=new ArrayList<Integer>(); 

	  	  
	      int sizeOfLabels=listOfLabel.size();
	      int numberOfUser=0;
	      int numberOfLabels=0;
	      int currentLabel=0;
	      int currentUser=0;
	      int controlIn=0;
	      int currentSize=0;
	      boolean b=false;
	      int k=0;
	      int l=0; int m=0; 
	      i=0;
          j=0;
	      for(i=0; i<listOfInstance.size(); i++) {
	  	  storeOfLabel= new ArrayList<LABEL>(); 
	  	      //Random value of max users
    	      numberOfUser= (int) (Math.random()* lengthUser)+1;

	    	  //The random value shows how many labels for current instance
	    	  numberOfLabels = (int) (Math.random()* maxLabel)+1;	    	  
	    	  
	    	  j=0;
	    	  currentUser=0;
	    	  while(j<numberOfUser) {
	    		controlIn=0;
	    		//The random value shows which label on the instance.
	    	    currentUser=(int) (Math.random()*(lengthUser));  
	    	    
	    	    //Check the label whether it exists.
	    	    if(numberOfUserIndex.size()!=0) {
	    	    	if(!numberOfUserIndex.contains(currentUser)) {
	    	    		numberOfUserIndex.add(currentUser);
	    	    		j++;
	    	    		controlIn=1;
	    	    	}
				   }
	    	    else {
    	    		numberOfUserIndex.add(currentUser);
    	    		j++;
    	    		controlIn=1;
	    	    }	
	    	    
	    	    if(controlIn==1) {
	    	      k=0;
	   	    	  currentLabel=0;
	   	    	  while(k<numberOfLabels) {
	   	    		//The random value shows which label on the instance.
	   	    	    currentLabel=(int) (Math.random()*sizeOfLabels);  
	   	    	    //System.out.println(currentLabel);
	   	    	    //Check the label whether it exists.
	   	    	    if(numberOfLabelIndex.size()!=0) {
	   	    	    	if(!numberOfLabelIndex.contains(currentLabel)) {
	   	    	    		numberOfLabelIndex.add(currentLabel);
	   	    	    		storeOfLabel.add(listOfLabel.get(currentLabel));
	   	    	    		k++;
	   	    	    	}
	   				   }
	   	    	    else {
	       	    		numberOfLabelIndex.add(currentLabel);
	       	    		storeOfLabel.add(listOfLabel.get(currentLabel));
	       	    		k++;
	   	    	    }	            
	   	    	  } 
	   	    	  
	   	    		   	    	  
	   	    	  
	   	    	  element=new LABEL_ASSIGNMENT(listOfUser.get(currentUser),listOfInstance.get(i),storeOfLabel);
	   	          listOfLabelAssignment.add(element);
	   	          
	   		  	  
	   	          System.out.print("instance id:" + listOfInstance.get(i).getInstanceid() + ", class labels ids:[" );
	   		      

	   	          n=0;
	   	          for(n=0; n<storeOfLabel.size(); n++) {
	   	        	  if(n==storeOfLabel.size()-1) {
	   	        		  System.out.print(storeOfLabel.get(n).getLabelid()+"],");
	   	        	  }
	   	        	  else {
	   	        		  System.out.print(storeOfLabel.get(n).getLabelid()+",");
	   	        	  }
	   	          }
	   	          System.out.print(" user :" +listOfUser.get(currentUser).getUserId()+", ");
	   	          currentSize=listOfLabelAssignment.size();
	   	          System.out.println(" datetime :" +listOfLabelAssignment.get(currentSize-1).getDatetime()+" is created.");

                  storeOfLabel= new ArrayList<LABEL>(); 
	   	          numberOfLabelIndex.clear();
	    	    }
	    	  }
	    	  numberOfUserIndex.clear();
	      }
      
	   }
	   }
