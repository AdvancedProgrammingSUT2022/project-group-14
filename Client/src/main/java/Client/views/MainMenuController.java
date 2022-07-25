package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.enums.QueryRequests;
import Client.models.User;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.HashMap;

public class MainMenuController {
    public static User loggedInUser;
    @FXML
    private Circle usersAvatar;

    public void initialize() {
        usersAvatar.setFill(new ImagePattern(loggedInUser.getImage()));
    }

    public void startGame(MouseEvent mouseEvent) {
        App.changeScene("startGameMenuPage");
    }

    public void goToProfileMenu(MouseEvent mouseEvent) {
        App.changeScene("profileMenuPage");
    }

    public void goToScoreboard(MouseEvent mouseEvent) {
        App.changeScene("scoreboardPage");
    }

    public void goToChatroom(MouseEvent mouseEvent) {
        App.changeScene("chatRoomPage");
    }

    public void logout(MouseEvent mouseEvent) {
        ClientSocketController.sendRequestAndGetResponse(QueryRequests.LOGOUT_USER, new HashMap<>(){{
            put("username", loggedInUser.getUsername());
        }});
        loggedInUser = null;
        App.changeScene("loginPage");
    }
}
