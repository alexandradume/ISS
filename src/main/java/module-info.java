module com.example.offfff {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.offfff to javafx.fxml;
    exports com.example.offfff;
}