import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;

public class Utils {

    private static final String TEMP_SCHEMA = "TempSchema";
    private static final String FILE = "file";
    private static final String URL = "url";

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

    public String validate(String pathToSchemaFile, String pathToSaveFile,  String jsonToValidate, boolean createFile) {
        String violationsAsOutput = "";
        Schema schema;
        JSONObject rawSchema;
        File file;
        Logger logger = LoggerFactory.getLogger(Utils.class);

        try {
            // Check if path to schema comes from a URL.
            UrlValidator urlValidator = new UrlValidator();
            if(urlValidator.isValid(pathToSchemaFile)) {
                file = createFile(URL, pathToSchemaFile);
            } else if(isValidPath(pathToSchemaFile)){
                // Local file
                file = new File(pathToSchemaFile);
            } else {
                // pathToSchemaFile = the schema itself.
                // Create a temp file from the schema.
                file = createFile(FILE, pathToSchemaFile);
            }

            // Set the raw schema from file
            try(InputStream is = new FileInputStream(file)) {
                rawSchema = new JSONObject(new JSONTokener(is));
            }

            try{ // Schema validation try/catch
                schema = SchemaLoader.load(rawSchema);
                schema.validate(new JSONObject(jsonToValidate)); // throws a ValidationException if this object is invalid
                violationsAsOutput = "Schema validation has passed";
            } catch (ValidationException e) {
                List<String> listOfViolations = e.getAllMessages();
                // If not defined, it will save it in the Downloads folder.
                if(pathToSaveFile == null)
                    pathToSaveFile = Paths.get(System.getProperty("user.home"), "Downloads").toString();

                if(createFile) {
                    try(PrintWriter writer = new PrintWriter(String.format("%s%sSchemaViolation.txt", pathToSaveFile, File.separator)
                            , "UTF-8")) {
                        writer.println("The following violations were present:");
                        listOfViolations.forEach(writer::println);
                    }
                }
                StringBuilder result = new StringBuilder();
                result.append("The following violations were present:").append(System.lineSeparator());
                listOfViolations.forEach(violation -> {
                    result.append(violation).append(System.lineSeparator());
                });

                violationsAsOutput = result.toString();
            }
        } catch (IOException fileNotFoundException) {
            violationsAsOutput = "Failed to open schema file.";
            logger.error("Failed to open schema file", fileNotFoundException);
        } catch(JSONException e) {
            violationsAsOutput = "JSON schema is invalid, " + System.lineSeparator() + e.getMessage();
            logger.error("JSON schema is invalid", e);
        }

        return violationsAsOutput;
    }

    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }

    private File createFile(String from, String path) throws IOException {
        File file = File.createTempFile(TEMP_SCHEMA, ".json");
        switch (from) {
            case FILE:
                FileUtils.writeStringToFile(file, path, "UTF-8");
                break;
            case URL:
                FileUtils.copyURLToFile(new URL(path), file);
                break;
        }

        return file;
    }
}
