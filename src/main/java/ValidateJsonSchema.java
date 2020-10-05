import com.google.common.base.Strings;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.addons.GenericAction;
import io.testproject.java.sdk.v2.addons.helpers.AddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

@Action(name = "Validate JSON using schema",
        summary = "Validates JSON value using a JSON schema",
        description = "Validates JSON value using a JSON schema, output the validation to a file if selected.")
public class ValidateJsonSchema implements GenericAction {

    @Parameter(description = "The path to the schema file (Local/URL/Schema itself)")
    private String pathToSchema;

    @Parameter(description = "Json value to validate")
    private String jsonToValidate;

    @Parameter(description = "Path to save the schema validation output (Optional)")
    private String pathToSaveFile;

    @Parameter(description = "Create an output file of the schema validation (true/false)")
    private boolean createFile;

    @Parameter(description = "The schema validation result", direction = ParameterDirection.OUTPUT)
    private String validationOutput;

    @Override
    public ExecutionResult execute(AddonHelper helper) throws FailureException {
        if(Strings.isNullOrEmpty(jsonToValidate) || Strings.isNullOrEmpty(pathToSchema)) {
            helper.getReporter().result("Json to Validate and Schema must be provided");
            return ExecutionResult.FAILED;
        }
        validationOutput = new Utils().validate(pathToSchema, pathToSaveFile, jsonToValidate, createFile);
        helper.getReporter().result("Validation result: " + validationOutput);
        return ExecutionResult.PASSED;
    }
}
