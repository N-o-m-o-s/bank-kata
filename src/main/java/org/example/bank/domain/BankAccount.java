package org.example.bank.domain;

import org.example.bank.factory.TransactionFactory;
import org.example.bank.util.AmountValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private final List<Transaction> transactions = new ArrayList<>();
    private BigDecimal balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    private final TransactionFactory txFactory;

    // Tests - inherit clock
    public BankAccount(Clock clock) {
        txFactory = new TransactionFactory(clock);
    }

    // Production - system clock
    public BankAccount() {
        this(Clock.systemDefaultZone());
    }

    public void deposit(BigDecimal amount) {
        AmountValidator.requirePositive(amount);
        balance = balance.add(amount).setScale(2, RoundingMode.HALF_UP);
        transactions.add(txFactory.deposit(amount, balance));
    }

    public void withdraw(BigDecimal amount) {
        AmountValidator.requirePositive(amount);
        balance = balance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
        transactions.add(txFactory.withdrawal(amount, balance));
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }
}