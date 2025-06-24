package org.example.bank;

import org.example.bank.domain.Transaction;
import org.example.bank.factory.TransactionFactory;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionTest {

    @Test
    void ofDeposit_shouldSetPositiveAmountAndCorrectDateAndBalance() {
        Clock fixed = Clock.fixed(Instant.parse("2025-06-01T00:00:00Z"), ZoneId.of("UTC"));
        TransactionFactory factory = new TransactionFactory(fixed);

        Transaction tx = factory.deposit(123, 500);
        assertEquals(LocalDate.of(2025, 6, 1), tx.getDate());
        assertEquals(123, tx.getAmount());
        assertEquals(500, tx.getBalance());
    }

    @Test
    void ofWithdrawal_shouldSetNegativeAmount() {
        Clock fixed = Clock.fixed(Instant.parse("2025-06-02T00:00:00Z"), ZoneId.of("UTC"));
        TransactionFactory factory = new TransactionFactory(fixed);

        Transaction tx = factory.withdrawal(50, 450);
        assertEquals(LocalDate.of(2025, 6, 2), tx.getDate());
        assertTrue(tx.getAmount() < 0);
        assertEquals(450, tx.getBalance());
    }
}
