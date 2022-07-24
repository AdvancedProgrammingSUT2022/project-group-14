module Client {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires com.google.gson;
    requires javafx.media;

    opens Client.views to javafx.fxml;
    exports Client.application to javafx.graphics;
    exports Client.views to javafx.fxml;
}