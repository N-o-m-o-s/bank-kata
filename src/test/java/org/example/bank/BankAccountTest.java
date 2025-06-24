package org.example.bank;

import org.example.bank.domain.BankAccount;
import org.example.bank.domain.Transaction;
import org.example.bank.exception.InvalidAmountException;
import org.example.bank.service.ConsoleStatementPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        Clock fixedClock = Clock.fixed(Instant.parse("2025-06-10T00:00:00Z"), ZoneId.of("UTC"));
        account = new BankAccount(fixedClock);
    }

    @Test
    void deposit_and_withdraw_flow() {
        account.deposit(200);
        account.withdraw(50);
        List<Transaction> tx = account.getTransactions();

        assertEquals(2, tx.size());
        assertEquals(200, tx.get(0).getAmount());
        assertEquals(150, tx.get(1).getBalance());
    }

    @Test
    void multiple_deposits_accumulateBalance() {
        account.deposit(50);
        account.deposit(70);
        account.deposit(30);
        List<Transaction> tx = account.getTransactions();

        assertEquals(3, tx.size());
        assertEquals(50, tx.get(0).getAmount());
        assertEquals(120, tx.get(1).getBalance());
        assertEquals(150, tx.get(2).getBalance());
    }

    @Test
    void transactions_order_isPreserved() {
        account.deposit(10);
        account.withdraw(5);
        account.deposit(20);
        List<Transaction> tx = account.getTransactions();

        assertEquals(List.of(10, -5, 20),
                tx.stream().map(Transaction::getAmount).toList());
    }

    @Test
    void getTransactions_returnsImmutableList() {
        account.deposit(10);
        List<Transaction> tx = account.getTransactions();
        assertThrows(UnsupportedOperationException.class,
                () -> tx.add(Transaction.of(LocalDate.now(), 5, 15)));
        // Check that collection inside was not changed
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    void defaultConstructor_usesSystemClock() {
        BankAccount systemAccount = new BankAccount();
        systemAccount.deposit(1);
        LocalDate today = LocalDate.now();
        assertEquals(today, systemAccount.getTransactions().get(0).getDate());
    }

    @Test
    void statementPrinter_integrationTest() {
        account.deposit(100);
        account.withdraw(30);
        String[] lines = new ConsoleStatementPrinter()
                .print(account.getTransactions())
                .split("\n");

        assertEquals("DATE | AMOUNT | BALANCE", lines[0]);
        assertTrue(lines[1].contains("| +100 |"));
        assertTrue(lines[1].contains("| 100"));
        assertTrue(lines[2].contains("| -30 |"));
        assertTrue(lines[2].contains("| 70"));
    }

    @Test
    void deposit_zero_shouldThrowInvalidAmountException() {
        assertThrows(InvalidAmountException.class,
                () -> account.deposit(0));
    }

    @Test
    void withdraw_negative_shouldThrowInvalidAmountException() {
        assertThrows(InvalidAmountException.class,
                () -> account.withdraw(-10));
    }
}