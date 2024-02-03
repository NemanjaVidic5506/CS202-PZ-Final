package com.example.slots.scenes;

import com.example.slots.Application;
import com.example.slots.database.Database;
import com.example.slots.database.data.BankAccount;
import com.example.slots.database.data.Game;
import com.example.slots.database.data.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class MenuScene extends Scene {
    public MenuScene() {
        super(new HBox(10), Application.WIDTH, Application.HEIGHT);

        final HBox root = (HBox) this.getRoot();
        root.setAlignment(javafx.geometry.Pos.CENTER);
        root.setStyle("-fx-background-color: #4D5F81FF");

        final VBox userPanel = new VBox();
        userPanel.setAlignment(javafx.geometry.Pos.CENTER);

        final Player player = Player.getLocalPlayer();

        final Image image = new Image("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        userPanel.getChildren().add(imageView);

        Label username = new Label();
        username.setStyle("-fx-font-size: 20px;");
        username.setText("Username: " + player.getName());
        userPanel.getChildren().add(username);

        Label email = new Label();
        email.setStyle("-fx-font-size: 18px;");
        email.setText("Email: " + player.getEmail());
        userPanel.getChildren().add(email);

        Label balance = new Label();
        balance.setStyle("-fx-font-size: 18px;");
        balance.setText("Balance: " + BankAccount.getLocalBankAccount().getBalance());
        userPanel.getChildren().add(balance);

        final GridPane gamesGrid = getGamePane();
        root.getChildren().addAll(userPanel, gamesGrid);
    }

    private static GridPane getGamePane() {
        final GridPane gamesGrid = new GridPane();
        gamesGrid.setAlignment(Pos.CENTER);
        gamesGrid.setHgap(10);
        gamesGrid.setVgap(10);
        final List<Game> games = Database.getGames();
        final int columns = 3;
        int column = 0;
        int row = 0;
        for (Game game : games) {
            Rectangle rectangle = new Rectangle();
            rectangle.setOnMouseClicked(e -> {
                Application.primaryStage.setScene(new GameScene(game));
            });
            rectangle.setWidth(100);
            rectangle.setHeight(100);
            rectangle.setFill(Color.OLIVE);
            rectangle.setStroke(Color.RED);
            rectangle.setStrokeWidth(2);
            gamesGrid.add(rectangle, column, row);

            VBox temp = new VBox();
            temp.setMouseTransparent(true); // Setujemo da ne moze da se klikne
            temp.setAlignment(Pos.CENTER);
            Label gameName = new Label();
            gameName.setStyle("-fx-font-size: 20px; -fx-text-fill: blue");
            gameName.setAlignment(Pos.TOP_CENTER);
            gameName.setText(game.getGameName());
            temp.getChildren().add(gameName);

            Label gameDetails = new Label();
            gameDetails.setStyle("-fx-font-size: 14px; -fx-text-fill: green");
            gameDetails.setAlignment(Pos.BOTTOM_CENTER);
            gameDetails.setText("Minimal pay: " + game.getMinimalPay() + "\nMaximal pay: " + game.getMaximalPay());
            temp.getChildren().add(gameDetails);

            gamesGrid.add(temp, column, row);

            column++;
            if (column >= columns) {
                column = 0;
                row++;
            }
        }
        return gamesGrid;
    }
}
