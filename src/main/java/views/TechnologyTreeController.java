package views;

import enums.Technologies;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.util.HashSet;

public class TechnologyTreeController {
    @FXML
    public AnchorPane mainPane;

    public void initialize() {
        initTechnologies();
    }

    public void initTechnologies() {
        HashSet<Technologies> upLayers, currentLayer, DownLayer;

    }
}
