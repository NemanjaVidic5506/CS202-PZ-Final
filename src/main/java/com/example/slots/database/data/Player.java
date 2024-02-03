package com.example.slots.database.data;

public class Player {
    private static Player localPlayer;

    public static Player getLocalPlayer() {
        return localPlayer;
    }

    public static void setLocalPlayer(Player player) {
        localPlayer = player;
    }

    private final int playerID;
    private final String name;
    private final String email;
    private String phone;
    private String address;
    private final int bankAccountID;

    public Player(int playerID, String name, String email, String phone, String address, int bankAccountID) {
        this.playerID = playerID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bankAccountID = bankAccountID;
    }

    public int getPlayerID() {
        return playerID;
    }

    /**
     * A method to get the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBankAccountID() {
        return bankAccountID;
    }
}
