package Datareader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonReader {

    public static JSONObject readJsonFile(String filePath) 
    {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;

        try (FileReader reader = new FileReader(filePath)) {
            // Parse the JSON file
            Object obj = jsonParser.parse(reader);
            jsonObject = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}