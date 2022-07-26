package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.enums.Avatars;
import Client.enums.QueryRequests;
import Client.models.chats.Chat;
import Client.models.chats.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;

public class DiscussPanelController {

    @FXML
    private ScrollPane chatMessagesScrollPane;
    @FXML
    private TextField chatMessageTextField;
    @FXML
    private Text chatTitle;
    @FXML
    private VBox chatMessagesVBox;
    @FXML
    private VBox chatNamesVBox;
    @FXML
    private MenuButton civilizationsMenuButton;
    public Chat selectedChat;

    public void initialize() {
        initPanes();
        chatTitle.setText("");
        chatMessagesVBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (chatMessagesVBox.getChildren().size() <= 9) {
                chatMessagesScrollPane.setVvalue(0);
            } else {
                chatMessagesScrollPane.setVvalue(1.0d);
            }
        });
        chatMessageTextField.setVisible(false);
        chatMessageTextField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode().getName().equals("Enter")) {
                Message message = new Message(chatMessageTextField.getText(), MainMenuController.loggedInUser.getUsername(), new Date());
                ClientSocketController.sendRequestAndGetResponse(QueryRequests.ADD_DISCUSS_MESSAGE, new HashMap<>() {{
                    put("chat", new Gson().toJson(selectedChat));
                    put("message", new Gson().toJson(message));
                }});
                chatMessageTextField.setText("");
                HBox hBox = new HBox(new Circle(20, new ImagePattern(getImage(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_USER_AVATAR, new HashMap<>() {{
                    put("senderUsername", message.getSenderUsername());
                }})).getParams().get("address")))), new Text(" " + message.getSenderUsername() + " :    " + message.getText() + "\t\t\t" + message.getDate().toString().substring(4, 16)));
                hBox.setPrefWidth(chatMessagesVBox.getPrefWidth());
                hBox.setPrefHeight(50);
                chatMessagesVBox.getChildren().add(hBox);
            }
        });
        addChatButtons();
    }

    public void initPanes() {
        ArrayList<String> civilizationNames = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_ALL_CIVILIZATIONS_NAMES, new HashMap<>())).getParams().get("names"),
                new TypeToken<List<String>>() {
                }.getType());
        for (String civilizationName : civilizationNames) {
            if (!civilizationName.equals(MainMenuController.loggedInUser.getUsername())) {
                civilizationsMenuButton.getItems().add(new CheckMenuItem(civilizationName));
            }
        }
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("infoPanelPage");
    }

    public void createChatClicked(MouseEvent mouseEvent) {
        ArrayList<String> civilizations = new ArrayList<>();
        civilizations.add(MainMenuController.loggedInUser.getUsername());
        for (MenuItem item : civilizationsMenuButton.getItems()) {
            if (((CheckMenuItem) item).isSelected())
                civilizations.add(item.getText());
        }
        if (civilizations.size() == 1)
            return;
        StringBuilder chatName = new StringBuilder();
        for (String civilization : civilizations) {
            chatName.append(civilization).append("-");
        }
        ClientSocketController.sendRequestAndGetResponse(QueryRequests.ADD_DISCUSS_CHAT, new HashMap<>() {{
            put("usernames", new Gson().toJson(civilizations));
            put("chatName", chatName.toString());
        }});
        for (MenuItem item : civilizationsMenuButton.getItems()) {
            ((CheckMenuItem) item).setSelected(false);
        }
        addChatButtons();
    }

    public void addChatButtons() {
        chatNamesVBox.getChildren().clear();
        ArrayList<Chat> chats = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_CIV_CHATS, new HashMap<>())).getParams().get("chats"),
                new TypeToken<Collection<Chat>>() {
                }.getType());
        for (Chat value : chats) {
            if (selectedChat != null && selectedChat.getName().equals(value.getName())) {
                selectedChat = value;
            }
            Button button = new Button(value.getName());
            button.setStyle("-fx-border-radius: 0; -fx-background-color: #111a4d; -fx-pref-width: 350");
            button.setOnMouseClicked(mouseEvent -> {
                selectedChat = value;
                changeChat(selectedChat);
            });
            chatNamesVBox.getChildren().add(button);
        }
    }

    public void changeChat(Chat chat) {
        chatTitle.setText(chat.getName());
        chatMessageTextField.setVisible(true);
        chatMessagesVBox.getChildren().clear();
        for (Message message : chat.getMessages()) {
            Text text = new Text(" " + message.getSenderUsername() + " :    " + message.getText() + "\t\t\t" + message.getDate().toString().substring(4, 16));
            text.setFill(Color.ORANGE);
            HBox hBox = new HBox(new Circle(20, new ImagePattern(getImage(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_USER_AVATAR, new HashMap<>() {{
                put("senderUsername", message.getSenderUsername());
            }})).getParams().get("address")))), text);
            hBox.setPrefWidth(chatMessagesVBox.getPrefWidth());
            hBox.setPrefHeight(50);
            chatMessagesVBox.getChildren().add(hBox);
        }
    }

    public Image getImage(String avatarFileAddress) {
        if (Avatars.contains(avatarFileAddress)) {
            return new Image(Avatars.valueOf(avatarFileAddress).getAddress());
        } else {
            return new Image(avatarFileAddress);
        }
    }
}
