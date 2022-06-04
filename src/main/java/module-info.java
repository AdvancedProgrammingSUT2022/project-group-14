module Civilization {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires com.google.gson;

    opens application to javafx.fxml;
    exports application;
    exports views;
    exports models;
    opens views to javafx.fxml;
    opens models;
}