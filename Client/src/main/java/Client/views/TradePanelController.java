package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.enums.QueryRequests;
import Client.enums.resources.LuxuryResourceTypes;
import Client.enums.resources.StrategicResourceTypes;
import Client.models.Trade;
import Client.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TradePanelController {

    @FXML
    private Text message;
    @FXML
    private ChoiceBox<String> civilizationName;
    @FXML
    private TextField yourOfferedGold;
    @FXML
    private ChoiceBox<String> yourOfferedStrategicResource;
    @FXML
    private ChoiceBox<String> yourOfferedLuxuryResource;
    @FXML
    private TextField yourRequestedGold;
    @FXML
    private ChoiceBox<String> yourRequestedStrategicResource;
    @FXML
    private ChoiceBox<String> yourRequestedLuxuryResource;

    public void initialize(){
        message.setVisible(false);
        initCivilizationNames();

        yourOfferedLuxuryResource.setValue(null);
        yourOfferedLuxuryResource.getItems().addAll(LuxuryResourceTypes.getAllResourcesNames());

        yourOfferedStrategicResource.setValue(null);
        yourOfferedStrategicResource.getItems().addAll(StrategicResourceTypes.getAllResourcesNames());

        yourRequestedLuxuryResource.setValue(null);
        yourRequestedLuxuryResource.getItems().addAll(LuxuryResourceTypes.getAllResourcesNames());

        yourRequestedStrategicResource.setValue(null);
        yourRequestedStrategicResource.getItems().addAll(StrategicResourceTypes.getAllResourcesNames());
    }

    public void initCivilizationNames() {
        String currentCivilizationName = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_CURRENT_CIVILIZATION_NAME, new HashMap<>())).getParams().get("name"), String.class);
        ArrayList<String> civilizationNames = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_ALL_CIVILIZATIONS_NAMES, new HashMap<>())).getParams().get("names"),
                new TypeToken<List<String>>() {
                }.getType());
        civilizationNames.remove(currentCivilizationName);
        civilizationName.setValue(null);
        civilizationName.getItems().addAll(civilizationNames);
    }

    public void tradeButtonClicked(MouseEvent mouseEvent) {
        String currentCivilizationName = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_CURRENT_CIVILIZATION_NAME, new HashMap<>())).getParams().get("name"), String.class);
        if (offeredGoldIsInteger() && requestedGoldIsInteger()) {
            message.setVisible(true);
            if (civilizationName.getValue() != null){
                Trade trade = new Trade(currentCivilizationName, civilizationName.getValue(), Integer.parseInt(yourOfferedGold.getText()), Integer.parseInt(yourRequestedGold.getText()), yourOfferedLuxuryResource.getValue(), yourRequestedLuxuryResource.getValue(), yourOfferedStrategicResource.getValue(), yourRequestedStrategicResource.getValue());
                ClientSocketController.sendRequestAndGetResponse(QueryRequests.ADD_TRADE, new HashMap<>(){{put("trade", new Gson().toJson(trade));}});

                message.setText("your offer was sent to civilization " + civilizationName.getValue());
            } else {
                message.setText("you should select aa civilization");
            }
        }
    }

    public boolean offeredGoldIsInteger() {
        try {
            Integer.parseInt(yourOfferedGold.getText());
            return true;
        } catch (NumberFormatException e){
            message.setVisible(true);
            message.setText("your offered gold isn't an integer");
            return false;
        }
    }

    public boolean requestedGoldIsInteger() {
        try {
            Integer.parseInt(yourRequestedGold.getText());
            return true;
        } catch (NumberFormatException e){
            message.setVisible(true);
            message.setText("your requested gold isn't an integer");
            return false;
        }
    }

    public void goToOfferedTradesPage(MouseEvent mouseEvent) {
        App.changeScene("offeredTradesPage");
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
         App.changeScene("infoPanelPage");
    }
}
