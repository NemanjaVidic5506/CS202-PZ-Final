package com.example.slots;

import com.example.slots.database.Database;
import com.example.slots.database.data.Game;
import com.example.slots.networking.Client;
import com.example.slots.scenes.GameScene;
import com.example.slots.scenes.LoginScene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Application extends javafx.application.Application {
    public static Stage primaryStage;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    /**
     * Initializes and starts the JavaFX application by setting up the primary stage, establishing connections
     * to the database and server, and setting the initial scene to the login scene.
     *
     * @param stage The primary stage of the JavaFX application.
     * @throws IOException if an I/O error occurs during the setup or connection process.
     *                     The IOException is thrown when there is a failure in the underlying network or database operations.
     * @see LoginScene The initial scene set for the primary stage.
     */

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        stage.setTitle("MetSlots");

        try {
            Database.connect();
            Client.connect();
        } catch (ClassNotFoundException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return;
        }
        stage.setScene(new LoginScene());
        stage.show();
    }
    /**
     * Overrides the default JavaFX application stop method to ensure proper cleanup and disconnection
     * from the database and server when the application is closed.
     *
     * @throws Exception if an exception occurs during the cleanup process.
     *                   The exception is thrown when there is an issue in the underlying operations.
     */
    @Override
    public void stop() throws Exception {
        Database.disconnect();
        Client.disconnect();
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}