package org.example.bank;

import org.example.bank.exception.InvalidAmountException;
import org.example.bank.util.AmountValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AmountValidatorTest {

    @Test
    void requirePositive_zeroOrNegative_shouldThrow() {
        assertThrows(InvalidAmountException.class, () -> AmountValidator.requirePositive(0));
        assertThrows(InvalidAmountException.class, () -> AmountValidator.requirePositive(-5));
    }

    @Test
    void requirePositive_positive_shouldNotThrow() {
        assertDoesNotThrow(() -> AmountValidator.requirePositive(1));
    }
}