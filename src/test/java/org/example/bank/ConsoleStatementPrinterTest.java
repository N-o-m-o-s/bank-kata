package org.example.bank;

import org.example.bank.domain.Transaction;
import org.example.bank.factory.TransactionFactory;
import org.example.bank.service.ConsoleStatementPrinter;
import org.example.bank.service.StatementPrinter;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleStatementPrinterTest {

    @Test
    void print_emptyTransactions_showsOnlyHeader() {
        StatementPrinter printer = new ConsoleStatementPrinter();
        String out = printer.print(List.of());
        assertEquals("DATE | AMOUNT | BALANCE", out.trim());
    }

    @Test
    void print_multipleTransactions_formatsCorrectly() {
        Clock fixed = Clock.fixed(Instant.parse("2025-06-01T00:00:00Z"), ZoneId.of("UTC"));
        TransactionFactory factory = new TransactionFactory(fixed);
        List<Transaction> txs = List.of(
                factory.deposit(100, 100),
                factory.withdrawal(20, 80)
        );

        StatementPrinter printer = new ConsoleStatementPrinter();
        String[] lines = printer.print(txs).split("\n");

        assertEquals("DATE | AMOUNT | BALANCE", lines[0]);
        assertTrue(lines[1].contains("2025-06-01 | +100 | 100"));
        assertTrue(lines[2].contains("2025-06-01 | -20 | 80"));
    }
}
