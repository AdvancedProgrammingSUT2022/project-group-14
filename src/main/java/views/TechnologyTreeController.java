package views;

import application.App;
import enums.Technologies;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.util.*;

public class TechnologyTreeController {
    @FXML
    public HBox hBox;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Pane test;

    public void initialize() {
        hBox.setSpacing(200);
        initTechnologies();
//        Line line = new Line(10, 10, 200, 200);
//        anchorPane.getChildren().add(line);

    }

    public void initTechnologies() {
        Set<Technologies> upLayers = new HashSet<>(Collections.singleton(Technologies.AGRICULTURE));
        ArrayList<Technologies> currentLayer = new ArrayList<>(Collections.singleton(Technologies.AGRICULTURE));
        ArrayList<Technologies> downLayer = new ArrayList<>();
        Set<Technologies> downLayers = new HashSet<>(List.of(Technologies.values()));
        downLayers.remove(Technologies.AGRICULTURE);
        ArrayList<Technologies> formerLayer = currentLayer;
        int layerNumber = 0;

        addLayerToGroup(currentLayer);

        while (downLayers.size() > 0) {
            layerNumber++;
            Iterator<Technologies> iterator = downLayers.iterator();
            Set<String> upLayersNames = new HashSet<>();
            upLayers.forEach(x -> upLayersNames.add(x.getName().toUpperCase()));
            while (iterator.hasNext()) {
                Technologies technology = iterator.next();
                if (upLayersNames.containsAll(technology.getRequiredTechnologies())) {
                    iterator.remove(); //dige down layers nist
                    downLayer.add(technology);
                }
            }
            upLayers.addAll(downLayer);
            formerLayer = currentLayer;
            currentLayer = downLayer;     //TODO
            downLayer = new ArrayList<>(); //TODO clone...
            addLayerToGroup(currentLayer);
//            drawLinesBetweenLayers(formerLayer, currentLayer, layerNumber);
        }

    }

    public void drawLinesBetweenLayers(ArrayList<Technologies> formerLayer, ArrayList<Technologies> currentLayer, int layerNumber) {
        for (int i = 0; i < currentLayer.size(); i++) {
            for (int j = 0; j < formerLayer.size(); j++) {
                if (currentLayer.get(i).getRequiredTechnologies().contains(formerLayer.get(j).getName().toUpperCase())) {
                    double startX = ((VBox) hBox.getChildren().get(layerNumber - 1)).getChildren().get(j).getLayoutX() + hBox.getChildren().get(layerNumber - 1).getLayoutX() + scrollPane.getLayoutX();
                    double startY = ((VBox) hBox.getChildren().get(layerNumber - 1)).getChildren().get(j).getLayoutY() + hBox.getChildren().get(layerNumber - 1).getLayoutY() + scrollPane.getLayoutY();
                    double endX = ((VBox) hBox.getChildren().get(layerNumber)).getChildren().get(i).getLayoutX() + hBox.getChildren().get(layerNumber).getLayoutX() + scrollPane.getLayoutX();
                    double endY = ((VBox) hBox.getChildren().get(layerNumber)).getChildren().get(i).getLayoutY() + hBox.getChildren().get(layerNumber).getLayoutY() + scrollPane.getLayoutY();
                    Line line = new Line(startX, startY, endX, endY);

                    anchorPane.getChildren().add(line);

                }
            }
        }
    }

    public void addLayerToGroup(ArrayList<Technologies> currentLayer) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        for (Technologies technology : currentLayer) {
            vBox.getChildren().add(technology.getTechnologyGroup(0, 0));
        }
        hBox.getChildren().add(vBox);
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("gamePage");
    }
}
