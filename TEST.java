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
	        
	        