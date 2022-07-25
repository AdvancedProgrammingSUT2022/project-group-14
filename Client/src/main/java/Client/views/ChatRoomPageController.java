package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.enums.Avatars;
import Client.enums.QueryRequests;
import Client.enums.QueryResponses;
import Client.models.User;
import Client.models.chats.Chat;
import Client.models.chats.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class ChatRoomPageController {
    @FXML
    private Button privateChatButton;
    @FXML
    private ScrollPane chatMessagesScrollPane;
    @FXML
    private TextField chatNameTextField;
    @FXML
    private TextField chatMessageTextField;
    @FXML
    private Text chatTitle;
    @FXML
    private VBox chatMessagesVBox;
    @FXML
    private VBox chatNamesVBox;
    @FXML
    private MenuButton usernamesMenuButton;
    public static boolean updateChatroom = false;
    public Chat selectedChat;
    private Timeline timeline;

    public void initialize() {
        initPanes();
        timeline.play();
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
                ClientSocketController.sendRequestAndGetResponse(QueryRequests.ADD_MESSAGE, new HashMap<>() {{
                    put("chat", new Gson().toJson(selectedChat));
                    put("message", new Gson().toJson(message));
                }});
                HBox hBox = new HBox(new Circle(20, new ImagePattern(getImage(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_USER_AVATAR, new HashMap<>() {{
                    put("username", message.getSenderUsername());
                }})).getParams().get("address")))),
                        new Text(" " + message.getSenderUsername() + " :    " + message.getText() + "\t\t\t" + message.getDate().toString().substring(4, 16)));
                chatMessageTextField.setText("");
                hBox.setPrefWidth(chatMessagesVBox.getPrefWidth());
                hBox.setPrefHeight(50);
                chatMessagesVBox.getChildren().add(hBox);
            }
        });
        chatNameTextField.setOnKeyPressed(keyEvent -> {
            chatNameTextField.setPromptText("Enter your chat name");
            chatNameTextField.setStyle("-fx-border-color: none");
        });
        addChatButtons();
    }

    public void initPanes() {
        ArrayList<User> users = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_USERS, new HashMap<>())).getParams().get("users"),
                new TypeToken<List<User>>() {
                }.getType());
        for (User user : users) {
            if (!user.getUsername().equals(MainMenuController.loggedInUser.getUsername())) {
                usernamesMenuButton.getItems().add(new CheckMenuItem(user.getUsername()));
            }
        }
        timeline = new Timeline(new KeyFrame(Duration.millis(500), actionEvent -> {
            if (updateChatroom) {
                updateChatroom = false;
                addChatButtons();
                if (selectedChat != null) {
                    selectedChat = MainMenuController.loggedInUser.getChats().get(selectedChat.getName());
                    changeChat(selectedChat);
                }
            }
        }));
        timeline.setCycleCount(-1);
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
         timeline.stop();
        App.changeScene("mainMenuPage");
    }

    public void createChatClicked(MouseEvent mouseEvent) {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add(MainMenuController.loggedInUser.getUsername());
        for (MenuItem item : usernamesMenuButton.getItems()) {
            if (((CheckMenuItem) item).isSelected())
                usernames.add(item.getText());
        }
        if (!creatingChatIsValid(usernames))
            return;
        if (mouseEvent.getSource().equals(privateChatButton) && usernames.size() > 2) {
            chatNameTextField.setText("");
            chatNameTextField.setPromptText("You can't add more than one person to private chats");
            chatNameTextField.setStyle("-fx-border-color: #ff0022");
            return;
        }
        ClientSocketController.sendRequestAndGetResponse(QueryRequests.ADD_CHAT, new HashMap<>() {{
            put("usernames", new Gson().toJson(usernames));
            put("chatName", chatNameTextField.getText());
        }});
        for (MenuItem item : usernamesMenuButton.getItems()) {
            ((CheckMenuItem) item).setSelected(false);
        }
        addChatButtons();
    }

    public boolean creatingChatIsValid(ArrayList<String> usernames) {
        if (chatNameTextField.getText().equals("")) {
            chatNameTextField.setPromptText("You should give a name to your chat");
            chatNameTextField.setStyle("-fx-border-color: #ff0022");
            return false;
        }
        if (usernames.size() == 1) {
            chatNameTextField.setPromptText("You should select at least one more person");
            chatNameTextField.setStyle("-fx-border-color: #ff0022");
            return false;
        }
        QueryResponses queryResponse = Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.VALID_CHAT_NAME, new HashMap<>() {{
            put("chatName", chatNameTextField.getText());
            put("usernames", new Gson().toJson(usernames));
        }})).getQueryResponse();
        if (queryResponse == QueryResponses.IGNORE) {
            chatNameTextField.setText("");
            chatNameTextField.setPromptText("You or your friends have a chat with this name");
            chatNameTextField.setStyle("-fx-border-color: #ff0022");
            return false;
        } else {
            return true;
        }
    }

    public void addChatButtons() {
        chatNamesVBox.getChildren().clear();
        for (Chat value : MainMenuController.loggedInUser.getChats().values()) {
            if (!value.getName().equals("Public Chat")) {
                Button button = new Button(value.getName());
                button.setStyle("-fx-border-radius: 0; -fx-background-color: #111a4d; -fx-pref-width: 350");
                button.setOnMouseClicked(mouseEvent -> {
                    selectedChat = MainMenuController.loggedInUser.getChats().get(button.getText());
                    changeChat(selectedChat);
                });
                chatNamesVBox.getChildren().add(button);
            }
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
                put("username", message.getSenderUsername());
            }})).getParams().get("address")))), text);
            hBox.setPrefWidth(chatMessagesVBox.getPrefWidth());
            hBox.setPrefHeight(50);
            chatMessagesVBox.getChildren().add(hBox);
        }
    }

    public void publicChatButtonClicked(MouseEvent mouseEvent) {
        selectedChat = MainMenuController.loggedInUser.getChats().get("Public Chat");
        changeChat(selectedChat);
    }

    public Image getImage(String avatarFileAddress) {
        if (Avatars.contains(avatarFileAddress)) {
            return new Image(Avatars.valueOf(avatarFileAddress).getAddress());
        } else {
            return new Image(avatarFileAddress);
        }
    }
}
