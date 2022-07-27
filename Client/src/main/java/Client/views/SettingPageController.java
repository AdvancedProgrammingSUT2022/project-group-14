package Client.views;

import Client.application.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class SettingPageController {

    @FXML
    private Button muteButton;

    public void initialize() {
        if (App.isMute()) muteButton.setText("play music");
        else muteButton.setText("mute music");
    }

    public void muteButtonClicked(MouseEvent mouseEvent) {
        if (muteButton.getText().equals("mute music")) {
            App.setMute(true);
            App.muteMedia();
            muteButton.setText("play music");
        }
        else {
            App.setMute(false);
            App.playNext();
            muteButton.setText("mute music");
        }
    }

    public void nextTrackClicked(MouseEvent mouseEvent) {
        if (!App.isMute()) App.playNext();
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("gamePage");
    }
}
