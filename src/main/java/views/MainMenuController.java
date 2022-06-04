package views;

import application.App;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainMenuController {

    public void startGame(MouseEvent mouseEvent) {
        App.changeScene("gameMenuPage");
    }

    public void goToProfileMenu(MouseEvent mouseEvent) {
        App.changeScene("profileMenu");
    }

    public void goToScoreboard(MouseEvent mouseEvent) {
        App.changeScene("scoreboard");
    }

    public void goToChatroom(MouseEvent mouseEvent) {
        App.changeScene("chatroomPage");
    }

    public void logout(MouseEvent mouseEvent) {
        App.changeScene("loginPage");
    }
}
