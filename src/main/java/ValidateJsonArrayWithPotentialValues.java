import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.sdk.v2.addons.GenericAction;
import io.testproject.java.sdk.v2.addons.helpers.AddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import io.testproject.java.sdk.v2.reporters.ActionReporter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class ValidateJsonArrayWithPotentialValues implements GenericAction {

    @Parameter(description = "The JSON Array to iterate on.")
    public String jsonArray;

    @Parameter(description = "The keys in the Json Element, for example: key1,key2,key3")
    public String keys;

    @Parameter(description = "The potential values to validate in the JSON Array +" +
            "Values needs to be in the following format: x1,x2,x3\ny1,y2,y3")
    public String potentialValues;

    @Override
    public ExecutionResult execute(AddonHelper helper) throws FailureException {
        ActionReporter reporter = helper.getReporter();

        String[] arrayOfKeys = keys.split(",");
        String[] valueArrays = StringUtils.stripAll(potentialValues.split(System.lineSeparator()));
        List<String[]> splitValues = new ArrayList<>();
        for(String valueArray : valueArrays) {
            splitValues.add(valueArray.split(","));
        }
        Map<String, String[]> keysAndArrayOfValues = new HashMap<>();
        if(arrayOfKeys.length != valueArrays.length)
            throw  new FailureException("The size of the keys array must have the same size as the values array!");
        int size = arrayOfKeys.length; // must be the same size

        for(int i = 0; i < size; i++) {
            keysAndArrayOfValues.put(arrayOfKeys[i], splitValues.get(i));
        }

        JsonArray rootObj = Utils.getJsonElements(jsonArray);

        for(JsonElement element : rootObj) {
            JsonObject asJsonObject = element.getAsJsonObject();
            for(String key : keysAndArrayOfValues.keySet()) {
                String[] potValues = keysAndArrayOfValues.get(key);

            }
        }
        return ExecutionResult.PASSED;
    }
}
