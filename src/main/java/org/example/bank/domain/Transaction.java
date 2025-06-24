package org.example.bank.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Transaction {
    private final LocalDate date;
    private final BigDecimal amount;
    private final BigDecimal balance;
    private final TransactionType type;

    private Transaction(LocalDate date, BigDecimal amount, BigDecimal balance, TransactionType type) {
        this.date = date;
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.balance = balance.setScale(2, RoundingMode.HALF_UP);
        this.type = type;
    }

    public static Transaction of(LocalDate date, BigDecimal amount, BigDecimal balance, TransactionType type) {
        return new Transaction(date, amount, balance, type);
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        // format "YYYY-MM-DD | +100 | 100"
        String sign = amount.compareTo(BigDecimal.ZERO) >= 0 ? "+" : "";
        return String.format("%s | %s | %s%s | %s",
                getDate(),
                getType(),
                sign, getAmount().toPlainString(),
                getBalance().toPlainString()
        );
    }
}