DROP
DATABASE IF EXISTS metSlots;
     CREATE
DATABASE metSlots;
            USE
metSlots;

CREATE TABLE bankAccount
(
    accountID INT            NOT NULL AUTO_INCREMENT PRIMARY KEY,
    balance   DECIMAL(10, 2) NOT NULL DEFAULT 1000.00
);

CREATE TABLE player
(
    playerID      INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    phone         VARCHAR(255),
    address       VARCHAR(255),
    bankAccountID INT          NOT NULL,
    FOREIGN KEY (bankAccountID) REFERENCES bankAccount (accountID)
);

CREATE TABLE game
(
    gameID     INT            NOT NULL AUTO_INCREMENT PRIMARY KEY,
    gameName   VARCHAR(50)    NOT NULL,
    minimalPay DECIMAL(10, 2) NOT NULL DEFAULT 1.0,
    maximalPay DECIMAL(10, 2) NOT NULL DEFAULT 10.0
);

CREATE TABLE gameTransactions
(
    gameTransactionID INT            NOT NULL AUTO_INCREMENT PRIMARY KEY,
    gameID            INT            NOT NULL,
    playerID          INT            NOT NULL,
    state             ENUM('PUSH', 'PAYOUT') NOT NULL,
    amount            DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (gameID) REFERENCES game (gameID),
    FOREIGN KEY (playerID) REFERENCES player (playerID)
);

INSERT INTO bankAccount
(balance)
VALUES (5000.00),
       (12561.00),
       (21515.00),
       (12311.00),
       (10.00);

INSERT INTO player
(name, password, email, phone, address, bankAccountID)
VALUES ('admin', 'admin', 'admin@gmail.com', '06/123456', 'address', 1),
       ('player1', 'player1', 'player1@gmail.com', NULL, 'address1', 2),
       ('player2', 'player2', 'player2@gmail.com', '06/654321', 'address2', 3),
       ('player3', 'player3', 'player3@gmail.com', NULL, 'address3', 4),
       ('player4', 'player4', 'player4@gmail.com', '06/987654', 'address4', 5);

INSERT INTO game
(gameName, minimalPay, maximalPay)
VALUES ('Game1', 1.00, 10.00),
       ('Game2', 2.00, 20.00),
       ('Game3', 3.00, 30.00),
       ('Game4', 4.00, 40.00),
       ('Game5', 5.00, 50.00);

INSERT INTO gameTransactions
(gameID, playerID, state, amount)
VALUES (1, 1, 'PUSH', 1.00),
       (2, 2, 'PAYOUT', 2.00),
       (3, 3, 'PUSH', 3.00),
       (4, 4, 'PAYOUT', 4.00),
       (5, 5, 'PUSH', 5.00);

-- TODO : Add one more table