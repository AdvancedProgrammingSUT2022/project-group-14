package Client.application;

import Client.controllers.ClientSocketController;
import Client.enums.QueryRequests;
import Client.views.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class App extends javafx.application.Application {
    private static Scene scene;
    private static MediaPlayer mediaPlayer;
    private static Stage stage;
    private static final String[] musics = {"Hans Zimmer - Time - musicgeek.ir", "Hans-Zimmer-S.T.A.Y",
                                        "16 Ramin Djawadi - To Vaes Dothrak", "02 Ramin Djawadi - A Lannister Always Pays His Debts",
                                        "19 Ramin Djawadi - For the Realm", "01. Main Titles", "02 Blood of the Dragon",
                                        "03. Light of the Seven", "16. Trust Each Other", "04 - The Queen's Justice", "20 - Ironborn"};
    private static final AudioClip swordSound = new AudioClip(Objects.requireNonNull(App.class.getResource("/musics/draw-sword1-44724.mp3")).toString());


    private static final AudioClip coinSound = new AudioClip(Objects.requireNonNull(App.class.getResource("/musics/coin.mp3")).toString());
    private static boolean mute = false;
    private static int indexOfCurrentMedia;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        ClientSocketController.startConnecting(8000);
        indexOfCurrentMedia = new Random().nextInt(11);
        changeMedia(musics[indexOfCurrentMedia]);
        App.stage = stage;
        Parent root = loadFXML("loginPage");
        assert root != null;
        App.scene = new Scene(root);


        stage.setScene(App.scene);
        stage.setTitle("CivilizationVI");
        stage.setResizable(false);
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.show();
    }

    public static void setMute(boolean mute) {
        App.mute = mute;
    }

    public static void playNext() {
        indexOfCurrentMedia++;
        indexOfCurrentMedia %= 11;
        changeMedia(musics[indexOfCurrentMedia]);
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
        if (mediaPlayer != null)
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

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static boolean isMute() {
        return mute;
    }

    public static AudioClip getSwordSound() {
        return swordSound;
    }

    public static AudioClip getCoinSound() {
        return coinSound;
    }
}
