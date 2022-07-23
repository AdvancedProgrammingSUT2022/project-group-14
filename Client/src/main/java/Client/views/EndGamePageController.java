package Client.views;

import Client.application.App;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Date;
import java.util.Objects;

public class EndGamePageController {

    private static String winnerCivilization;
    @FXML
    private Text text;

    public void initialize() {
        Objects.requireNonNull(UserController.getUserByUsername(winnerCivilization)).setDateOfLastWin(new Date());
        Objects.requireNonNull(UserController.getUserByUsername(winnerCivilization)).changeScore(100);
        if (UserController.getLoggedInUser().getUsername().equals(winnerCivilization)) {
            text.setText("Congrats! You've won the game 😎");
        } else {
            text.setText("You've lost the game 💩");
        }
        text.setLayoutX(640 - text.getBoundsInParent().getWidth());
        text.setFill(Color.rgb(238, 128, 0));
    }

    public static void setWinnerCivilization(String winner) {
        winnerCivilization = winner;
    }

    public void mainMenuButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("mainMenuPage");
    }
}
