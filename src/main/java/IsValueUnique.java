import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.sdk.v2.addons.GenericAction;
import io.testproject.java.sdk.v2.addons.helpers.AddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

public class IsValueUnique implements GenericAction {
    @Parameter(description = "The JSON Array to check.")
    private String jsonArray = "";
    @Parameter(description = "The key")
    private String key = "";
    @Parameter(description = "The corresponding value for {{key}}")
    private String value = "";
    @Parameter(description = "Expected result (true/false)")
    private boolean expectedResult = true;

    public void setJsonArray(String jsonArray) {
        this.jsonArray = jsonArray;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setExpectedResult(boolean expectedResult) {
        this.expectedResult = expectedResult;
    }

    @Override
    public ExecutionResult execute(AddonHelper helper) throws FailureException {
        JsonArray rootObj = Utils.getJsonElements(jsonArray);
        int counter = 0;
        String respondingValue = "";
        for(JsonElement element : rootObj) {
            JsonObject asJsonObject = element.getAsJsonObject();
            try{
                respondingValue = asJsonObject.get(key).getAsString();
            } catch (Exception e) {
                throw new FailureException("Could not find " + key);
            }
            if(respondingValue.equals(value)) {
                counter++;
            }
        }
        if (counter > 1) {
            if(expectedResult) {
                helper.getReporter().result(String.format("%s is not unique, expected result was set to true.", value));
                return ExecutionResult.FAILED;
            } else {
                helper.getReporter().result(String.format("%s is not unique, expected result was set to false.", value));
                return ExecutionResult.PASSED;
            }
        } else if (counter == 1) {
            if(expectedResult) {
                helper.getReporter().result(String.format("%s is unique, expected result was set to true.", value));
                return ExecutionResult.PASSED;
            } else {
                helper.getReporter().result(String.format("%s is unique, expected result was set to false.", value));
                return ExecutionResult.FAILED;
            }
        } else {
            helper.getReporter().result("The value was not found");
            return ExecutionResult.FAILED;
        }
    }
}