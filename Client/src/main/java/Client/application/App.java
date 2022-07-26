package Client.application;

import Client.controllers.ClientSocketController;
import Client.enums.QueryRequests;
import Client.views.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

public class App extends javafx.application.Application {
    private static Scene scene;
    private static MediaPlayer mediaPlayer;
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        ClientSocketController.startConnecting(8000);

        App.stage = stage;
        Parent root = loadFXML("loginPage");
        assert root != null;
        App.scene = new Scene(root);

//        mediaPlayer = loadMediaPlayer("");
//        assert mediaPlayer != null;
//        mediaPlayer.play();

        stage.setScene(App.scene);
        stage.setTitle("CivilizationVI");
        stage.setResizable(false);
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.show();
    }

    @Override
    public void stop() throws IOException {
        if (MainMenuController.loggedInUser != null) {
            ClientSocketController.sendRequestAndGetResponse(QueryRequests.LEAVE_LOBBY, new HashMap<>(){{
                put("username", MainMenuController.loggedInUser.getUsername());
            }});
            ClientSocketController.sendRequestAndGetResponse(QueryRequests.LOGOUT_USER, new HashMap<>() {{
                put("username", MainMenuController.loggedInUser.getUsername());
            }});
        }
    }

    public static void changeScene(String address) {
        Parent root = loadFXML(address);
        App.scene.setRoot(root);
    }

    private static Parent loadFXML(String address) {
        try {
            URL url = new URL(Objects.requireNonNull(App.class.getResource("/fxml/" + address + ".fxml")).toExternalForm());
            return FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void showPopUp(Popup popup) {
        popup.setX(popup.getX() + stage.getX());
        popup.setY(popup.getY() + stage.getY());
        popup.show(stage);
    }

    public static void muteMedia() {
        App.mediaPlayer.setMute(!App.mediaPlayer.isMute());
    }

    public static void changeMedia(String address) {
        App.mediaPlayer.stop();
        App.mediaPlayer = loadMediaPlayer(address);
        assert App.mediaPlayer != null;
        App.mediaPlayer.play();
    }

    private static MediaPlayer loadMediaPlayer(String address) {
        try{
            Media media = new Media(Objects.requireNonNull(App.class.getResource("/musics/" + address + ".mp3")).toExternalForm());
            return new MediaPlayer(media);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }


}
