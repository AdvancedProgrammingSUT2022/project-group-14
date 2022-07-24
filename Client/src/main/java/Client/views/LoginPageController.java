package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.enums.QueryCommands;
import Client.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;

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
        usernameTextField.setFocusTraversable(false);
        nicknameTextField.setFocusTraversable(false);
        passwordTextField.setFocusTraversable(false);
    }

    public void loginButtonClicked(MouseEvent mouseEvent) {
        //User user = UserController.getUserByUsername(usernameTextField.getText());
        User user = (User) ClientSocketController.sendRequestAndGetResponse(QueryCommands.GET_USER_BY_USERNAME, new HashMap<String, Object>(){{put("username", usernameTextField.getText());}}).getReturnedValue();
        if (user == null || !user.getPassword().equals(passwordTextField.getText())) {
            usernameTextField.setStyle("-fx-border-color: #ff0022");
            passwordTextField.setStyle("-fx-border-color: #ff0022");
            loginButton.setDisable(true);
            passwordTextField.setText("");
            passwordTextField.setPromptText("Username and Password didn't match");
        } else {
            System.out.println(user.getUsername());
            //UserController.setLoggedInUser(user);
            ClientSocketController.sendRequestAndGetResponse(QueryCommands.SET_LOGGED_IN_USER, new HashMap<>(){{put("user", user);}});
            //App.changeScene("mainMenuPage");
            System.out.println("got in");
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
        } else if (ClientSocketController.sendRequestAndGetResponse(QueryCommands.GET_USER_BY_USERNAME, new HashMap<String, Object>(){{put("username", usernameTextField.getText());}}).getReturnedValue() != null) {
            usernameTextField.setStyle("-fx-border-color: #ff0022");
            usernameTextField.setText("");
            usernameTextField.setPromptText("Username already exists!");
        } else if (ClientSocketController.sendRequestAndGetResponse(QueryCommands.GET_USER_BY_NICKNAME, new HashMap<String, Object>(){{put("username", nicknameTextField.getText());}}).getReturnedValue() != null) {
            nicknameTextField.setStyle("-fx-border-color: #ff0022");
            nicknameTextField.setText("");
            nicknameTextField.setPromptText("Nickname already exists!");
        } else {
            //UserController.addUser(usernameTextField.getText(), passwordTextField.getText(), nicknameTextField.getText());
            ClientSocketController.sendRequestAndGetResponse(QueryCommands.ADD_USER, new HashMap<>(){{
                                                                                                        put("username", usernameTextField.getText());
                                                                                                        put("password", passwordTextField.getText());
                                                                                                        put("nickname", nicknameTextField.getText());}});
            usernameTextField.setStyle("-fx-border-color: #11aa11");
            nicknameTextField.setStyle("-fx-border-color: #11aa11");
            passwordTextField.setStyle("-fx-border-color: #11aa11");
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
