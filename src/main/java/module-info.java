module com.example.laboration3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.laboration3 to javafx.fxml;
    exports com.example.laboration3;
}