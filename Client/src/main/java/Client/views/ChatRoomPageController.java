package Client.views;

import Client.application.App;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ChatRoomPageController {
//    @FXML
//    private Button privateChatButton;
//    @FXML
//    private ScrollPane chatMessagesScrollPane;
//    @FXML
//    private TextField chatNameTextField;
//    @FXML
//    private TextField chatMessageTextField;
//    @FXML
//    private Text chatTitle;
//    @FXML
//    private VBox chatMessagesVBox;
//    @FXML
//    private VBox chatNamesVBox;
//    @FXML
//    private MenuButton usernamesMenuButton;
//
//    private final ArrayList<Chat> selectedChats = new ArrayList<>();
//
//
//    public void initialize() {
//        initPanes();
//        chatTitle.setText("");
//        chatMessagesVBox.heightProperty().addListener((observable, oldValue, newValue) -> {
//            if (chatMessagesVBox.getChildren().size() <= 9) {
//                chatMessagesScrollPane.setVvalue(0);
//            } else {
//                chatMessagesScrollPane.setVvalue(1.0d);
//            }
//        });
//        chatMessageTextField.setVisible(false);
//        chatMessageTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                if (keyEvent.getCode().getName().equals("Enter")) {
//                    Message message = new Message(chatMessageTextField.getText(), UserController.getLoggedInUser().getUsername(), new Date());
//                    for (Chat selectedChat : selectedChats) {
//                        selectedChat.addMessage(message);
//                    }
//                    HBox hBox = new HBox(new Circle(20, new ImagePattern(Objects.requireNonNull(UserController.getUserByUsername(message.getSenderUsername())).getImage())),
//                            new Text(" " + message.getSenderUsername() + " :\t" + message.getText() + "\t\t\t" + message.getDate().toString().substring(4, 16)));
//                    chatMessageTextField.setText("");
//                    hBox.setPrefWidth(chatMessagesVBox.getPrefWidth());
//                    hBox.setPrefHeight(50);
//                    chatMessagesVBox.getChildren().add(hBox);
//                }
//            }
//        });
//        chatNameTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                chatNameTextField.setPromptText("Enter your chat name");
//                chatNameTextField.setStyle("-fx-border-color: none");
//            }
//        });
//        addChatButtons();
//    }
//
//    public void initPanes() {
//        for (User user : UserController.getUsers()) {
//            if (user != UserController.getLoggedInUser()) {
//                usernamesMenuButton.getItems().add(new CheckMenuItem(user.getUsername()));
//            }
//        }
//    }
//
//    public void backButtonClicked(MouseEvent mouseEvent) {
//        App.changeScene("mainMenuPage");
//    }
//
//    public void createChatClicked(MouseEvent mouseEvent) {
//        ArrayList<String> usernames = new ArrayList<>();
//        usernames.add(UserController.getLoggedInUser().getUsername());
//        for (MenuItem item : usernamesMenuButton.getItems()) {
//            if (((CheckMenuItem) item).isSelected())
//                usernames.add(item.getText());
//        }
//        if (!creatingChatIsValid(usernames))
//            return;
//        if (mouseEvent.getSource().equals(privateChatButton) && usernames.size() > 2) {
//            chatNameTextField.setText("");
//            chatNameTextField.setPromptText("You can't add more than one person to private chats");
//            chatNameTextField.setStyle("-fx-border-color: #ff0022");
//            return;
//        }
//        for (int i = 0; i < usernames.size(); i++) {
//            Objects.requireNonNull(UserController.getUserByUsername(usernames.get(i))).addChats(new Chat(usernames, chatNameTextField.getText()));
//        }
//        for (MenuItem item : usernamesMenuButton.getItems()) {
//            ((CheckMenuItem) item).setSelected(false);
//        }
//        addChatButtons();
//    }
//
//    public boolean creatingChatIsValid(ArrayList<String> usernames) {
//        if (chatNameTextField.getText().equals("")) {
//            chatNameTextField.setPromptText("You should give a name to your chat");
//            chatNameTextField.setStyle("-fx-border-color: #ff0022");
//            return false;
//        }
//        if (usernames.size() == 1) {
//            chatNameTextField.setPromptText("You should select at least one more person");
//            chatNameTextField.setStyle("-fx-border-color: #ff0022");
//            return false;
//        }
//        for (String username : usernames) {
//            if (Objects.requireNonNull(UserController.getUserByUsername(username)).getChats().containsKey(chatNameTextField.getText())) {
//                chatNameTextField.setText("");
//                chatNameTextField.setPromptText("You or your friends have a chat with this name");
//                chatNameTextField.setStyle("-fx-border-color: #ff0022");
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void addChatButtons() {
//        chatNamesVBox.getChildren().clear();
//        for (Chat value : UserController.getLoggedInUser().getChats().values()) {
//            if (!value.getName().equals("Public Chat")) {
//                Button button = new Button(value.getName());
//                button.setStyle("-fx-border-radius: 0; -fx-background-color: #111a4d; -fx-pref-width: 350");
//                button.setOnMouseClicked(mouseEvent -> {
//                    for (String username : UserController.getLoggedInUser().getChats().get(button.getText()).getUsernames()) {
//                        selectedChats.add(Objects.requireNonNull(UserController.getUserByUsername(username)).getChats().get(button.getText()));
//                    }
//                    changeChat(UserController.getLoggedInUser().getChats().get(button.getText()));
//                });
//                chatNamesVBox.getChildren().add(button);
//            }
//        }
//    }
//
//    public void changeChat(Chat chat) {
//        chatTitle.setText(chat.getName());
//        chatMessageTextField.setVisible(true);
//        chatMessagesVBox.getChildren().clear();
//        for (Message message : chat.getMessages()) {
//            Text text = new Text(" " + message.getSenderUsername() + " :\t" + message.getText() + "\t\t\t" + message.getDate().toString().substring(4, 16));
//            text.setFill(Color.ORANGE);
//            HBox hBox = new HBox(new Circle(20, new ImagePattern(Objects.requireNonNull(UserController.getUserByUsername(message.getSenderUsername())).getImage())), text);
//            hBox.setPrefWidth(chatMessagesVBox.getPrefWidth());
//            hBox.setPrefHeight(50);
//            chatMessagesVBox.getChildren().add(hBox);
//        }
//    }
//
//    public void publicChatButtonClicked(MouseEvent mouseEvent) {
//        for (String username : UserController.getLoggedInUser().getChats().get("Public Chat").getUsernames()) {
//            selectedChats.add(Objects.requireNonNull(UserController.getUserByUsername(username)).getChats().get("Public Chat"));
//        }
//        changeChat(UserController.getLoggedInUser().getChats().get("Public Chat"));
//    }
}
