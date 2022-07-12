package views;

import application.App;
import controllers.UserController;
import controllers.WorldController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.User;

import java.util.ArrayList;
import java.util.Objects;

public class GameMenuController {
    @FXML
    private Spinner<Integer> numberOfPlayersSpinner;
    @FXML
    private Spinner<Integer> mapHeightSpinner;
    @FXML
    private Spinner<Integer> mapWidthSpinner;
    @FXML
    private VBox invitationsVBox;
    @FXML
    private MenuButton usernamesMenuButton;

    public void initialize() {
        initPanes();
        initInvitations();
    }

    public void initPanes() {
        for (User user : UserController.getUsers()) {
            if (user != UserController.getLoggedInUser()) {
                usernamesMenuButton.getItems().add(new CheckMenuItem(user.getUsername()));
            }
        }
        numberOfPlayersSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10, 1));
        mapHeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, 40, 1));
        mapWidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, 40,1));
    }

    public void initInvitations(){
        invitationsVBox.getChildren().clear();
        for (String invitation : UserController.getLoggedInUser().getInvitations()) {
            invitationsVBox.getChildren().add(makeInvitationBox(invitation));
        }
    }

    public HBox makeInvitationBox(String invitation) {
        Button accept = new Button("✔");
        Button decline = new Button("❌");
        accept.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                User host = UserController.getUserByUsername(invitation.substring(16));
                assert host != null;
                UserController.getLoggedInUser().resetPeopleInLobby();
                for (String s : host.getPeopleInLobby()) {
                    UserController.getLoggedInUser().addPersonToLobby(s);
                }
                host.addPersonToLobby(UserController.getLoggedInUser().getUsername());
                for (String s : host.getPeopleInLobby()) {
                    if (!s.equals(host.getUsername()) && !s.equals(UserController.getLoggedInUser().getUsername()))
                        Objects.requireNonNull(UserController.getUserByUsername(s)).addPersonToLobby(UserController.getLoggedInUser().getUsername());
                }
                UserController.getLoggedInUser().removeInvitation(invitation);
                initInvitations();
            }
        });
        decline.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UserController.getLoggedInUser().removeInvitation(invitation);
                initInvitations();
            }
        });
        accept.setStyle("-fx-pref-width: 50");
        decline.setStyle("-fx-pref-width: 50");
        Text text = new Text(invitation);
        text.setFill(Color.ORANGE);
        HBox hBox = new HBox(text, accept, decline);
        hBox.setSpacing(5);
        return hBox;
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("mainMenuPage");
    }

    public void sendInvitations(MouseEvent mouseEvent) {
        for (MenuItem item : usernamesMenuButton.getItems()) {
            if (((CheckMenuItem) item).isSelected())
                Objects.requireNonNull(UserController.getUserByUsername(item.getText())).addInvitations(UserController.getLoggedInUser().getUsername());
        }
    }

    public void startGameButtonClicked(MouseEvent mouseEvent) {
        //TODO player number issues
        System.out.println(UserController.getLoggedInUser().getPeopleInLobby());
        WorldController.newWorld(UserController.getLoggedInUser().getPeopleInLobby());
        App.changeScene("gamePage");
    }
}
