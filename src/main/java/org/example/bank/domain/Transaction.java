package org.example.bank.domain;

import java.time.LocalDate;

public class Transaction {
    private final LocalDate date;
    private final int amount;
    private final int balance;

    private Transaction(LocalDate date, int amount, int balance) {
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }

    public static Transaction ofDeposit(int amount, int balance) {
        return new Transaction(LocalDate.now(), amount, balance);
    }

    public static Transaction ofWithdrawal(int amount, int balance) {
        return new Transaction(LocalDate.now(), -amount, balance);
    }

    public LocalDate getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        // format "YYYY-MM-DD | +100 | 100"
        String sign = amount >= 0 ? "+" : "";
        return date + " | " + sign + amount + " | " + balance;
    }
}