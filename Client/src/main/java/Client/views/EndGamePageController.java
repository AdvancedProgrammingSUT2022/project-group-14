package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.enums.QueryRequests;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class EndGamePageController {

    private static String winnerCivilization;
    @FXML
    private Text text;

    public void initialize() {
        if (winnerCivilization != null) {
            text.setText("Congrats " + winnerCivilization + "! You've won the game 😎");
        } else {
            text.setText("You've lost the game 😭");
        }
        text.setLayoutX(640 - text.getBoundsInParent().getWidth());
        text.setFill(Color.rgb(238, 128, 0));
    }

    public static void setWinnerCivilization(String winner) {
        winnerCivilization = winner;
    }

    public void mainMenuButtonClicked(MouseEvent mouseEvent) {
        ClientSocketController.sendRequestAndGetResponse(QueryRequests.LEAVE_LOBBY, new HashMap<>(){{
            put("username", MainMenuController.loggedInUser.getUsername());
        }});
        App.changeScene("mainMenuPage");
    }
}
