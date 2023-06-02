module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires services;
    requires Domain;
    requires networking;

    opens client.gui to javafx.fxml,javafx.base;
    exports client;
}