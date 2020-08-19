import io.testproject.java.enums.AutomatedBrowserType;
import io.testproject.java.sdk.v2.Runner;

public class Tester {
    public static void main(String[] args) throws Exception {
        Runner runner = Runner.createWeb("kJCeheoppFyo8uaZ17k0JQyBck1qLIf5ZrynbI6t7Fk1", AutomatedBrowserType.Chrome);
        ValidateJsonArray action = new ValidateJsonArray();
        action.setIndexOfJsonElement("0");
        action.setJsonArray("[\n" +
                " {\n" +
                "   \"WhatsApp\": \"WHATAPPS0000\",\n" +
                "   \"WeChat\": \"WECHAT0000\",\n" +
                "   \"Line\": \"LINE0000\",\n" +
                "   \"BBM\": \"BBM0000\"\n" +
                " },\n" +
                " {\n" +
                "   \"WhatsApp\": \"WHATAPPS0001\",\n" +
                "   \"WeChat\": \"WECHAT0001\",\n" +
                "   \"Line\": \"LINE0001\",\n" +
                "   \"BBM\": \"BBM0001\"\n" +
                " }\n" +
                "]");
        action.setJsonValues("WhatsApp:WHATAPPS0000\nWeChat:WECHAT0000");
        runner.run(action);
    }
}
