package views;

import application.App;
import enums.Technologies;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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

    public int TechnologyGroupWidth = 270;
    public int TechnologyGroupLength = 80;
    public final int hBoxSpacing = 200;

    public void initialize() {
        hBox.setSpacing(hBoxSpacing);
        initTechnologies();
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
            drawLinesBetweenLayers(formerLayer, currentLayer, layerNumber);
        }

    }

    public void drawLinesBetweenLayers(ArrayList<Technologies> formerLayer, ArrayList<Technologies> currentLayer, int layerNumber) {
        for (int i = 0; i < currentLayer.size(); i++) {
            for (int j = 0; j < formerLayer.size(); j++) {
                if (currentLayer.get(i).getRequiredTechnologies().contains(formerLayer.get(j).getName().toUpperCase())) {
                    double startX = getVBoxStartXByNumber(layerNumber - 1) + 270;
                    double startY = getVBoxStartYByNumber(j, formerLayer.size());
                    double endX = getVBoxStartXByNumber(layerNumber);
                    double endY = getVBoxStartYByNumber(i, currentLayer.size());
                    Line line = new Line(startX, startY, endX, endY);
                    line.setStrokeWidth(2.0);
                    anchorPane.getChildren().add(line);

                }
            }
        }
    }

    public double getVBoxStartXByNumber(int number) {
        return number * (hBoxSpacing + 270);
    }

    public double getVBoxStartYByNumber(int i, int number) {
        if (number % 2 == 1)
            return 360 + 100 * (i - (double) (number - 1) / 2);
        else
            return 360 + 100 * (i - (double) (number) / 2) + 40;
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
