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

public class TEST {

	   public static void main(String args[]) throws FileNotFoundException, IOException, ParseException, SecurityException {
	      

		   //Creating a JSONParser object
		   
		  ArrayList<LABEL>listOfLabel=new ArrayList<>();
		  ArrayList<INSTANCE>listOfInstance=new ArrayList<>();
	      JSONParser jsonParser = new JSONParser();
	      int maxLabel=0;
	      JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("CES3063F20_LabelingProject_Input-2.json"));
	      JSONObject jsonUser = (JSONObject) jsonParser.parse(new FileReader("configuration.json"));

	         //Parsing the contents of the JSON file
	         //Forming URL
	      
	         System.out.println("Input file is read succesfully.\n");
	         System.out.println("Dataset id: "+jsonObject.get("dataset id") + " is created");
	         System.out.println("Dataset name: "+jsonObject.get("dataset name")+ " is created");
	         System.out.println("maximum number of labels per instance: "+jsonObject.get("maximum number of labels per instance")+ " is created\n");
	         //Take the maximum labels for each instances
	         maxLabel = Integer.parseInt(jsonObject.get("maximum number of labels per instance").toString());
	      
	         
	         //Retrieving the array
	         JSONArray jsonArray = (JSONArray)(jsonObject.get("class labels"));
	         
	        
	         //Iterating the contents of the array
	      
	        
	         JSONArray jsonArray1 = (JSONArray)(jsonObject.get("instances"));
	       
	        
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
		        user = new USER(id1,text2,text3);
	    	    listOfUser.add(user);
	    	    user.printUser();
	      }
	      System.out.println();
          
	      
	      
	      //**********************RANDOM ASSIGNMENT************************************************
		  ArrayList <LABEL_ASSIGNMENT> listOfLabelAssignment =new ArrayList<LABEL_ASSIGNMENT>();
		  LABEL_ASSIGNMENT element ; 
  	      ArrayList<Integer> numberOfLabelIndex= new ArrayList<Integer>(); 
	  	  ArrayList<LABEL> storeOfLabel= new ArrayList<LABEL>(); 
	  	  ArrayList<Integer> numberOfUserIndex=new ArrayList<Integer>(); 

	      int sizeOfLabels=listOfLabel.size();
	      int numberOfUser=0;
	      int numberOfLabels=0;
	      int currentLabel=0;
	      int currentUser=0;
	      int controlIn=0;
	      int k=0; int i=0; int j=0;
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
	   	    	    	if(!numberOfLabelIndex.contains(currentLabel+1)) {
	   	    	    		numberOfLabelIndex.add(currentLabel+1);
	   	    	    		storeOfLabel.add(listOfLabel.get(currentLabel));
	   	    	    		k++;
	   	    	    	}
	   				   }
	   	    	    else {
	       	    		numberOfLabelIndex.add(currentLabel+1);
	       	    		storeOfLabel.add(listOfLabel.get(currentLabel));
	       	    		k++;
	   	    	    }	            
	   	    	  } 
	    
	   	    	  element=new LABEL_ASSIGNMENT(listOfUser.get(currentUser).getUserId(),listOfInstance.get(i).getInstanceid(),storeOfLabel);
	   	          listOfLabelAssignment.add(element);
	   	          //print actions
	   	          element.sortLabels();
	   	          element.print();
	   	     	  
                  storeOfLabel= new ArrayList<LABEL>(); 
	   	          numberOfLabelIndex.clear();
	    	    }
	    	  }
	    	  numberOfUserIndex.clear();
	      }
	      	      
	    
	      //Creating a JSONObjectWrite object to write json file.
	      JSONObject jsonObjectWrite = new JSONObject();
	      JSONArray classAssignmentArray = new JSONArray();
	     
	      JSONArray writeLabels;
	      JSONArray writeUsers;
	      
	     //jsonObjectWrite.putAll(jsonObject);
	     
	  /*   for(i=0; i<listOfLabelAssignment.size(); i++) {

	    	 JSONObject   classAssignmentObjectDetails = new JSONObject();
		     
	    	 classAssignmentObjectDetails.put("ainstance id",listOfLabelAssignment.get(i).getInstance().getInstanceid() );
	    
	    	 writeLabels = new JSONArray(); 
	    	 
	/*    	for(j=0; j<listOfLabelAssignment.get(i).getLabels().size(); j++) {
	    		
	    		writeLabels.add(listOfLabelAssignment.get(i).getLabels().get(j).getLabelid());
	     }
	    	 classAssignmentObjectDetails.put("cclass label ids", writeLabels );	    	 
		     classAssignmentObjectDetails.put("buser id", listOfLabelAssignment.get(i).getUser().getUserId() );	
		     classAssignmentObjectDetails.put("xdatetime", listOfLabelAssignment.get(i).getDatetime() );
	    
	    	 classAssignmentArray.add(classAssignmentObjectDetails);
  	
	     }*/
	    // System.out.println(listOfLabelAssignment.get(0).getInstance().getInstanceid());
	     
	   //jsonObjectWrite.put("class label assignment:" ,classAssignmentArray);
	      
	      //Inserting key-value pairs into the json object
	     
	     
	     //Gson gsonInput=new GsonBuilder().setPrettyPrinting().create();
	     Gson gson=new Gson();
	     String json2="";
	 
	    // jsonObjectWrite.put("class label assignment:" ,json);
	     //System.out.print(json);
	      try {
	         FileWriter file = new FileWriter("output.json");
	         //file.write(gsonInput.toJson(jsonObject));
	         file.write("{\"dataset id\":"+(jsonObject.get("dataset id").toString())+"\n"+"\"dataset name\":"
	    	         +(jsonObject.get("dataset name")).toString()+"\n");
	    	    
	         file.write("class label assignments" +":[\n");
	         //Print Assignment to json file
	         for(int c=0;c<listOfLabelAssignment.size();c++) {

	    	     json2=gson.toJson(listOfLabelAssignment.get(c));
	    	     file.write(json2+",\n");
	         }
	         file.write("]\n");
	         file.write("}");
	         file.flush();
	         file.close();
	         System.out.println("\nOutput is written sucesfully.");
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      //System.out.println();
	     // System.out.println("JSON file created: "+json2);
	   }
	   
}
