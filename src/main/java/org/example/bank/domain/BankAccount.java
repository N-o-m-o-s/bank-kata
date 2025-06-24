package org.example.bank.domain;

import org.example.bank.factory.TransactionFactory;
import org.example.bank.util.AmountValidator;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private final List<Transaction> transactions = new ArrayList<>();
    private int balance = 0;
    private final TransactionFactory txFactory;

    // Tests - inherit clock
    public BankAccount(Clock clock) {
        txFactory = new TransactionFactory(clock);
    }

    // Production - system clock
    public BankAccount() {
        this(Clock.systemDefaultZone());
    }

    public void deposit(int amount) {
        AmountValidator.requirePositive(amount);
        balance += amount;
        transactions.add(txFactory.deposit(amount, balance));
    }

    public void withdraw(int amount) {
        AmountValidator.requirePositive(amount);
        balance -= amount;
        transactions.add(txFactory.withdrawal(amount, balance));
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }
}