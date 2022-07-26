package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.enums.QueryRequests;
import Client.enums.QueryResponses;
import Client.models.User;
import Client.models.network.Response;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class LoginPageController {
    @FXML
    private TextField nicknameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    public void initialize() {
        if (!App.isMute() && App.getMediaPlayer().isMute()) {
            App.playNext();
        }
        usernameTextField.setFocusTraversable(false);
        nicknameTextField.setFocusTraversable(false);
        passwordTextField.setFocusTraversable(false);
    }

    public void loginButtonClicked(MouseEvent mouseEvent) throws IOException {
        Response response = Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.LOGIN_USER, new HashMap<>() {{
            put("username", usernameTextField.getText());
            put("password", passwordTextField.getText());
        }}));
        switch (response.getQueryResponse()) {
            case OK -> {
                MainMenuController.loggedInUser = new Gson().fromJson(response.getParams().get("user"), User.class);
                ClientSocketController.startListener(8000);
                App.changeScene("mainMenuPage");
            }
            case USER_NOT_EXIST, PASSWORD_INCORRECT -> {
                usernameTextField.setStyle("-fx-border-color: #ff0022");
                passwordTextField.setStyle("-fx-border-color: #ff0022");
                loginButton.setDisable(true);
                passwordTextField.setText("");
                passwordTextField.setPromptText("Username and Password didn't match");
            }
        }
    }

    public void registerButtonClicked(MouseEvent mouseEvent) {
        if (usernameTextField.getText().length() == 0 || nicknameTextField.getText().length() == 0 || passwordTextField.getText().length() == 0) {
            if (usernameTextField.getText().length() == 0) {
                usernameTextField.setStyle("-fx-border-color: #ff0022");
                usernameTextField.setPromptText("Complete this field!");
            }
            if (nicknameTextField.getText().length() == 0) {
                nicknameTextField.setStyle("-fx-border-color: #ff0022");
                nicknameTextField.setPromptText("Complete this field!");
            }
            if (passwordTextField.getText().length() == 0) {
                passwordTextField.setStyle("-fx-border-color: #ff0022");
                passwordTextField.setPromptText("Complete this field!");
            }
        } else {
            QueryResponses queryResponse = Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.CREATE_USER, new HashMap<>() {{
                put("username", usernameTextField.getText());
                put("nickname", nicknameTextField.getText());
                put("password", passwordTextField.getText());
            }})).getQueryResponse();
            switch (queryResponse) {
                case OK -> {
                    usernameTextField.setStyle("-fx-border-color: #11aa11");
                    nicknameTextField.setStyle("-fx-border-color: #11aa11");
                    passwordTextField.setStyle("-fx-border-color: #11aa11");
                }
                case USERNAME_EXIST -> {
                    usernameTextField.setStyle("-fx-border-color: #ff0022");
                    usernameTextField.setText("");
                    usernameTextField.setPromptText("Username already exists!");
                }
                case NICKNAME_EXIST -> {
                    nicknameTextField.setStyle("-fx-border-color: #ff0022");
                    nicknameTextField.setText("");
                    nicknameTextField.setPromptText("Nickname already exists!");
                }
            }
        }
    }

    public void resetTextField(KeyEvent keyEvent) {
        usernameTextField.setStyle("-fx-border-color: none");
        usernameTextField.setPromptText("Enter your username");
        nicknameTextField.setStyle("-fx-border-color: none");
        nicknameTextField.setPromptText("Enter your nickname");
        passwordTextField.setStyle("-fx-border-color: none");
        passwordTextField.setPromptText("Enter your password");
        registerButton.setDisable(false);
        loginButton.setDisable(false);
    }
}
