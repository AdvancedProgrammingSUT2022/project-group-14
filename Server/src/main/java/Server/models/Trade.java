package Server.models;

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

    public String getSecondCivilization() {
        return secondCivilization;
    }

    public String getOfferingCivilization() {
        return offeringCivilization;
    }

    public int getOfferedGold() {
        return offeredGold;
    }

    public int getRequestedGold() {
        return requestedGold;
    }

    public String getOfferedLuxuryResource() {
        return offeredLuxuryResource;
    }

    public String getRequestedLuxuryResource() {
        return requestedLuxuryResource;
    }

    public String getOfferedStrategicResource() {
        return offeredStrategicResource;
    }

    public String getRequestedStrategicResource() {
        return requestedStrategicResource;
    }
}
