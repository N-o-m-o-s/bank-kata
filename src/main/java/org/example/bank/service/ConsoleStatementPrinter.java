package org.example.bank.service;

import org.example.bank.domain.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleStatementPrinter implements StatementPrinter {
    private static final String HEADER = "DATE | AMOUNT | BALANCE";

    public String print(List<Transaction> transactions) {
        String body = transactions.stream()
                .map(Transaction::toString)
                .collect(Collectors.joining("\n"));
        return HEADER + "\n" + body;
    }
}