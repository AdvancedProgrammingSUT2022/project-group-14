package views;

import application.App;
import controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Objects;

public class scoreboardController {
    @FXML
    private GridPane users;

    public void initialize(){
        UserController.sortUsers();
        users.setLayoutX(500);
        users.setLayoutY(150);
        for (int i = 0; i < UserController.getUsers().size(); i++) {
//            Circle avatar = new Circle();
//            avatar.setRadius(30);
//            ImagePattern imagePattern = new ImagePattern(new Image(
//                    Objects.requireNonNull(getClass().getResource(
//                            "/images/avatar" + GameNet.getUsers().get(i).getAvatar() + ".jpeg")).toExternalForm()));
//            avatar.setFill(imagePattern);
            Text username = new Text();
            Text point = new Text();
            username.setText("  " + UserController.getUsers().get(i).getUsername());
            point.setText("  score: " + UserController.getUsers().get(i).getScore());
            username.setStyle("-fx-font-size: 20");
            point.setStyle("-fx-font-size: 20");
            if (i == 0) {
                username.setFill(Color.GOLD);
                point.setFill(Color.GOLD);
            }
            if (i == 1) {
                username.setFill(Color.SILVER);
                point.setFill(Color.SILVER);
            }
            if (i == 2) {
                username.setFill(Color.BROWN);
                point.setFill(Color.BROWN);
            }
            if (UserController.getUsers().get(i) == UserController.getLoggedInUser()){
                username.setFill(Color.BLUE);
                point.setFill(Color.BLUE);
            }
            //users.add(avatar, 0, i);
            users.add(username, 1, i);
            users.add(point, 3, i);
        }
    }

    public void goToMainMenu(MouseEvent mouseEvent) {
        App.changeScene("mainMenuPage");
    }
}
