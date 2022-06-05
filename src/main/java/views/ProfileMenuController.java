package views;

import application.App;
import controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import models.imagesEnums.Avatars;

import java.io.File;
import java.util.Objects;

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

    public void initialize() {
        passwordTextField.setFocusTraversable(false);
        newNicknameTextField.setFocusTraversable(false);
        oldPasswordTextField.setFocusTraversable(false);
        newPasswordTextField.setFocusTraversable(false);
        usersAvatar.setFill(new ImagePattern(UserController.getLoggedInUser().getImage()));
        first.setImage(Avatars.IMG0.getImage());
        second.setImage(Avatars.IMG1.getImage());
        third.setImage(Avatars.IMG2.getImage());
        fourth.setImage(Avatars.IMG3.getImage());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("mainMenuPage");
    }

    public void changeNicknameButtonClicked(MouseEvent mouseEvent) {
        if (!UserController.getLoggedInUser().getPassword().equals(passwordTextField.getText())) {
            passwordTextField.setText("");
            passwordTextField.setPromptText("Password is incorrect!");
            passwordTextField.setStyle("-fx-border-color: #ff0022");
        } else if (UserController.getUserByNickname(newNicknameTextField.getText()) != null) {
            newNicknameTextField.setText("");
            newNicknameTextField.setPromptText("Nickname already exists!");
            newNicknameTextField.setStyle("-fx-border-color: #ff0022");
        } else {
            UserController.getLoggedInUser().setNickname(newNicknameTextField.getText());
            passwordTextField.setText("");
            passwordTextField.setStyle("-fx-border-color: #11aa11");
            newNicknameTextField.setText("");
            newNicknameTextField.setStyle("-fx-border-color: #11aa11");
        }
    }

    public void changePasswordButtonClicked(MouseEvent mouseEvent) {
        if (!UserController.getLoggedInUser().getPassword().equals(oldPasswordTextField.getText())) {
            oldPasswordTextField.setText("");
            oldPasswordTextField.setPromptText("Password in incorrect!");
            oldPasswordTextField.setStyle("-fx-border-color: #ff0022");
        } else {
            UserController.getLoggedInUser().setPassword(newPasswordTextField.getText());
            oldPasswordTextField.setText("");
            oldPasswordTextField.setStyle("-fx-border-color: #11aa11");
            newPasswordTextField.setText("");
            newPasswordTextField.setStyle("-fx-border-color: #11aa11");
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
        if (first.equals(source)) {
            UserController.getLoggedInUser().setAvatarFileAddress("IMG0");
            usersAvatar.setFill(new ImagePattern(UserController.getLoggedInUser().getImage()));
        } else if (second.equals(source)) {
            UserController.getLoggedInUser().setAvatarFileAddress("IMG1");
            usersAvatar.setFill(new ImagePattern(UserController.getLoggedInUser().getImage()));
        } else if (third.equals(source)) {
            UserController.getLoggedInUser().setAvatarFileAddress("IMG2");
            usersAvatar.setFill(new ImagePattern(UserController.getLoggedInUser().getImage()));
        } else if (fourth.equals(source)) {
            UserController.getLoggedInUser().setAvatarFileAddress("IMG3");
            usersAvatar.setFill(new ImagePattern(UserController.getLoggedInUser().getImage()));
        }
    }

    public void chooseFileButtonClicked(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            UserController.getLoggedInUser().setAvatarFileAddress(file.toURI().toString());
            usersAvatar.setFill(new ImagePattern(new Image(file.toURI().toString())));
        }
    }

}
