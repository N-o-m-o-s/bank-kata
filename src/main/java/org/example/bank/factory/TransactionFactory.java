package org.example.bank.factory;

import org.example.bank.domain.Transaction;

import java.time.Clock;
import java.time.LocalDate;

public class TransactionFactory {
    private final Clock clock;

    public TransactionFactory(Clock clock) {
        this.clock = clock;
    }

    public Transaction deposit(int amount, int balance) {
        return Transaction.of(LocalDate.now(clock), amount, balance);
    }

    public Transaction withdrawal(int amount, int balance) {
        return Transaction.of(LocalDate.now(clock), -amount, balance);
    }
}