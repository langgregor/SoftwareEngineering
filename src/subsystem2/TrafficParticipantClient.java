package subsystem2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class TrafficParticipantClient {
    private boolean authorized = false;
    private JSONObject rootUsers;
    private JSONObject rootParticipant;

    public TrafficParticipantClient() {
        JSONParser parser = new JSONParser();
        try {
            rootUsers = (JSONObject) parser.parse(new FileReader("user.json"));
            rootParticipant = (JSONObject) parser.parse(new FileReader("participant.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int connect(String username, String password) {
        JSONArray userList = (JSONArray) rootUsers.get("Users");
        for (JSONObject o : (Iterable<JSONObject>) userList) {
            if (username.equals(o.get("username")) && password.equals(o.get("password")) && !authorized) {
                authorized = true;
                return 0;
            }
        }
        return 1;
    }

    public void disconnect() {
        authorized = false;
    }

    public String getValuesAsJson(TrafficParticipantType type, Long numberOfLanes, Long emergencyLevel) {
        Participant p = getValuesAsObject(type, numberOfLanes, emergencyLevel);
        if (p != null) {
            return p.getAsJson();
        }
        return "{ \"participant\": \"Not authorized!\"}";
    }

    public String getValuesAsXml(TrafficParticipantType type, Long numberOfLanes, Long emergencyLevel) {
        Participant p = getValuesAsObject(type, numberOfLanes, emergencyLevel);
        if (p != null) {
            return p.getAsXml();
        }
        return "<participant>Not authorized!</participant>";
    }

    private Participant getValuesAsObject(TrafficParticipantType type, Long numberOfLanes, Long emergencyLevel) {
        if (authorized) {
            String t;
            switch (type) {
                case Pedestrian: t = "pedestrian"; break;
                case PublicVehicle: t = "publicVehicle"; break;
                case RegularVehicle: t = "regularVehicle"; break;
                case MaintenanceVehicle: t = "maintenanceVehicle"; break;
                case AnimalPoweredVehicle: t = "animalPoweredVehicle"; break;
                case HumanPoweredVehicle: t = "humanPoweredVehicle"; break;
                default: return null;
            }

            JSONArray list = (JSONArray) ((JSONObject) rootParticipant.get("participant")).get(t);
            for (JSONObject o : (Iterable<JSONObject>) list) {
                if (o.get("numberOfLanes").equals(numberOfLanes) && o.get("emergency").equals(emergencyLevel)) {
                    Participant p = new Participant();
                    p.priorityLevel = Integer.parseInt(o.get("priorityLevel").toString());
                    p.dangerLevel = Integer.parseInt(o.get("dangerLevel").toString());
                    return p;
                }
            }
        }
        return null;
    }
}