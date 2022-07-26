module Client {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires com.google.gson;
    requires javafx.media;

    opens Client.views to javafx.fxml, com.google.gson;
    opens Client.enums to com.google.gson;
    opens Client.enums.resources to com.google.gson;
    opens Client.enums.tiles to com.google.gson;
    opens Client.enums.units to com.google.gson;
    opens Client.models to com.google.gson;
    opens Client.models.chats to com.google.gson;
    opens Client.models.network to com.google.gson;
    opens Client.models.tiles to com.google.gson;
    opens Client.models.resources to com.google.gson;
    opens Client.models.units to com.google.gson;
    exports Client.models.tiles to com.google.gson;
    exports Client.models.network to com.google.gson;
    exports Client.models;
    exports Client.models.chats;
    exports Client.enums;
    exports Client.models.units;
    exports Client.application to javafx.graphics;
    exports Client.views to javafx.fxml, com.google.gson;
}