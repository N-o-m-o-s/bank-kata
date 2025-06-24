package org.example.bank.service;

import org.example.bank.domain.Transaction;

import java.util.List;

public interface StatementPrinter {
    String print(List<Transaction> transactions);
}