package com.example.slots.database.data;

public class BankAccount {
    private static BankAccount localBankAccount;

    public static BankAccount getLocalBankAccount() {
        return localBankAccount;
    }

    public static void setLocalBankAccount(BankAccount bankAccount) {
        localBankAccount = bankAccount;
    }

    private final int accountID;

    private double balance;

    public BankAccount(int accountID, double balance) {
        this.accountID = accountID;
        this.balance = balance;
    }

    public BankAccount(int accountID) {
        this(accountID, 0);
    }

    public int getAccountID() {
        return accountID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
