package org.example.bank.util;

public final class AmountValidator {
    public static void requirePositive(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");
    }
}