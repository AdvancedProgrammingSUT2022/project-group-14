package Client.models;

import Client.enums.resources.LuxuryResourceTypes;

public class Trade {
    private String offeringCivilization;
    private String secondCivilization;
    private int offeredGold;
    private int requestedGold;
    private String offeredLuxuryResource;
    private String requestedLuxuryResource;
    private String offeredStrategicResource;
    private String requestedStrategicResource;

    public Trade(String offeringCivilization, String secondCivilization, int offeredGold, int requestedGold, String offeredLuxuryResource, String requestedLuxuryResource, String offeredStrategicResource, String requestedStrategicResource) {
        this.offeringCivilization = offeringCivilization;
        this.secondCivilization = secondCivilization;
        this.offeredGold = offeredGold;
        this.requestedGold = requestedGold;
        this.offeredLuxuryResource = offeredLuxuryResource;
        this.requestedLuxuryResource = requestedLuxuryResource;
        this.offeredStrategicResource = offeredStrategicResource;
        this.requestedStrategicResource = requestedStrategicResource;
    }

    public String tradeInfo() {
        String output = "";
        output += "trade offer from " + offeringCivilization +
                "\noffered gold : " + offeredGold + "                                           wanted gold : " + requestedGold +
                "\noffered strategic resource : " + offeredStrategicResource + "                wanted strategic resource : " + requestedStrategicResource +
                "\noffered luxury resource : " + offeredLuxuryResource + "                wanted luxury resource : " + requestedLuxuryResource;

        return output;
    }
}
