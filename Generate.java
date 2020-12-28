import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Generate {

	
	Generate(){
		
	}
	public void readDataset(Logger logger,DATASET current) {
        logger.info("Input file is read succesfully.\n");
        logger.info("Dataset id: "+ current.getId()+ " is created");
        logger.info("Dataset name: "+current.getName()+ " is created");
        logger.info("maximum number of labels per instance: "+current.getMax_label()+ " is created\n");	
	}
	public void readInstance(Logger logger,JSONArray jsonArrayInstance,ArrayList<LABEL> listOfLabel,ArrayList<INSTANCE >listOfInstance ) {      
	      INSTANCE addInstance;	         
	      for (int i=0;i<jsonArrayInstance.size();i++) {
	    	    JSONObject jsonArray1Object=(JSONObject) jsonArrayInstance.get(i);
	    	    addInstance =new INSTANCE((long) jsonArray1Object.get("id"),(String) jsonArray1Object.get("instance"));
	    	    addInstance.createListForLabelCount(listOfLabel.size());// creating countOfLabel list and assign 0 to countOfLabel list in the beginning
	    	    addInstance.createListForLabelPercentage(listOfLabel.size());//// creating percentageOfLabel list and assign 0 to percentageOfLabel list in the beginning
		        listOfInstance.add(addInstance); 
	      } 

	}	
	public void readLabel(Logger logger,JSONArray jsonArray,ArrayList<LABEL> listOfLabel) {
		LABEL label1;
	      for (int i=0;i<jsonArray.size();i++) {
	    	  JSONObject jsonArrayObject=(JSONObject) jsonArray.get(i);
		      label1=new LABEL((long) jsonArrayObject.get("label id"),(String) jsonArrayObject.get("label text"));
		        listOfLabel.add(label1);
		        
	      }
	}
	//Read user
	public void readUser(Logger logger,JSONArray jsonArrayUser,ArrayList<USER> listOfUser) {
	      USER user;
	      for(int i=0; i<jsonArrayUser.size(); i++) {
	    	  JSONObject jsonArrayUserObject=(JSONObject) jsonArrayUser.get(i);
		        user = new USER((long) jsonArrayUserObject.get("user id"),(String) jsonArrayUserObject.get("user name"),(String) jsonArrayUserObject.get("user type"),(double) jsonArrayUserObject.get("ConsistencyCheckProbability"));
	    	    listOfUser.add(user);
	    	    user.printUser(logger);
	    	   
	      }
	}
}
