module Civilization {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires com.google.gson;

    opens application to javafx.fxml;
    exports application;
    exports views;
    exports models;
    exports enums.tiles;
    opens views to javafx.fxml;
    opens models;
    exports models.chats;
    opens models.chats;
    exports models.tiles;
    opens models.tiles;
}