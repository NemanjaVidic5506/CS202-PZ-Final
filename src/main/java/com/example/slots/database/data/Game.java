package com.example.slots.database.data;

public class Game {
    private final int gameID;
    private final String gameName;
    private final double minimalPay;
    private final double maximalPay;

    public Game(int gameID, String gameName, double minimalPay, double maximalPay) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.minimalPay = minimalPay;
        this.maximalPay = maximalPay;
    }

    public int getGameID() {
        return gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public double getMinimalPay() {
        return minimalPay;
    }

    public double getMaximalPay() {
        return maximalPay;
    }
}