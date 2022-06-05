package views;

import application.App;
import controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Objects;

public class scoreboardController {
    @FXML
    private GridPane gridPane;


    public void initialize(){
        UserController.sortUsers();
        Text rank, username, score, dateOfLastWin, dateOfLastLogin;
        Circle avatar;
        boolean currentUserWasShowed = false;
        for (int i = 0, j = 1; i < Math.min(10,UserController.getUsers().size()); i++, j++) {
            if (i == 9 && !currentUserWasShowed){
              i = UserController.getUsers().indexOf(UserController.getLoggedInUser());
              j = i + 1;
            }
            avatar = new Circle(15, new ImagePattern(UserController.getUsers().get(i).getImage()));
            rank = new Text(j + "-");
            username = new Text(UserController.getUsers().get(i).getUsername());
            score = new Text("- " + UserController.getUsers().get(i).getScore() + " -");
            dateOfLastWin = new Text("- N/A -");
            if (UserController.getUsers().get(i).getDateOfLastWin() != null) {
                dateOfLastWin.setText("- " + UserController.getUsers().get(i).getDateOfLastWin().toString().substring(4, 19) + " -");
            }
            dateOfLastLogin = new Text("- N/A -");
            if (UserController.getUsers().get(i).getDateOfLastLogin() != null) {
                dateOfLastLogin.setText("- " + UserController.getUsers().get(i).getDateOfLastLogin().toString().substring(4, 19) + " -");
            }
            rank.setStyle("-fx-font-size: 30");
            username.setStyle("-fx-font-size: 30");
            score.setStyle("-fx-font-size: 30");
            dateOfLastWin.setStyle("-fx-font-size: 30");
            dateOfLastLogin.setStyle("-fx-font-size: 30");
            if (UserController.getUsers().get(i) == UserController.getLoggedInUser()) {
                rank.setFill(Color.RED);
                username.setFill(Color.RED);
                score.setFill(Color.RED);
                dateOfLastWin.setFill(Color.RED);
                dateOfLastLogin.setFill(Color.RED);
                currentUserWasShowed = true;
            } else {
                rank.setFill(Color.ORANGE);
                username.setFill(Color.ORANGE);
                score.setFill(Color.ORANGE);
                dateOfLastWin.setFill(Color.ORANGE);
                dateOfLastLogin.setFill(Color.ORANGE);
            }
            i = Math.min(9, i);
            gridPane.add(rank, 0, i+1);
            gridPane.add(avatar, 1, i+1);
            gridPane.add(username, 2, i+1);
            gridPane.add(score, 3, i+1);
            gridPane.add(dateOfLastWin, 4, i+1);
            gridPane.add(dateOfLastLogin, 5, i+1);
        }
    }

    public void goToMainMenu(MouseEvent mouseEvent) {
        App.changeScene("mainMenuPage");
    }
}
