package com.example.slots.scenes;

import com.example.slots.Application;
import com.example.slots.database.Database;
import com.example.slots.database.data.BankAccount;
import com.example.slots.database.data.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginScene extends Scene {

    public LoginScene() {
        super(new VBox(5), Application.WIDTH, Application.HEIGHT);

        final VBox vbox = (VBox) getRoot();
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #4d5f81");

        Label usernameLabel = new Label("Username:");
        final TextField usernameField = new TextField();
        usernameField.setMaxWidth(200);

        Label passwordLabel = new Label("Password:");
        final PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(200);

        Button loginButton = getLoginButton(usernameField, passwordField);

        vbox.getChildren().addAll(
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                loginButton
        );
    }

    private static Button getLoginButton(TextField usernameField, PasswordField passwordField) {
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            final String username = usernameField.getText();
            final String password = passwordField.getText();

            final int id = Database.validateLogin(username, password);
            if (id == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid username or password.");
                alert.showAndWait();
                return;
            }
            Player.setLocalPlayer(Database.getPlayer(id));
            BankAccount.setLocalBankAccount(Database.getBankAccount(Player.getLocalPlayer().getBankAccountID()));
            Application.primaryStage.setScene(new MenuScene());
        });
        return loginButton;
    }
}