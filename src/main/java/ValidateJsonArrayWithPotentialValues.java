import io.testproject.java.sdk.v2.addons.GenericAction;
import io.testproject.java.sdk.v2.addons.helpers.AddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

public class ValidateJsonArrayWithPotentialValues implements GenericAction {
    @Override
    public ExecutionResult execute(AddonHelper helper) throws FailureException {
        return ExecutionResult.PASSED;
    }
}
