package org.example.bank;

import org.example.bank.domain.BankAccount;
import org.example.bank.domain.Transaction;
import org.example.bank.service.StatementPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;
    private StatementPrinter printer;

    @BeforeEach
    void setUp() {
        account = new BankAccount();
        printer = new StatementPrinter();
    }

    @Test
    void should_deposit_and_update_balance() {
        account.deposit(100);
        assertEquals(1, account.getTransactions().size());
        Transaction t = account.getTransactions().get(0);
        assertEquals(100, t.getAmount());
        assertEquals(100, t.getBalance());
    }

    @Test
    void should_withdraw_and_update_balance() {
        account.deposit(200);
        account.withdraw(50);
        List<Transaction> tx = account.getTransactions();
        assertEquals(2, tx.size());
        assertEquals(-50, tx.get(1).getAmount());
        assertEquals(150, tx.get(1).getBalance());
    }

    @Test
    void should_print_full_statement() {
        account.deposit(100);
        account.withdraw(30);
        String output = printer.print(account.getTransactions());

        String[] lines = output.split("\n");
        assertEquals("DATE | AMOUNT | BALANCE", lines[0]);
        assertTrue(lines[1].contains("| +100 |"));
        assertTrue(lines[2].contains("| -30 |"));
        assertTrue(lines[1].contains("| 100"));
        assertTrue(lines[2].contains("| 70"));
    }

    @Test
    void deposit_non_positive_should_throw() {
        assertThrows(IllegalArgumentException.class,
                () -> account.deposit(0));
    }

    @Test
    void withdraw_non_positive_should_throw() {
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(-10));
    }
}