module com.example.slots {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.slots to javafx.fxml;
    exports com.example.slots;
    exports com.example.slots.scenes;
    exports com.example.slots.utill;
    exports com.example.slots.database.data;
    opens com.example.slots.scenes to javafx.fxml;
}