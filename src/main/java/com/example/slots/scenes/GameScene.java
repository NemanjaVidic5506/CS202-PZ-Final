package com.example.slots.scenes;

import com.example.slots.Application;
import com.example.slots.database.Database;
import com.example.slots.database.data.BankAccount;
import com.example.slots.database.data.Game;
import com.example.slots.networking.Client;
import com.example.slots.utill.Matrix;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

public class GameScene extends Scene {
    private final Image[] images = new Image[4];
    private final ImageView[][] imageViews = new ImageView[3][5];

    private static final Color[] COLORS = {
            Color.YELLOW,
            Color.RED,
            Color.ORANGE,
            Color.GREENYELLOW,
            Color.BLUE,
            Color.BEIGE,
            Color.BLUEVIOLET,
            Color.GREEN,
            Color.PURPLE,
            Color.PEACHPUFF
    };
    private static final int[][] CONNECTIONS = {
            {0, 0, 2, 2, 0, 4},
            {0, 0, 0, 4},
            {0, 0, 0, 1, 2, 3, 2, 4},
            {1, 0, 2, 1, 2, 3, 1, 4},
            {1, 0, 1, 4},
            {1, 0, 0, 1, 0, 3, 1, 4},
            {2, 0, 2, 1, 0, 3, 0, 4},
            {2, 0, 2, 4},
            {2, 0, 0, 2, 2, 4},
            {2, 0, 1, 1, 1, 3, 0, 4}
    };
    private final Line[][] lines = new Line[10][];

    private void hideLines() {
        for (Line[] line : lines) {
            if (line != null) {
                for (Line l : line) {
                    if (l != null)
                        l.setVisible(false);
                }
            }
        }
    }
    /**
     * Sets the visibility of the graphical representation of a specific line.
     * Iterates through the Line objects associated with the given index in the lines array,
     * making them visible if they are not null.
     *
     * @param i The index representing the specific line to show.
     *          The lines array is assumed to contain graphical representations of different lines.
     */
    public void showLine(int i) {
        for (Line l : lines[i]) {
            if (l != null)
                l.setVisible(true);
        }
    }

    private final TextField creditsField;
    private final TextField winningsField;
    private double credits;
    private double bet;

    public GameScene(final Game game) {
        super(new VBox(), Application.WIDTH, Application.HEIGHT);

        super.setOnKeyReleased(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.ENTER) {
                onHit();
            }
        });

        final VBox root = (VBox) this.getRoot();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #4D5F81FF;");

        credits = BankAccount.getLocalBankAccount().getBalance();
        bet = game.getMinimalPay();

        for (int i = 0; i < 4; i++) {
            final String imageURL =
                    Objects.requireNonNull(getClass().getResource(
                            "/images/" + game.getGameName() + "/image" + i + ".png"
                    )).toExternalForm();
            images[i] = new Image(imageURL);
        }

        Button exitButton = new Button("Back to menu");
        exitButton.setOnAction(e -> {
            Database.updateBankAccount(BankAccount.getLocalBankAccount());
            Application.primaryStage.setScene(new MenuScene());
        });
        root.getChildren().add(exitButton);

        final Pane pane = new Pane();

        GridPane result = new GridPane();
        result.setAlignment(Pos.CENTER);
        result.setHgap(10);
        result.setVgap(10);
        result.setTranslateX(100);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                final ImageView imageView = new ImageView(images[0]);
                imageViews[i][j] = imageView;

                imageView.setFitWidth(100);
                imageView.setFitHeight(100);

                result.add(imageView, j, i);
            }
        }

        pane.getChildren().add(result);

        for (int i = 0; i < CONNECTIONS.length; i++) {
            lines[i] = new Line[CONNECTIONS[i].length / 2];
            for (int j = 0; j < CONNECTIONS[i].length - 2; j += 2) {
                Line line = getLine(i, j);

                lines[i][j / 2] = line;
                pane.getChildren().add(line);
            }
        }

        root.getChildren().addAll(pane);

        HBox lineRewards = new HBox(10);
        lineRewards.setAlignment(Pos.CENTER);
        final int[] rewards = {4, 2, 9, 6, 1, 7, 8, 3, 5, 10};
        for (int i = 0; i < rewards.length; i++) {
            Label label = new Label(String.valueOf(rewards[i]));
            label.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            label.setTextFill(Color.BLACK);
            label.setBackground(Background.fill(COLORS[i]));
            lineRewards.getChildren().add(label);
        }
        root.getChildren().add(lineRewards);

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        Label creditsLabel = new Label("Credits:");
        creditsField = new TextField();
        creditsField.setEditable(false);
        creditsField.setText(String.valueOf(credits));
        hBox.getChildren().addAll(creditsLabel, creditsField);

        Label betLabel = new Label("Bet:");
        TextField betField = new TextField();
        betField.setEditable(false);
        betField.setText(String.valueOf(bet));
        hBox.getChildren().addAll(betLabel, betField);

        Label winningsLabel = new Label("Winnings:");
        winningsField = new TextField();
        winningsField.setEditable(false);
        hBox.getChildren().addAll(winningsLabel, winningsField);

        root.getChildren().add(hBox);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        Button betOneButton = new Button("Bet 1");
        betOneButton.setOnAction(e -> {
            bet += 1.0;
            if (bet > game.getMaximalPay())
                bet = game.getMinimalPay();
            betField.setText(String.valueOf(bet));
        });
        Button betFiveButton = new Button("Bet 5");
        betFiveButton.setOnAction(e -> {
            bet += 5.0;
            if (bet > game.getMaximalPay())
                bet = game.getMinimalPay();
            betField.setText(String.valueOf(bet));
        });
        Button hitButton = getHitButton();
        buttons.getChildren().addAll(betOneButton, betFiveButton, hitButton);
        root.getChildren().add(buttons);
    }

    private static Line getLine(int i, int j) {
        Line line = new Line();

        line.setStroke(COLORS[i]);
        line.setStrokeWidth(4);

        int startY = CONNECTIONS[i][j];
        int startX = CONNECTIONS[i][j + 1];

        int endY = CONNECTIONS[i][j + 2];
        int endX = CONNECTIONS[i][j + 3];

        line.setStartX(100 + 100 * startX + 50 + startX * 10);
        line.setEndX(100 + 100 * endX + 50 + endX * 10);

        line.setStartY(100 * startY + startY * 10 + (i + 1) * 10);
        line.setEndY(100 * endY + endY * 10 + (i + 1) * 10);
        return line;
    }

    final Matrix resultMatrix = new Matrix();

    private Button getHitButton() {
        Button hitButton = new Button("Hit");
        hitButton.setOnAction(e -> onHit());
        return hitButton;
    }

    private void onHit() {
        hideLines();

        if (credits <= 0 || credits - bet < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not enough credits");
            alert.showAndWait();
            return;
        }

        Database.makeTransaction(false, bet);

        credits -= bet;
        creditsField.setText(String.valueOf(credits));

        final int[][] matrix = Client.request();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                imageViews[i][j].setImage(images[matrix[i][j]]);
            }
        }

        resultMatrix.setValues(matrix);
        final double totalAmount = bet * resultMatrix.getMultiplier(this::showLine);
        winningsField.setText(String.valueOf(totalAmount));
        credits += totalAmount;
        creditsField.setText(String.valueOf(credits));

        Database.makeTransaction(true, totalAmount);
        BankAccount.getLocalBankAccount().setBalance(credits);
    }
}