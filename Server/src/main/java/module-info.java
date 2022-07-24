module Server {
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.graphics;
    opens Server.models to com.google.gson;
    opens Server.controllers to javafx.fxml, javafx.graphics;
    exports Server.controllers;
    exports Server.models;
}