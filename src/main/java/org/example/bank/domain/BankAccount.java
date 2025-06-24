package org.example.bank.domain;

import org.example.bank.util.AmountValidator;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private final List<Transaction> transactions = new ArrayList<>();
    private int balance = 0;

    public void deposit(int amount) {
        AmountValidator.requirePositive(amount);
        balance += amount;
        transactions.add(Transaction.ofDeposit(amount, balance));
    }

    public void withdraw(int amount) {
        AmountValidator.requirePositive(amount);
        balance -= amount;
        transactions.add(Transaction.ofWithdrawal(amount, balance));
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }
}