/**
 *
 */
module com.example.projectl {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.projectl to javafx.fxml;
    exports com.example.projectl;
}
