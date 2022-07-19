package views;

import enums.Technologies;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class TechnologyTreeController {
    @FXML
    public AnchorPane mainPane;

    public void initialize() {
        initTechnologies();
    }

    public void initTechnologies() {
        ArrayList<Technologies> currentLayer = new ArrayList<>(Collections.singleton(Technologies.AGRICULTURE));
        HashSet<Technologies> downLayers = new HashSet<>(List.of(Technologies.values()));
        downLayers.remove(Technologies.AGRICULTURE);
        ArrayList<Technologies> downLayer = new ArrayList<>();
        VBox vbox = new VBox();
        vbox.getChildren().add(Technologies.getTechnologyGroup(Technologies.AGRICULTURE,0,0));


    }
}
