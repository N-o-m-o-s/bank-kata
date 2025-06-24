package org.example.bank.util;

import org.example.bank.exception.InvalidAmountException;

public final class AmountValidator {
    public static void requirePositive(int amount) {
        if (amount <= 0) throw new InvalidAmountException("Amount must be > 0");
    }
}