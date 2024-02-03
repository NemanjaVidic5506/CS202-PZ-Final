package com.example.slots.database;

import com.example.slots.database.data.BankAccount;
import com.example.slots.database.data.Game;
import com.example.slots.database.data.Player;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.List;

public class Database {
    private static boolean connected = false;
    private static final String URL = "jdbc:mysql://localhost/metSlots";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    /**
     * Establishes a connection to a MySQL database using the JDBC driver.
     * If already connected, this method returns without attempting to reconnect.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void connect() throws ClassNotFoundException, SQLException {
        if (connected) return;

        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(URL, USER, PASSWORD);

        connected = true;
    }

    /**
     * Disconnects from the MySQL database by closing the established connection.
     * If not currently connected, this method returns without attempting to disconnect.
     *
     * @throws RuntimeException if a SQLException occurs while attempting to close the connection.
     *                         The SQLException is wrapped in a RuntimeException for simplicity.
     */
    public static void disconnect() {
        if (!connected) return;

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        connected = false;
    }
    /**
     * Validates user login credentials by querying the 'player' table in the database.
     *
     * @param username The username to be validated.
     * @param password The password associated with the provided username.
     * @return The playerID if the login is successful; otherwise, returns -1.
     * @throws RuntimeException if a SQLException occurs during the database query or connection handling.
     *                         The SQLException is wrapped in a RuntimeException for simplicity.
     */
    public static int validateLogin(final String username, final String password) {
        final String query = """
                SELECT * FROM player WHERE name = ? AND password = ?;
                """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("playerID");
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Retrieves a list of BankAccount objects by querying the 'bankAccount' table in the database.
     *
     * @return A List of BankAccount objects containing accountID and balance information.
     * @throws RuntimeException if a SQLException occurs during the database query or connection handling.
     *                         The SQLException is wrapped in a RuntimeException for simplicity.
     */
    public static List<BankAccount> getBankAccounts() {
        final String query = """
                SELECT * FROM bankAccount;
                """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<BankAccount> bankAccounts = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    bankAccounts.add(new BankAccount(resultSet.getInt("accountID"), resultSet.getDouble("balance")));
                }
                return bankAccounts;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a specific BankAccount object by querying the 'bankAccount' table in the database based on the accountID.
     *
     * @param accountID The unique identifier of the BankAccount to retrieve.
     * @return The BankAccount object with the specified accountID, or null if not found.
     * @throws RuntimeException if a SQLException occurs during the database query or connection handling.
     *                         The SQLException is wrapped in a RuntimeException for simplicity.
     */
    public static BankAccount getBankAccount(final int accountID) {
        final List<BankAccount> bankAccounts = getBankAccounts();
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getAccountID() == accountID) {
                return bankAccount;
            }
        }
        return null;
    }
    /**
     * Updates the balance of a BankAccount in the 'bankAccount' table in the database.
     *
     * @param bankAccount The BankAccount object containing the updated balance and accountID.
     * @throws RuntimeException if a SQLException occurs during the database update or connection handling.
     *                         The SQLException is wrapped in a RuntimeException for simplicity.
     */
    public static void updateBankAccount(final BankAccount bankAccount) {
        final String query = """
                UPDATE bankAccount SET balance = ? WHERE accountID = ?;
                """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, bankAccount.getBalance());
            statement.setInt(2, bankAccount.getAccountID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Retrieves a list of Player objects by querying the 'player' table in the database.
     *
     * @return A List of Player objects containing playerID, name, email, phone, address, and bankAccountID information.
     * @throws RuntimeException if a SQLException occurs during the database query or connection handling.
     *                         The SQLException is wrapped in a RuntimeException for simplicity.
     */
    public static List<Player> getPlayers() {
        final String query = """
                    SELECT * FROM player;
                """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Player> players = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    players.add(new Player(resultSet.getInt("playerID"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getString("address"),
                            resultSet.getInt("bankAccountID")
                    ));
                }
                return players;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Retrieves a specific Player object by querying the 'player' table in the database based on the playerID.
     *
     * @param playerID The unique identifier of the Player to retrieve.
     * @return The Player object with the specified playerID, or null if not found.
     * @throws RuntimeException if a SQLException occurs during the database query or connection handling.
     *                         The SQLException is wrapped in a RuntimeException for simplicity.
     */
    public static Player getPlayer(final int playerID) {
        final List<Player> players = getPlayers();
        for (Player player : players) {
            if (player.getPlayerID() == playerID) {
                return player;
            }
        }
        return null;
    }
    /**
     * Retrieves a list of Game objects by querying the 'game' table in the database.
     *
     * @return A List of Game objects containing gameID, gameName, minimalPay, and maximalPay information.
     * @throws RuntimeException if a SQLException occurs during the database query or connection handling.
     *                         The SQLException is wrapped in a RuntimeException for simplicity.
     */
    public static List<Game> getGames() {
        final String query = """
                SELECT * FROM game;
                """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Game> games = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    games.add(new Game(resultSet.getInt("gameID"),
                            resultSet.getString("gameName"),
                            resultSet.getDouble("minimalPay"),
                            resultSet.getDouble("maximalPay")));
                }
                return games;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Retrieves a specific Game object by querying the 'game' table in the database based on the gameID.
     *
     * @param gameID The unique identifier of the Game to retrieve.
     * @return The Game object with the specified gameID, or null if not found.
     * @throws RuntimeException if a SQLException occurs during the database query or connection handling.
     *                         The SQLException is wrapped in a RuntimeException for simplicity.
     */
    public static Game getGame(final int gameID) {
        final List<Game> games = getGames();
        for (Game game : games) {
            if (game.getGameID() == gameID) {
                return game;
            }
        }
        return null;
    }

    public static void makeTransaction(boolean payout, double amount) {
        return;
//        final String query = """
//                INSERT INTO gameTransactions (playerID, state, amount) VALUES (?, ?, ?);
//                """;
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, Player.getLocalPlayer().getPlayerID());
//            statement.setString(2, payout ? "PAYOUT" : "PUSH");
//            statement.setDouble(3, amount);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}