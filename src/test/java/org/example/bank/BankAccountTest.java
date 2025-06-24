package org.example.bank;

import org.example.bank.domain.BankAccount;
import org.example.bank.domain.Transaction;
import org.example.bank.domain.TransactionType;
import org.example.bank.exception.InvalidAmountException;
import org.example.bank.service.ConsoleStatementPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;
    private final Clock fixedClock = Clock.fixed(
            Instant.parse("2025-06-10T00:00:00Z"),
            ZoneId.of("UTC")
    );

    @BeforeEach
    void setUp() {
        account = new BankAccount(fixedClock);
    }

    @Test
    void deposit_and_withdraw_flow() {
        account.deposit(new BigDecimal("200.00"));
        account.withdraw(new BigDecimal("50.00"));

        List<Transaction> tx = account.getTransactions();
        assertEquals(2, tx.size());

        // First transaction - deposit
        Transaction t1 = tx.get(0);
        assertEquals(TransactionType.DEPOSIT, t1.getType());
        assertEquals(new BigDecimal("200.00"), t1.getAmount());
        assertEquals(new BigDecimal("200.00"), t1.getBalance());

        // Second transaction - withdrawal
        Transaction t2 = tx.get(1);
        assertEquals(TransactionType.WITHDRAWAL, t2.getType());
        assertEquals(new BigDecimal("-50.00"), t2.getAmount());
        assertEquals(new BigDecimal("150.00"), t2.getBalance());
    }

    @Test
    void multiple_deposits_accumulateBalance_withFractions() {
        account.deposit(new BigDecimal("2.23"));
        account.deposit(new BigDecimal("3.77"));
        account.deposit(new BigDecimal("0.10"));

        List<Transaction> tx = account.getTransactions();
        assertEquals(3, tx.size());

        assertEquals(new BigDecimal("2.23"), tx.get(0).getAmount());
        assertEquals(new BigDecimal("2.23"), tx.get(0).getBalance());

        assertEquals(new BigDecimal("3.77"), tx.get(1).getAmount());
        assertEquals(new BigDecimal("6.00"), tx.get(1).getBalance());  // 2.23 + 3.77 = 6.00

        assertEquals(new BigDecimal("0.10"), tx.get(2).getAmount());
        assertEquals(new BigDecimal("6.10"), tx.get(2).getBalance());
    }

    @Test
    void transactions_order_isPreserved() {
        account.deposit(new BigDecimal("10.00"));
        account.withdraw(new BigDecimal("5.00"));
        account.deposit(new BigDecimal("20.00"));

        List<Transaction> tx = account.getTransactions();
        List<BigDecimal> amounts = tx.stream()
                .map(Transaction::getAmount)
                .toList();

        assertEquals(List.of(
                new BigDecimal("10.00"),
                new BigDecimal("-5.00"),
                new BigDecimal("20.00")
        ), amounts);
    }

    @Test
    void getTransactions_returnsImmutableList() {
        account.deposit(new BigDecimal("10.00"));
        List<Transaction> tx = account.getTransactions();

        assertThrows(UnsupportedOperationException.class,
                () -> tx.add(Transaction.of(
                        LocalDate.now(),
                        new BigDecimal("1.00"),
                        new BigDecimal("11.00"),
                        TransactionType.DEPOSIT)));

        // Check that collection inside was not changed
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    void deposit_zeroOrNegative_shouldThrowInvalidAmountException() {
        assertThrows(InvalidAmountException.class,
                () -> account.deposit(new BigDecimal("0.00")));
        assertThrows(InvalidAmountException.class,
                () -> account.deposit(new BigDecimal("-0.01")));
    }

    @Test
    void withdraw_zeroOrNegative_shouldThrowInvalidAmountException() {
        assertThrows(InvalidAmountException.class,
                () -> account.withdraw(new BigDecimal("0.00")));
        assertThrows(InvalidAmountException.class,
                () -> account.withdraw(new BigDecimal("-5.00")));
    }

    @Test
    void defaultConstructor_usesSystemClock() {
        BankAccount sys = new BankAccount();
        sys.deposit(new BigDecimal("1.23"));
        LocalDate today = LocalDate.now();
        assertEquals(today, sys.getTransactions().get(0).getDate());
    }

    @Test
    void statementPrinter_integrationTest_includesTypeAndScale() {
        account.deposit(new BigDecimal("100.00"));
        account.withdraw(new BigDecimal("30.50"));

        String[] lines = new ConsoleStatementPrinter()
                .print(account.getTransactions())
                .split("\n");

        assertEquals("DATE | TYPE | AMOUNT | BALANCE", lines[0]);
        assertTrue(lines[1].contains("DEPOSIT"));
        assertTrue(lines[1].contains("+100.00"));
        assertTrue(lines[2].contains("WITHDRAWAL"));
        assertTrue(lines[2].contains("-30.50"));
        assertTrue(lines[2].contains("69.50"));  // 100.00 - 30.50 = 69.50
    }
}