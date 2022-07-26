package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.enums.Avatars;
import Client.enums.QueryRequests;
import Client.enums.QueryResponses;
import Client.models.User;
import Client.models.network.Response;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class ProfileMenuController {
    @FXML
    private Circle usersAvatar;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField oldPasswordTextField;
    @FXML
    private TextField newPasswordTextField;
    @FXML
    private TextField newNicknameTextField;
    @FXML
    private ImageView first;
    @FXML
    private ImageView second;
    @FXML
    private ImageView third;
    @FXML
    private ImageView fourth;

    private final FileChooser fileChooser = new FileChooser();
    private int random = new Random().nextInt(0, 40);

    public void initialize() {
        if (!App.isMute() && App.getMediaPlayer().isMute()) {
            App.playNext();
        }
        passwordTextField.setFocusTraversable(false);
        newNicknameTextField.setFocusTraversable(false);
        oldPasswordTextField.setFocusTraversable(false);
        newPasswordTextField.setFocusTraversable(false);
        usersAvatar.setFill(new ImagePattern(MainMenuController.loggedInUser.getImage()));
        first.setImage(Avatars.valueOf("IMG" + random).getImage());
        random = (random + 1) % 40;
        second.setImage(Avatars.valueOf("IMG" + random).getImage());
        random = (random + 1) % 40;
        third.setImage(Avatars.valueOf("IMG" + random).getImage());
        random = (random + 1) % 40;
        fourth.setImage(Avatars.valueOf("IMG" + random).getImage());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("mainMenuPage");
    }

    public void changeNicknameButtonClicked(MouseEvent mouseEvent) {
        Response response = Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.CHANGE_NICKNAME, new HashMap<>() {{
            put("username", MainMenuController.loggedInUser.getUsername());
            put("password", passwordTextField.getText());
            put("nickname", newNicknameTextField.getText());
        }}));
        switch (response.getQueryResponse()) {
            case OK -> {
                MainMenuController.loggedInUser = new Gson().fromJson(response.getParams().get("user"), User.class);
                passwordTextField.setText("");
                passwordTextField.setStyle("-fx-border-color: #11aa11");
                newNicknameTextField.setText("");
                newNicknameTextField.setStyle("-fx-border-color: #11aa11");
            }
            case PASSWORD_INCORRECT -> {
                passwordTextField.setText("");
                passwordTextField.setPromptText("Password is incorrect!");
                passwordTextField.setStyle("-fx-border-color: #ff0022");
            }
            case NICKNAME_EXIST -> {
                newNicknameTextField.setText("");
                newNicknameTextField.setPromptText("Nickname already exists!");
                newNicknameTextField.setStyle("-fx-border-color: #ff0022");
            }
        }
    }

    public void changePasswordButtonClicked(MouseEvent mouseEvent) {
        Response response = Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.CHANGE_PASSWORD, new HashMap<>() {{
            put("username", MainMenuController.loggedInUser.getUsername());
            put("oldPassword", oldPasswordTextField.getText());
            put("newPassword", newPasswordTextField.getText());
        }}));
        switch (response.getQueryResponse()) {
            case OK -> {
                ClientSocketController.sendRequestAndGetResponse(QueryRequests.LOGOUT_USER, new HashMap<>(){{
                    put("username", MainMenuController.loggedInUser.getUsername());
                }});
                MainMenuController.loggedInUser = null;
                App.changeScene("loginPage");
            }
            case PASSWORD_INCORRECT -> {
                oldPasswordTextField.setText("");
                oldPasswordTextField.setPromptText("Password in incorrect!");
                oldPasswordTextField.setStyle("-fx-border-color: #ff0022");
            }
        }
    }

    public void resetChangeNicknameTextFields(KeyEvent keyEvent) {
        passwordTextField.setPromptText("Password");
        passwordTextField.setStyle("-fx-border-color: none");
        newNicknameTextField.setPromptText("New Nickname");
        newNicknameTextField.setStyle("-fx-border-color: none");
        oldPasswordTextField.setText("");
        newPasswordTextField.setText("");
    }

    public void resetChangePasswordTextFields(KeyEvent keyEvent) {
        oldPasswordTextField.setPromptText("Old Password");
        oldPasswordTextField.setStyle("-fx-border-color: none");
        newPasswordTextField.setPromptText("New Password");
        newPasswordTextField.setStyle("-fx-border-color: none");
        passwordTextField.setText("");
        newNicknameTextField.setText("");
    }

    public void clickedOnAvatar(MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();
        String fileAddress = "";
        if (first.equals(source)) {
            fileAddress = "IMG" + (random + 37) % 40;
        } else if (second.equals(source)) {
            fileAddress = "IMG" + (random + 38) % 40;
        } else if (third.equals(source)) {
            fileAddress = "IMG" + (random + 39) % 40;
        } else if (fourth.equals(source)) {
            fileAddress = "IMG" + random;
        }
        String finalFileAddress = fileAddress;
        Response response = Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.CHANGE_AVATAR, new HashMap<>() {{
            put("username", MainMenuController.loggedInUser.getUsername());
            put("address", finalFileAddress);
        }}));
        MainMenuController.loggedInUser = new Gson().fromJson(response.getParams().get("user"), User.class);
        usersAvatar.setFill(new ImagePattern(MainMenuController.loggedInUser.getImage()));
    }

    public void chooseFileButtonClicked(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Response response = Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.CHANGE_AVATAR, new HashMap<>() {{
                put("username", MainMenuController.loggedInUser.getUsername());
                put("address", file.toURI().toString());
            }}));
            MainMenuController.loggedInUser = new Gson().fromJson(response.getParams().get("user"), User.class);
            usersAvatar.setFill(new ImagePattern(new Image(file.toURI().toString())));
        }
    }

}
