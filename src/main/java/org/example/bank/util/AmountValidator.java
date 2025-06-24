package org.example.bank.util;

import org.example.bank.exception.InvalidAmountException;

import java.math.BigDecimal;

public final class AmountValidator {
    public static void requirePositive(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be > 0");
        }
    }
}