import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.addons.GenericAction;
import io.testproject.java.sdk.v2.addons.helpers.AddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;


@Action(name = "Does JSON Array contains JSON element?", description = "Does {{jsonArray}} contains {{jsonElement}}?")
public class DoesJsonArrayContains implements GenericAction {

    @Parameter(description = "The JSON Array to check.")
    public String jsonArray = "";

    public void setJsonArray(String jsonArray) {
        this.jsonArray = jsonArray;
    }

    public void setJsonElement(String jsonElement) {
        this.jsonElement = jsonElement;
    }

    @Parameter(description = "The JSON element.")
    public String jsonElement = "";
    @Parameter(description = "The index where the Json element was found", direction = ParameterDirection.OUTPUT)
    public int jsonElementIndex = 0;

    @Override
    public ExecutionResult execute(AddonHelper helper) throws FailureException {
        try {
            JsonArray rootObj = Utils.getJsonElements(jsonArray);
            JsonElement asJsonElement = new JsonParser().parse(jsonElement);
            if(rootObj.contains(asJsonElement)) {
                jsonElementIndex = findIndex(asJsonElement, rootObj);
                helper.getReporter().result(String.format("Found '%s' in index number '%s'", jsonElement, findIndex(asJsonElement, rootObj)));
                return ExecutionResult.PASSED;
            } else {
                helper.getReporter().result("Json element was not found.");
                return ExecutionResult.FAILED;
            }
        } catch (Exception e) {
            helper.getReporter().result("Failed to parse JSON element");
            return ExecutionResult.FAILED;
        }
    }

    private int findIndex(JsonElement jsonElement, JsonArray rootObj) {
        int index = 0;
        for(JsonElement element : rootObj) {
            if(jsonElement.equals(element))
                break;
            index++;
        }
        return index;
    }
}
