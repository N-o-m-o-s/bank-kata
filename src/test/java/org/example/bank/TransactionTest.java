package org.example.bank;

import org.example.bank.domain.Transaction;
import org.example.bank.domain.TransactionType;
import org.example.bank.factory.TransactionFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionTest {

    private final Clock fixed = Clock.fixed(
            Instant.parse("2025-06-01T00:00:00Z"), ZoneId.of("UTC")
    );
    private final TransactionFactory factory = new TransactionFactory(fixed);

    @Test
    void ofDeposit_setsCorrectFieldsAndTypeAndScale() {
        Transaction tx = factory.deposit(
                new BigDecimal("2.235"),
                new BigDecimal("10.005")
        );

        assertEquals(LocalDate.of(2025,6,1), tx.getDate());
        assertEquals(TransactionType.DEPOSIT, tx.getType());
        assertEquals(new BigDecimal("2.24"), tx.getAmount());
        assertEquals(new BigDecimal("10.01"), tx.getBalance());
    }

    @Test
    void ofWithdrawal_setsNegativeAmountAndType() {
        Transaction tx = factory.withdrawal(
                new BigDecimal("1.235"),
                new BigDecimal("8.765")
        );

        assertEquals(TransactionType.WITHDRAWAL, tx.getType());
        assertTrue(tx.getAmount().compareTo(BigDecimal.ZERO) < 0);
        assertEquals(new BigDecimal("8.77"), tx.getBalance()); // 8.765 → 8.77
    }

    @Test
    void toString_includesAllColumns() {
        Transaction tx = Transaction.of(
                LocalDate.of(2025, 6, 5),
                new BigDecimal("3.50"),
                new BigDecimal("100.00"),
                TransactionType.DEPOSIT
        );
        String s = tx.toString();
        assertTrue(s.startsWith("2025-06-05 | DEPOSIT | +3.50 | 100.00"));
    }
}
