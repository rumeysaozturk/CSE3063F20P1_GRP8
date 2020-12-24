import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// File Name: Singleton.java
public class Singleton {

   private static Singleton singleton = new Singleton( );

   
   private Singleton() { }

   public static Singleton getInstance( ) {
      return singleton;
   }
   protected static void createDatasets(JSONArray jsonArraydataset,ArrayList<DATASET>datasets) throws FileNotFoundException, IOException, ParseException{
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
  
}