module Server {
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.graphics;
    opens Server.models to com.google.gson;
    opens Server.controllers to javafx.fxml, javafx.graphics;
    opens Server.models.chats to com.google.gson;
    opens Server.models.network to com.google.gson;
    opens Server.enums to com.google.gson;
    exports Server.enums to com.google.gson;
    exports Server.models.network to com.google.gson;
    exports Server.controllers;
    exports Server.models;
}