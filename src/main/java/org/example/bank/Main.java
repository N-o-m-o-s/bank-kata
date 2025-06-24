package org.example.bank;

import org.example.bank.domain.BankAccount;
import org.example.bank.service.ConsoleStatementPrinter;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(); // uses system clock

        account.deposit(new BigDecimal("100.00"));
        account.withdraw(new BigDecimal("30.50"));

        String statement = new ConsoleStatementPrinter()
                .print(account.getTransactions());

        System.out.println(statement);
    }
}