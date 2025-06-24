package org.example.bank;

import org.example.bank.domain.Transaction;
import org.example.bank.domain.TransactionType;
import org.example.bank.service.ConsoleStatementPrinter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleStatementPrinterTest {

    @Test
    void print_emptyTransactions_showsOnlyHeader() {
        String out = new ConsoleStatementPrinter()
                .print(List.of());
        assertEquals("DATE | TYPE | AMOUNT | BALANCE", out.trim());
    }

    @Test
    void print_includesTypeAndAmountsWithScale() {
        List<Transaction> txs = List.of(
                Transaction.of(LocalDate.of(2025,6,1),
                        new BigDecimal("10.1"),
                        new BigDecimal("10.10"),
                        TransactionType.DEPOSIT),
                Transaction.of(LocalDate.of(2025,6,1),
                        new BigDecimal("5.555").negate(),
                        new BigDecimal("4.54"),
                        TransactionType.WITHDRAWAL)
        );
        String[] lines = new ConsoleStatementPrinter()
                .print(txs)
                .split("\n");

        assertEquals("DATE | TYPE | AMOUNT | BALANCE", lines[0]);
        assertTrue(lines[1].contains("DEPOSIT | +10.10 | 10.10"));
        assertTrue(lines[2].contains("WITHDRAWAL | -5.56 | 4.54"));
    }
}
