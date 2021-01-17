package subsystem2;

public class Participant {
    public int priorityLevel = 0;
    public int dangerLevel = 0;

    Participant() {}

    String getAsJson() {
        return "{" +
                "\"priorityLevel\":" + priorityLevel + ", " +
                "\"dangerLevel\":" + dangerLevel +
                "}";
    }

    String getAsXml() {
        return "<participant>" +
                "<priorityLevel>" + priorityLevel + "</priorityLevel>" +
                "<dangerLevel>" + dangerLevel + "</dangerLevel>" +
                "</participant>";
    }
}
