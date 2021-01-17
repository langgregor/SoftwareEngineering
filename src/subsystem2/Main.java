package subsystem2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

public class Main {
    public static void main(String args[]) {
        TrafficParticipantClient c = new TrafficParticipantClient();

        c.connect("Admin", "supersecure");

        System.out.println(c.getValuesAsJson(TrafficParticipantType.HumanPoweredVehicle, 1L, 0L));
        System.out.println(c.getValuesAsXml(TrafficParticipantType.RegularVehicle, 2L, 1L));

        c.disconnect();
    }
}
