package org.example.bank.factory;

import org.example.bank.domain.Transaction;
import org.example.bank.domain.TransactionType;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;

public class TransactionFactory {
    private final Clock clock;

    public TransactionFactory(Clock clock) {
        this.clock = clock;
    }

    public Transaction deposit(BigDecimal amount, BigDecimal balance) {
        return Transaction.of(LocalDate.now(clock), amount, balance, TransactionType.DEPOSIT);
    }

    public Transaction withdrawal(BigDecimal amount, BigDecimal balance) {
        return Transaction.of(LocalDate.now(clock), amount.negate(), balance, TransactionType.WITHDRAWAL);
    }
}