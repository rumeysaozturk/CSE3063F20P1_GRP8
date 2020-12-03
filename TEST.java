import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TEST {

	   public static void main(String args[]) {
	      //Creating a JSONParser object
		   
		  
		   
	      JSONParser jsonParser = new JSONParser();
	      try {
	         //Parsing the contents of the JSON file
	         JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("CES3063F20_LabelingProject_Input-2.json"));
	         //Forming URL
	         System.out.println("Contents of the JSON are: ");
	         System.out.println("dataset id: "+jsonObject.get("dataset id"));
	         System.out.println("Dataset name: "+jsonObject.get("dataset name"));
	         System.out.println("Maximum number of labels per instance: "+jsonObject.get("maximum number of labels per instance"));
	       
	         //Retrieving the array
	         JSONArray jsonArray = (JSONArray)(jsonObject.get("class labels"));
	         System.out.println("");
		       
	         //Iterating the contents of the array
	      
	        
	      JSONArray jsonArray1 = (JSONArray)(jsonObject.get("instances"));
	         System.out.println("");
	        
	         //Iterating the contents of the array
	    
	        
	        
	      ArrayList<LABEL>listl=new ArrayList<>();
	      
	      for (int i=0;i<jsonArray.size();i++) {
	    	  JSONObject a=(JSONObject) jsonArray.get(i);
		        long id =(long) a.get("label id");
		        String text=(String) a.get("label text");
		        LABEL a1=new LABEL(id,text);
		        listl.add(a1);
		        
	      }
	      
      ArrayList<INSTANCE>listl2=new ArrayList<>();
	      
	      for (int i=0;i<jsonArray1.size();i++) {
	    	  JSONObject a2=(JSONObject) jsonArray1.get(i);
		        long id1 =(long) a2.get("id");
		        String text2=(String) a2.get("instance");
		       INSTANCE a3=new INSTANCE(id1,text2);
		        listl2.add(a3);
	        
	               
	      }    
	      
	      for (int i=0;i<listl.size();i++) {
	
		     System.out.println(listl.get(i).getLabelid()+"  "+listl.get(i).getText());
		        
	      }
	      
	      
	      for (int i=0;i<listl2.size();i++) {
	
		     System.out.println(listl2.get(i).getInstanceid()+"  "+listl2.get(i).getText());
		        
	      }
	    
	      
	      
	      } catch (FileNotFoundException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	            e.printStackTrace();
	      } catch (ParseException e) {
	            e.printStackTrace();
	      }
	   }}


