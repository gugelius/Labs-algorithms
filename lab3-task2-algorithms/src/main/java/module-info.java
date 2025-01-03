module com.example.lab3task2algorithms {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lab3task2algorithms to javafx.fxml;
    exports com.example.lab3task2algorithms;
}