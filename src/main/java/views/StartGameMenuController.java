package views;

import application.App;
import controllers.UserController;
import controllers.WorldController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.User;

import java.util.Objects;

public class StartGameMenuController {
    @FXML
    private AnchorPane anchorPane;
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
    @FXML
    private TextArea cheatCodeArea;
    @FXML
    private Text cheatCodeText;

    public void initialize() {
        initPanes();
        initInvitations();
        cheatCodeText.setVisible(false);
        cheatCodeArea.setVisible(false);
        anchorPane.setOnKeyReleased(keyEvent -> {
            if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getCode().getName().equals("C")) {
                cheatCodeArea.setVisible(!cheatCodeArea.isVisible());
                cheatCodeText.setVisible(!cheatCodeText.isVisible());
            }
        });
    }

    public void initPanes() {
        for (User user : UserController.getUsers()) {
            if (user != UserController.getLoggedInUser()) {
                usernamesMenuButton.getItems().add(new CheckMenuItem(user.getUsername()));
            }
        }
        numberOfPlayersSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10, 1));
        mapHeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, 10, 1));
        mapWidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, 10, 1));
    }

    public void initInvitations() {
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

    public void cheatCodeAreaTyped(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Enter")) {
            String command = cheatCodeArea.getText().substring(cheatCodeArea.getText().substring(0, cheatCodeArea.getText().length() - 1)
                    .lastIndexOf("\n") + 1, cheatCodeArea.getText().length() - 1);
            if (command.equals("clear")) {
                cheatCodeArea.clear();
            } else {
                GameCommandsValidation.checkCommands(command);
            }
        }
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
        if (UserController.getLoggedInUser().getPeopleInLobby().size() < 2) {
            System.out.println("can't start");
            return;
        } else if (UserController.getLoggedInUser().getPeopleInLobby().size() > numberOfPlayersSpinner.getValue()) {
            System.out.println("number of players is less the actual players");
            return;
        }
        System.out.println(UserController.getLoggedInUser().getPeopleInLobby() + " * " + mapWidthSpinner.getValue() + " " + mapHeightSpinner.getValue());
        WorldController.newWorld(UserController.getLoggedInUser().getPeopleInLobby(), mapWidthSpinner.getValue(), mapHeightSpinner.getValue());
        App.changeScene("gamePage");
    }
}
