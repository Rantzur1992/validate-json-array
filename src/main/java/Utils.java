import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.testproject.java.sdk.v2.exceptions.FailureException;

public class Utils {
    public static JsonArray getJsonElements(String jsonArray) throws FailureException {
        JsonArray rootObj;
        JsonParser parser = new JsonParser();
        try{
            rootObj = parser.parse(jsonArray).getAsJsonArray();
        }
        catch (JsonSyntaxException e){
            throw new FailureException("Error: " + e.toString());
        }
        return rootObj;
    }
}
