import io.testproject.java.enums.AutomatedBrowserType;
import io.testproject.java.sdk.v2.Runner;

public class Tester {
    public static void main(String[] args) throws Exception {
        Runner runner = Runner.create("kJCeheoppFyo8uaZ17k0JQyBck1qLIf5ZrynbI6t7Fk1");
        DoesJsonArrayContains action = new DoesJsonArrayContains();
        action.setJsonArray("[{\"id\":3192,\"empCode\":\"T-3192\",\"leaveYear\":1598342455000,\"leaveType\":\"Annual\",\"entitled\":21,\"availed\":4,\"balance\":17,\"leaveId\":1},{\"id\":3192,\"empCode\":\"T-3192\",\"leaveYear\":1598342455000,\"leaveType\":\"Casual\",\"entitled\":10,\"availed\":2,\"balance\":8,\"leaveId\":2},{\"id\":3192,\"empCode\":\"T-3192\",\"leaveYear\":1598342455000,\"leaveType\":\"Medical\",\"entitled\":8,\"availed\":0.5,\"balance\":7.5,\"leaveId\":3},{\"id\":3192,\"empCode\":\"T-3192\",\"leaveYear\":1598342455000,\"leaveType\":\"Compensation\",\"entitled\":0,\"availed\":0,\"balance\":0,\"leaveId\":4},{\"id\":3192,\"empCode\":\"T-3192\",\"leaveYear\":1598342456000,\"leaveType\":\"Unpaid\",\"entitled\":0,\"availed\":0,\"balance\":0,\"leaveId\":6},{\"id\":3192,\"empCode\":\"T-3192\",\"leaveYear\":1598342456000,\"leaveType\":\"Paternity\",\"entitled\":3,\"availed\":0,\"balance\":3,\"leaveId\":10},{\"id\":3192,\"empCode\":\"T-3192\",\"leaveYear\":1598342456000,\"leaveType\":\"Hajj/Umrah\",\"entitled\":15,\"availed\":0,\"balance\":15,\"leaveId\":11}]");
        action.setJsonElement("{\n" +
                "      \"id\":3192,\n" +
                "      \"empCode\":\"T-3192\",\n" +
                "      \"leaveYear\":1598342456000,\n" +
                "      \"leaveType\":\"Hajj/Umrah\",\n" +
                "      \"entitled\":15,\n" +
                "      \"availed\":0,\n" +
                "      \"balance\":15,\n" +
                "      \"leaveId\":11\n" +
                "   }");
        runner.run(action);
    }
}
