import com.google.gson.*;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.sdk.v2.addons.GenericAction;
import io.testproject.java.sdk.v2.addons.helpers.AddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Action(name = "Validate JSON array", description = "Validates if a JSON array " +
        "contains specific given keys and values")
public class ValidateJsonArray implements GenericAction {
    public void setJsonValues(String jsonValues) {
        this.jsonValues = jsonValues;
    }
    public void setJsonArray(String jsonArray) {
        this.jsonArray = jsonArray;
    }
    public void setIndexOfJsonElement(String indexOfJsonElement) { this.indexOfJsonElement = indexOfJsonElement; }

    @Parameter(description = "Json value and their expected result delimited by a newline," +
            "for example:{ platform:TestProject\n" +
            "name:Ran } " +
            "It will check for value platform and expected result of TestProject.")
    public String jsonValues;
    @Parameter(description = "The JSON Array to iterate on.")
    public String jsonArray;
    @Parameter(description = "The index of the json element to validate, zero based.(Keep empty to validate on all elements)")
    public String indexOfJsonElement = "";

    @Override
    public ExecutionResult execute(AddonHelper helper) throws FailureException {
        String[] commaDelimited = jsonValues.split("\\r?\\n");
        Map<String, String> keyValue = new HashMap<>();
        for(String content : commaDelimited){
            Pattern p = Pattern.compile("([^:]*):(.*)");
            Matcher m = p.matcher(content);
            if (m.matches()) {
                keyValue.put(m.group(1), m.group(2));
            }
        }
        JsonArray rootObj = Utils.getJsonElements(jsonArray);
        if(indexOfJsonElement.isEmpty()) {
            for(JsonElement jsonElement : rootObj) {
                iterateAction(keyValue, jsonElement);
            }
        } else {
            int index = Integer.parseInt(indexOfJsonElement);
            if(rootObj.size() < index || index < 0)
                throw new FailureException("Index was out of bounds!");
            JsonElement jsonElement = rootObj.get(index);
            iterateAction(keyValue, jsonElement);
        }
        helper.getReporter().result("All values and their corresponding keys are in place.");
        return ExecutionResult.PASSED;
    }


    private void iterateAction(Map<String, String> keyValue, JsonElement jsonElement) throws FailureException {
        for (String key : keyValue.keySet()) {
            if (jsonElement != null) {
                String value = keyValue.get(key);
                JsonObject asJsonObject = jsonElement.getAsJsonObject();
                if (!asJsonObject.has(key))
                    throw new FailureException(String.format("The key '%s' was not found in '%s'", key, jsonElement));
                if (!asJsonObject.get(key).getAsString().equals(value)) {
                    throw new FailureException(String.format("The key: '%s' did not match the value: '%s' in JSON array, found: '%s'"
                            , key
                            , value
                            , asJsonObject.get(key)));
                }
            } else {
                throw new FailureException("Could not find json element in json array");
            }
        }
    }
}
