package views;

import application.App;
import controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Chat;
import models.User;

import java.util.ArrayList;

public class ChatRoomPageController {
    @FXML
    private VBox chatNames;
    @FXML
    private AnchorPane chatsPane;
    @FXML
    private ChoiceBox<String> groupChatBox;
    @FXML
    private ChoiceBox<String> privateChatBox;

    private Chat selectedChat;


    public void initialize() {
        for (User user : UserController.getUsers()) {
            if (user != UserController.getLoggedInUser()) {
                privateChatBox.getItems().add(user.getUsername());
                groupChatBox.getItems().add(user.getUsername());
            }
        }

        for (Chat value : UserController.getLoggedInUser().getChats().values()) {
            Button button = new Button(value.getName());
            button.setStyle("-fx-border-radius: 0; -fx-background-color: #111a4d; -fx-pref-width: 350");
            chatNames.getChildren().add(button);
        }
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("mainMenuPage");
    }

    public void createPrivateChatClicked(MouseEvent mouseEvent) {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add(privateChatBox.getValue());
        usernames.add(UserController.getLoggedInUser().getUsername());
        for (String username : usernames) {
            UserController.getUserByUsername(username).addChats(new Chat(usernames, "Chat -" + UserController.getUserByUsername(username).getChats().values().size()));
        }
    }
}
