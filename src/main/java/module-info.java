module com.example.laboration3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    requires org.junit.jupiter.api;

    opens com.example.laboration3 to javafx.fxml;
    exports com.example.laboration3;
}