package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.enums.QueryRequests;
import Client.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

//salam from other side
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
    @FXML
    private Button continueButton;
    @FXML
    private Button saveGameButton;
    @FXML
    private TextField saveNameTextField;

    public void initialize() {
        initPanes();
        initInvitations();
//        continueButton.setVisible(WorldController.getWorld() != null);
//        saveGameButton.setVisible(WorldController.getWorld() != null);
//        saveNameTextField.setVisible(WorldController.getWorld() != null);
        continueButton.setVisible(false);
        saveGameButton.setVisible(false);
        saveNameTextField.setVisible(false);
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
        ArrayList<User> users = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_USERS, new HashMap<>())).getParams().get("users"),
                new TypeToken<List<User>>() {
                }.getType());
        for (User user : users) {
            if (!user.getUsername().equals(MainMenuController.loggedInUser.getUsername()))
                usernamesMenuButton.getItems().add(new CheckMenuItem(user.getUsername()));
        }
        numberOfPlayersSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10, 1));
        mapHeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, 10, 1));
        mapWidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, 10, 1));
    }

    public void initInvitations() {
        invitationsVBox.getChildren().clear();
//        for (String invitation : UserController.getLoggedInUser().getInvitations()) {
//            invitationsVBox.getChildren().add(makeInvitationBox(invitation));
//        }
    }

    public HBox makeInvitationBox(String invitation) {
        Button accept = new Button("✔");
        Button decline = new Button("❌");
        accept.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                User host = UserController.getUserByUsername(invitation.substring(16));
//                assert host != null;
//                UserController.getLoggedInUser().resetPeopleInLobby();
//                for (String s : host.getPeopleInLobby()) {
//                    UserController.getLoggedInUser().addPersonToLobby(s);
//                }
//                host.addPersonToLobby(UserController.getLoggedInUser().getUsername());
//                for (String s : host.getPeopleInLobby()) {
//                    if (!s.equals(host.getUsername()) && !s.equals(UserController.getLoggedInUser().getUsername()))
//                        Objects.requireNonNull(UserController.getUserByUsername(s)).addPersonToLobby(UserController.getLoggedInUser().getUsername());
//                }
//                UserController.getLoggedInUser().removeInvitation(invitation);
//                initInvitations();
            }
        });
        decline.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                UserController.getLoggedInUser().removeInvitation(invitation);
//                initInvitations();
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
//        for (MenuItem item : usernamesMenuButton.getItems()) {
//            if (((CheckMenuItem) item).isSelected())
//                Objects.requireNonNull(UserController.getUserByUsername(item.getText())).addInvitations(UserController.getLoggedInUser().getUsername());
//        }
    }

    public void startGameButtonClicked(MouseEvent mouseEvent) {
//        if (UserController.getLoggedInUser().getPeopleInLobby().size() < 2) {
//            System.out.println("can't start");
//            return;
//        } else if (UserController.getLoggedInUser().getPeopleInLobby().size() > numberOfPlayersSpinner.getValue()) {
//            System.out.println("number of players is less the actual players");
//            return;
//        }
        System.out.println(MainMenuController.loggedInUser.getPeopleInLobby() + " * " + mapWidthSpinner.getValue() + " " + mapHeightSpinner.getValue());
        ClientSocketController.sendRequestAndGetResponse(QueryRequests.NEW_WORLD, new HashMap<>(){{
            //put people in lobby
            put("width", String.valueOf(mapWidthSpinner.getValue()));
            put("height", String.valueOf(mapHeightSpinner.getValue()));
        }});
        App.changeScene("gamePage");
    }

    public void continueButtonClicked(MouseEvent mouseEvent) {
//        App.changeScene("gamePage");
    }

    public void saveGameButtonClicked(MouseEvent mouseEvent) throws IOException {
//        WorldController.saveGame(saveNameTextField.getText());
    }
}
