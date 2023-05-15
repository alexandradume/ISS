module com.example.demoiss {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires spring.context;
    requires java.naming;
    requires net.bytebuddy;


    opens com.example.demoiss to javafx.fxml;
    opens com.example.demoiss.model;
    exports com.example.demoiss;
}