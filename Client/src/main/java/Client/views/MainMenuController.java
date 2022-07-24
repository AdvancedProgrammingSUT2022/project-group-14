package Client.views;

import Client.application.App;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class MainMenuController {
    @FXML
    private Circle usersAvatar;

    public void initialize() {
        usersAvatar.setFill(new ImagePattern(UserController.getLoggedInUser().getImage()));
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
        App.changeScene("loginPage");
    }
}
