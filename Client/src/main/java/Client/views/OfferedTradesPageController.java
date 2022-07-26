package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.enums.QueryRequests;
import Client.enums.QueryResponses;
import Client.models.City;
import Client.models.Trade;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class OfferedTradesPageController {
    @FXML
    private AnchorPane pane;

    public void initialize() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setLayoutX(50);
        vBox.setLayoutY(50);
        ArrayList<Trade> trades = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_CIVILIZATION_TRADES, new HashMap<>())).getParams().get("trades"),
                new TypeToken<List<Trade>>() {
                }.getType());
        for (int i = 0; i < trades.size(); i++) {
            Text tradeInfo = new Text(trades.get(i).tradeInfo());
            tradeInfo.setFill(Color.rgb(238, 128, 0));
            Text message = new Text("");
            message.setFill(Color.rgb(238, 128, 0));
            message.setVisible(false);
            Button acceptButton = new Button("accept");
            int finalI = i;
            acceptButton.setOnMouseClicked(mouseEvent -> {
                if (!message.getText().equals("trade is done")){

                    QueryResponses queryResponse = Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.ACCEPT_TRADE, new HashMap<>() {{
                        put("indexOfTrade", new Gson().toJson(finalI));
                    }})).getQueryResponse();
                    message.setVisible(true);
                    switch (queryResponse) {
                        case YOU_NOT_ENOUGH_GOLD -> {
                            message.setText("you don't have enough gold");
                        }
                        case YOU_LACK_LUXURY_RESOURCE -> {
                            message.setText("you lack luxury resource");
                        }
                        case YOU_LACK_STRATEGIC_RESOURCE -> {
                            message.setText("you lack strategic resource");
                        }
                        case OTHER_CIVILIZATION_NOT_ENOUGH_GOLD -> {
                            message.setText("other civilization don't have enough gold");
                        }
                        case OTHER_CIVILIZATION_LACK_LUXURY_RESOURCE -> {
                            message.setText("other civilization lack luxury resource");
                        }
                        case OTHER_CIVILIZATION_LACK_STRATEGIC_RESOURCE -> {
                            message.setText("other civilization lack strategic resource");
                        }
                        case OK -> {
                            message.setText("trade is done");
                        }
                    }
                }
            });
            HBox hBox = new HBox(tradeInfo, acceptButton, message);
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
        }
        pane.getChildren().add(vBox);
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("tradePanel");
    }
}
