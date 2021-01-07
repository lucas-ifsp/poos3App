module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;

    opens org.example.view to javafx.fxml;
    opens org.example.controller to javafx.fxml;
    opens org.example.model to javafx.base;

    exports org.example.view;
    exports org.example.controller;
}