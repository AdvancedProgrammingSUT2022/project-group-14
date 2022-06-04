package views;

import application.App;
import controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class MainMenuController {
    @FXML
    private Circle usersAvatar;

    public void initialize() {
        usersAvatar.setFill(new ImagePattern(new Image(UserController.getLoggedInUser().getAvatarFileAddress())));
    }

    public void startGame(MouseEvent mouseEvent) {
        App.changeScene("gameMenuPage");
    }

    public void goToProfileMenu(MouseEvent mouseEvent) {
        App.changeScene("profileMenuPage");
    }

    public void goToScoreboard(MouseEvent mouseEvent) {
        App.changeScene("scoreboardPage");
    }

    public void goToChatroom(MouseEvent mouseEvent) {
        App.changeScene("chatroomPage");
    }

    public void logout(MouseEvent mouseEvent) {
        App.changeScene("loginPage");
    }
}
