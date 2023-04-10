module com.example.demoiss {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demoiss to javafx.fxml;
    exports com.example.demoiss;
}