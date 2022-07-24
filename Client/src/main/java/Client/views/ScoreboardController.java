package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.enums.QueryRequests;
import Client.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ScoreboardController {
    @FXML
    private GridPane gridPane;


    public void initialize() {
        ClientSocketController.sendRequestAndGetResponse(QueryRequests.SORT_USERS, new HashMap<>());
        Text rank, username, score, dateOfLastWin, dateOfLastLogin;
        Circle avatar;
        boolean currentUserWasShowed = false;
        ArrayList<User> users = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_USERS, new HashMap<>())).getParams().get("users"),
                new TypeToken<List<User>>() {
                }.getType());
        for (int i = 0, j = 1; i < Math.min(10, users.size()); i++, j++) {
            if (i == 9 && !currentUserWasShowed) {
                i = getIndexOfUser(users);
                j = i + 1;
            }
            avatar = new Circle(15, new ImagePattern(users.get(i).getImage()));
            rank = new Text(j + "-");
            username = new Text(users.get(i).getUsername());
            score = new Text("- " + users.get(i).getScore() + " -");
            dateOfLastWin = new Text("- N/A -");
            if (users.get(i).getDateOfLastWin() != null) {
                dateOfLastWin.setText("- " + users.get(i).getDateOfLastWin().toString().substring(4, 19) + " -");
            }
            dateOfLastLogin = new Text("- N/A -");
            if (users.get(i).getDateOfLastLogin() != null) {
                dateOfLastLogin.setText("- " + users.get(i).getDateOfLastLogin().toString().substring(4, 19) + " -");
            }
            rank.setStyle("-fx-font-size: 30");
            username.setStyle("-fx-font-size: 30");
            score.setStyle("-fx-font-size: 30");
            dateOfLastWin.setStyle("-fx-font-size: 30");
            dateOfLastLogin.setStyle("-fx-font-size: 30");
            if (users.get(i).getUsername().equals(MainMenuController.loggedInUser.getUsername())) {
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
            gridPane.add(rank, 0, i + 1);
            gridPane.add(avatar, 1, i + 1);
            gridPane.add(username, 2, i + 1);
            gridPane.add(score, 3, i + 1);
            gridPane.add(dateOfLastWin, 4, i + 1);
            gridPane.add(dateOfLastLogin, 5, i + 1);
        }
    }

    public int getIndexOfUser(ArrayList<User> users) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(MainMenuController.loggedInUser.getUsername()))
                return i;
        }
        return 0;
    }

    public void goToMainMenu(MouseEvent mouseEvent) {
        App.changeScene("mainMenuPage");
    }
}
